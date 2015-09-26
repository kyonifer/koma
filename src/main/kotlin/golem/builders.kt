/**
 *   A DSL for writing matrix literals. You can define a new matrix via e.g.
 *
 *   var a = mat[1, 2 end
 *               3, 4]
 */
package golem

import golem.matrix.Matrix


object mat {
    fun get(vararg ts: Any): Matrix<Double> {
        // Todo: check for malformed inputs to avoid ambiguous out of bounds exceptions

        val numStops = ts.filter{ it is Pair<*, *> }.count()
        val numRows = numStops + 1
        val numElements = ts.count() - numStops + 2 * numStops
        val numCols = numElements / numRows

        var out = factory.zeros(numRows, numCols)
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
                is Long -> {
                    out[curRow, curCol] = ele.toDouble()
                }
                is Float -> {
                    out[curRow, curCol] = ele.toDouble()
                }
                is Pair<*, *> -> {
                    out[curRow, curCol] = ele.first as Double
                    out[curRow + 1, 0] = ele.second as Double
                    curRow += 1
                    curCol = 1
                }
                else -> throw Exception("Invalid initial value to matrix builder")
            }
        }
        return out
    }
}
fun Double.end(other: Double) = Pair(this, other)
fun Double.end(other: Int) = Pair(this, other.toDouble())
fun Int.end(other: Double) = Pair(this.toDouble(), other)
fun Int.end(other: Int) = Pair(this.toDouble(), other.toDouble())
