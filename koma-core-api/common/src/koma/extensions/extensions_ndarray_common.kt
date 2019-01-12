@file:koma.internal.JvmName("NDArray")
@file:koma.internal.JvmMultifileClass

package koma.extensions

import koma.ndarray.NDArray

/**
 * Determine whether every element of one NDArray is sufficiently close to the corresponding element
 * of another array.  Two elements are considered close if
 *
 * abs(ele1 - ele2) < (atol + rtol * abs(ele1))
 *
 * @param other   the array to compare this one to
 * @param rtol    the maximum relative (i.e. fractional) difference to allow between elements
 * @param atol    the maximum absolute difference to allow between elements
 */
fun <T: Number, R: Number> NDArray<T>.allClose(other: NDArray<R>, rtol: Double=1e-05, atol: Double=1e-08): Boolean {
    if (!(shape().toIntArray() contentEquals other.shape().toIntArray()))
        return false
    for (i in 0 until size) {
        val a = getDouble(i)
        val b = other.getDouble(i)
        val diff = kotlin.math.abs(a - b)
        if (diff > atol + rtol*kotlin.math.abs(a) || diff.isNaN() || a.isNaN())
            return false
    }
    return true
}

/**
 * Create a new NDArray that represents a slice of this one.  Each index may be either an Int
 * or an Iterable<Int>.
 */
inline operator fun <reified T> NDArray<T>.get(index1: Iterable<Int>): NDArray<T> {
    return getSliceInternal(index1)
}

/**
 * Create a new NDArray that represents a slice of this one.  Each index may be either an Int
 * or an Iterable<Int>.
 */
inline operator fun <reified T> NDArray<T>.get(index1: Int, index2: Iterable<Int>): NDArray<T> {
    return getSliceInternal(index1, index2)
}

/**
 * Create a new NDArray that represents a slice of this one.  Each index may be either an Int
 * or an Iterable<Int>.
 */
inline operator fun <reified T> NDArray<T>.get(index1: Iterable<Int>, index2: Int): NDArray<T> {
    return getSliceInternal(index1, index2)
}

/**
 * Create a new NDArray that represents a slice of this one.  Each index may be either an Int
 * or an Iterable<Int>.
 */
inline operator fun <reified T> NDArray<T>.get(index1: Iterable<Int>, index2: Iterable<Int>): NDArray<T> {
    return getSliceInternal(index1, index2)
}

/**
 * Create a new NDArray that represents a slice of this one.  Each index may be either an Int
 * or an Iterable<Int>.
 */
inline operator fun <reified T> NDArray<T>.get(index1: Int, index2: Int, vararg indices: Any): NDArray<T> {
    return getSliceInternal(index1, index2, *indices)
}

/**
 * Create a new NDArray that represents a slice of this one.  Each index may be either an Int
 * or an Iterable<Int>.
 */
inline operator fun <reified T> NDArray<T>.get(index1: Int, index2: Iterable<Int>, vararg indices: Any): NDArray<T> {
    return getSliceInternal(index1, index2, *indices)
}

/**
 * Create a new NDArray that represents a slice of this one.  Each index may be either an Int
 * or an Iterable<Int>.
 */
inline operator fun <reified T> NDArray<T>.get(index1: Iterable<Int>, index2: Int, vararg indices: Any): NDArray<T> {
    return getSliceInternal(index1, index2, *indices)
}

/**
 * Create a new NDArray that represents a slice of this one.  Each index may be either an Int
 * or an Iterable<Int>.
 */
inline operator fun <reified T> NDArray<T>.get(index1: Iterable<Int>, index2: Iterable<Int>, vararg indices: Any): NDArray<T> {
    return getSliceInternal(index1, index2, *indices)
}

/**
 * This is intended for internal use and should not be called directly.
 */
inline fun <reified T> NDArray<T>.getSliceInternal(vararg indices: Any): NDArray<T> {
    return when (T::class) {
        Double::class -> getSliceDouble(*indices) as NDArray<T>
        Float::class  -> getSliceFloat(*indices) as NDArray<T>
        Long::class   -> getSliceLong(*indices) as NDArray<T>
        Int::class    -> getSliceInt(*indices) as NDArray<T>
        Short::class  -> getSliceShort(*indices) as NDArray<T>
        Byte::class   -> getSliceByte(*indices) as NDArray<T>
        else          -> getSliceGeneric(*indices)
    }
}

/**
 * Set all elements in a slice of this array to a new value.  Each index may be either an Int
 * or an Iterable<Int>.
 */
inline operator fun <reified T> NDArray<T>.set(index1: Iterable<Int>, value: T) {
    setSliceInternal(index1, value=value)
}

/**
 * Set all elements in a slice of this array to a new value.  Each index may be either an Int
 * or an Iterable<Int>.
 */
inline operator fun <reified T> NDArray<T>.set(index1: Int, index2: Iterable<Int>, value: T) {
    setSliceInternal(index1, index2, value=value)
}

/**
 * Set all elements in a slice of this array to a new value.  Each index may be either an Int
 * or an Iterable<Int>.
 */
inline operator fun <reified T> NDArray<T>.set(index1: Iterable<Int>, index2: Int, value: T) {
    setSliceInternal(index1, index2, value=value)
}

/**
 * Set all elements in a slice of this array to a new value.  Each index may be either an Int
 * or an Iterable<Int>.
 */
inline operator fun <reified T> NDArray<T>.set(index1: Iterable<Int>, index2: Iterable<Int>, value: T) {
    setSliceInternal(index1, index2, value=value)
}

/**
 * Set all elements in a slice of this array to a new value.  Each index may be either an Int
 * or an Iterable<Int>.
 */
inline operator fun <reified T> NDArray<T>.set(index1: Int, index2: Int, vararg indices: Any, value: T) {
    setSliceInternal(index1, index2, *indices, value=value)
}

/**
 * Set all elements in a slice of this array to a new value.  Each index may be either an Int
 * or an Iterable<Int>.
 */
inline operator fun <reified T> NDArray<T>.set(index1: Int, index2: Iterable<Int>, vararg indices: Any, value: T) {
    setSliceInternal(index1, index2, *indices, value=value)
}

/**
 * Set all elements in a slice of this array to a new value.  Each index may be either an Int
 * or an Iterable<Int>.
 */
inline operator fun <reified T> NDArray<T>.set(index1: Iterable<Int>, index2: Int, vararg indices: Any, value: T) {
    setSliceInternal(index1, index2, *indices, value=value)
}

/**
 * Set all elements in a slice of this array to a new value.  Each index may be either an Int
 * or an Iterable<Int>.
 */
inline operator fun <reified T> NDArray<T>.set(index1: Iterable<Int>, index2: Iterable<Int>, vararg indices: Any, value: T) {
    setSliceInternal(index1, index2, *indices, value=value)
}

/**
 * This is intended for internal use and should not be called directly.
 */
inline fun <reified T> NDArray<T>.setSliceInternal(vararg indices: Any, value: T) {
    when (T::class) {
        Double::class -> setSliceDouble(*indices, value=value as Double)
        Float::class  -> setSliceFloat(*indices, value=value as Float)
        Long::class   -> setSliceLong(*indices, value=value as Long)
        Int::class    -> setSliceInt(*indices, value=value as Int)
        Short::class  -> setSliceShort(*indices, value=value as Short)
        Byte::class   -> setSliceByte(*indices, value=value as Byte)
        else          -> setSliceGeneric(*indices, value=value)
    }
}
/**
 * Set a slice of this array equal to another array.  Each index may be either an Int
 * or an Iterable<Int>.
 */
inline operator fun <reified T> NDArray<T>.set(index1: Iterable<Int>, value: NDArray<T>) {
    setSliceInternal(index1, value=value)
}

/**
 * Set a slice of this array equal to another array.  Each index may be either an Int
 * or an Iterable<Int>.
 */
inline operator fun <reified T> NDArray<T>.set(index1: Int, index2: Iterable<Int>, value: NDArray<T>) {
    setSliceInternal(index1, index2, value=value)
}

/**
 * Set a slice of this array equal to another array.  Each index may be either an Int
 * or an Iterable<Int>.
 */
inline operator fun <reified T> NDArray<T>.set(index1: Iterable<Int>, index2: Int, value: NDArray<T>) {
    setSliceInternal(index1, index2, value=value)
}

/**
 * Set a slice of this array equal to another array.  Each index may be either an Int
 * or an Iterable<Int>.
 */
inline operator fun <reified T> NDArray<T>.set(index1: Iterable<Int>, index2: Iterable<Int>, value: NDArray<T>) {
    setSliceInternal(index1, index2, value=value)
}

/**
 * Set a slice of this array equal to another array.  Each index may be either an Int
 * or an Iterable<Int>.
 */
inline operator fun <reified T> NDArray<T>.set(index1: Int, index2: Int, vararg indices: Any, value: NDArray<T>) {
    setSliceInternal(index1, index2, *indices, value=value)
}

/**
 * Set a slice of this array equal to another array.  Each index may be either an Int
 * or an Iterable<Int>.
 */
inline operator fun <reified T> NDArray<T>.set(index1: Int, index2: Iterable<Int>, vararg indices: Any, value: NDArray<T>) {
    setSliceInternal(index1, index2, *indices, value=value)
}

/**
 * Set a slice of this array equal to another array.  Each index may be either an Int
 * or an Iterable<Int>.
 */
inline operator fun <reified T> NDArray<T>.set(index1: Iterable<Int>, index2: Int, vararg indices: Any, value: NDArray<T>) {
    setSliceInternal(index1, index2, *indices, value=value)
}

/**
 * Set a slice of this array equal to another array.  Each index may be either an Int
 * or an Iterable<Int>.
 */
inline operator fun <reified T> NDArray<T>.set(index1: Iterable<Int>, index2: Iterable<Int>, vararg indices: Any, value: NDArray<T>) {
    setSliceInternal(index1, index2, *indices, value=value)
}

/**
 * This is intended for internal use and should not be called directly.
 */
inline fun <reified T> NDArray<T>.setSliceInternal(vararg indices: Any, value: NDArray<T>) {
    when (T::class) {
        Double::class -> setSliceDouble(*indices, value=value as NDArray<Double>)
        Float::class  -> setSliceFloat(*indices, value=value as NDArray<Float>)
        Long::class   -> setSliceLong(*indices, value=value as NDArray<Long>)
        Int::class    -> setSliceInt(*indices, value=value as NDArray<Int>)
        Short::class  -> setSliceShort(*indices, value=value as NDArray<Short>)
        Byte::class   -> setSliceByte(*indices, value=value as NDArray<Byte>)
        else          -> setSliceGeneric(*indices, value=value)
    }
}