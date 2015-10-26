/**
 * This file contains top-level common mathematical functions that operate on
 * Matrices. These definitions follow numpy as close as possible, and allow
 * one to do things like cos(randn(5,5))
 */

@file:JvmName("Golem")
@file:JvmMultifileClass

package golem

import golem.matrix.Matrix

/**
 * Returns a matrix of the arccos of each element in the input matrix.
 *
 * @return A matrix consisting of the operation performed element-wise.
 */
fun acos(arr: Matrix<Double>) = arr.mapMat { java.lang.Math.acos(it) }
/**
 * Returns a matrix of the arcsin of each element in the input matrix.
 *
 * @return A matrix consisting of the operation performed element-wise.
 */
fun asin(arr: Matrix<Double>) = arr.mapMat { java.lang.Math.asin(it) }
/**
 * Returns a matrix of the arctan of each element in the input matrix.
 *
 * @return A matrix consisting of the operation performed element-wise.
 */
fun atan(arr: Matrix<Double>) = arr.mapMat { java.lang.Math.atan(it) }
/**
 * Returns a matrix of the absolute value of each element in the input matrix.
 *
 * @return A matrix consisting of the operation performed element-wise.
 */
fun abs(arr: Matrix<Double>) = arr.mapMat {java.lang.Math.abs(it)}

/**
 * Rounds each element to the integer which is nearest to the element and still less than the
 * element (i.e. truncation).
 *
 * @return A matrix consisting of the operation performed element-wise.
 */
fun ceil(arr: Matrix<Double>) = arr.mapMat {java.lang.Math.ceil(it)}

/**
 * Returns a matrix of the cos of each element in the input matrix.
 *
 * @return A matrix consisting of the operation performed element-wise.
 *
 */
fun cos(arr: Matrix<Double>) = arr.mapMat {java.lang.Math.cos(it)}
/**
 * Returns a matrix of E.pow(element) for each element in the input matrix.
 *
 * @return A matrix consisting of the operation performed element-wise.
 *
 */
fun exp(arr: Matrix<Double>) = arr.mapMat {java.lang.Math.exp(it)}
/**
 * Returns a matrix of the natural logarithm of each element in the input matrix.
 *
 * @return A matrix consisting of the operation performed element-wise.
 *
 */
fun log(arr: Matrix<Double>) = arr.mapMat {java.lang.Math.log(it)}
/**
 * Returns a matrix consisting of each element in the input matrix raised to the given power.
 *
 * @return A matrix consisting of the operation performed element-wise.
 *
 */
fun epow(arr: Matrix<Double>, num: Double) = arr.epow(num)
/**
 * Returns a matrix which is the input matrix multiplied by itself num times (NOT elementwise multiplication!!)
 *
 * @return A matrix consisting of num matrix multiplies of the input.
 *
 */
fun pow(arr: Matrix<Double>, num:Int) = arr.pow(num)
/**
 * Calculates a matrix consisting of the sign of each element in the input matrix.
 * Returns -1 for positive values, -1 for negative values, 0 for 0.
 * @return A matrix consisting of the operation performed element-wise.
 */
fun sign(arr: Matrix<Double>) = arr.mapMat {java.lang.Math.signum(it)}
/**
 * Returns a matrix of the sin of each element in the input matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 *
 */
fun sin(arr: Matrix<Double>) = arr.mapMat {java.lang.Math.sin(it)}
/**
 * Returns a matrix of the sqrt of each element in the input matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 *
 */
fun sqrt(arr: Matrix<Double>) = arr.mapMat {java.lang.Math.sqrt(it)}
/**
 * Returns a matrix of the tan of each element in the input matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 *
 */
fun tan(arr: Matrix<Double>) = arr.mapMat {java.lang.Math.tan(it)}
/**
 * Rounds each element to the nearest integer value. For elements exactly between integers,
 * choose the highest value.
 */
fun round(arr: Matrix<Double>) = arr.mapMat {java.lang.Math.round(it).toDouble()}

/**
 * Rounds each element to the integer which is nearest to the element and still less than the
 * element (i.e. truncation).
 */
fun floor(arr: Matrix<Double>) = arr.mapMat {java.lang.Math.floor(it)}
/**
 * Returns a matrix of the log-base-b of each element in the input matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 *
 */
fun logb(base: Int, arr: Matrix<Double>) = arr.mapMat {java.lang.Math.log(it) / Math.log(base.toDouble())}

// Matrix funcs
fun diag(arr: Matrix<Double>) = arr.diag()

/**
 * Calculates the cumulative (ongoing) sum of a matrix's elements. For example,
 * cumsum(mat[1,2,3]) would return mat[1,3,6].
 *
 * @param arr The matrix to calculate the cumsum on. Sum will be computed in row-major order.
 *
 * @return A 1xarr.numRows*arr.numCols vector storing the ongoing cumsum.
 *
 */
fun cumsum(arr: Matrix<Double>) = arr.cumSum()
/**
 * Returns the max element in the input matrix
 *
 * @return The maximum value
 *
 */
fun max(arr: Matrix<Double>) = arr.max()
/**
 * Returns the mean element in the input matrix
 *
 * @return The maximum value
 *
 */
fun mean(arr: Matrix<Double>) = arr.mean()
/**
 * Returns the min element in the input matrix
 *
 * @return The maximum value
 *
 */
fun min(arr: Matrix<Double>) = arr.min()
/**
 * Returns the index of the max element in the input matrix
 *
 * @return The maximum value
 *
 */
fun argMax(arr: Matrix<Double>) = arr.argMax()
/**
 * Returns the index of the mean element in the input matrix
 *
 * @return The maximum value
 *
 */
fun argMean(arr: Matrix<Double>) = arr.argMean()
/**
 * Returns the index of the min element in the input matrix
 *
 * @return The maximum value
 *
 */
fun argMin(arr: Matrix<Double>) = arr.argMin()
/**
 * Returns the L2 norm of the input vector
 *
 * @return The maximum value
 *
 */
fun norm(arr: Matrix<Double>) = arr.norm()

// Adv funcs
fun expm(A: Matrix<Double>) = A.expm()



// TODO:
//fun fft(arr: Matrix)
//fun hstack(vararg arrs: Matrix) = .hstack(*arrs)
//fun vstack(vararg arrs: Matrix) = .vstack(*arrs)
