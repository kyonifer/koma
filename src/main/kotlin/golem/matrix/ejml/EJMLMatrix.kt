package golem.matrix.ejml

import golem.ceil
import golem.logb
import golem.matFormat
import golem.pow
import golem.matrix.Matrix
import golem.matrix.ejml.backend.*
import org.ejml.simple.SimpleMatrix
import org.ejml.ops.*
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class EJMLMatrix(var storage: SimpleMatrix) : Matrix<Double>
{
    override fun getDoubleData() = this.storage.matrix.getData()
    override fun diag() = EJMLMatrix(storage.extractDiag())
    override fun cumsum() = storage.elementSum()
    override fun max() = CommonOps.elementMax(this.storage.matrix)
    override fun mean() = elementSum() / (numCols()*numRows())
    override fun min() = CommonOps.elementMin(this.storage.matrix)
    override fun argMax() = throw UnsupportedOperationException()
    override fun argMean() = throw UnsupportedOperationException()
    override fun argMin() = throw UnsupportedOperationException()
    override fun norm() = this.storage.normF()

    override fun getDouble(i: Int, j: Int) = this.storage.get(i, j)
    override fun getDouble(i: Int) = this.storage.get(i)
    override fun setDouble(i: Int, v: Double) = this.storage.set(i, v)
    override fun setDouble(i: Int, j: Int, v: Double) = this.storage.set(i,j,v)

    override fun numRows() = this.storage.numRows()
    override fun numCols() = this.storage.numCols()
    override fun times(other: Matrix<Double>) = EJMLMatrix(this.storage.times(castOrBail(other).storage))
    override fun times(other: Double) = EJMLMatrix(this.storage.times(other))
    override fun elementTimes(other: Matrix<Double>) = EJMLMatrix(this.storage.elementMult(castOrBail(other).storage))
    override fun mod(other: Matrix<Double>) = EJMLMatrix(this.storage.mod(castOrBail(other).storage))
    override fun minus() = EJMLMatrix(this.storage.minus())
    override fun minus(other: Double) = EJMLMatrix(this.storage.minus(other))
    override fun minus(other: Matrix<Double>) = EJMLMatrix(this.storage.minus(castOrBail(other).storage))
    override fun div(other: Int) = EJMLMatrix(this.storage.div(other))
    override fun div(other: Double) = EJMLMatrix(this.storage.div(other))
    override fun transpose() = EJMLMatrix(this.storage.transpose())
    override fun copy() = EJMLMatrix(this.storage.copy())
    override fun set(i: Int, v: Double): Unit = if(this.storage.numRows()==1) this.storage.set(1,i,v) else this.storage.set(i,1,v)
    override fun set(i: Int, j: Int, v: Double) = this.storage.set(i,j,v)
    override fun get(i: Int, j: Int) = this.storage.get(i,j)
    override fun get(i: Int) = this.storage.get(i)
    override fun getRow(row: Int) = EJMLMatrix(SimpleMatrix(CommonOps.extractRow(this.storage.matrix, row, null)))
    override fun getCol(col: Int) = EJMLMatrix(SimpleMatrix(CommonOps.extractColumn(this.storage.matrix, col, null)))
    override fun plus(other: Matrix<Double>) = EJMLMatrix(this.storage.plus(castOrBail(other).storage))
    override fun plus(other: Double) = EJMLMatrix(this.storage.plus(other))
    override fun chol() = EJMLMatrix(SimpleMatrix(this.storage.chol().getT(null)))
    override fun inv() = EJMLMatrix(this.storage.inv())
    override fun det() = this.storage.determinant()
    override fun pinv()= EJMLMatrix(this.storage.pseudoInverse())
    override fun normf() = this.storage.normF()
    override fun elementSum() = this.storage.elementSum()
    override fun trace() = this.storage.trace()
    override fun epow(other: Double) = EJMLMatrix(this.storage.elementPower(other))
    override fun epow(other: Int) = EJMLMatrix(this.storage.elementPower(other.toDouble()))
    override fun pow(exponent: Int): EJMLMatrix {
        var out = this.copy()
        for (i in 1..exponent-1)
            out *= this
        return out
    }
    override fun setCol(index: Int, col: Matrix<Double>) {
        for (i in 0..col.numRows()-1)
            this[i,index] = col[i]
    }

    override fun setRow(index: Int, row: Matrix<Double>) {
        for (i in 0..row.numCols()-1)
            this[index, i] = row[i]
    }

    override fun getFactory() = golem.matrix.ejml.factory
    override val T: EJMLMatrix
        get() = this.transpose()

    override fun solve(A: Matrix<Double>, B: Matrix<Double>): EJMLMatrix {
        var out = this.getFactory().zeros(A.numCols(), 1)
        CommonOps.solve(castOrBail(A).storage.matrix, castOrBail(B).storage.matrix, out.storage.matrix)
        return out
    }
    override fun expm(): EJMLMatrix {
        // Casts are safe since generation happens from mat.getFactory()
        return golem.matrix.common.expm(this,
                { mat -> org.ejml.ops.NormOps.inducedP1((mat as EJMLMatrix).storage.matrix) },
                { Q, Pcol -> solve(Q, Pcol) }
        ) as EJMLMatrix
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

    override fun repr() = this.toString()

    override fun toString(): String {
        val stream = ByteArrayOutputStream()

        // Numbers are numChars, precision
        when (matFormat) {
            "S" -> {
                MatrixIO.print(PrintStream(stream), this.storage.matrix, 6, 3)
            }
            "L" -> {
                MatrixIO.print(PrintStream(stream), this.storage.matrix, 14, 12)
            }
            "VL" -> {
                MatrixIO.print(PrintStream(stream), this.storage.matrix, 20, 20)
            }
        }
        return stream.toString()
    }

    // TODO: Fix this
    /**
     * Eventually we will support operations between matrices with different
     * backends. However, for now we'll exception out of it.
     *
     */
    private fun castOrBail(mat: Matrix<Double>): EJMLMatrix
    {
        when (mat) {
            is EJMLMatrix -> return mat
            else -> throw Exception("Operations between matrices with different backends not yet supported.")
        }

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

}
