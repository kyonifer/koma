@file:koma.internal.JvmName("MatrixExtensions")
@file:koma.internal.JvmMultifileClass

package koma.extensions

import koma.matrix.Matrix

/**
 * Allow operator overloading with non-Double scalars
 */
operator fun Matrix<Double>.plus(value: Int) = this.plus(value.toDouble())

/**
 * Allow operator overloading with non-Double scalars
 */
operator fun Matrix<Double>.minus(value: Int) = this.minus(value.toDouble())

/**
 * Allow infix operator "a emul b" to be element-wise multiplication of two matrices.
 */
infix fun Matrix<Double>.emul(other: Matrix<Double>) = this.elementTimes(other)



/**
 * Add a scalar to a matrix
 */
operator fun Double.plus(other: Matrix<Double>) = other.plus(this)

/**
 * Add a scalar to a matrix
 */
operator fun Int.plus(other: Matrix<Double>) = other.plus(this)

/**
 * Subtract a matrix from a scala
 */
operator fun Double.minus(other: Matrix<Double>) = other * -1 + this

/**
 * Subtract a matrix from a scala
 */
operator fun Int.minus(other: Matrix<Double>) = other * -1 + this

/**
 * Multiply a scalar by a matrix
 */
operator fun Double.times(other: Matrix<Double>) = other.times(this)

/**
 * Multiply a scalar by a matrix
 */
operator fun Int.times(other: Matrix<Double>) = other.times(this.toDouble())

/**
 * Multiply a scalar by a matrix
 */
operator fun Matrix<Double>.times(other: Int) = this * other.toDouble()

/**
 * Convert an `Iterable<T>` to a `Matrix<Double>`
 */
fun <T, N: Number> Iterable<T>.toMatrix(vararg selectors: (T) -> N): Matrix<kotlin.Double> {

    val items = toList()

    return koma.fill(items.size, selectors.size) { row, col -> selectors[col](items[row]).toDouble() }
}

/**
 * Convert a `Iterable<T>` to a single column `Matrix<Double>`
 */
fun <T, N: Number> Iterable<T>.toVector(selector: (T) -> N): Matrix<kotlin.Double> {

    val items = toList()

    return koma.fill(items.size, 1) { row, _ -> selector(items[row]).toDouble() }
}


/**
 * Convert a `Sequence<T>` to a `Matrix<Double>`
 */
fun <T, N: Number> Sequence<T>.toMatrix(vararg selectors: (T) -> N): Matrix<kotlin.Double> = toList().toMatrix(*selectors)

@Suppress("ReplaceGetOrSet")
        /**
 * A helper function that allows for quick construction of matrix literals.
 *
 * For example, one can write
 * ```
 * var a = matrixOf[1,2,3 end
 *                  4,5,6]
 * ```
 *
 * to get a 2x3 [Matrix<Double>] with the given values. `end` is a helper object that indicates the end of a row
 * to this object. Note that one currently cannot use this function to generate a column vector:
 *
 * ```// ERROR:```
 *
 * ```matrixOf[1 end 2 end 3]```
 *
 * Instead do this:
 *
 * ```// Define a column vector by transposing a row-vector```
 *
 * ```matrixOf[1, 2, 3].T```
 */
fun matrixOf(vararg ts: Any): Matrix<Double> = koma.mat.get(*ts)


@Suppress("ReplaceGetOrSet")
/**
 * A helper function that allows for quick construction of a vector implemented as a Matrix.
 *
 * For example, one can write
 * ```
 * var a = vectorOf[1,2,3]
 * ```
 */
fun vectorOf(vararg ts: Any): Matrix<Double>  {
    if (ts.any { it is Pair<*,*> }) throw Exception("There can only be one row in a vector!")
    return koma.mat.get(*ts).T
}