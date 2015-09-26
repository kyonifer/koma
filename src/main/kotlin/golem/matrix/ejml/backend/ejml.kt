/**
 * Some functions to make ejml matrices more usable directly in Kotlin.
 * If raw performance isnt needed, try using math.matrix facade instead,
 * which wraps this class and allows you to swap out matrix implementations.
 *
 * In particular, this enables you to write e.g.
 *
 *      var one = zeros(4,5)    // 4x5 zero matrix
 *      var two = one*one.T()
 *      println(two.repr())     // A nice representation of a square matrix
 *
 * In addition, a shorthand for matrix construction is
 *
 *      var foo = mat[1,2,3 end
 *                    4,5,6 end
 *                    7,8,9]
 */

package golem.matrix.ejml.backend

import org.ejml.factory.DecompositionFactory
import org.ejml.ops.CommonOps
import org.ejml.simple.*
import java.util.*

// Algebraic Operators (Already has plus, minus, set(i,v:Float), set(i,j,v:Float), toString)
fun SimpleMatrix.times(other: SimpleMatrix) = this.mult(other)
fun SimpleMatrix.times(other: Int) = this.scale(other.toDouble())
fun SimpleMatrix.times(other: Double) = this.scale(other)
fun SimpleMatrix.mod(other: SimpleMatrix) = this.elementMult(other)
fun SimpleMatrix.minus() = this.scale(-1.0)
fun SimpleMatrix.div(other: Int) = this.divide(other.toDouble())
fun SimpleMatrix.div(other: Double) = this.divide(other)
val SimpleMatrix.T: SimpleMatrix
    get() = this.transpose()

// Index syntax (already has get)
fun SimpleMatrix.set(i: Int, v: Int) = this.set(i,v.toDouble())
fun SimpleMatrix.set(i: Int, j:Int, v:Int) = this.set(i,j,v.toDouble())

// Scalar Arithmetic
fun SimpleMatrix.plus(other: Int) = this.plus(other.toDouble(), this)
fun SimpleMatrix.plus(other: Double) = this.plus(other, this)

// Decompositions (Already has eig, svd) [expm,schur not available]
fun SimpleMatrix.chol() = DecompositionFactory.chol(this.numCols(), false)
fun SimpleMatrix.LU() = DecompositionFactory.lu(this.numRows(), this.numCols())
fun SimpleMatrix.QR() = DecompositionFactory.qr(this.numRows(), this.numCols())

// Basic Functions
fun SimpleMatrix.inv() = this.invert()
// Already existing: det, pinv, normf, elementSum

// Matrix Generators
fun zeros(rows: Int, cols: Int) = SimpleMatrix(rows, cols)
fun eye(size: Int) = SimpleMatrix.identity(size)
fun ones(rows: Int, cols: Int): SimpleMatrix {
    val out = SimpleMatrix(rows, cols)
    CommonOps.fill(out.getMatrix(), 1.0)
    return out
}
fun rand(len: Int, seed: Long) = SimpleMatrix.random(1, len, 0.0, 1.0, Random(seed))
fun rand(len: Int) = rand(len, System.currentTimeMillis())

fun randn(len: Int): SimpleMatrix {
    return randn(len, len)
}
fun randn(rows: Int, cols: Int): SimpleMatrix {
    return randn(rows, cols, System.currentTimeMillis())
}
fun randn(rows: Int, cols: Int, seed: Long): SimpleMatrix {
    val random = Random(seed)
    val out = SimpleMatrix(rows, cols)
    for (i in 0..rows-1)
        for (j in 0..cols-1)
            out[i,j] = random.nextGaussian()
    return out
}

fun arange(start: Double, stop: Double, step: Double): SimpleMatrix{
    val shape = ((stop - start) / step).toInt()
    if (shape <= 0)
        throw Exception("Invalid Range due to bounds/step")
    val out = zeros(shape, 1)
    var idx = 0
    if (step >= 0){
        for (atom_val in start..(stop-step) step `step`){
            out[idx, 0] = atom_val
            idx += 1
        }
    }
    else{
        for (atom_val in start downTo (stop-step) step java.lang.Math.abs(`step`)){
            out[idx, 0] = atom_val
            idx += 1
        }
    }
    return out
}

fun SimpleMatrix.map( f: (Double)-> Double) {
    for (row in 0..this.numRows()-1)
        for (col in 0..this.numCols()-1)
            this[row,col] = f(this[row,col])
}

object arr {
    fun get(vararg ts: Any): DoubleArray {
        // Todo: check for malformed inputs to avoid ambiguous out of bounds exceptions

        val len = ts.count()
        var out = DoubleArray(len)
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
    fun get(vararg ts: Any): SimpleMatrix {
        // Todo: check for malformed inputs to avoid ambiguous out of bounds exceptions

        val numStops = (ts.filter { it is Pair<*, *> }).count()
        val numRows = numStops + 1
        val numElements = ts.count() - numStops + 2 * numStops;
        val numCols = numElements / numRows

        var out = SimpleMatrix(numRows, numCols)
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
