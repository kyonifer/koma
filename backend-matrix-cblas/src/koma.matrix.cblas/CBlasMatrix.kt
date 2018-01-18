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
    
    override fun diag() = TODO()
    override fun max(): Double {
        var max = Double.NEGATIVE_INFINITY
        this.forEach { ele ->
            if (ele > max) {
                max = ele
            }
        }
        return max
    }
    override fun mean() = elementSum() / (numCols() * numRows())
    override fun min(): Double {
        var min = Double.POSITIVE_INFINITY
        this.forEach { ele ->
            if (ele < min) {
                min = ele
            }
        }
        return min
    }
    override fun argMax(): Int {
        var max = 0
        for (i in 0..this.numCols() * this.numRows() - 1)
            if (this[i] > this[max])
                max = i
        return max
    }

    override fun argMin(): Int {
        var min = 0
        for (i in 0..this.numCols() * this.numRows() - 1)
            if (this[i] < this[min])
                min = i
        return min
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
    
    override fun times(other: Double) = map { it*other }

    override fun elementTimes(other: Matrix<Double>)
        = mapIndexed { row, col, ele ->
            ele*other[row, col]
        }
    
    override fun rem(other: Matrix<Double>) = TODO()
    override fun unaryMinus() = this * -1
    override fun minus(other: Double) = map { it - other }
    override fun minus(other: Matrix<Double>) = mapIndexed { row, col, ele -> ele - other[row, col] }
    override fun div(other: Int) = map { it / other }
    override fun div(other: Double) = map { it / other }
    override fun transpose() = mapIndexed { row, col, _ -> this[col, row] }
    override fun copy() = map{it}
    override fun setDouble(i: Int, v: Double): Unit { storage[i] = v }
    override fun setDouble(i: Int, j: Int, v: Double) { storage[i*numCols() + j] = v}
    override fun getDouble(i: Int, j: Int) = storage[i*numCols() + j]
    override fun getDouble(i: Int) = storage[i]
    override fun getRow(row: Int) = zeros(1, numCols()).mapIndexed { _, col, _ ->
        this[row, col]
    }
    override fun getCol(col: Int) = zeros(numRows(), 1).mapIndexed { row, _, _ ->
        this[row, col]
    }
    override fun plus(other: Matrix<Double>) = mapIndexed { row, col, ele -> ele + other[row, col]}
    override fun plus(other: Double) = map{it + other}
    override fun chol(): CBlasMatrix {
        TODO()
    }

    override fun inv() = TODO()
    override fun det() = TODO()
    override fun pinv() = TODO()
    override fun norm() = TODO()
    override fun normF() = TODO()
    override fun normIndP1() = TODO()
    override fun elementSum(): Double {
        var out = 0.0
        this.forEach { ele ->
            out += ele
        }
        return out
    }
    override fun trace() = TODO()
    override fun epow(other: Double) = map { pow(it, other)}
    override fun epow(other: Int) = map { pow(it, other)}


    override fun setCol(index: Int, col: Matrix<Double>) {
        col.forEachIndexed { row, _, ele ->
            this[row, index] = ele
        }
    }

    override fun setRow(index: Int, row: Matrix<Double>) {
        row.forEachIndexed { _, col, ele ->
            this[index, col] = ele
        }
    }

    override fun getFactory() = factoryInstance

    override fun T() = transpose()

    override val T: Matrix<Double>
        get() = transpose()

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
