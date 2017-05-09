/**
 * This file contains top-level common mathematical functions that operate on
 * Matrices. These definitions follow numpy as close as possible, and allow
 * one to do things like cos(randn(5,5))
 */

@file:JvmName("Koma")
@file:JvmMultifileClass

package koma

import koma.matrix.*
import koma.matrix.DoubleMatrix
import koma.platformsupport.*
import koma.polyfill.annotations.*

/**
 * Returns a matrix of the arccos of each element in the input matrix.
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 */
fun acos(arr: DoubleMatrix) = arr.mapMat { Math.acos(it) }

/**
 * Returns a matrix of the arcsin of each element in the input matrix.
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 */
fun asin(arr: DoubleMatrix) = arr.mapMat { Math.asin(it) }

/**
 * Returns a matrix of the arctan of each element in the input matrix.
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 */
fun atan(arr: DoubleMatrix) = arr.mapMat { Math.atan(it) }

/**
 * Returns a matrix of the absolute value of each element in the input matrix.
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 */
fun abs(arr: DoubleMatrix) = arr.mapMat { Math.abs(it) }

/**
 * Rounds each element to the integer which is nearest to the element and still less than the
 * element (i.e. truncation).
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 */
// explicit toDouble() for javascript
fun ceil(arr: DoubleMatrix) = arr.mapMat { Math.ceil(it).toDouble() }

/**
 * Returns a matrix of the cos of each element in the input matrix.
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 *
 */
fun cos(arr: DoubleMatrix) = arr.mapMat { Math.cos(it) }

/**
 * Returns a matrix of E.pow(element) for each element in the input matrix.
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 *
 */
fun exp(arr: DoubleMatrix) = arr.mapMat { Math.exp(it) }

/**
 * Returns a matrix of the natural logarithm of each element in the input matrix.
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 *
 */
fun log(arr: DoubleMatrix) = arr.mapMat { Math.log(it) }

/**
 * Returns a matrix consisting of each element in the input matrix raised to the given power.
 *
 * @param arr An arbitrarily sized matrix
 * @param num the power to raise the matrix to.
 *
 * @return A matrix consisting of the operation performed element-wise.
 *
 */
fun epow(arr: DoubleMatrix, num: Double) = arr.epow(num)

/**
 * Returns a matrix which is the input matrix multiplied by itself num times (NOT elementwise multiplication!!).
 * For elementwise see [epow].
 *
 * @param arr An arbitrarily sized matrix
 * @param num The integer power
 *
 * @return A matrix consisting of num matrix multiplies of the input.
 *
 */
fun pow(arr: DoubleMatrix, num: Int) = arr.pow(num)

/**
 * Calculates a matrix consisting of the sign of each element in the input matrix.
 * Returns -1 for positive values, -1 for negative values, 0 for 0.
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 */
fun sign(arr: DoubleMatrix) = arr.mapMat { signum(it) }

/**
 * Returns a matrix of the sin of each element in the input matrix
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 *
 */
fun sin(arr: DoubleMatrix) = arr.mapMat { Math.sin(it) }

/**
 * Returns a matrix of the sqrt of each element in the input matrix. Does
 * not yet support complex numbers.
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 *
 */
fun sqrt(arr: DoubleMatrix) = arr.mapMat { Math.sqrt(it) }

/**
 * Returns a matrix of the tan of each element in the input matrix
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 *
 */
fun tan(arr: DoubleMatrix) = arr.mapMat { Math.tan(it) }

/**
 * Rounds each element to the nearest integer value. For elements exactly between integers,
 * choose the highest value.
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 */
fun round(arr: DoubleMatrix) = arr.mapMat { Math.round(it).toDouble() }

/**
 * Rounds each element to the integer which is nearest to the element and still less than the
 * element (i.e. truncation).
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 */
// explicit toDouble() for javascript
fun floor(arr: DoubleMatrix) = arr.mapMat { Math.floor(it).toDouble() }

/**
 * Returns a matrix of the log-base-b of each element in the input matrix
 *
 * @param arr An arbitrarily sized matrix
 * @param base the base of the log (i.e. performs log-base-[base] of [arr]
 *
 * @return A matrix consisting of the operation performed element-wise.
 *
 */
fun logb(base: Int, arr: DoubleMatrix) = arr.mapMat { Math.log(it) / Math.log(base.toDouble()) }

/**
 * Extracts the diagonal of the matrix.
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return a Nx1 column vector.
 */
fun diag(arr: DoubleMatrix) = arr.diag().asColVector()

/**
 * Calculates the cumulative (ongoing) sum of a matrix's elements. For example,
 * ```cumsum(mat[1,2,3])``` would return ```mat[1,3,6]```.
 *
 * @param arr The matrix to calculate the cumsum on. Sum will be computed in row-major order.
 *
 * @return A 1x(arr.numRows*arr.numCols) vector storing the ongoing cumsum.
 *
 */
fun cumsum(arr: DoubleMatrix) = arr.cumSum()

/**
 * Returns the max element in the input matrix
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return The maximum value
 *
 */
fun max(arr: DoubleMatrix) = arr.max()

/**
 * Returns the mean element in the input matrix
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return The maximum value
 *
 */
fun mean(arr: DoubleMatrix) = arr.mean()

/**
 * Returns the min element in the input matrix
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return The maximum value
 *
 */
fun min(arr: DoubleMatrix) = arr.min()

/**
 * Returns the index of the max element in the input matrix
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return The maximum value
 *
 */
fun argMax(arr: DoubleMatrix) = arr.argMax()

/**
 * Returns the index of the min element in the input matrix
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return The maximum value
 *
 */
fun argMin(arr: DoubleMatrix) = arr.argMin()

/**
 * Returns the L2 norm of the input vector for vectors.
 *
 * TODO: Generalize this to matrices
 *
 * @param arr A Nx1 or 1xN vector
 *
 * @return The maximum value
 *
 */
fun norm(arr: DoubleMatrix) = arr.norm()

// Adv funcs
/**
 * Calculates the matrix exponential of the input matrix. Note that this is
 * NOT the same thing as the elementwise exponential function.
 *
 * @param A The input matrix
 * @return e to the A
 */
fun expm(A: DoubleMatrix) = A.expm()

/**
 * Converts a 3x1 or 1x3 vector of angles into the skew symmetric matrix
 * equivalent.
 *
 * @param angles The input matrix
 * @Return 3x3 skew symmetric matrix
 */
fun skew(angles: DoubleMatrix): DoubleMatrix {
    val s = mat [0, -angles[2], angles[1] end
            angles[2], 0, -angles[0] end
            -angles[1], angles[0], 0]
    return s
}

/**
 * Calculates the cross product of two vectors
 */
fun cross(vec1: DoubleMatrix, vec2: DoubleMatrix) = skew(vec1) * vec2

/**
 * Calculates the cross product of two vectors
 */
fun dot(vec1: DoubleMatrix, vec2: DoubleMatrix) = (vec1.asRowVector() * vec2.asColVector())[0]

fun hstack(vararg arrs: DoubleMatrix): DoubleMatrix {
    val outRows = arrs[0].numRows()
    var outCols = 0
    arrs.forEach { if (it.numRows() != outRows) throw IllegalArgumentException("All matrices passed to hstack must have the same number of rows.") }
    arrs.forEach { outCols += it.numCols() }

    val out = zeros(outRows, outCols)
    var cursor = 0
    arrs.forEach {
        out[0..outRows - 1, cursor..(cursor + it.numCols() - 1)] = it
        cursor += it.numCols()
    }
    return out
}

fun vstack(vararg arrs: DoubleMatrix): DoubleMatrix {
    val outCols = arrs[0].numCols()
    var outRows = 0
    arrs.forEach { if (it.numCols() != outCols) throw IllegalArgumentException("All matrices passed to vstack must have the same number of cols.") }
    arrs.forEach { outRows += it.numRows() }

    val out = zeros(outRows, outCols)
    var cursor = 0
    arrs.forEach {
        out[cursor..(cursor + it.numRows() - 1), 0..outCols - 1] = it
        cursor += it.numRows()
    }
    return out
}

// TODO:
//fun fft(arr: Matrix)
