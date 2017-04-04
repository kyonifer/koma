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
fun zeros(rows: Int, cols: Int): Matrix<Double> = zeros(rows, cols, factory=MatrixTypes.DoubleType)
fun <T> zeros(rows:Int, 
              cols:Int, 
              factory: MatrixFactory<Matrix<T>>): Matrix<T> 
        = factory.zeros(rows,cols)

/**
 * Creates a square zero-filled matrix with the given size
 */
fun zeros(size: Int): Matrix<Double> = zeros(size, factory=MatrixTypes.DoubleType)
fun <T> zeros(size: Int,
              factory: MatrixFactory<Matrix<T>>): Matrix<T>
        = factory.zeros(size, size)

/**
 * Creates a matrix filled with the given range of values.
 */
fun create(data: IntRange) = create(data, factory=MatrixTypes.DoubleType)
fun <T> create(data: IntRange,
               factory: MatrixFactory<Matrix<T>>): Matrix<T> 
        = factory.create(data)

/**
 * Creates a matrix filled with the given set of values as a row-vector.
 */
fun create(data: DoubleArray) = create(data, factory=MatrixTypes.DoubleType)
fun <T> create(data: DoubleArray,
               factory: MatrixFactory<Matrix<T>>): Matrix<T> 
        = factory.create(data).asRowVector()

/**
 * Creates a matrix filled with the given set of values in row-major order.
 */
fun create(data: DoubleArray, numRows: Int, numCols: Int): Matrix<Double> =
    create(data, numRows, numCols, factory=MatrixTypes.DoubleType)
fun <T> create(data: DoubleArray, 
               numRows: Int, 
               numCols: Int,
               factory: MatrixFactory<Matrix<T>>): Matrix<T> {
    // TODO: Replace when also exists in kotlin-native
    val out = factory.zeros(numRows,numCols)
    data.forEachIndexed { idx, value -> out.setDouble(idx,value) }
    return out
} 

/**
 * Creates a matrix filled with the given data, assuming input is row major.
 */
fun create(data: Array<DoubleArray>): Matrix<Double> = create(data, factory=MatrixTypes.DoubleType)
fun <T> create(data: Array<DoubleArray>,
               factory: MatrixFactory<Matrix<T>>): Matrix<T> 
        = factory.create(data)

/**
 * Creates a one-filled square matrix with the given size
 */
fun ones(size: Int): Matrix<Double> = ones(size, factory=MatrixTypes.DoubleType)
inline fun <reified T> ones(size: Int,
                            factory: MatrixFactory<Matrix<T>>): Matrix<T> 
        = factory.ones(size, size)

/**
 * Creates a one-filled matrix with the given size
 */
fun ones(rows: Int, columns: Int): Matrix<Double> = ones(rows, columns, factory=MatrixTypes.DoubleType)
fun <T> ones(rows: Int, 
             columns: Int,
             factory: MatrixFactory<Matrix<T>>): Matrix<T> 
        = factory.ones(rows, columns)

/**
 * Creates a square identity matrix with the given size
 */
fun eye(size: Int): Matrix<Double> = eye(size, factory=MatrixTypes.DoubleType)
fun <T> eye(size: Int,
            factory: MatrixFactory<Matrix<T>>): Matrix<T> 
        = factory.eye(size)

/**
 * Creates an identity matrix with the given size
 */
fun eye(rows: Int, cols: Int): Matrix<Double> = eye(rows, cols, factory=MatrixTypes.DoubleType)
fun <T> eye(rows: Int, 
            cols: Int,
            factory: MatrixFactory<Matrix<T>>): Matrix<T> 
        = factory.eye(rows, cols)

/**
 * Creates a new matrix that fills all the values with the return values of func(row,val)
 */
fun fill(rows: Int, cols: Int, func: (Int, Int) -> Double) = zeros(rows, cols).fill(func)
fun <T> fill(rows: Int, 
             cols: Int,
             factory: MatrixFactory<Matrix<T>>,
             func: (Int, Int) -> T) 
        = zeros(rows, cols, factory).fill(func)

/**
 * Creates a new matrix that fills all the values with [value]
 */
fun fill(rows: Int, 
         cols: Int, 
         value: Double) = zeros(rows, cols).fill({ r, c -> value })
fun <T> fill(rows: Int, 
             cols: Int, 
             value: T,
             factory: MatrixFactory<Matrix<T>>) 
        = zeros(rows, cols, factory).fill({ r, c -> value })

/**
 * Creates an 1x[cols] matrix filled with unit uniform random numbers
 */
fun rand(cols: Int): Matrix<Double> = rand(cols, factory=MatrixTypes.DoubleType)
fun <T> rand(cols: Int,
             factory: MatrixFactory<Matrix<T>>): Matrix<T> 
        = factory.rand(1, cols)

/**
 * Creates an matrix filled with unit uniform random numbers
 */
fun rand(rows: Int, cols: Int): Matrix<Double> = rand(rows, cols, factory=MatrixTypes.DoubleType)
fun <T> rand(rows: Int, 
         cols: Int,
             factory: MatrixFactory<Matrix<T>>): Matrix<T> 
        = factory.rand(rows, cols)

/**
 * Creates an matrix filled with unit normal random numbers, using the given seed for the RNG.
 * Subsequent calls with the same seed will produce identical numbers.
 */
fun rand(rows: Int, cols: Int, seed: Long): Matrix<Double> = rand(rows, 
                                                                  cols, 
                                                                  seed, 
                                                                  factory=MatrixTypes.DoubleType)
fun <T> rand(rows: Int, 
             cols: Int, 
             seed: Long,
             factory: MatrixFactory<Matrix<T>>): Matrix<T> 
        = factory.rand(rows, cols, seed)

/**
 * Creates an 1x[cols] matrix filled with unit normal random numbers
 */
fun randn(cols: Int): Matrix<Double> = randn(cols, factory=MatrixTypes.DoubleType)
fun <T> randn(cols: Int,
              factory: MatrixFactory<Matrix<T>>): Matrix<T> 
        = factory.randn(1, cols)

/**
 * Creates an matrix filled with unit normal random numbers
 */
fun randn(rows: Int, cols: Int): Matrix<Double> = randn(rows, 
                                                        cols, 
                                                        factory=MatrixTypes.DoubleType)
fun <T> randn(rows: Int, 
              cols: Int,
              factory: MatrixFactory<Matrix<T>>): Matrix<T> 
        = factory.randn(rows, cols)

/**
 * Creates an matrix filled with unit normal random numbers, using the given seed for the RNG.
 * Subsequent calls with the same seed will produce identical numbers.
 */
fun randn(rows: Int, cols: Int, seed: Long): Matrix<Double> = randn(rows, 
                                                                    cols, 
                                                                    seed, 
                                                                    factory=MatrixTypes.DoubleType)
fun <T> randn(rows: Int, 
              cols: Int, 
              seed: Long,
              factory: MatrixFactory<Matrix<T>>): Matrix<T> 
        = factory.randn(rows, cols, seed)

/**
 * Creates an vector filled in by the given range information. The filled values will start at [start] and
 * end at [stop], with the interval between each value [step].
 */
fun arange(start: Double, stop: Double, step: Double): Matrix<Double> = arange(start, 
                                                                               stop, 
                                                                               step,
                                                                               factory=MatrixTypes.DoubleType)
fun <T> arange(start: Double, 
               stop: Double, 
               step: Double, 
               factory: MatrixFactory<Matrix<T>>): Matrix<T> 
        = factory.arange(start, stop, step)

// TODO: Get these versions working
//fun linspace(...) = factory.linspace(lower, upper, num)


