package koma.matrix.cblas

import koma.matrix.Matrix
import koma.matrix.MatrixFactory
import koma.matrix.common.DoubleMatrixBase
import koma.matrix.cblas.internal.factoryInstance
import kotlinx.cinterop.*
import koma.zeros
import koma.extensions.*

/**
 * An implementation of the Matrix<Double> interface using 
 * raw cblas calls.
 * 
 * @param storage A raw `double *` with enough space preallocated to hold\
 *                the matrix. The [CBlasMatrix] takes full control of the 
 *                pointer, including freeing the memory pointed at.
 */
class CBlasMatrix(private val nRows: Int, 
                  private val nCols: Int,
                  private val storage: CArrayPointer<DoubleVar> ): Matrix<Double>, DoubleMatrixBase() {

    constructor(nRows: Int, nCols: Int): this(nRows, nCols, nativeHeap.allocArray<DoubleVar>(nRows*nCols))

     // TODO: Only expose this to CBlasMatrix, not the user, 
     // and add a pinned version for the user

    /* 
     * The return of this could be finalized at any moment, and so should
     * not be used except by functions which still have a reference
     * to the CBlasMatrix using it.
     */ 
    override fun getBaseMatrix() = storage

    protected fun finalize() {
        println("finalize")
        nativeHeap.free(storage)
    }

    override fun getDoubleData()
        = DoubleArray(nRows*nCols).also {
            for (i in 0 until nRows*nCols)
                it[i] = this[i]
        }

    override fun numRows() = this.nRows
    override fun numCols() = this.nCols
    
    override fun times(other: Matrix<Double>): CBlasMatrix {
        if (this.numCols() != other.numRows())
            throw IllegalArgumentException("Cannot multiply matrices: incompatible dimensions")
        val out = CBlasMatrix(this.numRows(), other.numCols())
        val innerOther = castOrCopy(other, {it:CBlasMatrix->it}, getFactory()).storage
        val innerThis = this.getBaseMatrix()
        val innerOut = out.getBaseMatrix()

        // Since named params are unstable in cblas header, 
        // commented here instead of named in call:
        //
        // Order: CBLAS_ORDER, TransA: CBLAS_TRANSPOSE, TransB: CBLAS_TRANSPOSE, 
        // M: blasint, N: blasint, K: blasint, 
        // alpha: Double, 
        // A: CValuesRef<DoubleVar>?, lda: blasint, 
        // B: CValuesRef<DoubleVar>?, ldb: blasint, 
        // beta: Double, 
        // C: CValuesRef<DoubleVar>?, ldc: blasint

        cblas.cblas_dgemm(cblas.CblasRowMajor, cblas.CblasNoTrans, cblas.CblasNoTrans, 
                            this.numRows(), other.numCols(), other.numRows(), 
                            1.0, 
                            innerThis, this.numCols(), 
                            innerOther, other.numCols(), 
                            1.0, 
                            innerOut, out.numCols())
        return out
    }
    

    override fun setDouble(i: Int, v: Double): Unit { storage[i] = v }
    override fun setDouble(i: Int, j: Int, v: Double) { storage[i*numCols() + j] = v}
    override fun getDouble(i: Int, j: Int) = storage[i*numCols() + j]
    override fun getDouble(i: Int) = storage[i]

    override fun chol(): CBlasMatrix {
        val out = getFactory().zeros(numRows(), numCols())
        out.fill { row, col -> 
            if (row >= col)
                this[row, col]
            else
                0.0
        }
        // matrix_layout: Int, uplo: Byte, n: Int, a: CValuesRef<DoubleVar>?, lda: Int
        val res = lapacke.LAPACKE_dpotrf(
                    lapacke.LAPACK_ROW_MAJOR,
                    'L'.toByte(), 
                    this.numRows(),
                    out.storage,
                    this.numCols())
        if (res != 0)
            throw IllegalStateException("chol decomposition failed (is matrix positive semi-definite?)")
        return out
    }

    override fun copy(): CBlasMatrix = getFactory().zeros(numRows(), numCols()).also{it.fill{row, col -> this[row, col]}}
    override fun diag() = TODO()
    override fun inv(): CBlasMatrix {
        val pivLen = kotlin.math.min(numRows(), numCols())
        memScoped {
            val pivot = allocArray<IntVar>(pivLen)
            val out = rawLU(pivot)

            // matrix_layout: Int, n: Int, a: CValuesRef<DoubleVar>?, lda: Int, ipiv: CValuesRef<IntVar>?
            val res = lapacke.LAPACKE_dgetri(
                lapacke.LAPACK_ROW_MAJOR,
                numCols(),
                out.storage,
                numCols(),
                pivot
            )
            if (res != 0)
                throw IllegalStateException("Matrix inversion (dgetri) failed: return code $res")
            return out
        }
    }
    override fun det() = TODO()
    override fun pinv() = TODO()
    override fun normF() = rawNorm(normType='F')
    override fun normIndP1() = rawNorm(normType='1')

    private fun rawNorm(normType: Char): Double {
        // matrix_layout: Int, norm: Byte, m: Int, n: Int, a: CValuesRef<DoubleVar>?, lda: Int
        return lapacke.LAPACKE_dlange(
            lapacke.LAPACK_ROW_MAJOR,
            normType.toByte(),
            numRows(),
            numCols(),
            this.storage,
            numCols()
        )
    }

    override fun trace() = TODO()

    override fun getFactory(): MatrixFactory<CBlasMatrix> = factoryInstance

    override fun solve(other: Matrix<Double>): CBlasMatrix {
        val Ac = this.copy()
        val Bc = castOrCopy(other.copy(), {it:CBlasMatrix->it}, getFactory())
        val pivLen = kotlin.math.min(this.numRows(), other.numCols())
        memScoped {
            val pivot = allocArray<IntVar>(pivLen)
            // matrix_layout: Int, n: Int, nrhs: Int, a: CValuesRef<DoubleVar>?, lda: Int, ipiv: CValuesRef<IntVar>?, b: CValuesRef<DoubleVar>?, ldb: Int
            val res = lapacke.LAPACKE_dgesv(
                lapacke.LAPACK_ROW_MAJOR,
                Ac.numRows(),
                Bc.numCols(),
                Ac.storage,
                Ac.numCols(),
                pivot,
                Bc.storage,
                Bc.numCols()
            )
            if (res != 0)
                throw IllegalStateException("Solve (dgesv) failed: return code $res")
            return Bc
        }
    }

    // Returns pivot via the passed in memory, combined LU in the returned matrix
    // Storage must have min(numRows(), numCols()) space allocated
    private fun rawLU(pivotStorage: CArrayPointer<IntVar>): CBlasMatrix {
        val out: CBlasMatrix = copy()
        // matrix_layout: Int, m: Int, n: Int, a: CValuesRef<DoubleVar>?, lda: Int, ipiv: CValuesRef<IntVar>?
        val res = lapacke.LAPACKE_dgetrf(
            lapacke.LAPACK_ROW_MAJOR,
            numRows(),
            numCols(),
            out.storage,
            numCols(),
            pivotStorage
        )
        if (res != 0) {
            throw IllegalStateException("LU decomposition (dgetrf) failed: return code $res")

        }
        return out
    }

    override fun LU(): Triple<Matrix<Double>, Matrix<Double>, Matrix<Double>> {
        val pivLen = kotlin.math.min(numRows(), numCols())
        memScoped {
            val pivot = allocArray<IntVar>(pivLen)
            val LU = rawLU(pivot)
            val p = zeros(pivLen, 1).also{it.fill{row, col -> pivot[row].toDouble()}}
            val l = LU.mapIndexed{row, col, ele ->
                if (row == col) 
                    1.0
                else if (row > col)
                    ele
                else
                    0.0
            }
            val u = LU.mapIndexed{row, col, ele ->
                if (row > col)
                    0.0
                else
                    ele
            }
            return Triple(p,l,u)
        }

    }

    override fun QR(): Pair<CBlasMatrix, CBlasMatrix> {
        TODO()
    }

    override fun SVD(): Triple<CBlasMatrix, CBlasMatrix, CBlasMatrix> {
        TODO()
    }
}
