package koma.matrix.cblas

import koma.matrix.Matrix
import koma.matrix.common.DoubleMatrixBase
import koma.matrix.cblas.internal.factoryInstance
import kotlinx.cinterop.*
import koma.*

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
        val out = CBlasMatrix(this.numRows(), other.numCols())
        val innerOther = castOrCopy(other, {it:CBlasMatrix->it}, getFactory()).storage
        val innerThis = this.getBaseMatrix()
        val innerOut = out.getBaseMatrix()

        cblas.cblas_dgemm(Order=cblas.CblasRowMajor, TransA=cblas.CblasNoTrans, TransB=cblas.CblasNoTrans, 
                            M=this.numRows(), N=other.numCols(), K=other.numRows(), 
                            alpha=1.0, 
                            A=innerThis, lda=this.numCols(), 
                            B=innerOther, ldb=other.numCols(), 
                            beta=1.0, 
                            C=innerOut, ldc=out.numCols())
        return out
    }
    

    override fun setDouble(i: Int, v: Double): Unit { storage[i] = v }
    override fun setDouble(i: Int, j: Int, v: Double) { storage[i*numCols() + j] = v}
    override fun getDouble(i: Int, j: Int) = storage[i*numCols() + j]
    override fun getDouble(i: Int) = storage[i]

    override fun chol(): CBlasMatrix {
        TODO()
    }

    override fun diag() = TODO()
    override fun inv() = TODO()
    override fun det() = TODO()
    override fun pinv() = TODO()
    override fun norm() = TODO()
    override fun normF() = TODO()
    override fun normIndP1() = TODO()

    override fun trace() = TODO()

    override fun getFactory() = factoryInstance

    override fun solve(A: Matrix<Double>, B: Matrix<Double>): CBlasMatrix {
        TODO()
    }

    override fun LU(): Triple<CBlasMatrix, CBlasMatrix, CBlasMatrix> {
        TODO()
    }

    override fun QR(): Pair<CBlasMatrix, CBlasMatrix> {
        TODO()
    }

    override fun SVD(): Triple<CBlasMatrix, CBlasMatrix, CBlasMatrix> {
        TODO()
    }
}
