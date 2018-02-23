@file:JvmName("RawMTJ")

package koma.matrix.mtj.backend

import koma.*
import no.uib.cipr.matrix.*
import java.util.*

val Matrix.T: Matrix
    get() = this.transpose()

fun DenseMatrix.mapMat(f: (Double) -> Double): DenseMatrix {
    val out = DenseMatrix(this.numRows(), this.numColumns())

    for (row in 0..this.numRows() - 1)
        for (col in 0..this.numColumns() - 1)
            out[row, col] = f(this[row, col])
    return out
}

// Algebraic Operators (Already has plus, minus, set(i,v:Float), set(i,j,v:Float), toString)
operator fun DenseMatrix.times(other: DenseMatrix): DenseMatrix {
    val out = DenseMatrix(this.numRows(), other.numColumns())
    this.mult(other, out)
    return out
}

operator fun DenseMatrix.times(other: Int) = this.times(other.toDouble())
operator fun DenseMatrix.times(other: Double) = this.mapMat { it * other }

// Element multiplication
operator fun DenseMatrix.rem(other: DenseMatrix): DenseMatrix {
    val out = DenseMatrix(this.numRows(), this.numColumns())
    for (i in 0..this.numRows() - 1)
        for (j in 0..this.numColumns() - 1)
            out[i, j] = this[i, j] * other[i, j]
    return out
}

operator fun DenseMatrix.unaryMinus() = this.times(-1.0)
// Can't override built-in minus
fun DenseMatrix.minusElement(other: Double) = this.plusElement(other * -1)

fun DenseMatrix.minusElement(other: Int) = this.plusElement(other * -1)
operator fun DenseMatrix.minus(other: DenseMatrix) = this.plusMatrix(DenseMatrix(other * -1))

// TODO: Numerical stability
operator fun DenseMatrix.div(other: Int) = this.times(1.0 / (other.toDouble()))

operator fun DenseMatrix.div(other: Double) = this.times(1.0 / other)

// Index syntax
operator fun DenseMatrix.set(i: Int, v: Int) = this.set(i, v.toDouble())

operator fun DenseMatrix.set(i: Int, j: Int, v: Int) = this.set(i, j, v.toDouble())
operator fun DenseMatrix.set(i: Int, v: Double) = this.set(i / numColumns(), i % numColumns(), v)
operator fun DenseMatrix.get(i: Int) = this.get(i / numColumns(), i % numColumns())

// Scalar Arithmetic not available since extension functions cant override iterable plus()
fun DenseMatrix.plusElement(other: Int) = this.plusElement(other.toDouble())
fun DenseMatrix.plusElement(other: Double) = this.mapMat { it + other }

fun DenseMatrix.plusMatrix(other: DenseMatrix): DenseMatrix {
    if (other.numRows() == 1 && other.numColumns() == 1)
        return this.plusElement(other[0, 0])
    else if (this.numRows() == 1 && this.numColumns() == 1)
        return other.plusElement(this[0, 0])
    else return DenseMatrix(this.copy().add(other))
}

fun DenseMatrix.powElement(other: Int) = this.powElement(other.toDouble())
fun DenseMatrix.powElement(other: Double) = this.mapMat { pow(it, other) }

fun DenseMatrix.prod() = this.data.reduce { a, b -> a * b }

fun DenseMatrix.chol() = DenseCholesky(this.numRows(), false).factor(LowerSPDDenseMatrix(this)).l
fun DenseMatrix.svd() = SVD.factorize(this)
fun DenseMatrix.eig() = EVD.factorize(this)

fun DenseMatrix.LU(): Triple<DenseMatrix, DenseMatrix, DenseMatrix> {
    val LUout = DenseLU.factorize(this)
    val p = DenseMatrix(LUout.p)
    val L = DenseMatrix(LUout.l)
    val U = DenseMatrix(LUout.u)
    return Triple(p, L, U)
}

fun DenseMatrix.QR(): Pair<DenseMatrix, DenseMatrix> {
    val QRout = QR.factorize(this)
    val Q = QRout.q
    val R = QRout.r
    return Pair(Q, DenseMatrix(R))
}
//schur, svd, chol, eig, qr, lu
// det, pinv, normf, elementSum

// Matrix Generators
fun zeros(rows: Int, cols: Int) = DenseMatrix(rows, cols)

fun eye(size: Int) = Matrices.identity(size)
fun ones(rows: Int, cols: Int): DenseMatrix {
    val out = DenseMatrix(rows, cols)
    Arrays.fill(out.data, 1.0)
    return out
}

fun DenseMatrix.diag(): DenseMatrix {
    val out = DenseMatrix(1, koma.min(this.numColumns(), this.numRows()))
    for (i in 0..out.numColumns() - 1)
        out[0, i] = this[i, i]
    return out
}

// Basic Functions
fun DenseMatrix.inv(): DenseMatrix {
    val out = eye(this.numColumns())
    this.solve(eye(this.numColumns()), out)
    return out
}

fun DenseMatrix.det(): Double {
    val decomp = DenseLU.factorize(this)
    val L = DenseMatrix(decomp.l)
    val U = DenseMatrix(decomp.u)

    val pivots = decomp.pivots
    var swaps = 0
    pivots.forEachIndexed { idx, piv ->
        if (idx + 1 != piv)
            swaps += 1
    }
    val out = L.diag().prod() * U.diag().prod() * (if (swaps % 2 == 0) 1 else -1)


    return out


}

object mat {
    operator fun get(vararg ts: Any): DenseMatrix {
        // Todo: check for malformed inputs to avoid ambiguous out of bounds exceptions

        val numStops = (ts.filter { it is Pair<*, *> }).count()
        val numRows = numStops + 1
        val numElements = ts.count() - numStops + 2 * numStops
        val numCols = numElements / numRows

        val out = DenseMatrix(numRows, numCols)
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

