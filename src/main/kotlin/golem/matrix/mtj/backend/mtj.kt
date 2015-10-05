package golem.matrix.mtj.backend

import no.uib.cipr.matrix.*
import java.lang.reflect.Field
import java.util.*

val Matrix.T: Matrix
    get() = this.transpose()



// Algebraic Operators (Already has plus, minus, set(i,v:Float), set(i,j,v:Float), toString)
fun DenseMatrix.times(other: DenseMatrix): DenseMatrix {
    var out = DenseMatrix(other.numRows(), other.numColumns())
    this.mult(other, out)
    return out
}

fun DenseMatrix.times(other: Int) = this.times(other.toDouble())
fun DenseMatrix.times(other: Double): DenseMatrix {
    var out = DenseMatrix(this.numRows(), this.numColumns())
    for (i in 0..this.numRows()-1)
        for (j in 0..this.numColumns()-1)
            out[i,j] = this[i,j]*other
    return out
}

// Element multiplication
fun DenseMatrix.mod(other: DenseMatrix): DenseMatrix {
    var out = DenseMatrix(this.numRows(), this.numColumns())
    for (i in 0..this.numRows()-1)
        for (j in 0..this.numColumns()-1)
            out[i,j] = this[i,j]*other[i,j]
    return out
}
fun DenseMatrix.minus() = this.times(-1.0)
// Can't override built-in minus
fun DenseMatrix.minusElement(other: Double) = this.plusElement(other*-1)
fun DenseMatrix.minusElement(other: Int) = this.plusElement(other*-1)
fun DenseMatrix.minus(other: DenseMatrix) = this.plusMatrix(DenseMatrix(other*-1))

// TODO: Numerical stability
fun DenseMatrix.div(other: Int) = this.times(1.0/(other.toDouble()))
fun DenseMatrix.div(other: Double) = this.times(1.0/other)

// Index syntax
fun DenseMatrix.set(i: Int, v: Int) = this.set(i,v.toDouble())
fun DenseMatrix.set(i: Int, j:Int, v:Int) = this.set(i,j,v.toDouble())
fun DenseMatrix.set(i: Int, v: Double) = this.set(1,i,v)
fun DenseMatrix.get(i: Int) = this.get(1, i)

// Scalar Arithmetic not available since extension functions cant override iterable plus()
fun DenseMatrix.plusElement(other: Int) = this.plusElement(other.toDouble())
fun DenseMatrix.plusElement(other: Double): DenseMatrix {
    var out = DenseMatrix(this.numRows(), this.numColumns())
    for (i in 0..this.numRows()-1)
        for (j in 0..this.numColumns()-1)
            out[i,j] = this[i,j]+other
    return out
}
fun DenseMatrix.plusMatrix(other: DenseMatrix) = DenseMatrix(this.add(other))

fun DenseMatrix.prod() {
    this.data.reduce { a, b -> a*b }
}
fun DenseMatrix.chol() = DenseCholesky.factorize(this).l
fun DenseMatrix.svd() = SVD.factorize(this)
fun DenseMatrix.eig() = EVD.factorize(this)

fun DenseMatrix.LU(): Triple<DenseMatrix, DenseMatrix, DenseMatrix> {
    var LUout = DenseLU.factorize(this)
    var p = DenseMatrix(LUout.p)
    var L = DenseMatrix(LUout.l)
    var U = DenseMatrix(LUout.u)
    return Triple(p, L, U)
}
fun DenseMatrix.QR(): Pair<DenseMatrix, DenseMatrix>
{
    var QRout = QR.factorize(this)
    var Q = QRout.q
    var R = QRout.r
    return Pair(Q, DenseMatrix(R))
}
//schur, svd, chol, eig, qr, lu
// det, pinv, normf, elementSum

// Matrix Generators
fun zeros(rows: Int, cols: Int) = DenseMatrix(rows, cols)
fun eye(size: Int) = Matrices.identity(size)
fun ones(rows: Int, cols: Int): DenseMatrix {
    var out = DenseMatrix(rows, cols)
    Arrays.fill(out.data, 1.0)
    return out
}


// Basic Functions
fun DenseMatrix.inv(): DenseMatrix {
    var out = eye(3)
    this.solve(eye(3), out)
    return out
}

fun DenseMatrix.det(): Double {
    var decomp = DenseLU.factorize(this)
    var L = decomp.l
    var U = decomp.u
    var P = decomp.p

    var pivots = decomp.pivots
    var swaps = 0
    pivots.forEachIndexed { idx, piv ->
        if (idx != piv)
            swaps += 1
    }
    

    return 1.0



}


fun rand(rows: Int, cols: Int, seed: Long): DenseMatrix
{
    random.setSeed(seed)
    var out = DenseMatrix(rows, cols)
    for (i in 0..rows - 1)
        for (j in 0..cols - 1)
            out[i, j] = random.nextDouble()
    return out
}
fun rand(rows: Int, cols: Int) = rand(rows, cols, System.currentTimeMillis())
fun rand(len: Int, seed: Long) = rand(1, len, seed)
fun rand(len: Int) = rand(len, System.currentTimeMillis())

fun randn(len: Int) = randn(len, len)
fun randn(rows: Int, cols: Int) = randn(rows, cols, System.currentTimeMillis())
fun randn(rows: Int, cols: Int, seed: Long): DenseMatrix{
    val random = Random(seed)
    val out = DenseMatrix(rows, cols)
    for (i in 0..rows-1)
        for (j in 0..cols-1)
            out[i,j] = random.nextGaussian()
    return out
}

fun DenseMatrix.map( f: (Double)-> Double): DenseMatrix {
    var out = DenseMatrix(this.numRows(), this.numColumns())
    for (row in 0..this.numRows()-1)
        for (col in 0..this.numColumns()-1)
            out[row,col] = f(this[row,col])
    return out
}

object mat {
    fun get(vararg ts: Any): DenseMatrix {
        // Todo: check for malformed inputs to avoid ambiguous out of bounds exceptions

        val numStops = (ts.filter { it is Pair<*, *> }).count()
        val numRows = numStops + 1
        val numElements = ts.count() - numStops + 2 * numStops
        val numCols = numElements / numRows

        var out = DenseMatrix(numRows, numCols)
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

internal var random = java.util.Random()
