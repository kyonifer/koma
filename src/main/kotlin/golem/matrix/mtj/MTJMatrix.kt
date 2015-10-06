package golem.matrix.mtj

import golem.ceil
import golem.logb
import golem.pow
import golem.matFormat
import golem.matrix.Matrix
import golem.matrix.MatrixFactory
import golem.matrix.mtj.backend.*
import no.uib.cipr.matrix.DenseLU
import no.uib.cipr.matrix.DenseMatrix
import no.uib.cipr.matrix.Matrices
import no.uib.cipr.matrix.MatrixEntry
import java.io.ByteArrayOutputStream

public class MTJMatrix(var storage: DenseMatrix) : Matrix<Double> {

    // TODO: Fix UnsupportedOperationException

    override fun diag(): MTJMatrix {
        return MTJMatrix(this.storage.diag())
    }
    override fun cumsum() = storage.sumByDouble { it.get() }
    override fun max() = storage.maxBy{ it.get() }!!.get()
    override fun mean() = elementSum() / (numCols()*numRows())
    override fun min() = storage.minBy { it.get() }!!.get()
    override fun argMax() = throw UnsupportedOperationException()
    override fun argMean() = throw UnsupportedOperationException()
    override fun argMin() = throw UnsupportedOperationException()
    override fun norm() = this.storage.norm(no.uib.cipr.matrix.Matrix.Norm.Frobenius)

    override fun getDouble(i: Int, j: Int) = this.storage.get(i, j)
    override fun getDouble(i: Int) = this.storage.get(i)
    override fun setDouble(i: Int, v: Double) = this.storage.set(1,i,v)
    override fun setDouble(i: Int, j: Int, v: Double) = this.storage.set(i,j,v)

    override fun numRows() = this.storage.numRows()
    override fun numCols() = this.storage.numColumns()
    override fun times(other: Matrix<Double>) = MTJMatrix(DenseMatrix(this.storage.times(castOrBail(other).storage)))
    override fun times(other: Double) = MTJMatrix(DenseMatrix(this.storage.times(other)))
    override fun elementTimes(other: Matrix<Double>) = MTJMatrix(this.storage.mod(castOrBail(other).storage))
    override fun mod(other: Matrix<Double>) = elementTimes(other)
    override fun minus() = MTJMatrix(this.storage.minus())
    override fun minus(other: Double) = MTJMatrix(this.storage.minusElement(other))
    override fun minus(other: Matrix<Double>) = MTJMatrix(this.storage.minus(castOrBail(other).storage))
    override fun div(other: Int) = MTJMatrix(this.storage.div(other))
    override fun div(other: Double) = MTJMatrix(this.storage.div(other))
    override fun transpose() = MTJMatrix(DenseMatrix(this.storage.transpose()))
    override fun copy() = MTJMatrix(this.storage.copy())
    override fun set(i: Int, v: Double): Unit = if(this.storage.numRows()==1) this.storage.set(1,i,v) else this.storage.set(i,1,v)
    override fun set(i: Int, j: Int, v: Double) = this.storage.set(i,j,v)
    override fun get(i: Int, j: Int) = this.storage.get(i,j)
    override fun get(i: Int) = this.storage.get(i)
    override fun getRow(row: Int) = MTJMatrix(DenseMatrix(Matrices.getColumn(this.storage.transpose(), row)))
    override fun getCol(col: Int) = MTJMatrix(DenseMatrix(Matrices.getColumn(this.storage, col)))
    override fun plus(other: Matrix<Double>) = MTJMatrix(this.storage.plusMatrix(castOrBail(other).storage))
    override fun plus(other: Double) = MTJMatrix(this.storage.plusElement(other))
    override fun chol() = MTJMatrix(DenseMatrix(this.storage.chol()))
    override fun inv() = MTJMatrix(this.storage.inv())
    override fun pinv()= throw UnsupportedOperationException()//= EJMLMatrix(this.storage.pseudoInverse())
    override fun normf() = throw UnsupportedOperationException()//= this.storage.norm()
    override fun elementSum() = this.cumsum()
    override fun trace() = throw UnsupportedOperationException() //= this.storage.trace()
    override fun epow(other: Double) = throw UnsupportedOperationException()// = EJMLMatrix(this.storage.elementPower(other))
    override fun epow(other: Int)  = throw UnsupportedOperationException()//= EJMLMatrix(this.storage.elementPower(other.toDouble()))
    override fun det(): Double
    {
        return this.storage.det()
    }

    override fun pow(exponent: Int): MTJMatrix {
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

    override fun getFactory() = golem.matrix.mtj.factory

    override val T: MTJMatrix
        get() = this.transpose()

    override fun solve(A: Matrix<Double>, B: Matrix<Double>): MTJMatrix {
        var out = this.getFactory().zeros(A.numCols(), 1)
        castOrBail(A).storage.solve(castOrBail(B).storage, out.storage)
        return out
    }

    override fun expm(): MTJMatrix {
        // Casts are safe since generation happens from mat.getFactory()
        return golem.matrix.common.expm(this,
                { mat -> (mat as MTJMatrix).storage.norm(no.uib.cipr.matrix.Matrix.Norm.One) },
                { Q, Pcol -> solve(Q, Pcol) }
        ) as MTJMatrix
    }

    override fun LU(): Triple<MTJMatrix, MTJMatrix, MTJMatrix> {
        val (p, L, U) = this.storage.LU()
        return Triple(MTJMatrix(p), MTJMatrix(L), MTJMatrix(U))
    }

    override fun QR(): Pair<MTJMatrix, MTJMatrix> {
        val (Q, R) = this.storage.QR()
        return Pair(MTJMatrix(Q), MTJMatrix(R))
    }

    override fun repr() = this.toString()

    override fun toString(): String {
        return this.storage.toString()
        /*
        val stream = ByteArrayOutputStream()

        // Numbers are numChars, precision
        when (matFormat) {
            "S" -> {
                MatrixIO.print(PrintStream(stream), this.storage.matrix, 6, 3)
            }
            "L" -> {
                MatrixIO.print(PrintStream(stream), this.storage.matrix, 14, 8)
            }
            "VL" -> {
                MatrixIO.print(PrintStream(stream), this.storage.matrix, 20, 20)
            }
        }
        return stream.toString()
        */
        }

    // TODO: Fix this
    /**
     * Eventually we will support operations between matrices with different
     * backends. However, for now we'll exception out of it.
     *
     */
    private fun castOrBail(mat: Matrix<Double>): MTJMatrix
    {
        when (mat) {
            is MTJMatrix -> return mat
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