/**
 * This file defines a set of functions to create new matrices. Follows
 * numpy conventions as closely as possible. For example, a 3x3 zero
 * matrix can be created via zeros(3,3)
 */

package golem

import golem.matrix.Matrix

fun zeros(rows: Int, cols: Int): Matrix<Double> = factory.zeros(rows, cols)
fun zeros(size: Int): Matrix<Double> = factory.zeros(1, size)

// Support things like create(1..100)
fun create(data: IntRange): Matrix<Double> = factory.create(data)
fun create(data: DoubleRange): Matrix<Double> = factory.create(data)
fun create(data: DoubleArray): Matrix<Double> = factory.create(data)
fun create(data: Array<DoubleArray>): Matrix<Double> = factory.create(data)

fun ones(size: Int): Matrix<Double> = factory.ones(1, size)
fun ones(rows: Int, columns: Int): Matrix<Double> = factory.ones(rows, columns)

fun eye(size: Int): Matrix<Double> = factory.eye(size)
fun eye(rows: Int, cols: Int): Matrix<Double> = factory.eye(rows, cols)

fun rand(cols: Int): Matrix<Double> = factory.rand(1, cols)
fun rand(rows: Int, cols: Int): Matrix<Double> = factory.rand(rows, cols)
fun rand(rows: Int, cols: Int, seed: Long): Matrix<Double> = factory.rand(rows, cols, seed)

fun randn(cols: Int): Matrix<Double> = factory.randn(1, cols)
fun randn(rows: Int, cols: Int): Matrix<Double> = factory.randn(rows, cols)
fun randn(rows: Int, cols: Int, seed: Long): Matrix<Double> = factory.randn(rows, cols, seed)

// TODO: Get these versions working

//fun randn(rows: Int, cols: Int, rng: Random) = factory.randn(rows, cols,rng)
//fun randn(rows: Int, cols: Int, seed: Long) =  factory.randn(rows, cols, seed)
//fun linspace(...) = factory.linspace(lower, upper, num)

fun arange(start: Double, stop: Double, step: Double) = factory.arange(start, stop, step)
//fun arange(begin: Double) = factory.arange(begin)
//fun arange(begin: Int) = arange(begin.toDouble())
// fun arange(begin: Double, end: Double) = factory.arange(begin, end)
// fun arange(begin: Int, end: Int) = arange(begin.toDouble(), end.toDouble())

