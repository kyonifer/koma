/**
 * This file defines a set of functions to create new matrices. Follows
 * numpy conventions as closely as possible. For example, a 3x3 zero
 * matrix can be created via zeros(3,3)
 */

@file:JvmName("Golem")
@file:JvmMultifileClass

package golem

import golem.matrix.*

/**
 * Creates a zero-filled matrix with the given size
 */
fun zeros(rows: Int, cols: Int): Matrix<Double> = factory.zeros(rows, cols)

/**
 * Creates a square zero-filled matrix with the given size
 */
fun zeros(size: Int): Matrix<Double> = factory.zeros(size, size)

/**
 * Creates a matrix filled with the given range of values.
 */
fun create(data: IntRange): Matrix<Double> = factory.create(data)

/**
 * Creates a matrix filled with the given set of values as a row-vector.
 */
fun create(data: DoubleArray): Matrix<Double> = factory.create(data).asRowVector()

/**
 * Creates a matrix filled with the given data, assuming input is row major.
 */
fun create(data: Array<DoubleArray>): Matrix<Double> = factory.create(data)

/**
 * Creates a one-filled square matrix with the given size
 */
fun ones(size: Int): Matrix<Double> = factory.ones(size, size)

/**
 * Creates a one-filled matrix with the given size
 */
fun ones(rows: Int, columns: Int): Matrix<Double> = factory.ones(rows, columns)

/**
 * Creates a square identity matrix with the given size
 */
fun eye(size: Int): Matrix<Double> = factory.eye(size)

/**
 * Creates an identity matrix with the given size
 */
fun eye(rows: Int, cols: Int): Matrix<Double> = factory.eye(rows, cols)

/**
 * Creates a new matrix that fills all the values with the return values of func(row,val)
 */
fun fill(rows: Int, cols: Int, func: (Int, Int) -> Double) = zeros(rows, cols).fill(func)

/**
 * Creates a new matrix that fills all the values with [value]
 */
fun fill(rows: Int, cols: Int, value: Double) = zeros(rows, cols).fill({ r, c -> value })

/**
 * Creates an 1x[cols] matrix filled with unit normal random numbers
 */
fun rand(cols: Int): Matrix<Double> = factory.rand(1, cols)

/**
 * Creates an matrix filled with unit normal random numbers
 */
fun rand(rows: Int, cols: Int): Matrix<Double> = factory.rand(rows, cols)

/**
 * Creates an matrix filled with unit normal random numbers, using the given seed for the RNG.
 * Subsequent calls with the same seed will produce identical numbers.
 */
fun rand(rows: Int, cols: Int, seed: Long): Matrix<Double> = factory.rand(rows, cols, seed)

/**
 * Creates an 1x[cols] matrix filled with unit normal random numbers
 */
fun randn(cols: Int): Matrix<Double> = factory.randn(1, cols)

/**
 * Creates an matrix filled with unit normal random numbers
 */
fun randn(rows: Int, cols: Int): Matrix<Double> = factory.randn(rows, cols)

/**
 * Creates an matrix filled with unit normal random numbers, using the given seed for the RNG.
 * Subsequent calls with the same seed will produce identical numbers.
 */
fun randn(rows: Int, cols: Int, seed: Long): Matrix<Double> = factory.randn(rows, cols, seed)

/**
 * Creates an vector filled in by the given range information. The filled values will start at [start] and
 * end at [stop], with the interval between each value [step].
 */
fun arange(start: Double, stop: Double, step: Double): Matrix<Double> = factory.arange(start, stop, step)

// TODO: Get these versions working
//fun linspace(...) = factory.linspace(lower, upper, num)


