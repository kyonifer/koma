package koma.matrix.jblas

import koma.matrix.*
import koma.matrix.common.*
import koma.matrix.jblas.backend.*
import koma.extensions.*
import org.jblas.DoubleMatrix
import org.jblas.Singular

/**
 * An implementation of the Matrix<Double> interface using jBlas.
 * You should rarely construct this class directly, instead make one via the
 * top-level functions in creators.kt (e.g. zeros(5,5)) or [JBlasMatrixFactory].
 */

class JBlasMatrix(var storage: DoubleMatrix) : DoubleMatrixBase() {
    override fun _getBaseMatrix() = this.storage

    override fun _normIndP1(): Double {
        throw UnsupportedOperationException()
    }

    override fun _normF(): Double {
        throw UnsupportedOperationException()
    }

    override fun _getDoubleData() = this.storage.data
    override fun _copy() = JBlasMatrix(this.storage.dup())
    override fun _epow(other: Double) = JBlasMatrix(this.storage.powElement(other))
    override fun _epow(other: Int): Matrix<Double> = JBlasMatrix(this.storage.powElement(other))
    override fun _transpose() = JBlasMatrix(this.storage.transpose())
    override fun _div(other: Int) = JBlasMatrix(this.storage.div(other.toDouble()))
    override fun _div(other: Double) = JBlasMatrix(this.storage.div(other))
    override fun _times(other: Matrix<Double>) =
            JBlasMatrix(this.storage.times(castOrCopy(other, ::JBlasMatrix, _getFactory()).storage))
    override fun _times(other: Double) = JBlasMatrix(this.storage.mul(other))
    override fun _elementTimes(other: Matrix<Double>) =
            JBlasMatrix(this.storage.rem(castOrCopy(other, ::JBlasMatrix, _getFactory()).storage))
    override fun _unaryMinus() = this.times(-1.0)
    override fun _minus(other: Double) = JBlasMatrix(this.storage.sub(other))
    override fun _minus(other: Matrix<Double>) =
            JBlasMatrix(this.storage.minus(castOrCopy(other, ::JBlasMatrix, _getFactory()).storage))
    override fun _plus(other: Double) = JBlasMatrix(this.storage.plusElement(other))
    override fun _plus(other: Matrix<Double>) =
            JBlasMatrix(this.storage.plus(castOrCopy(other, ::JBlasMatrix, _getFactory()).storage))
    override fun _numRows() = this.storage.rows
    override fun _numCols() = this.storage.columns

    override fun _setDouble(i: Int, v: Double) {
        this.storage[rowToColMajor(i)] = v
    }

    override fun _setDouble(i: Int, j: Int, v: Double) {
        if (i>=this.numRows() || j>=this.numCols())
            throw ArrayIndexOutOfBoundsException("Index into row/col $i/$j out of bounds.")
        this.storage[i, j] = v
    }

    override fun _getDouble(i: Int, j: Int): Double {
        if (i<this.numRows() && j<this.numCols())
            return this.storage.get(i, j)
        throw ArrayIndexOutOfBoundsException("Index into row/col $i/$j out of bounds.")
    }
    override fun _getDouble(i: Int) = this.storage.get(rowToColMajor(i))

    override fun _getRow(row: Int) = JBlasMatrix(this.storage.getRow(row))
    override fun _getCol(col: Int) = JBlasMatrix(this.storage.getColumn(col))

    override fun _setCol(index: Int, col: Matrix<Double>) {
        this.storage.putColumn(index, castOrCopy(col, ::JBlasMatrix, _getFactory()).storage)
    }

    override fun _setRow(index: Int, row: Matrix<Double>) {
        this.storage.putRow(index, castOrCopy(row, ::JBlasMatrix, _getFactory()).storage)
    }

    override fun _chol() = JBlasMatrix(this.storage.chol().T)

    override fun _LU(): Triple<Matrix<Double>, Matrix<Double>, Matrix<Double>> {
        val raw = this.storage.LU()
        return Triple(JBlasMatrix(raw.p), JBlasMatrix(raw.l), JBlasMatrix(raw.u))
    }

    override fun _QR(): Pair<Matrix<Double>, Matrix<Double>> {
        val raw = this.storage.QR()
        return Pair(JBlasMatrix(raw.q), JBlasMatrix(raw.r))
    }

    override fun _SVD(): Triple<Matrix<Double>, Matrix<Double>, Matrix<Double>> {
        val raw = Singular.fullSVD(this.storage)
        val sArray = raw[1]
        val sMatrix = this.getFactory().zeros(raw[0].columns, raw[2].columns) // raw[2] is transposed.
        for(i in 0 until sArray.length) {
		    sMatrix.set(i, i, sArray[i])
        }
        return Triple(JBlasMatrix(raw[0]), sMatrix, JBlasMatrix(raw[2]))
    }

    override fun _expm() = JBlasMatrix(this.storage.expm())

    override fun _solve(other: Matrix<Double>): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun _inv() = JBlasMatrix(this.storage.inv())
    override fun _det() = this.storage.det()

    override fun _pinv(): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun _elementSum() = this.storage.sum()
    override fun _diag() = JBlasMatrix(this.storage.diag())
    override fun _max() = this.storage.max()
    override fun _mean() = this.storage.mean()
    override fun _min() = this.storage.min()
    override fun _argMax() = colToRowMajor(this.storage.argmax())
    override fun _argMin() = this.storage.argmin()

    override fun _trace(): Double {
        throw UnsupportedOperationException()
    }

    override fun _getFactory() = JBlasMatrixFactory()

    /**
     * Gets row-major index from a col-major index.
     */
    private fun colToRowMajor(idx: Int): Int {
        return idx % storage.rows * storage.columns + idx / storage.rows
    }

    /**
     * Gets col-major index from a row-major index.
     */
    private fun rowToColMajor(idx: Int): Int {
        return idx % storage.columns * storage.rows + idx / storage.columns
    }

}