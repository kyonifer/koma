@file:koma.internal.JvmName("Matrix")
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
