package koma.matrix.mtj

import koma.min
import koma.extensions.*
import koma.matrix.Matrix
import koma.matrix.common.DoubleMatrixBase
import koma.matrix.mtj.backend.*
import no.uib.cipr.matrix.DenseMatrix
import no.uib.cipr.matrix.Matrices

/**
 * An implementation of the Matrix<Double> interface using MTJ.
 * You should rarely construct this class directly, instead make one via the
 * top-level functions in creators.kt (e.g. zeros(5,5)) or [MTJMatrixFactory].
 */
class MTJMatrix(var storage: DenseMatrix) : Matrix<Double>, DoubleMatrixBase() {
    override fun getBaseMatrix() = this.storage

    override fun getDoubleData() = this.T.storage.data

    override fun diag(): MTJMatrix {
        return MTJMatrix(this.storage.diag())
    }

    override fun max() = storage.maxBy { it.get() }!!.get()
    override fun mean() = elementSum() / (numCols() * numRows())
    override fun min() = storage.minBy { it.get() }!!.get()
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

    override fun normF() = this.storage.norm(no.uib.cipr.matrix.Matrix.Norm.Frobenius)
    override fun normIndP1() = this.storage.norm(no.uib.cipr.matrix.Matrix.Norm.One)

    override fun numRows() = this.storage.numRows()
    override fun numCols() = this.storage.numColumns()
    override fun times(other: Matrix<Double>) =
            MTJMatrix(this.storage.times(castOrCopy(other, ::MTJMatrix, getFactory()).storage))
    override fun times(other: Double) = MTJMatrix(this.storage.times(other))
    override fun elementTimes(other: Matrix<Double>) =
            MTJMatrix(this.storage.rem(castOrCopy(other, ::MTJMatrix, getFactory()).storage))
    override fun unaryMinus() = MTJMatrix(this.storage.unaryMinus())
    override fun minus(other: Double) = MTJMatrix(this.storage.minusElement(other))
    override fun minus(other: Matrix<Double>) =
            MTJMatrix(this.storage.minus(castOrCopy(other, ::MTJMatrix, getFactory()).storage))
    override fun div(other: Int) = MTJMatrix(this.storage.div(other))
    override fun div(other: Double) = MTJMatrix(this.storage.div(other))
    override fun transpose(): MTJMatrix {
        val out = DenseMatrix(this.numCols(), numRows())
        return MTJMatrix(DenseMatrix(this.storage.transpose(out)))
    }

    override fun copy() = MTJMatrix(this.storage.copy())
    override fun setDouble(i: Int, v: Double): Unit = this.storage.set(i, v)
    override fun setDouble(i: Int, j: Int, v: Double) = this.storage.set(i, j, v)
    override fun getDouble(i: Int, j: Int) = this.storage.get(i, j)
    override fun getDouble(i: Int) = this.storage[i]
    override fun getRow(row: Int): MTJMatrix {
        val out = DenseMatrix(1, this.numCols())
        for (col in 0 until this.numCols())
            out.set(0, col, this[row, col])
        return MTJMatrix(out)
    }

    override fun getCol(col: Int) = MTJMatrix(DenseMatrix(Matrices.getColumn(this.storage, col)))
    override fun plus(other: Matrix<Double>) =
            MTJMatrix(this.storage.plusMatrix(castOrCopy(other, ::MTJMatrix, getFactory()).storage))
    override fun plus(other: Double) = MTJMatrix(this.storage.plusElement(other))
    override fun chol() = MTJMatrix(DenseMatrix(this.storage.chol()))
    override fun inv() = MTJMatrix(this.storage.inv())
    override fun pinv() = throw UnsupportedOperationException()//= EJMLMatrix(this.storage.pseudoInverse())
    override fun elementSum() = storage.sumByDouble { it.get() }
    override fun trace() = throw UnsupportedOperationException() //= this.storage.trace()
    override fun epow(other: Double) = MTJMatrix(this.storage.powElement(other))
    override fun epow(other: Int) = MTJMatrix(this.storage.powElement(other))
    override fun det(): Double {
        return this.storage.det()
    }


    override fun setCol(index: Int, col: Matrix<Double>) {
        for (i in 0..col.numRows() - 1)
            this[i, index] = col[i]
    }

    override fun setRow(index: Int, row: Matrix<Double>) {
        for (i in 0..row.numCols() - 1)
            this[index, i] = row[i]
    }

    override fun getFactory() = koma.matrix.mtj.backend.factoryInstance

    override fun T() = this.T

    override val T: MTJMatrix
        get() = this.transpose()

    override fun solve(other: Matrix<Double>): MTJMatrix {
        val out = this.getFactory().zeros(this.numCols(), 1)
        this.storage.solve(castOrCopy(other, ::MTJMatrix, getFactory()).storage, out.storage)
        return out
    }

    override fun LU(): Triple<MTJMatrix, MTJMatrix, MTJMatrix> {
        val (p, L, U) = this.storage.LU()
        return Triple(MTJMatrix(p), MTJMatrix(L), MTJMatrix(U))
    }

    override fun QR(): Pair<MTJMatrix, MTJMatrix> {
        val (Q, R) = this.storage.QR()
        return Pair(MTJMatrix(Q), MTJMatrix(R))
    }

    override fun SVD(): Triple<Matrix<Double>, Matrix<Double>, Matrix<Double>> {
        val svd = this.storage.svd()
        val sArray = svd.s
        val sMat = this.getFactory().zeros(this.numRows(), this.numCols())
        // Copy values from SVD.s
        for(i in 0 until min(this.numRows(), this.numCols())) {
            sMat[i, i] = sArray[i]
        }
        return Triple(MTJMatrix(svd.u), sMat, MTJMatrix(svd.vt).transpose())
    }

}
