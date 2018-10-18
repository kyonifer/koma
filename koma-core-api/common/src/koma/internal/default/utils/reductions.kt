package koma.internal.default.utils

/**
 * This file defines functions that take a sequence of values as input and compute a single value from them.
 */

/**
 * Compute the sum of a sequence of numbers.
 *
 * @param length   the number of values in the sequence
 * @param get      a function to retrieve a value by index
 */
fun sumBytes(length: Int, get: (Int) -> Byte): Long {
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
fun sumShorts(length: Int, get: (Int) -> Short): Long {
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
fun sumInts(length: Int, get: (Int) -> Int): Long {
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
fun sumLongs(length: Int, get: (Int) -> Long): Long {
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
fun sumFloats(length: Int, get: (Int) -> Float): Double {
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
fun sumDoubles(length: Int, get: (Int) -> Double): Double {
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
