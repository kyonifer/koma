/**
 * Some helper conversion functions
 */
@file:JvmName("UtilConversions")

package koma.util
import koma.polyfill.annotations.*

/**
 * Converts a collection into a primitive DoubleArray
 */
fun fromCollection(collection: Collection<Double>): DoubleArray {
    val out = DoubleArray(collection.size)
    val it = collection.iterator()
    for (i in collection.indices) {
        out[i] = it.next()
    }
    return out
}

/**
 * Converts a collection into a primitive DoubleArray
 */
fun fromCollection(collection: Collection<Int>): IntArray {
    val out = IntArray(collection.size)
    val it = collection.iterator()
    for (i in collection.indices) {
        out[i] = it.next()
    }
    return out
}