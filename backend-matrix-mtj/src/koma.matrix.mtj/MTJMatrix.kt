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
class MTJMatrix(var storage: DenseMatrix) : DoubleMatrixBase() {
    override fun _getBaseMatrix() = this.storage

    override fun _getDoubleData() = this._transpose().storage.data

    override fun _diag(): MTJMatrix {
        return MTJMatrix(this.storage.diag())
    }

    override fun _max() = storage.maxBy { it.get() }!!.get()
    override fun _mean() = elementSum() / (numCols() * numRows())
    override fun _min() = storage.minBy { it.get() }!!.get()
    override fun _argMax(): Int {
        var max = 0
        for (i in 0..this.numCols() * this.numRows() - 1)
            if (this[i] > this[max])
                max = i
        return max
    }

    override fun _argMin(): Int {
        var min = 0
        for (i in 0..this.numCols() * this.numRows() - 1)
            if (this[i] < this[min])
                min = i
        return min
    }

    override fun _normF() = this.storage.norm(no.uib.cipr.matrix.Matrix.Norm.Frobenius)
    override fun _normIndP1() = this.storage.norm(no.uib.cipr.matrix.Matrix.Norm.One)

    override fun _numRows() = this.storage.numRows()
    override fun _numCols() = this.storage.numColumns()
    override fun _times(other: Matrix<Double>) =
            MTJMatrix(this.storage.times(castOrCopy(other, ::MTJMatrix, _getFactory()).storage))
    override fun _times(other: Double) = MTJMatrix(this.storage.times(other))
    override fun _elementTimes(other: Matrix<Double>) =
            MTJMatrix(this.storage.rem(castOrCopy(other, ::MTJMatrix, _getFactory()).storage))
    override fun _unaryMinus() = MTJMatrix(this.storage.unaryMinus())
    override fun _minus(other: Double) = MTJMatrix(this.storage.minusElement(other))
    override fun _minus(other: Matrix<Double>) =
            MTJMatrix(this.storage.minus(castOrCopy(other, ::MTJMatrix, _getFactory()).storage))
    override fun _div(other: Int) = MTJMatrix(this.storage.div(other))
    override fun _div(other: Double) = MTJMatrix(this.storage.div(other))
    override fun _transpose(): MTJMatrix {
        val out = DenseMatrix(this.numCols(), numRows())
        return MTJMatrix(DenseMatrix(this.storage.transpose(out)))
    }

    override fun _copy() = MTJMatrix(this.storage.copy())
    override fun _setDouble(i: Int, v: Double): Unit = this.storage.set(i, v)
    override fun _setDouble(i: Int, j: Int, v: Double) = this.storage.set(i, j, v)
    override fun _getDouble(i: Int, j: Int) = this.storage.get(i, j)
    override fun _getDouble(i: Int) = this.storage[i]
    override fun _getRow(row: Int): MTJMatrix {
        val out = DenseMatrix(1, this.numCols())
        for (col in 0 until this.numCols())
            out.set(0, col, this[row, col])
        return MTJMatrix(out)
    }

    override fun _getCol(col: Int) = MTJMatrix(DenseMatrix(Matrices.getColumn(this.storage, col)))
    override fun _plus(other: Matrix<Double>) =
            MTJMatrix(this.storage.plusMatrix(castOrCopy(other, ::MTJMatrix, _getFactory()).storage))
    override fun _plus(other: Double) = MTJMatrix(this.storage.plusElement(other))
    override fun _chol() = MTJMatrix(DenseMatrix(this.storage.chol()))
    override fun _inv() = MTJMatrix(this.storage.inv())
    override fun _pinv() = throw UnsupportedOperationException()//= EJMLMatrix(this.storage.pseudoInverse())
    override fun _elementSum() = storage.sumByDouble { it.get() }
    override fun _trace() = throw UnsupportedOperationException() //= this.storage.trace()
    override fun _epow(other: Double) = MTJMatrix(this.storage.powElement(other))
    override fun _epow(other: Int) = MTJMatrix(this.storage.powElement(other))
    override fun _det(): Double {
        return this.storage.det()
    }


    override fun _setCol(index: Int, col: Matrix<Double>) {
        for (i in 0..col.numRows() - 1)
            this[i, index] = col[i]
    }

    override fun _setRow(index: Int, row: Matrix<Double>) {
        for (i in 0..row.numCols() - 1)
            this[index, i] = row[i]
    }

    override fun _getFactory() = koma.matrix.mtj.backend.factoryInstance

    override fun _solve(other: Matrix<Double>): MTJMatrix {
        val out = this._getFactory().zeros(this.numCols(), 1)
        this.storage.solve(castOrCopy(other, ::MTJMatrix, _getFactory()).storage, out.storage)
        return out
    }

    override fun _LU(): Triple<MTJMatrix, MTJMatrix, MTJMatrix> {
        val (p, L, U) = this.storage.LU()
        return Triple(MTJMatrix(p), MTJMatrix(L), MTJMatrix(U))
    }

    override fun _QR(): Pair<MTJMatrix, MTJMatrix> {
        val (Q, R) = this.storage.QR()
        return Pair(MTJMatrix(Q), MTJMatrix(R))
    }

    override fun _SVD(): Triple<Matrix<Double>, Matrix<Double>, Matrix<Double>> {
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
