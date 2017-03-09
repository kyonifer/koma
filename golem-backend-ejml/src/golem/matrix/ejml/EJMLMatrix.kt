package golem.matrix.ejml

import golem.matrix.*
import golem.matrix.common.*
import golem.matrix.ejml.backend.*
import org.ejml.ops.CommonOps
import org.ejml.simple.SimpleMatrix

/**
 * An implementation of the Matrix<Double> interface using EJML.
 * You should rarely use this class directly, instead use one of the
 * top-level functions in creators.kt (e.g. zeros(5,5)).
 */
class EJMLMatrix(var storage: SimpleMatrix) : Matrix<Double>, DoubleMatrixBase() {
    override fun getBaseMatrix() = this.storage

    override fun getDoubleData() = this.storage.matrix.getData()
    override fun diag() = EJMLMatrix(storage.extractDiag())
    override fun max() = CommonOps.elementMax(this.storage.matrix)
    override fun mean() = elementSum() / (numCols() * numRows())
    override fun min() = CommonOps.elementMin(this.storage.matrix)
    override fun argMax(): Int {
        var max = 0
        for (i in 0..this.numCols() * this.numRows() - 1)
            if (this[i] > this[max])
                max = i
        return max
    }

    override fun argMin(): Int {
        var max = 0
        for (i in 0..this.numCols() * this.numRows() - 1)
            if (this[i] < this[max])
                max = i
        return max
    }

    override fun getDouble(i: Int, j: Int) = this.storage.get(i, j)
    override fun getDouble(i: Int) = this.storage.get(i)
    override fun setDouble(i: Int, v: Double) = this.storage.set(i, v)
    override fun setDouble(i: Int, j: Int, v: Double) = this.storage.set(i, j, v)

    override fun numRows() = this.storage.numRows()
    override fun numCols() = this.storage.numCols()
    override fun times(other: Matrix<Double>) =
            EJMLMatrix(this.storage.times(castOrCopy(other, ::EJMLMatrix, getFactory()).storage))
    override fun times(other: Double) = EJMLMatrix(this.storage.times(other))
    override fun elementTimes(other: Matrix<Double>) =
            EJMLMatrix(this.storage.elementMult(castOrCopy(other, ::EJMLMatrix, getFactory()).storage))
    override fun mod(other: Matrix<Double>) =
            EJMLMatrix(this.storage.mod(castOrCopy(other, ::EJMLMatrix, getFactory()).storage))
    override fun unaryMinus() = EJMLMatrix(this.storage.unaryMinus())
    override fun minus(other: Double) = EJMLMatrix(this.storage.minus(other))
    override fun minus(other: Matrix<Double>) =
            EJMLMatrix(this.storage.minus(castOrCopy(other, ::EJMLMatrix, getFactory()).storage))
    override fun div(other: Int) = EJMLMatrix(this.storage.div(other))
    override fun div(other: Double) = EJMLMatrix(this.storage.div(other))
    override fun transpose() = EJMLMatrix(this.storage.transpose())
    override fun copy() = EJMLMatrix(this.storage.copy())
    override fun set(i: Int, v: Double): Unit = this.storage.set(i, v)
    override fun set(i: Int, j: Int, v: Double) = this.storage.set(i, j, v)
    override fun get(i: Int, j: Int) = this.storage.get(i, j)
    override fun get(i: Int) = this.storage.get(i)
    override fun getRow(row: Int) = EJMLMatrix(SimpleMatrix(CommonOps.extractRow(this.storage.matrix, row, null)))
    override fun getCol(col: Int) = EJMLMatrix(SimpleMatrix(CommonOps.extractColumn(this.storage.matrix, col, null)))
    override fun plus(other: Matrix<Double>) =
            EJMLMatrix(this.storage.plus(castOrCopy(other, ::EJMLMatrix, getFactory()).storage))
    override fun plus(other: Double) = EJMLMatrix(this.storage.plus(other))
    override fun chol(): EJMLMatrix {
        val decomp = this.storage.chol()
        // Copy required to prevent decompose implementations distorting the input matrix
        if (decomp.decompose(this.storage.matrix.copy()))
            return EJMLMatrix(SimpleMatrix(decomp.getT(null)))
        else
            throw Exception("Decomposition failed")
    }

    override fun inv() = EJMLMatrix(this.storage.inv())
    override fun det() = this.storage.determinant()
    override fun pinv() = EJMLMatrix(this.storage.pseudoInverse())
    override fun norm() = normF()
    override fun normF() = this.storage.normF()
    override fun normIndP1() = org.ejml.ops.NormOps.inducedP1(this.storage.matrix)
    override fun elementSum() = this.storage.elementSum()
    override fun trace() = this.storage.trace()
    override fun epow(other: Double) = EJMLMatrix(this.storage.elementPower(other))
    override fun epow(other: Int) = EJMLMatrix(this.storage.elementPower(other.toDouble()))


    override fun setCol(index: Int, col: Matrix<Double>) {
        for (i in 0..col.numRows() - 1)
            this[i, index] = col[i]
    }

    override fun setRow(index: Int, row: Matrix<Double>) {
        for (i in 0..row.numCols() - 1)
            this[index, i] = row[i]
    }

    override fun getFactory() = factoryInstance

    override fun T() = this.T

    override val T: EJMLMatrix
        get() = this.transpose()

    override fun solve(A: Matrix<Double>, B: Matrix<Double>): EJMLMatrix {
        val out = this.getFactory().zeros(A.numCols(), 1)
        CommonOps.solve(castOrCopy(A, ::EJMLMatrix, getFactory()).storage.matrix,
                        castOrCopy(B, ::EJMLMatrix, getFactory()).storage.matrix,
                        out.storage.matrix)
        return out
    }

    override fun LU(): Triple<EJMLMatrix, EJMLMatrix, EJMLMatrix> {
        val decomp = this.storage.LU()
        return Triple(EJMLMatrix(SimpleMatrix(decomp.getPivot(null))),
                      EJMLMatrix(SimpleMatrix(decomp.getLower(null))),
                      EJMLMatrix(SimpleMatrix(decomp.getUpper(null))))
    }

    override fun QR(): Pair<EJMLMatrix, EJMLMatrix> {
        val decomp = this.storage.QR()
        return Pair(EJMLMatrix(SimpleMatrix(decomp.getQ(null, false))),
                    EJMLMatrix(SimpleMatrix(decomp.getR(null, false))))
    }


    override fun toString() = this.repr()


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


}
