/**
 * Some functions to make jblas matrices more usable in Kotlin
 * If raw performance isnt needed, try using koma.matrix.Matrix facade instead,
 * which wraps this class and allows you to swap out matrix implementations.
 *
 * In particular, this enables you to write e.g.
 *
 *      var one = zeros(4,5)    // 4x5 zero matrix
 *      var two = one*one.T
 *
 * In addition, a shorthand for matrix construction is
 *
 *      var foo = mat[1,2,3 end
 *                    4,5,6 end
 *                    7,8,9]
 */
@file:JvmName("RawJBlas")

package koma.matrix.jblas.backend

import koma.*
import org.jblas.*

fun DoubleMatrix.mapMat(f: (Double) -> Double): DoubleMatrix {
    val out = DoubleMatrix(this.rows, this.columns)
    for (row in 0..this.rows - 1)
        for (col in 0..this.columns - 1)
            out[row, col] = f(this[row, col])
    return out
}

// Algebraic Operators
operator fun DoubleMatrix.plus(other: DoubleMatrix) = this.add(other)
operator fun DoubleMatrix.minus(other: DoubleMatrix) = this.sub(other)
operator fun DoubleMatrix.times(other: DoubleMatrix) = this.mmul(other)
operator fun DoubleMatrix.rem(other: DoubleMatrix) = this.mul(other)

fun DoubleMatrix.plusElement(other: Int) = this.plusElement(other.toDouble())
fun DoubleMatrix.plusElement(other: Double) = this.mapMat { it + other }

fun DoubleMatrix.powElement(other: Int) = this.powElement(other.toDouble())
fun DoubleMatrix.powElement(other: Double) = this.mapMat { pow(it, other) }



val DoubleMatrix.T: DoubleMatrix
    get() = this.transpose()

operator fun DoubleMatrix.set(i: Int, v: Double) = this.put(i, v)
operator fun DoubleMatrix.set(i: Int, j: Int, v: Double) = this.put(i, j, v)
operator fun DoubleMatrix.set(i: Int, j: Int, v: Int) = this.put(i, j, v.toDouble())

// Pretty Printing
fun DoubleMatrix.repr(): String {
    val out = StringBuilder()

    for (row in RowsAsListView(this)) {
        out.append(row)
        out.append("\n")
    }
    return out.toString()
}

// Decompositions (already has schur)
fun DoubleMatrix.eigVals() = Eigen.eigenvalues(this)

fun DoubleMatrix.eigVectors() = Eigen.eigenvectors(this)
fun DoubleMatrix.chol() = Decompose.cholesky(this)
fun DoubleMatrix.LU() = Decompose.lu(this)
fun DoubleMatrix.QR() = Decompose.qr(this)
fun DoubleMatrix.expm() = MatrixFunctions.expm(this)
fun DoubleMatrix.inv() = Solve.solve(this, DoubleMatrix.eye(this.rows))

fun DoubleMatrix.det(): Double {
    if (rows!=columns)
        throw IllegalArgumentException("Determinant only defined for square matrices")
    return this.LU().u.diag().prod()
}

fun zeros(rows: Int, cols: Int) = DoubleMatrix.zeros(rows, cols)
fun ones(rows: Int, cols: Int) = DoubleMatrix.ones(rows, cols)
fun randn(len: Int) = DoubleMatrix.randn(len)
fun randn(rows: Int, cols: Int) = DoubleMatrix.randn(rows, cols)
fun rand(len: Int) = DoubleMatrix.rand(len)
fun rand(rows: Int, cols: Int) = DoubleMatrix.rand(rows, cols)
fun eye(size: Int) = DoubleMatrix.eye(size)

object arr {
    operator fun get(vararg ts: Any): DoubleArray {
        // Todo: check for malformed inputs to avoid ambiguous out of bounds exceptions

        val len = ts.count()
        val out = DoubleArray(len)
        for (i in (0..len - 1)) {
            // Smart cast doesn't work on indexed collection
            val ele = ts[i]
            if (ele is Double)
                out[i] = ele
            else if (ele is Int)
                out[i] = ele.toDouble()
            else throw Exception("Invalid initial value to internal construction")
        }
        return out
    }
}

object mat {
    operator fun get(vararg ts: Any): DoubleMatrix {
        // Todo: check for malformed inputs to avoid ambiguous out of bounds exceptions

        val numStops = (ts.filter { it is Pair<*, *> }).count()
        val numRows = numStops + 1
        val numElements = ts.count() - numStops + 2 * numStops
        val numCols = numElements / numRows

        val out = DoubleMatrix(numRows, numCols)
        var curRow = 0
        var curCol = 0

        for (ele in ts) {
            when (ele) {
                is Double -> {
                    out[curRow, curCol] = ele
                    curCol += 1
                }
                is Int -> {
                    out[curRow, curCol] = ele.toDouble()
                    curCol += 1
                }
                is Pair<*, *> -> {
                    out[curRow, curCol] = ele.first as Double
                    out[curRow + 1, 0] = ele.second as Double
                    curRow += 1
                    curCol = 1
                }
                else -> throw Exception("Invalid initial value to internal construction")
            }
        }
        return out
    }
}

fun Double.end(other: Double) = Pair(this, other)
fun Double.end(other: Int) = Pair(this, other.toDouble())
fun Int.end(other: Double) = Pair(this.toDouble(), other)
fun Int.end(other: Int) = Pair(this.toDouble(), other.toDouble())


