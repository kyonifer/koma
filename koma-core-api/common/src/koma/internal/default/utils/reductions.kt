package koma.internal.default.utils

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
