/**
 *   A DSL for writing matrix literals. You can define a new matrix via e.g.
 *
 *   var a = mat[1, 2 end
 *               3, 4]
 */

@file:JvmName("Golem")
@file:JvmMultifileClass

package golem

import golem.matrix.*


// TODO: Remove these when they are added to kotlin-native
public inline fun <T, C : MutableCollection<in T>> Array<out T>.filterTo(destination: C, predicate: (T) -> Boolean): C {
    for (element in this) if (predicate(element)) destination.add(element)
    return destination
}
public inline fun <T> Array<out T>.filter(predicate: (T) -> Boolean): List<T> {
    return filterTo(ArrayList<T>(), predicate)
}



//@formatter:off

/**
 * A helper object that allows for quick construction of matrix literals.
 *
 * For example, one can write
 * ```
 * var a = mat[1,2,3 end
 *             4,5,6]
 * ```
 *
 * to get a 2x3 [Matrix<Double>] with the given values. end is a helper object that indicates the end of a row
 * to this object. Note that one currently cannot use this function to generate a column vector:
 *
 * ```// ERROR:```
 *
 * ```mat[1 end 2 end 3]```
 *
 * Instead do this:
 *
 * ```// Define a column vector by transposing a row-vector```
 *
 * ```mat[1 2 3].T```
 */
object mat {
    /**
     * See [mat] description.
     */
    operator fun get(vararg ts: Any): Matrix<Double> {
        // Todo: check for malformed inputs to avoid ambiguous out of bounds exceptions

        val numStops = ts.filter { it is Pair<*, *> }.count()
        val numRows = numStops + 1
        val numElements = ts.count() - numStops + 2 * numStops
        val numCols = numElements / numRows

        if (numRows * numCols != numElements)
            throw IllegalArgumentException("When building matrices with mat[] please give even rows/cols")

        val out = zeros(numRows, numCols)
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
                else -> throw Exception("Invalid initial value to matrix builder: \n$ele")
            }
        }
        return out
    }
}

// @formatter:on
/**
 * A helper object for the builder DSL. See [mat].
 */
infix fun Double.end(other: Double) = Pair(this, other)

/**
 * A helper object for the builder DSL. See [mat].
 */
infix fun Double.end(other: Int) = Pair(this, other.toDouble())

/**
 * A helper object for the builder DSL. See [mat].
 */
infix fun Int.end(other: Double) = Pair(this.toDouble(), other)

/**
 * A helper object for the builder DSL. See [mat].
 */
infix fun Int.end(other: Int) = Pair(this.toDouble(), other.toDouble())
