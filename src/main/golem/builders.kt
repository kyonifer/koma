/**
 *   A DSL for writing matrix literals. You can define a new matrix via e.g.
 *
 *   var a = mat[1, 2 end
 *               3, 4]
 */

@file:JvmName("Golem")
@file:JvmMultifileClass

package golem

import golem.matrix.Matrix


object mat {
    operator fun get(vararg ts: Any): Matrix<Double> {
        // Todo: check for malformed inputs to avoid ambiguous out of bounds exceptions

        val numStops = ts.filter{ it is Pair<*, *> }.count()
        val numRows = numStops + 1
        val numElements = ts.count() - numStops + 2 * numStops
        val numCols = numElements / numRows

        if (numRows*numCols != numElements)
            throw IllegalArgumentException("When building matrices with mat[] please give even rows/cols")

        var out = zeros(numRows, numCols)
        var curRow = 0
        var curCol = 0

        for (ele in ts) {
            if (curCol >= numCols)
                    throw IllegalArgumentException("When building matrices with mat[] please give even rows/cols")
            when (ele) {
                is Double -> {
                    out[curRow, curCol] = ele
                    curCol += 1
                }
                is Int -> {
                    out[curRow, curCol] = ele.toDouble()
                    curCol += 1
                }
                is Long -> {
                    out[curRow, curCol] = ele.toDouble()
                    curCol += 1
                }
                is Float -> {
                    out[curRow, curCol] = ele.toDouble()
                    curCol += 1
                }
                is Pair<*, *> -> {
                    out[curRow, curCol] = ele.first as Double
                    out[curRow + 1, 0] = ele.second as Double
                    curRow += 1
                    curCol = 1
                }
                else -> throw Exception("Invalid initial value to matrix builder: ${ele.javaClass}")
            }
        }
        return out
    }
}
infix fun Double.end(other: Double) = Pair(this, other)
infix fun Double.end(other: Int) = Pair(this, other.toDouble())
infix fun Int.end(other: Double) = Pair(this.toDouble(), other)
infix fun Int.end(other: Int) = Pair(this.toDouble(), other.toDouble())
