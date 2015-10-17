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
import java.io.PrintStream

public class MTJMatrix(var storage: DenseMatrix) : Matrix<Double> {
    override fun getDoubleData() = this.storage.data

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
    override fun times(other: Matrix<Double>) = MTJMatrix(this.storage.times(castOrBail(other).storage))
    override fun times(other: Double) = MTJMatrix(this.storage.times(other))
    override fun elementTimes(other: Matrix<Double>) = MTJMatrix(this.storage.mod(castOrBail(other).storage))
    override fun mod(other: Matrix<Double>) = elementTimes(other)
    override fun minus() = MTJMatrix(this.storage.minus())
    override fun minus(other: Double) = MTJMatrix(this.storage.minusElement(other))
    override fun minus(other: Matrix<Double>) = MTJMatrix(this.storage.minus(castOrBail(other).storage))
    override fun div(other: Int) = MTJMatrix(this.storage.div(other))
    override fun div(other: Double) = MTJMatrix(this.storage.div(other))
    override fun transpose(): MTJMatrix {
        var out = DenseMatrix(this.numCols(), numRows())
        return MTJMatrix(DenseMatrix(this.storage.transpose(out)))
    }
    override fun copy() = MTJMatrix(this.storage.copy())
    override fun set(i: Int, v: Double): Unit = if(this.storage.numRows()==1) this.storage.set(0,i,v) else this.storage.set(i,0,v)
    override fun set(i: Int, j: Int, v: Double) = this.storage.set(i,j,v)
    override fun get(i: Int, j: Int) = this.storage.get(i,j)
    override fun get(i: Int) = this.storage.get(i)
    override fun getRow(row: Int) : MTJMatrix {
        var out = DenseMatrix(1, this.numCols())
        for (col in 0 until this.numCols())
            out.set(0, col, this.get(row, col))
        return MTJMatrix(out)
    }
    override fun getCol(col: Int) = MTJMatrix(DenseMatrix(Matrices.getColumn(this.storage, col)))
    override fun plus(other: Matrix<Double>) = MTJMatrix(this.storage.plusMatrix(castOrBail(other).storage))
    override fun plus(other: Double) = MTJMatrix(this.storage.plusElement(other))
    override fun chol() = MTJMatrix(DenseMatrix(this.storage.chol()))
    override fun inv() = MTJMatrix(this.storage.inv())
    override fun pinv()= throw UnsupportedOperationException()//= EJMLMatrix(this.storage.pseudoInverse())
    override fun normf() = throw UnsupportedOperationException()//= this.storage.norm()
    override fun elementSum() = this.cumsum()
    override fun trace() = throw UnsupportedOperationException() //= this.storage.trace()
    override fun epow(other: Double) = MTJMatrix(this.storage.powElement(other))
    override fun epow(other: Int) = MTJMatrix(this.storage.powElement(other))
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
        val bstream = ByteArrayOutputStream()
        val pstream = PrintStream(bstream)

        val data = this.storage.data

        // Numbers are numChars, precision
        var (numChars, precision) =
            when (matFormat) {
                "S" -> Pair(6,3)
                "L" -> Pair(14,12)
                "VL" -> Pair(20,20)
                else -> Pair(14,8)
            }

        data.forEachIndexed { i, ele ->
            if (i != 0 && i % this.storage.numColumns() == 0)
                pstream.append("\n")
            pstream.format("%${numChars}.${precision}f", ele)
            pstream.append("  ")
        }

        return bstream.toString()
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