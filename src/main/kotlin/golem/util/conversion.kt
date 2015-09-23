/**
 * Some helper conversion functions
 */

package golem.util

// Doesn't seem to be a built in conversion yet
fun fromCollection(collection: Collection<Double>): DoubleArray {
    var out = DoubleArray(collection.size())
    val it = collection.iterator()
    for (i in collection.indices) {
        out[i] = it.next()
    }
    return out
}
fun fromCollection(collection: Collection<Int>): IntArray {
    var out = IntArray(collection.size())
    val it = collection.iterator()
    for (i in collection.indices) {
        out[i] = it.next()
    }
    return out
}