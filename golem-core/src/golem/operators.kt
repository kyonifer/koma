/**
 * This file contains operators definitions to support overloading Kotlin operators.
 *
 * Allows for things like
 *
 * a[1,2] = 3
 * a*b
 * a[1..3,4] = 5
 * a[0..1,2..3] = mat[1,2 end 3,4]
 */

@file:JvmName("Golem")
@file:JvmMultifileClass

package golem

import golem.matrix.*
import golem.polyfill.annotations.*

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
 * Allow set-index overloading for integers. See [golem.matrix.Matrix<T>.set] for doubles.
 */
operator fun Matrix<Double>.set(index: Int, value: Int) = this.set(index, value.toDouble())

/**
 * Allow set-index overloading for integers. See [golem.matrix.Matrix<T>.set] for doubles.
 */
operator fun Matrix<Double>.set(row: Int, col: Int, value: Int) = this.set(row, col, value.toDouble())


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


// Todo: ND array:
//fun Mat.get(vararg index: Long) = this.
//fun Mat.get(vararg index: Int) =this.
