package golem.polyfill

// TODO: Remove these when they are added to kotlin-native
public inline fun <T, C : MutableCollection<in T>> Array<out T>.filterTo(destination: C, predicate: (T) -> Boolean): C {
    for (element in this) if (predicate(element)) destination.add(element)
    return destination
}
public inline fun <T> Array<out T>.filter(predicate: (T) -> Boolean): List<T> {
    return filterTo(ArrayList<T>(), predicate)
}
public inline fun IntArray.reduce(operation: (acc: Int, Int) -> Int): Int {
    if (isEmpty())
        throw UnsupportedOperationException("Empty array can't be reduced.")
    var accumulator = this[0]
    for (index in 1..lastIndex) {
        accumulator = operation(accumulator, this[index])
    }
    return accumulator
}
public fun IntArray.toList(): List<Int> {
    return when (size) {
        0 -> emptyList()
        1 -> listOf(this[0])
        else -> this.toMutableList()
    }
}
public infix fun <R> IntArray.zip(other: Iterable<R>): List<Pair<Int, R>> {
    return zip(other) { t1, t2 -> t1 to t2 }
}
public inline fun <R> IntArray.mapIndexed(transform: (index: Int, Int) -> R): List<R> {
    return mapIndexedTo(ArrayList<R>(size), transform)
}
public inline fun <R, C : MutableCollection<in R>> IntArray.mapIndexedTo(destination: C, transform: (index: Int, Int) -> R): C {
    var index = 0
    for (item in this)
        destination.add(transform(index++, item))
    return destination
}
public inline fun <R> IntArray.map(transform: (Int) -> R): List<R> {
    return mapTo(ArrayList<R>(size), transform)
}
public inline fun <R, C : MutableCollection<in R>> IntArray.mapTo(destination: C, transform: (Int) -> R): C {
    for (item in this)
        destination.add(transform(item))
    return destination
}
public infix fun IntArray.zip(other: IntArray): List<Pair<Int, Int>> {
    return zip(other) { t1, t2 -> t1 to t2 }
}
public inline fun <V> IntArray.zip(other: IntArray, transform: (a: Int, b: Int) -> V): List<V> {
    val size = minOf(size, other.size)
    val list = ArrayList<V>(size)
    for (i in 0..size-1) {
        list.add(transform(this[i], other[i]))
    }
    return list
}
public fun IntArray.toMutableList(): MutableList<Int> {
    val list = ArrayList<Int>(size)
    for (item in this) list.add(item)
    return list
}
public inline fun <R, V> IntArray.zip(other: Iterable<R>, transform: (a: Int, b: R) -> V): List<V> {
    val arraySize = size
    val list = ArrayList<V>(minOf(other.collectionSizeOrDefault(10), arraySize))
    var i = 0
    for (element in other) {
        if (i >= arraySize) break
        list.add(transform(this[i++], element))
    }
    return list
}
fun <T> Iterable<T>.collectionSizeOrDefault(default: Int): Int = if (this is Collection<*>) this.size else default

public inline fun <R> DoubleArray.map(transform: (Double) -> R): List<R> {
    return mapTo(ArrayList<R>(size), transform)
}

public inline fun <R> LongArray.map(transform: (Long) -> R): List<R> {
    return mapTo(ArrayList<R>(size), transform)
}

public inline fun <R> FloatArray.map(transform: (Float) -> R): List<R> {
    return mapTo(ArrayList<R>(size), transform)
}

public inline fun <R, C : MutableCollection<in R>> LongArray.mapTo(destination: C, transform: (Long) -> R): C {
    for (item in this)
        destination.add(transform(item))
    return destination
}

public inline fun <R, C : MutableCollection<in R>> FloatArray.mapTo(destination: C, transform: (Float) -> R): C {
    for (item in this)
        destination.add(transform(item))
    return destination
}

public inline fun <R, C : MutableCollection<in R>> DoubleArray.mapTo(destination: C, transform: (Double) -> R): C {
    for (item in this)
        destination.add(transform(item))
    return destination
}