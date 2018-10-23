package koma

import koma.extensions.*
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
fun <T: Number, R: Number> allclose(arr1: NDArray<T>, arr2: NDArray<R>, rtol: Double=1e-05, atol: Double=1e-08) =
    arr1.allClose(arr2, rtol, atol)

/**
 * Compute abs() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("absFloat")
fun abs(arr: NDArray<Float>) =
    arr.map { kotlin.math.abs(it) }

/**
 * Compute abs() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("absDouble")
fun abs(arr: NDArray<Double>) =
    arr.map { kotlin.math.abs(it) }

/**
 * Compute abs() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("absInt")
fun abs(arr: NDArray<Int>) =
    arr.map { kotlin.math.abs(it) }

/**
 * Compute abs() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("absLong")
fun abs(arr: NDArray<Long>) =
    arr.map { kotlin.math.abs(it) }

/**
 * Compute acos() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("acosFloat")
fun acos(arr: NDArray<Float>) =
    arr.map { kotlin.math.acos(it) }

/**
 * Compute acos() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("acosDouble")
fun acos(arr: NDArray<Double>) =
    arr.map { kotlin.math.acos(it) }

/**
 * Compute acosh() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("acoshFloat")
fun acosh(arr: NDArray<Float>) =
    arr.map { kotlin.math.acosh(it) }

/**
 * Compute acosh() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("acoshDouble")
fun acosh(arr: NDArray<Double>) =
    arr.map { kotlin.math.acosh(it) }

/**
 * Compute asin() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("asinFloat")
fun asin(arr: NDArray<Float>) =
    arr.map { kotlin.math.asin(it) }

/**
 * Compute asin() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("asinDouble")
fun asin(arr: NDArray<Double>) =
    arr.map { kotlin.math.asin(it) }

/**
 * Compute asinh() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("asinhFloat")
fun asinh(arr: NDArray<Float>) =
    arr.map { kotlin.math.asinh(it) }

/**
 * Compute asinh() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("asinhDouble")
fun asinh(arr: NDArray<Double>) =
    arr.map { kotlin.math.asinh(it) }

/**
 * Compute atan() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("atanFloat")
fun atan(arr: NDArray<Float>) =
    arr.map { kotlin.math.atan(it) }

/**
 * Compute atan() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("atanDouble")
fun atan(arr: NDArray<Double>) =
    arr.map { kotlin.math.atan(it) }

/**
 * Compute atanh() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("atanhFloat")
fun atanh(arr: NDArray<Float>) =
    arr.map { kotlin.math.atanh(it) }

/**
 * Compute atanh() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("atanhDouble")
fun atanh(arr: NDArray<Double>) =
    arr.map { kotlin.math.atanh(it) }

/**
 * Compute ceil() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("ceilFloat")
fun ceil(arr: NDArray<Float>) =
    arr.map { kotlin.math.ceil(it) }

/**
 * Compute ceil() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("ceilDouble")
fun ceil(arr: NDArray<Double>) =
    arr.map { kotlin.math.ceil(it) }

/**
 * Compute cos() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("cosFloat")
fun cos(arr: NDArray<Float>) =
    arr.map { kotlin.math.cos(it) }

/**
 * Compute cos() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("cosDouble")
fun cos(arr: NDArray<Double>) =
    arr.map { kotlin.math.cos(it) }

/**
 * Compute cosh() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("coshFloat")
fun cosh(arr: NDArray<Float>) =
    arr.map { kotlin.math.cosh(it) }

/**
 * Compute cosh() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("coshDouble")
fun cosh(arr: NDArray<Double>) =
    arr.map { kotlin.math.cosh(it) }

/**
 * Compute exp() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("expFloat")
fun exp(arr: NDArray<Float>) =
    arr.map { kotlin.math.exp(it) }

/**
 * Compute exp() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("expDouble")
fun exp(arr: NDArray<Double>) =
    arr.map { kotlin.math.exp(it) }

/**
 * Compute floor() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("floorFloat")
fun floor(arr: NDArray<Float>) =
    arr.map { kotlin.math.floor(it) }

/**
 * Compute floor() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("floorDouble")
fun floor(arr: NDArray<Double>) =
    arr.map { kotlin.math.floor(it) }

/**
 * Compute ln() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("lnFloat")
fun ln(arr: NDArray<Float>) =
    arr.map { kotlin.math.ln(it) }

/**
 * Compute ln() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("lnDouble")
fun ln(arr: NDArray<Double>) =
    arr.map { kotlin.math.ln(it) }

/**
 * Compute round() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("roundFloat")
fun round(arr: NDArray<Float>) =
    arr.map { kotlin.math.round(it) }

/**
 * Compute round() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("roundDouble")
fun round(arr: NDArray<Double>) =
    arr.map { kotlin.math.round(it) }

/**
 * Compute sign() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("signFloat")
fun sign(arr: NDArray<Float>) =
    arr.map { kotlin.math.sign(it) }

/**
 * Compute sign() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("signDouble")
fun sign(arr: NDArray<Double>) =
    arr.map { kotlin.math.sign(it) }

/**
 * Compute sin() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("sinFloat")
fun sin(arr: NDArray<Float>) =
    arr.map { kotlin.math.sin(it) }

/**
 * Compute sin() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("sinDouble")
fun sin(arr: NDArray<Double>) =
    arr.map { kotlin.math.sin(it) }

/**
 * Compute sinh() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("sinhFloat")
fun sinh(arr: NDArray<Float>) =
    arr.map { kotlin.math.sinh(it) }

/**
 * Compute sinh() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("sinhDouble")
fun sinh(arr: NDArray<Double>) =
    arr.map { kotlin.math.sinh(it) }

/**
 * Compute sqrt() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("sqrtFloat")
fun sqrt(arr: NDArray<Float>) =
    arr.map { kotlin.math.sqrt(it) }

/**
 * Compute sqrt() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("sqrtDouble")
fun sqrt(arr: NDArray<Double>) =
    arr.map { kotlin.math.sqrt(it) }

/**
 * Compute tan() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("tanFloat")
fun tan(arr: NDArray<Float>) =
    arr.map { kotlin.math.tan(it) }

/**
 * Compute tan() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("tanDouble")
fun tan(arr: NDArray<Double>) =
    arr.map { kotlin.math.tan(it) }

/**
 * Compute tanh() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("tanhFloat")
fun tanh(arr: NDArray<Float>) =
    arr.map { kotlin.math.tanh(it) }

/**
 * Compute tanh() of each element of an NDArray and return the result in a new array of the same shape.
 */
@KomaJvmName("tanhDouble")
fun tanh(arr: NDArray<Double>) =
    arr.map { kotlin.math.tanh(it) }

/**
 * Compute the sum of all elements in an NDArray.
 */
@KomaJvmName("sumFloatArray")
fun sum(arr: NDArray<Float>) =
    sumFloats(arr.size) { arr.getFloat(it) }

/**
 * Compute the sum of all elements in an NDArray.
 */
@KomaJvmName("sumDoubleArray")
fun sum(arr: NDArray<Double>) =
    sumDoubles(arr.size) { arr.getDouble(it) }

/**
 * Compute the sum of all elements in an NDArray.
 */
@KomaJvmName("sumByteArray")
fun sum(arr: NDArray<Byte>) =
    sumBytes(arr.size) { arr.getByte(it) }

/**
 * Compute the sum of all elements in an NDArray.
 */
@KomaJvmName("sumShortArray")
fun sum(arr: NDArray<Short>) =
    sumShorts(arr.size) { arr.getShort(it) }

/**
 * Compute the sum of all elements in an NDArray.
 */
@KomaJvmName("sumIntArray")
fun sum(arr: NDArray<Int>) =
    sumInts(arr.size) { arr.getInt(it) }

/**
 * Compute the sum of all elements in an NDArray.
 */
@KomaJvmName("sumLongArray")
fun sum(arr: NDArray<Long>) =
    sumLongs(arr.size) { arr.getLong(it) }

/**
 * Compute the sum of elements along one axis of an array, returning the result in a new array.
 *
 * @param array     the array to compute the sum over
 * @param axis      the axis to compute the sum over
 * @param keepdims  if true, the output array has the same number of dimensions as the original one,
 *                  with [axis] having size 1.  If false, the output array has one fewer dimensions
 *                  than the original one.
 */
@KomaJvmName("sumFloatArrayAxis")
fun sum(arr: NDArray<Float>, axis: Int, keepdims: Boolean=false) =
    reduceArrayAxis(arr, { length: Int, get: (Int) -> Float -> sumFloats(length, get) }, axis, keepdims)

/**
 * Compute the sum of elements along one axis of an array, returning the result in a new array.
 *
 * @param array     the array to compute the sum over
 * @param axis      the axis to compute the sum over
 * @param keepdims  if true, the output array has the same number of dimensions as the original one,
 *                  with [axis] having size 1.  If false, the output array has one fewer dimensions
 *                  than the original one.
 */
@KomaJvmName("sumDoubleArrayAxis")
fun sum(arr: NDArray<Double>, axis: Int, keepdims: Boolean=false) =
    reduceArrayAxis(arr, { length: Int, get: (Int) -> Double -> sumDoubles(length, get) }, axis, keepdims)

/**
 * Compute the sum of elements along one axis of an array, returning the result in a new array.
 *
 * @param array     the array to compute the sum over
 * @param axis      the axis to compute the sum over
 * @param keepdims  if true, the output array has the same number of dimensions as the original one,
 *                  with [axis] having size 1.  If false, the output array has one fewer dimensions
 *                  than the original one.
 */
@KomaJvmName("sumByteArrayAxis")
fun sum(arr: NDArray<Byte>, axis: Int, keepdims: Boolean=false) =
    reduceArrayAxis(arr, { length: Int, get: (Int) -> Byte -> sumBytes(length, get) }, axis, keepdims)

/**
 * Compute the sum of elements along one axis of an array, returning the result in a new array.
 *
 * @param array     the array to compute the sum over
 * @param axis      the axis to compute the sum over
 * @param keepdims  if true, the output array has the same number of dimensions as the original one,
 *                  with [axis] having size 1.  If false, the output array has one fewer dimensions
 *                  than the original one.
 */
@KomaJvmName("sumShortArrayAxis")
fun sum(arr: NDArray<Short>, axis: Int, keepdims: Boolean=false) =
    reduceArrayAxis(arr, { length: Int, get: (Int) -> Short -> sumShorts(length, get) }, axis, keepdims)

/**
 * Compute the sum of elements along one axis of an array, returning the result in a new array.
 *
 * @param array     the array to compute the sum over
 * @param axis      the axis to compute the sum over
 * @param keepdims  if true, the output array has the same number of dimensions as the original one,
 *                  with [axis] having size 1.  If false, the output array has one fewer dimensions
 *                  than the original one.
 */
@KomaJvmName("sumIntArrayAxis")
fun sum(arr: NDArray<Int>, axis: Int, keepdims: Boolean=false) =
    reduceArrayAxis(arr, { length: Int, get: (Int) -> Int -> sumInts(length, get) }, axis, keepdims)

/**
 * Compute the sum of elements along one axis of an array, returning the result in a new array.
 *
 * @param array     the array to compute the sum over
 * @param axis      the axis to compute the sum over
 * @param keepdims  if true, the output array has the same number of dimensions as the original one,
 *                  with [axis] having size 1.  If false, the output array has one fewer dimensions
 *                  than the original one.
 */
@KomaJvmName("sumLongArrayAxis")
fun sum(arr: NDArray<Long>, axis: Int, keepdims: Boolean=false) =
    reduceArrayAxis(arr, { length: Int, get: (Int) -> Long -> sumLongs(length, get) }, axis, keepdims)

/**
 * Compute the mean of all elements in an NDArray.
 */
@KomaJvmName("meanFloatArray")
fun mean(arr: NDArray<Float>) =
    sumFloats(arr.size, { arr.getFloat(it) })/arr.size

/**
 * Compute the mean of all elements in an NDArray.
 */
@KomaJvmName("meanDoubleArray")
fun mean(arr: NDArray<Double>) =
    sumDoubles(arr.size, { arr.getDouble(it) })/arr.size

/**
 * Compute the mean of all elements in an NDArray.
 */
@KomaJvmName("meanByteArray")
fun mean(arr: NDArray<Byte>) =
    sumBytes(arr.size, { arr.getByte(it) }).toDouble()/arr.size

/**
 * Compute the mean of all elements in an NDArray.
 */
@KomaJvmName("meanShortArray")
fun mean(arr: NDArray<Short>) =
    sumShorts(arr.size, { arr.getShort(it) }).toDouble()/arr.size

/**
 * Compute the mean of all elements in an NDArray.
 */
@KomaJvmName("meanIntArray")
fun mean(arr: NDArray<Int>) =
    sumInts(arr.size, { arr.getInt(it) }).toDouble()/arr.size

/**
 * Compute the mean of all elements in an NDArray.
 */
@KomaJvmName("meanLongArray")
fun mean(arr: NDArray<Long>) =
    sumLongs(arr.size, { arr.getLong(it) }).toDouble()/arr.size

/**
 * Compute the mean of elements along one axis of an array, returning the result in a new array.
 *
 * @param array     the array to compute the mean over
 * @param axis      the axis to compute the mean over
 * @param keepdims  if true, the output array has the same number of dimensions as the original one,
 *                  with [axis] having size 1.  If false, the output array has one fewer dimensions
 *                  than the original one.
 */
@KomaJvmName("meanFloatArrayAxis")
fun mean(arr: NDArray<Float>, axis: Int, keepdims: Boolean=false) =
    reduceArrayAxis(arr, { length: Int, get: (Int) -> Float -> sumFloats(length, get)/arr.shape()[axis] }, axis, keepdims)

/**
 * Compute the mean of elements along one axis of an array, returning the result in a new array.
 *
 * @param array     the array to compute the mean over
 * @param axis      the axis to compute the mean over
 * @param keepdims  if true, the output array has the same number of dimensions as the original one,
 *                  with [axis] having size 1.  If false, the output array has one fewer dimensions
 *                  than the original one.
 */
@KomaJvmName("meanDoubleArrayAxis")
fun mean(arr: NDArray<Double>, axis: Int, keepdims: Boolean=false) =
    reduceArrayAxis(arr, { length: Int, get: (Int) -> Double -> sumDoubles(length, get)/arr.shape()[axis] }, axis, keepdims)

/**
 * Compute the mean of elements along one axis of an array, returning the result in a new array.
 *
 * @param array     the array to compute the mean over
 * @param axis      the axis to compute the mean over
 * @param keepdims  if true, the output array has the same number of dimensions as the original one,
 *                  with [axis] having size 1.  If false, the output array has one fewer dimensions
 *                  than the original one.
 */
@KomaJvmName("meanByteArrayAxis")
fun mean(arr: NDArray<Byte>, axis: Int, keepdims: Boolean=false) =
    reduceArrayAxis(arr, { length: Int, get: (Int) -> Byte -> sumBytes(length, get).toDouble()/arr.shape()[axis] }, axis, keepdims)

/**
 * Compute the mean of elements along one axis of an array, returning the result in a new array.
 *
 * @param array     the array to compute the mean over
 * @param axis      the axis to compute the mean over
 * @param keepdims  if true, the output array has the same number of dimensions as the original one,
 *                  with [axis] having size 1.  If false, the output array has one fewer dimensions
 *                  than the original one.
 */
@KomaJvmName("meanShortArrayAxis")
fun mean(arr: NDArray<Short>, axis: Int, keepdims: Boolean=false) =
    reduceArrayAxis(arr, { length: Int, get: (Int) -> Short -> sumShorts(length, get).toDouble()/arr.shape()[axis] }, axis, keepdims)

/**
 * Compute the mean of elements along one axis of an array, returning the result in a new array.
 *
 * @param array     the array to compute the mean over
 * @param axis      the axis to compute the mean over
 * @param keepdims  if true, the output array has the same number of dimensions as the original one,
 *                  with [axis] having size 1.  If false, the output array has one fewer dimensions
 *                  than the original one.
 */
@KomaJvmName("meanIntArrayAxis")
fun mean(arr: NDArray<Int>, axis: Int, keepdims: Boolean=false) =
    reduceArrayAxis(arr, { length: Int, get: (Int) -> Int -> sumInts(length, get).toDouble()/arr.shape()[axis] }, axis, keepdims)

/**
 * Compute the mean of elements along one axis of an array, returning the result in a new array.
 *
 * @param array     the array to compute the mean over
 * @param axis      the axis to compute the mean over
 * @param keepdims  if true, the output array has the same number of dimensions as the original one,
 *                  with [axis] having size 1.  If false, the output array has one fewer dimensions
 *                  than the original one.
 */
@KomaJvmName("meanLongArrayAxis")
fun mean(arr: NDArray<Long>, axis: Int, keepdims: Boolean=false) =
    reduceArrayAxis(arr, { length: Int, get: (Int) -> Long -> sumLongs(length, get).toDouble()/arr.shape()[axis] }, axis, keepdims)

/**
 * Find the linear index of the minimum element in an NDArray.
 */
fun <T: Comparable<T>> argMin(arr: NDArray<T>): Int =
    arr.argMinInternal()

/**
 * Find the linear index of the minimum element along one axis of an array, returning the result in a new array.
 *
 * @param array     the array to compute the minimum over
 * @param axis      the axis to compute the minimum over
 * @param keepdims  if true, the output array has the same number of dimensions as the original one,
 *                  with [axis] having size 1.  If false, the output array has one fewer dimensions
 *                  than the original one.
 */
fun <T: Comparable<T>> argMin(array: NDArray<T>, axis: Int, keepdims: Boolean=false) =
        array.argMin(axis, keepdims)


/**
 * Find the linear index of the maximum element in an NDArray.
 */
fun <T: Comparable<T>> argMax(arr: NDArray<T>): Int =
    arr.argMaxInternal()

/**
 * Find the linear index of the maximum element along one axis of an array, returning the result in a new array.
 *
 * @param array     the array to compute the maximum over
 * @param axis      the axis to compute the maximum over
 * @param keepdims  if true, the output array has the same number of dimensions as the original one,
 *                  with [axis] having size 1.  If false, the output array has one fewer dimensions
 *                  than the original one.
 */
fun <T: Comparable<T>> argMax(array: NDArray<T>, axis: Int, keepdims: Boolean=false) =
        array.argMax(axis, keepdims)

/**
 * Find the value of the minimum element in an NDArray.
 */
fun <T: Comparable<T>> min(arr: NDArray<T>): T =
    arr.minInternal()

/**
 * Find the minimum element along one axis of an array, returning the result in a new array.
 *
 * @param array     the array to compute the minimum over
 * @param axis      the axis to compute the minimum over
 * @param keepdims  if true, the output array has the same number of dimensions as the original one,
 *                  with [axis] having size 1.  If false, the output array has one fewer dimensions
 *                  than the original one.
 */
inline fun <reified T: Comparable<T>> min(array: NDArray<T>, axis: Int, keepdims: Boolean=false) =
        array.min(axis, keepdims)

/**
 * Find the value of the maximum element in an NDArray.
 */
fun <T: Comparable<T>> max(arr: NDArray<T>): T =
    arr.maxInternal()

/**
 * Find the maximum element along one axis of an array, returning the result in a new array.
 *
 * @param array     the array to compute the maximum over
 * @param axis      the axis to compute the maximum over
 * @param keepdims  if true, the output array has the same number of dimensions as the original one,
 *                  with [axis] having size 1.  If false, the output array has one fewer dimensions
 *                  than the original one.
 */
inline fun <reified T: Comparable<T>> max(array: NDArray<T>, axis: Int, keepdims: Boolean=false) =
        array.max(axis, keepdims)
