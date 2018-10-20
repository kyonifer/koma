package koma.internal.default.utils

import koma.extensions.get
import koma.ndarray.NDArray

/**
 * This file defines functions that take a sequence of values as input and compute a single value from them.
 * These are used to implement various functions on arrays, but are mostly not designed to be called directly.
 */

/**
 * Compute the sum of a sequence of numbers.
 *
 * @param length   the number of values in the sequence
 * @param get      a function to retrieve a value by index
 */
inline fun sumBytes(length: Int, get: (Int) -> Byte): Long {
    var total = 0L
    for (i in 0 until length)
        total += get(i)
    return total
}

/**
 * Compute the sum of a sequence of numbers.
 *
 * @param length   the number of values in the sequence
 * @param get      a function to retrieve a value by index
 */
inline fun sumShorts(length: Int, get: (Int) -> Short): Long {
    var total = 0L
    for (i in 0 until length)
        total += get(i)
    return total
}

/**
 * Compute the sum of a sequence of numbers.
 *
 * @param length   the number of values in the sequence
 * @param get      a function to retrieve a value by index
 */
inline fun sumInts(length: Int, get: (Int) -> Int): Long {
    var total = 0L
    for (i in 0 until length)
        total += get(i)
    return total
}

/**
 * Compute the sum of a sequence of numbers.
 *
 * @param length   the number of values in the sequence
 * @param get      a function to retrieve a value by index
 */
inline fun sumLongs(length: Int, get: (Int) -> Long): Long {
    var total = 0L
    for (i in 0 until length)
        total += get(i)
    return total
}

/**
 * Compute the sum of a sequence of numbers.
 *
 * @param length   the number of values in the sequence
 * @param get      a function to retrieve a value by index
 */
inline fun sumFloats(length: Int, get: (Int) -> Float): Double {
    var total = 0.0
    for (i in 0 until length)
        total += get(i)
    return total
}

/**
 * Compute the sum of a sequence of numbers.
 *
 * @param length   the number of values in the sequence
 * @param get      a function to retrieve a value by index
 */
inline fun sumDoubles(length: Int, get: (Int) -> Double): Double {
    // Since the accumulator has the same precision as the values we are summing,
    // use compensated summation to avoid loss of accuracy.
    
    var total = 0.0
    var c = 0.0
    for (i in 0 until length) {
        val y = get(i)-c
        val t = total+y
        c = (t-total) - y
        total = t
    }
    return total
}

/**
 * Compute the index of the minimum element in a sequence of numbers.
 *
 * @param length   the number of values in the sequence
 * @param get      a function to retrieve a value by index
 */
inline fun argMinByte(length: Int, get: (Int) -> Byte): Int {
    var index = 0
    var value = get(0)
    for (i in 1 until length) {
        val newValue = get(i)
        if (newValue < value) {
            index = i
            value = newValue
        }
    }
    return index
}

/**
 * Compute the index of the minimum element in a sequence of numbers.
 *
 * @param length   the number of values in the sequence
 * @param get      a function to retrieve a value by index
 */
inline fun argMinShort(length: Int, get: (Int) -> Short): Int {
    var index = 0
    var value = get(0)
    for (i in 1 until length) {
        val newValue = get(i)
        if (newValue < value) {
            index = i
            value = newValue
        }
    }
    return index
}

/**
 * Compute the index of the minimum element in a sequence of numbers.
 *
 * @param length   the number of values in the sequence
 * @param get      a function to retrieve a value by index
 */
inline fun argMinInt(length: Int, get: (Int) -> Int): Int {
    var index = 0
    var value = get(0)
    for (i in 1 until length) {
        val newValue = get(i)
        if (newValue < value) {
            index = i
            value = newValue
        }
    }
    return index
}

/**
 * Compute the index of the minimum element in a sequence of numbers.
 *
 * @param length   the number of values in the sequence
 * @param get      a function to retrieve a value by index
 */
inline fun argMinLong(length: Int, get: (Int) -> Long): Int {
    var index = 0
    var value = get(0)
    for (i in 1 until length) {
        val newValue = get(i)
        if (newValue < value) {
            index = i
            value = newValue
        }
    }
    return index
}

/**
 * Compute the index of the minimum element in a sequence of numbers.
 *
 * @param length   the number of values in the sequence
 * @param get      a function to retrieve a value by index
 */
inline fun argMinFloat(length: Int, get: (Int) -> Float): Int {
    var index = 0
    var value = get(0)
    for (i in 1 until length) {
        val newValue = get(i)
        if (newValue < value) {
            index = i
            value = newValue
        }
    }
    return index
}

/**
 * Compute the index of the minimum element in a sequence of numbers.
 *
 * @param length   the number of values in the sequence
 * @param get      a function to retrieve a value by index
 */
inline fun argMinDouble(length: Int, get: (Int) -> Double): Int {
    var index = 0
    var value = get(0)
    for (i in 1 until length) {
        val newValue = get(i)
        if (newValue < value) {
            index = i
            value = newValue
        }
    }
    return index
}

/**
 * Compute the index of the minimum element in a sequence of numbers.
 *
 * @param length   the number of values in the sequence
 * @param get      a function to retrieve a value by index
 */
@Suppress("UNCHECKED_CAST")
inline fun <T> argMinGeneric(length: Int, get: (Int) -> T): Int {
    var index = 0
    var value = get(0)
    for (i in 0 until length) {
        val newValue = get(i)
        if (value !is Comparable<*>)
            throw IllegalArgumentException("This can only be called for types that implement Comparable");
        if ((value as Comparable<T>).compareTo(newValue) > 0) {
            index = i
            value = newValue
        }
    }
    return index
}

/**
 * Compute the index of the maximum element in a sequence of numbers.
 *
 * @param length   the number of values in the sequence
 * @param get      a function to retrieve a value by index
 */
inline fun argMaxByte(length: Int, get: (Int) -> Byte): Int {
    var index = 0
    var value = get(0)
    for (i in 1 until length) {
        val newValue = get(i)
        if (newValue > value) {
            index = i
            value = newValue
        }
    }
    return index
}

/**
 * Compute the index of the maximum element in a sequence of numbers.
 *
 * @param length   the number of values in the sequence
 * @param get      a function to retrieve a value by index
 */
inline fun argMaxShort(length: Int, get: (Int) -> Short): Int {
    var index = 0
    var value = get(0)
    for (i in 1 until length) {
        val newValue = get(i)
        if (newValue > value) {
            index = i
            value = newValue
        }
    }
    return index
}

/**
 * Compute the index of the maximum element in a sequence of numbers.
 *
 * @param length   the number of values in the sequence
 * @param get      a function to retrieve a value by index
 */
inline fun argMaxInt(length: Int, get: (Int) -> Int): Int {
    var index = 0
    var value = get(0)
    for (i in 1 until length) {
        val newValue = get(i)
        if (newValue > value) {
            index = i
            value = newValue
        }
    }
    return index
}

/**
 * Compute the index of the maximum element in a sequence of numbers.
 *
 * @param length   the number of values in the sequence
 * @param get      a function to retrieve a value by index
 */
inline fun argMaxLong(length: Int, get: (Int) -> Long): Int {
    var index = 0
    var value = get(0)
    for (i in 1 until length) {
        val newValue = get(i)
        if (newValue > value) {
            index = i
            value = newValue
        }
    }
    return index
}

/**
 * Compute the index of the maximum element in a sequence of numbers.
 *
 * @param length   the number of values in the sequence
 * @param get      a function to retrieve a value by index
 */
inline fun argMaxFloat(length: Int, get: (Int) -> Float): Int {
    var index = 0
    var value = get(0)
    for (i in 1 until length) {
        val newValue = get(i)
        if (newValue > value) {
            index = i
            value = newValue
        }
    }
    return index
}

/**
 * Compute the index of the maximum element in a sequence of numbers.
 *
 * @param length   the number of values in the sequence
 * @param get      a function to retrieve a value by index
 */
inline fun argMaxDouble(length: Int, get: (Int) -> Double): Int {
    var index = 0
    var value = get(0)
    for (i in 1 until length) {
        val newValue = get(i)
        if (newValue > value) {
            index = i
            value = newValue
        }
    }
    return index
}

/**
 * Compute the index of the maximum element in a sequence of numbers.
 *
 * @param length   the number of values in the sequence
 * @param get      a function to retrieve a value by index
 */
inline fun <T> argMaxGeneric(length: Int, get: (Int) -> T): Int {
    var index = 0
    var value = get(0)
    for (i in 0 until length) {
        val newValue = get(i)
        if (value !is Comparable<*>)
            throw IllegalArgumentException("This can only be called for types that implement Comparable");
        if ((value as Comparable<T>).compareTo(newValue) < 0) {
            index = i
            value = newValue
        }
    }
    return index
}

/**
 * Compute a reduction over one axis of an array, returning the result in a new array.
 *
 * @param array     the array to reduce
 * @param f         the reduction function that takes a sequence of values and returns a single value
 * @param axis      the axis to compute the reduction over
 * @param keepdims  if true, the output array has the same number of dimensions as the original one,
 *                  with [axis] having size 1.  If false, the output array has one fewer dimensions
 *                  than the original one.
 */
inline fun <T, reified R> reduceArrayAxis(array: NDArray<T>, crossinline f: (Int, (Int)->T) -> R, axis: Int, keepdims: Boolean): NDArray<R> {
    val shape = array.shape()
    if (axis < 0 || axis >= shape.size)
        throw IllegalArgumentException("Illegal axis: ${axis}")
    val reallyKeepDims = if (shape.size == 1) true else keepdims
    val newShape =
        if (reallyKeepDims)
            IntArray(shape.size, { if (it == axis) 1 else shape[it] })
        else
            IntArray(shape.size-1, { shape[if (it < axis) it else it+1] })
    val index = IntArray(shape.size)
    val newArray = NDArray(*newShape) { newIndex: IntArray ->
        if (reallyKeepDims)
            for (i in 0 until index.size)
                index[i] = newIndex[i]
        else
            for (i in 0 until newIndex.size)
                index[if (i < axis) i else i+1] = newIndex[i]
        f(shape[axis]) {
            index[axis] = it
            array.get(*index)
        }
    }
    return newArray
}