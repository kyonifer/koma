package koma.matrix.ejml

import koma.matrix.*
import koma.matrix.common.*
import koma.matrix.ejml.backend.*
import org.ejml.data.SingularMatrixException
import org.ejml.dense.row.CommonOps_DDRM
import org.ejml.dense.row.NormOps_DDRM
import org.ejml.simple.SimpleMatrix
import org.ejml.simple.SimpleSVD


/**
 * An implementation of the Matrix<Double> interface using EJML.
 * You should rarely construct this class directly, instead make one via the
 * top-level functions in creators.kt (e.g. zeros(5,5)) or [EJMLMatrixFactory].
 */
class EJMLMatrix(var storage: SimpleMatrix) : Matrix<Double>, DoubleMatrixBase() {
    override fun getBaseMatrix() = this.storage

    override fun getDoubleData() = this.storage.ddrm.getData()
    override fun diag() = EJMLMatrix(storage.diag())
    override fun maxInternal() = CommonOps_DDRM.elementMax(this.storage.ddrm)
    override fun mean() = elementSum() / (numCols() * numRows())
    override fun minInternal() = CommonOps_DDRM.elementMin(this.storage.ddrm)

    override fun numRows() = this.storage.numRows()
    override fun numCols() = this.storage.numCols()
    override fun times(other: Matrix<Double>) =
            EJMLMatrix(this.storage.times(castOrCopy(other, ::EJMLMatrix, getFactory()).storage))
    override fun times(other: Double) = EJMLMatrix(this.storage.times(other))
    override fun elementTimes(other: Matrix<Double>) =
            EJMLMatrix(this.storage.elementMult(castOrCopy(other, ::EJMLMatrix, getFactory()).storage))
    override fun unaryMinus() = EJMLMatrix(this.storage.unaryMinus())
    override fun minus(other: Double) = EJMLMatrix(this.storage.minus(other))
    override fun minus(other: Matrix<Double>) =
            EJMLMatrix(this.storage.minus(castOrCopy(other, ::EJMLMatrix, getFactory()).storage))
    override fun div(other: Int) = EJMLMatrix(this.storage.div(other))
    override fun div(other: Double) = EJMLMatrix(this.storage.div(other))
    override fun transpose() = EJMLMatrix(this.storage.transpose())
    override fun copy() = EJMLMatrix(this.storage.copy())
    override fun setDouble(i: Int, v: Double): Unit = this.storage.set(i, v)
    override fun setDouble(i: Int, j: Int, v: Double) = this.storage.set(i, j, v)
    override fun getDouble(i: Int, j: Int) = this.storage.get(i, j)
    override fun getDouble(i: Int) = this.storage.get(i)
    override fun getRow(row: Int) = EJMLMatrix(SimpleMatrix(this.storage.extractVector(true, row)))
    override fun getCol(col: Int) = EJMLMatrix(SimpleMatrix(this.storage.extractVector(false, col)))
    override fun plus(other: Matrix<Double>) =
            EJMLMatrix(this.storage.plus(castOrCopy(other, ::EJMLMatrix, getFactory()).storage))
    override fun plus(other: Double) = EJMLMatrix(this.storage.plus(other))
    override fun chol(): EJMLMatrix {
        val decomp = this.storage.chol()
        // Copy required to prevent decompose implementations distorting the input matrix
        if (decomp.decompose(this.storage.ddrm.copy()))
            return EJMLMatrix(SimpleMatrix(decomp.getT(null)))
        else
            throw IllegalStateException("chol decomposition failed (is the matrix full rank?)")
    }

    override fun inv() = EJMLMatrix(this.storage.inv())
    override fun det() = this.storage.determinant()
    override fun pinv() = EJMLMatrix(this.storage.pseudoInverse())
    override fun normF() = this.storage.normF()
    override fun normIndP1() = NormOps_DDRM.inducedP1(this.storage.ddrm)
    override fun elementSum() = this.storage.elementSum()
    override fun trace() = this.storage.trace()
    override fun epow(other: Double) = EJMLMatrix(this.storage.elementPower(other))
    override fun epow(other: Int) = EJMLMatrix(this.storage.elementPower(other.toDouble()))

    override fun getFactory() = factoryInstance

    override fun solve(other: Matrix<Double>): EJMLMatrix {
        val a = this.storage
        val b= castOrCopy(other, ::EJMLMatrix, getFactory()).storage

        return try {
            EJMLMatrix(a.solve(b))
        } catch(e: SingularMatrixException) {
            warnSingular
            EJMLMatrix(a.pseudoInverse().times(b))
        }
    }

    override fun LU(): Triple<EJMLMatrix, EJMLMatrix, EJMLMatrix> {
        val decomp = this.storage.LU()
        return Triple(EJMLMatrix(SimpleMatrix(decomp.getRowPivot(null))),
                      EJMLMatrix(SimpleMatrix(decomp.getLower(null))),
                      EJMLMatrix(SimpleMatrix(decomp.getUpper(null))))
    }

    override fun QR(): Pair<EJMLMatrix, EJMLMatrix> {
        val decomp = this.storage.QR()
        return Pair(EJMLMatrix(SimpleMatrix(decomp.getQ(null, false))),
                    EJMLMatrix(SimpleMatrix(decomp.getR(null, false))))
    }

    override fun SVD(): Triple<EJMLMatrix, EJMLMatrix, EJMLMatrix> {
        val svd = this.storage.svd()
        return Triple(EJMLMatrix(svd.u), EJMLMatrix(svd.w), EJMLMatrix(svd.v))
    }


}

val warnSingular by lazy { println("Koma EJML backend: warning: solving a singular matrix via fallback. If this is intentional, " +
        "please call `a.pinv() * b` directly.") }