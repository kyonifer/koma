package koma

import koma.extensions.map
import koma.internal.KomaJvmName
import koma.internal.default.utils.*
import koma.ndarray.NDArray

/**
 * This file contains top-level common mathematical functions that operate on
 * NDArrays. These definitions follow numpy as close as possible.
 */

/**
 * Determine whether every element of one NDArray is sufficiently close to the corresponding element
 * of another array.  Two elements are considered close if
 *
 * abs(ele1 - ele2) < (atol + rtol * abs(ele1))
 *
 * @param arr1    the first array to compare
 * @param arr2    the second array to compare
 * @param rtol    the maximum relative (i.e. fractional) difference to allow between elements
 * @param atol    the maximum absolute difference to allow between elements
 */
fun <T, R> allclose(arr1: NDArray<T>, arr2: NDArray<R>, rtol: Double=1e-05, atol: Double=1e-08): Boolean =
        arr1.allClose(arr2, rtol, atol)

/**
 * Compute abs() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("absFloat")
fun abs(arr: NDArray<Float>): NDArray<Float> {
    return arr.map { kotlin.math.abs(it) }
}

/**
 * Compute abs() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("absDouble")
fun abs(arr: NDArray<Double>): NDArray<Double> {
    return arr.map { kotlin.math.abs(it) }
}

/**
 * Compute abs() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("absInt")
fun abs(arr: NDArray<Int>): NDArray<Int> {
    return arr.map { kotlin.math.abs(it) }
}

/**
 * Compute abs() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("absLong")
fun abs(arr: NDArray<Long>): NDArray<Long> {
    return arr.map { kotlin.math.abs(it) }
}

/**
 * Compute acos() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("acosFloat")
fun acos(arr: NDArray<Float>): NDArray<Float> {
    return arr.map { kotlin.math.acos(it) }
}

/**
 * Compute acos() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("acosDouble")
fun acos(arr: NDArray<Double>): NDArray<Double> {
    return arr.map { kotlin.math.acos(it) }
}

/**
 * Compute acosh() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("acoshFloat")
fun acosh(arr: NDArray<Float>): NDArray<Float> {
    return arr.map { kotlin.math.acosh(it) }
}

/**
 * Compute acosh() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("acoshDouble")
fun acosh(arr: NDArray<Double>): NDArray<Double> {
    return arr.map { kotlin.math.acosh(it) }
}

/**
 * Compute asin() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("asinFloat")
fun asin(arr: NDArray<Float>): NDArray<Float> {
    return arr.map { kotlin.math.asin(it) }
}

/**
 * Compute asin() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("asinDouble")
fun asin(arr: NDArray<Double>): NDArray<Double> {
    return arr.map { kotlin.math.asin(it) }
}

/**
 * Compute asinh() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("asinhFloat")
fun asinh(arr: NDArray<Float>): NDArray<Float> {
    return arr.map { kotlin.math.asinh(it) }
}

/**
 * Compute asinh() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("asinhDouble")
fun asinh(arr: NDArray<Double>): NDArray<Double> {
    return arr.map { kotlin.math.asinh(it) }
}

/**
 * Compute atan() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("atanFloat")
fun atan(arr: NDArray<Float>): NDArray<Float> {
    return arr.map { kotlin.math.atan(it) }
}

/**
 * Compute atan() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("atanDouble")
fun atan(arr: NDArray<Double>): NDArray<Double> {
    return arr.map { kotlin.math.atan(it) }
}

/**
 * Compute atanh() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("atanhFloat")
fun atanh(arr: NDArray<Float>): NDArray<Float> {
    return arr.map { kotlin.math.atanh(it) }
}

/**
 * Compute atanh() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("atanhDouble")
fun atanh(arr: NDArray<Double>): NDArray<Double> {
    return arr.map { kotlin.math.atanh(it) }
}

/**
 * Compute ceil() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("ceilFloat")
fun ceil(arr: NDArray<Float>): NDArray<Float> {
    return arr.map { kotlin.math.ceil(it) }
}

/**
 * Compute ceil() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("ceilDouble")
fun ceil(arr: NDArray<Double>): NDArray<Double> {
    return arr.map { kotlin.math.ceil(it) }
}

/**
 * Compute cos() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("cosFloat")
fun cos(arr: NDArray<Float>): NDArray<Float> {
    return arr.map { kotlin.math.cos(it) }
}

/**
 * Compute cos() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("cosDouble")
fun cos(arr: NDArray<Double>): NDArray<Double> {
    return arr.map { kotlin.math.cos(it) }
}

/**
 * Compute cosh() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("coshFloat")
fun cosh(arr: NDArray<Float>): NDArray<Float> {
    return arr.map { kotlin.math.cosh(it) }
}

/**
 * Compute cosh() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("coshDouble")
fun cosh(arr: NDArray<Double>): NDArray<Double> {
    return arr.map { kotlin.math.cosh(it) }
}

/**
 * Compute exp() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("expFloat")
fun exp(arr: NDArray<Float>): NDArray<Float> {
    return arr.map { kotlin.math.exp(it) }
}

/**
 * Compute exp() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("expDouble")
fun exp(arr: NDArray<Double>): NDArray<Double> {
    return arr.map { kotlin.math.exp(it) }
}

/**
 * Compute floor() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("floorFloat")
fun floor(arr: NDArray<Float>): NDArray<Float> {
    return arr.map { kotlin.math.floor(it) }
}

/**
 * Compute floor() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("floorDouble")
fun floor(arr: NDArray<Double>): NDArray<Double> {
    return arr.map { kotlin.math.floor(it) }
}

/**
 * Compute ln() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("lnFloat")
fun ln(arr: NDArray<Float>): NDArray<Float> {
    return arr.map { kotlin.math.ln(it) }
}

/**
 * Compute ln() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("lnDouble")
fun ln(arr: NDArray<Double>): NDArray<Double> {
    return arr.map { kotlin.math.ln(it) }
}

/**
 * Compute round() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("roundFloat")
fun round(arr: NDArray<Float>): NDArray<Float> {
    return arr.map { kotlin.math.round(it) }
}

/**
 * Compute round() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("roundDouble")
fun round(arr: NDArray<Double>): NDArray<Double> {
    return arr.map { kotlin.math.round(it) }
}

/**
 * Compute sign() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("signFloat")
fun sign(arr: NDArray<Float>): NDArray<Float> {
    return arr.map { kotlin.math.sign(it) }
}

/**
 * Compute sign() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("signDouble")
fun sign(arr: NDArray<Double>): NDArray<Double> {
    return arr.map { kotlin.math.sign(it) }
}

/**
 * Compute sin() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("sinFloat")
fun sin(arr: NDArray<Float>): NDArray<Float> {
    return arr.map { kotlin.math.sin(it) }
}

/**
 * Compute sin() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("sinDouble")
fun sin(arr: NDArray<Double>): NDArray<Double> {
    return arr.map { kotlin.math.sin(it) }
}

/**
 * Compute sinh() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("sinhFloat")
fun sinh(arr: NDArray<Float>): NDArray<Float> {
    return arr.map { kotlin.math.sinh(it) }
}

/**
 * Compute sinh() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("sinhDouble")
fun sinh(arr: NDArray<Double>): NDArray<Double> {
    return arr.map { kotlin.math.sinh(it) }
}

/**
 * Compute sqrt() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("sqrtFloat")
fun sqrt(arr: NDArray<Float>): NDArray<Float> {
    return arr.map { kotlin.math.sqrt(it) }
}

/**
 * Compute sqrt() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("sqrtDouble")
fun sqrt(arr: NDArray<Double>): NDArray<Double> {
    return arr.map { kotlin.math.sqrt(it) }
}

/**
 * Compute tan() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("tanFloat")
fun tan(arr: NDArray<Float>): NDArray<Float> {
    return arr.map { kotlin.math.tan(it) }
}

/**
 * Compute tan() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("tanDouble")
fun tan(arr: NDArray<Double>): NDArray<Double> {
    return arr.map { kotlin.math.tan(it) }
}

/**
 * Compute tanh() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("tanhFloat")
fun tanh(arr: NDArray<Float>): NDArray<Float> {
    return arr.map { kotlin.math.tanh(it) }
}

/**
 * Compute tanh() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("tanhDouble")
fun tanh(arr: NDArray<Double>): NDArray<Double> {
    return arr.map { kotlin.math.tanh(it) }
}

/**
 * Compute the sum of all elements in an NDArray.
 */
@KomaJvmName("sumFloatArray")
fun sum(arr: NDArray<Float>): Double {
    return sumFloats(arr.size, { arr.getFloat(it) })
}

/**
 * Compute the sum of all elements in an NDArray.
 */
@KomaJvmName("sumDoubleArray")
fun sum(arr: NDArray<Double>): Double {
    return sumDoubles(arr.size, { arr.getDouble(it) })
}

/**
 * Compute the sum of all elements in an NDArray.
 */
@KomaJvmName("sumByteArray")
fun sum(arr: NDArray<Byte>): Long {
    return sumBytes(arr.size, { arr.getByte(it) })
}

/**
 * Compute the sum of all elements in an NDArray.
 */
@KomaJvmName("sumShortArray")
fun sum(arr: NDArray<Short>): Long {
    return sumShorts(arr.size, { arr.getShort(it) })
}

/**
 * Compute the sum of all elements in an NDArray.
 */
@KomaJvmName("sumIntArray")
fun sum(arr: NDArray<Int>): Long {
    return sumInts(arr.size, { arr.getInt(it) })
}

/**
 * Compute the sum of all elements in an NDArray.
 */
@KomaJvmName("sumLongArray")
fun sum(arr: NDArray<Long>): Long {
    return sumLongs(arr.size, { arr.getLong(it) })
}

/**
 * Compute the mean of all elements in an NDArray.
 */
@KomaJvmName("meanFloatArray")
fun mean(arr: NDArray<Float>): Double {
    return sumFloats(arr.size, { arr.getFloat(it) })/arr.size
}

/**
 * Compute the mean of all elements in an NDArray.
 */
@KomaJvmName("meanDoubleArray")
fun mean(arr: NDArray<Double>): Double {
    return sumDoubles(arr.size, { arr.getDouble(it) })/arr.size
}

/**
 * Compute the mean of all elements in an NDArray.
 */
@KomaJvmName("meanByteArray")
fun mean(arr: NDArray<Byte>): Double {
    return sumBytes(arr.size, { arr.getByte(it) }).toDouble()/arr.size
}

/**
 * Compute the mean of all elements in an NDArray.
 */
@KomaJvmName("meanShortArray")
fun mean(arr: NDArray<Short>): Double {
    return sumShorts(arr.size, { arr.getShort(it) }).toDouble()/arr.size
}

/**
 * Compute the mean of all elements in an NDArray.
 */
@KomaJvmName("meanIntArray")
fun mean(arr: NDArray<Int>): Double {
    return sumInts(arr.size, { arr.getInt(it) }).toDouble()/arr.size
}

/**
 * Compute the mean of all elements in an NDArray.
 */
@KomaJvmName("meanLongArray")
fun mean(arr: NDArray<Long>): Double {
    return sumLongs(arr.size, { arr.getLong(it) }).toDouble()/arr.size
}

/**
 * Find the linear index of the minimum element in an NDArray.
 * If the array contains non-comparable values, this throws an exception.
 */
fun <T> argMin(arr: NDArray<T>): Int {
    return arr.argMin()
}

/**
 * Find the linear index of the maximum element in an NDArray.
 * If the array contains non-comparable values, this throws an exception.
 */
fun <T> argMax(arr: NDArray<T>): Int {
    return arr.argMax()
}

/**
 * Find the value of the minimum element in an NDArray.
 * If the array contains non-comparable values, this throws an exception.
 */
fun <T> min(arr: NDArray<T>): T {
    return arr.min()
}

/**
 * Find the value of the maximum element in an NDArray.
 * If the array contains non-comparable values, this throws an exception.
 */
fun <T> max(arr: NDArray<T>): T {
    return arr.max()
}
