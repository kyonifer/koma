package golem.matrix.jblas

import golem.matrix.*
import golem.matrix.common.*
import golem.matrix.jblas.backend.*
import org.jblas.DoubleMatrix

class JBlasMatrix(var storage: DoubleMatrix) : Matrix<Double> {
    override fun getBaseMatrix() = this.storage

    override fun T() = this.T

    override fun normIndP1(): Double {
        throw UnsupportedOperationException()
    }

    override fun normF(): Double {
        throw UnsupportedOperationException()
    }

    override fun getDoubleData() = this.storage.data
    override fun copy() = JBlasMatrix(DoubleMatrix(this.storage.data))
    override fun epow(other: Double) = JBlasMatrix(this.storage.powElement(other))
    override fun epow(other: Int): Matrix<Double> = JBlasMatrix(this.storage.powElement(other))
    override fun mod(other: Matrix<Double>) = JBlasMatrix(this.storage.mod(castOrBail(other).storage))
    override fun transpose() = JBlasMatrix(this.storage.transpose())
    override fun div(other: Int) = JBlasMatrix(this.storage.div(other.toDouble()))
    override fun div(other: Double) = JBlasMatrix(this.storage.div(other))
    override fun times(other: Matrix<Double>) = JBlasMatrix(this.storage.times(castOrBail(other).storage))
    override fun times(other: Double) = JBlasMatrix(this.storage.mul(other))
    override fun elementTimes(other: Matrix<Double>) = JBlasMatrix(this.storage.mod(castOrBail(other).storage))
    override fun unaryMinus() = this.times(-1.0)
    override fun minus(other: Double) = JBlasMatrix(this.storage.sub(other))
    override fun minus(other: Matrix<Double>) = JBlasMatrix(this.storage.minus(castOrBail(other).storage))
    override fun plus(other: Double) = JBlasMatrix(this.storage.plusElement(other))
    override fun plus(other: Matrix<Double>) = JBlasMatrix(this.storage.plus(castOrBail(other).storage))
    override fun numRows() = this.storage.rows
    override fun numCols() = this.storage.columns

    override fun getDouble(i: Int, j: Int) = this.storage.get(i, j)
    override fun getDouble(i: Int) = this.storage[i]
    override fun setDouble(i: Int, v: Double) {
        this.storage.set(i, v)
    }

    override fun setDouble(i: Int, j: Int, v: Double) {
        this.storage.set(i, j, v)
    }

    override fun set(i: Int, v: Double) {
        this.storage.set(i, v)
    }

    override fun set(i: Int, j: Int, v: Double) {
        this.storage.set(i, j, v)
    }

    override fun get(i: Int, j: Int) = this.storage.get(i, j)
    override fun get(i: Int) = this.storage.get(i)

    override fun getRow(row: Int) = JBlasMatrix(this.storage.getRow(row))
    override fun getCol(col: Int) = JBlasMatrix(this.storage.getColumn(col))

    override fun setCol(index: Int, col: Matrix<Double>) {
        this.storage.putColumn(index, castOrBail(col).storage)
    }

    override fun setRow(index: Int, row: Matrix<Double>) {
        this.storage.putRow(index, castOrBail(row).storage)
    }

    override fun chol() = JBlasMatrix(this.storage.chol())

    override fun LU(): Triple<Matrix<Double>, Matrix<Double>, Matrix<Double>> {
        val raw = this.storage.LU()
        return Triple(JBlasMatrix(raw.p), JBlasMatrix(raw.l), JBlasMatrix(raw.u))
    }

    override fun QR(): Pair<Matrix<Double>, Matrix<Double>> {
        val raw = this.storage.QR()
        return Pair(JBlasMatrix(raw.q), JBlasMatrix(raw.r))
    }

    override fun expm() = JBlasMatrix(this.storage.expm())

    override fun solve(A: Matrix<Double>, B: Matrix<Double>): Matrix<Double> {
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
    override fun argMax() = this.storage.argmax()
    override fun argMin() = this.storage.argmin()

    override fun norm(): Double {
        throw UnsupportedOperationException()
    }

    override fun trace(): Double {
        throw UnsupportedOperationException()
    }

    override fun getFactory() = JBlasMatrixFactory()

    override fun repr(): String {
        throw UnsupportedOperationException()
    }

    /* These methods are defined in order to support fast non-generic calls. However,
       since our type is Double we'll disable them here in case someone accidentally
       uses them.
     */
    override fun getInt(i: Int, j: Int): Int {
        throw UnsupportedOperationException("Implicit cast of Double matrix to Int disabled to prevent subtle bugs. " +
                                            "Please call getDouble and cast manually if this is intentional.")
    }

    override fun getFloat(i: Int, j: Int): Float {
        throw UnsupportedOperationException("Implicit cast of Double matrix to Float disabled to prevent subtle bugs. " +
                                            "Please call getDouble and cast manually if this is intentional.")
    }

    override fun getInt(i: Int): Int {
        throw UnsupportedOperationException("Implicit cast of Double matrix to Int disabled to prevent subtle bugs. " +
                                            "Please call getDouble and cast manually if this is intentional.")
    }

    override fun getFloat(i: Int): Float {
        throw UnsupportedOperationException("Implicit cast of Double matrix to Float disabled to prevent subtle bugs. " +
                                            "Please call getDouble and cast manually if this is intentional.")
    }

    override fun setInt(i: Int, v: Int) {
        throw UnsupportedOperationException("Implicit cast of Double matrix to Int disabled to prevent subtle bugs. " +
                                            "Please call getDouble and cast manually if this is intentional.")
    }

    override fun setFloat(i: Int, v: Float) {
        throw UnsupportedOperationException("Implicit cast of Double matrix to Float disabled to prevent subtle bugs. " +
                                            "Please call getDouble and cast manually if this is intentional.")
    }

    override fun setInt(i: Int, j: Int, v: Int) {
        throw UnsupportedOperationException("Implicit cast of Double matrix to Int disabled to prevent subtle bugs. " +
                                            "Please call getDouble and cast manually if this is intentional.")
    }

    override fun setFloat(i: Int, j: Int, v: Float) {
        throw UnsupportedOperationException("Implicit cast of Double matrix to Float disabled to prevent subtle bugs. " +
                                            "Please call getDouble and cast manually if this is intentional.")
    }


    private fun castOrBail(mat: Matrix<Double>): JBlasMatrix {
        when (mat) {
            is JBlasMatrix -> return mat
            else           -> {
                val base = mat.getBaseMatrix()
                if (base is DoubleMatrix)
                    return JBlasMatrix(base)
                else
                // No friendly backend, need to convert manually
                    throw Exception("Operations between matrices with different backends not yet supported.")

            }
        }

    }

}