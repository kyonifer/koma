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

class JBlasMatrix(var storage: DoubleMatrix) : Matrix<Double>, DoubleMatrixBase() {
    override fun getBaseMatrix() = this.storage

    override fun normIndP1(): Double {
        throw UnsupportedOperationException()
    }

    override fun normF(): Double {
        throw UnsupportedOperationException()
    }

    override fun getDoubleData() = this.storage.data
    override fun copy() = JBlasMatrix(this.storage.dup())
    override fun epow(other: Double) = JBlasMatrix(this.storage.powElement(other))
    override fun epow(other: Int): Matrix<Double> = JBlasMatrix(this.storage.powElement(other))
    override fun transpose() = JBlasMatrix(this.storage.transpose())
    override fun div(other: Int) = JBlasMatrix(this.storage.div(other.toDouble()))
    override fun div(other: Double) = JBlasMatrix(this.storage.div(other))
    override fun times(other: Matrix<Double>) =
            JBlasMatrix(this.storage.times(castOrCopy(other, ::JBlasMatrix, getFactory()).storage))
    override fun times(other: Double) = JBlasMatrix(this.storage.mul(other))
    override fun elementTimes(other: Matrix<Double>) =
            JBlasMatrix(this.storage.rem(castOrCopy(other, ::JBlasMatrix, getFactory()).storage))
    override fun unaryMinus() = this.times(-1.0)
    override fun minus(other: Double) = JBlasMatrix(this.storage.sub(other))
    override fun minus(other: Matrix<Double>) =
            JBlasMatrix(this.storage.minus(castOrCopy(other, ::JBlasMatrix, getFactory()).storage))
    override fun plus(other: Double) = JBlasMatrix(this.storage.plusElement(other))
    override fun plus(other: Matrix<Double>) =
            JBlasMatrix(this.storage.plus(castOrCopy(other, ::JBlasMatrix, getFactory()).storage))
    override fun numRows() = this.storage.rows
    override fun numCols() = this.storage.columns

    override fun setDouble(i: Int, v: Double) {
        this.storage[rowToColMajor(i)] = v
    }

    override fun setDouble(i: Int, j: Int, v: Double) {
        if (i>=this.numRows() || j>=this.numCols())
            throw ArrayIndexOutOfBoundsException("Index into row/col $i/$j out of bounds.")
        this.storage[i, j] = v
    }

    override fun getDouble(i: Int, j: Int): Double {
        if (i<this.numRows() && j<this.numCols())
            return this.storage.get(i, j)
        throw ArrayIndexOutOfBoundsException("Index into row/col $i/$j out of bounds.")
    }
    override fun getDouble(i: Int) = this.storage.get(rowToColMajor(i))

    override fun getRow(row: Int) = JBlasMatrix(this.storage.getRow(row))
    override fun getCol(col: Int) = JBlasMatrix(this.storage.getColumn(col))

    override fun setCol(index: Int, col: Matrix<Double>) {
        this.storage.putColumn(index, castOrCopy(col, ::JBlasMatrix, getFactory()).storage)
    }

    override fun setRow(index: Int, row: Matrix<Double>) {
        this.storage.putRow(index, castOrCopy(row, ::JBlasMatrix, getFactory()).storage)
    }

    override fun chol() = JBlasMatrix(this.storage.chol().T)

    override fun LU(): Triple<Matrix<Double>, Matrix<Double>, Matrix<Double>> {
        val raw = this.storage.LU()
        return Triple(JBlasMatrix(raw.p), JBlasMatrix(raw.l), JBlasMatrix(raw.u))
    }

    override fun QR(): Pair<Matrix<Double>, Matrix<Double>> {
        val raw = this.storage.QR()
        return Pair(JBlasMatrix(raw.q), JBlasMatrix(raw.r))
    }

    override fun SVD(): Triple<Matrix<Double>, Matrix<Double>, Matrix<Double>> {
        val raw = Singular.fullSVD(this.storage)
        val sArray = raw[1]
        val sMatrix = this.getFactory().zeros(raw[0].columns, raw[2].columns) // raw[2] is transposed.
        for(i in 0 until sArray.length) {
		    sMatrix.set(i, i, sArray[i])
        }
        return Triple(JBlasMatrix(raw[0]), sMatrix, JBlasMatrix(raw[2]))
    }

    override fun expm() = JBlasMatrix(this.storage.expm())

    override fun solve(other: Matrix<Double>): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun inv() = JBlasMatrix(this.storage.inv())
    override fun det() = this.storage.det()

    override fun pinv(): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun elementSum() = this.storage.sum()
    override fun diag() = JBlasMatrix(this.storage.diag())
    override fun max() = this.storage.max()
    override fun mean() = this.storage.mean()
    override fun min() = this.storage.min()
    override fun argMax() = colToRowMajor(this.storage.argmax())
    override fun argMin() = this.storage.argmin()

    override fun trace(): Double {
        throw UnsupportedOperationException()
    }

    override fun getFactory() = JBlasMatrixFactory()

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