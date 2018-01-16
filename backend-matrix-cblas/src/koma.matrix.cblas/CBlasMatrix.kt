package koma.matrix.cblas

import koma.matrix.Matrix
import koma.matrix.common.DoubleMatrixBase
import kotlinx.cinterop.*
import koma.get

/**
 * An implementation of the Matrix<Double> interface using 
 * raw cblas calls.
 * 
 * @param storage A raw `double *` with enough space preallocated to hold\
 *                the matrix. The [CBlasMatrix] takes full control of the 
 *                pointer, including freeing the memory pointed at.
 */

class CBlasMatrix(val storage: CArrayPointer<DoubleVar>,
                  private val nRows: Int, private val nCols: Int): Matrix<Double>, DoubleMatrixBase() {

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


    override fun getDoubleData() = TODO()
    override fun diag() = TODO()
    override fun max() = TODO()
    override fun mean() = TODO()
    override fun min() = TODO()
    override fun argMax(): Int {
        TODO()
    }

    override fun argMin(): Int {
        TODO()
    }

    override fun numRows() = this.nRows
    override fun numCols() = this.nCols
    
    override fun times(other: Matrix<Double>): CBlasMatrix {
        val out = CBlasMatrix(nativeHeap.allocArray<DoubleVar>(25), this.numRows(), other.numCols())
        val innerOther = when(other) {
            is CBlasMatrix -> {
                other.getBaseMatrix()
            }
            else -> { TODO() }
        }
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
    
    override fun times(other: Double) = TODO()
    override fun elementTimes(other: Matrix<Double>) = TODO()
    override fun rem(other: Matrix<Double>) = TODO()
    override fun unaryMinus() = TODO()
    override fun minus(other: Double) = TODO()
    override fun minus(other: Matrix<Double>) = TODO()
    override fun div(other: Int) = TODO()
    override fun div(other: Double) = TODO()
    override fun transpose() = TODO()
    override fun copy() = TODO()
    override fun setDouble(i: Int, v: Double): Unit { storage[i] = v }
    override fun setDouble(i: Int, j: Int, v: Double) = TODO()
    override fun getDouble(i: Int, j: Int) = TODO()
    override fun getDouble(i: Int) = storage[i]
    override fun getRow(row: Int) = TODO()
    override fun getCol(col: Int) = TODO()
    override fun plus(other: Matrix<Double>) = TODO()
    override fun plus(other: Double) = TODO()
    override fun chol(): CBlasMatrix {
        TODO()
    }

    override fun inv() = TODO()
    override fun det() = TODO()
    override fun pinv() = TODO()
    override fun norm() = TODO()
    override fun normF() = TODO()
    override fun normIndP1() = TODO()
    override fun elementSum() = TODO()
    override fun trace() = TODO()
    override fun epow(other: Double) = TODO()
    override fun epow(other: Int) = TODO()


    override fun setCol(index: Int, col: Matrix<Double>) {
        TODO()
    }

    override fun setRow(index: Int, row: Matrix<Double>) {
        TODO()
    }

    override fun getFactory() = TODO()

    override fun T() = TODO()

    override val T: CBlasMatrix
        get() = TODO()

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

    override fun toString() = TODO()
}
