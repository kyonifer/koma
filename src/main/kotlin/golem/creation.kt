/**
 * This file defines a set of functions to create new matrices. Follows
 * numpy conventions as closely as possible. For example, a 3x3 zero
 * matrix can be created via zeros(3,3)
 */

package golem

import golem.util.fromCollection
import org.ujmp.core.Matrix

fun zeros(rows: Long, cols: Long): Matrix = factory.zeros(rows, cols)
fun zeros(size: Long): Matrix = factory.zeros(1, size)
fun zeros(vararg indices : Long): Matrix = factory.zeros(*indices)

// Support things like create(1..100)
fun create(data: IntRange): Matrix = factory.importFromArray(fromCollection(data.map { it.toDouble() }))
fun create(data: DoubleRange): Matrix = create(fromCollection(data.toList()))
fun create(data: DoubleArray): Matrix = factory.importFromArray(data)
fun create(data: Array<DoubleArray>): Matrix = factory.importFromArray(data)

fun ones(size: Long): Matrix = factory.ones(1, size)
fun ones(rows: Long, columns: Long): Matrix = factory.ones(rows, columns)
fun ones(vararg indices : Long): Matrix = factory.ones(*indices)

fun eye(size: Long): Matrix = factory.eye(size)

fun rand(cols: Long): Matrix = factory.rand(1, cols)
fun rand(rows: Long, cols: Long): Matrix = factory.rand(rows, cols)
fun rand(rows: Long, cols: Long, seed: Long): Matrix = factory.rand(rows, cols, seed)

fun randn(cols: Long): Matrix = factory.randn(1, cols)
fun randn(rows: Long, cols: Long): Matrix = factory.randn(rows, cols)

// TODO: Get these versions working

//fun randn(rows: Int, cols: Int, rng: Random) = factory.randn(rows, cols,rng)
//fun randn(rows: Int, cols: Int, seed: Long) =  factory.randn(rows, cols, seed)
//fun linspace(...) = factory.linspace(lower, upper, num)

/*
fun arange(begin: Double) = factory.arange(begin)
fun arange(begin: Int) = arange(begin.toDouble())
fun arange(begin: Double, end: Double) = factory.arange(begin, end)
fun arange(begin: Int, end: Int) = arange(begin.toDouble(), end.toDouble())
*/
