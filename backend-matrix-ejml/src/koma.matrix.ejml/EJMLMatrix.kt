package koma.matrix.ejml

import koma.extensions.*
import koma.matrix.*
import koma.matrix.common.*
import koma.matrix.ejml.backend.*
import org.ejml.ops.CommonOps
import org.ejml.simple.SimpleMatrix
import org.ejml.simple.SimpleSVD

/**
 * An implementation of the Matrix<Double> interface using EJML.
 * You should rarely construct this class directly, instead make one via the
 * top-level functions in creators.kt (e.g. zeros(5,5)) or [EJMLMatrixFactory].
 */
class EJMLMatrix(var storage: SimpleMatrix) : DoubleMatrixBase() {
    override fun _getBaseMatrix() = this.storage

    override fun _getDoubleData() = this.storage.matrix.getData()
    override fun _diag() = EJMLMatrix(storage.extractDiag())
    override fun _max() = CommonOps.elementMax(this.storage.matrix)
    override fun _mean() = elementSum() / (numCols() * numRows())
    override fun _min() = CommonOps.elementMin(this.storage.matrix)

    override fun _numRows() = this.storage.numRows()
    override fun _numCols() = this.storage.numCols()
    override fun _times(other: Matrix<Double>) =
            EJMLMatrix(this.storage.times(castOrCopy(other, ::EJMLMatrix, _getFactory()).storage))
    override fun _times(other: Double) = EJMLMatrix(this.storage.times(other))
    override fun _elementTimes(other: Matrix<Double>) =
            EJMLMatrix(this.storage.elementMult(castOrCopy(other, ::EJMLMatrix, _getFactory()).storage))
    override fun _unaryMinus() = EJMLMatrix(this.storage.unaryMinus())
    override fun _minus(other: Double) = EJMLMatrix(this.storage.minus(other))
    override fun _minus(other: Matrix<Double>) =
            EJMLMatrix(this.storage.minus(castOrCopy(other, ::EJMLMatrix, _getFactory()).storage))
    override fun _div(other: Int) = EJMLMatrix(this.storage.div(other))
    override fun _div(other: Double) = EJMLMatrix(this.storage.div(other))
    override fun _transpose() = EJMLMatrix(this.storage.transpose())
    override fun _copy() = EJMLMatrix(this.storage.copy())
    override fun _setDouble(i: Int, v: Double): Unit = this.storage.set(i, v)
    override fun _setDouble(i: Int, j: Int, v: Double) = this.storage.set(i, j, v)
    override fun _getDouble(i: Int, j: Int) = this.storage.get(i, j)
    override fun _getDouble(i: Int) = this.storage.get(i)
    override fun _getRow(row: Int) = EJMLMatrix(SimpleMatrix(CommonOps.extractRow(this.storage.matrix, row, null)))
    override fun _getCol(col: Int) = EJMLMatrix(SimpleMatrix(CommonOps.extractColumn(this.storage.matrix, col, null)))
    override fun _plus(other: Matrix<Double>) =
            EJMLMatrix(this.storage.plus(castOrCopy(other, ::EJMLMatrix, _getFactory()).storage))
    override fun _plus(other: Double) = EJMLMatrix(this.storage.plus(other))
    override fun _chol(): EJMLMatrix {
        val decomp = this.storage.chol()
        // Copy required to prevent decompose implementations distorting the input matrix
        if (decomp.decompose(this.storage.matrix.copy()))
            return EJMLMatrix(SimpleMatrix(decomp.getT(null)))
        else
            throw IllegalStateException("chol decomposition failed (is the matrix full rank?)")
    }

    override fun _inv() = EJMLMatrix(this.storage.inv())
    override fun _det() = this.storage.determinant()
    override fun _pinv() = EJMLMatrix(this.storage.pseudoInverse())
    override fun _normF() = this.storage.normF()
    override fun _normIndP1() = org.ejml.ops.NormOps.inducedP1(this.storage.matrix)
    override fun _elementSum() = this.storage.elementSum()
    override fun _trace() = this.storage.trace()
    override fun _epow(other: Double) = EJMLMatrix(this.storage.elementPower(other))
    override fun _epow(other: Int) = EJMLMatrix(this.storage.elementPower(other.toDouble()))


    override fun _setCol(index: Int, col: Matrix<Double>) {
        for (i in 0..col.numRows() - 1)
            this[i, index] = col[i]
    }

    override fun _setRow(index: Int, row: Matrix<Double>) {
        for (i in 0..row.numCols() - 1)
            this[index, i] = row[i]
    }

    override fun _getFactory() = factoryInstance

    override fun _solve(other: Matrix<Double>): EJMLMatrix {
        val out = this._getFactory().zeros(this.numCols(), 1)
        CommonOps.solve(this.storage.matrix,
                        castOrCopy(other, ::EJMLMatrix, _getFactory()).storage.matrix,
                        out.storage.matrix)
        return out
    }

    override fun _LU(): Triple<EJMLMatrix, EJMLMatrix, EJMLMatrix> {
        val decomp = this.storage.LU()
        return Triple(EJMLMatrix(SimpleMatrix(decomp.getPivot(null))),
                      EJMLMatrix(SimpleMatrix(decomp.getLower(null))),
                      EJMLMatrix(SimpleMatrix(decomp.getUpper(null))))
    }

    override fun _QR(): Pair<EJMLMatrix, EJMLMatrix> {
        val decomp = this.storage.QR()
        return Pair(EJMLMatrix(SimpleMatrix(decomp.getQ(null, false))),
                    EJMLMatrix(SimpleMatrix(decomp.getR(null, false))))
    }

    override fun _SVD(): Triple<EJMLMatrix, EJMLMatrix, EJMLMatrix> {
        val svd = this.storage.svd()
        return Triple(EJMLMatrix(svd.u), EJMLMatrix(svd.w), EJMLMatrix(svd.v))
    }


}
