/**
 * This file defines a set of functions to create new matrices. Follows
 * numpy conventions as closely as possible. For example, a 3x3 zero
 * matrix can be created via zeros(3,3)
 */

@file:JvmName("Koma")
@file:JvmMultifileClass

package koma

import koma.matrix.*
import koma.polyfill.annotations.*

/**
 * Creates a zero-filled matrix with the given size
 */
@JsName("zeros")
fun zeros(rows: Int, cols: Int): Matrix<Double> = zeros(rows, cols, dtype=MatrixTypes.DoubleType)
fun <T> zeros(rows:Int, 
              cols:Int,
              dtype: MatrixType<T>): Matrix<T> 
        = dtype().zeros(rows,cols)

/**
 * Creates a square zero-filled matrix with the given size
 */
@Deprecated(DEPRECATE_IMPLICIT_2D, ReplaceWith("zeros(size, size)"))
fun zeros(size: Int): Matrix<Double> = zeros(size, dtype=MatrixTypes.DoubleType)
fun <T> zeros(size: Int,
              dtype: MatrixType<T>): Matrix<T>
        = dtype().zeros(size, size)

/**
 * Creates a matrix filled with the given range of values.
 */
@JsName("createRange")
fun create(data: IntRange) = create(data, dtype=MatrixTypes.DoubleType)
fun <T> create(data: IntRange,
               dtype: MatrixType<T>): Matrix<T> 
        = dtype().create(data)

/**
 * Creates a matrix filled with the given set of values as a row-vector.
 */
@JsName("createArray")
fun create(data: DoubleArray) = create(data, dtype=MatrixTypes.DoubleType)
fun <T> create(data: DoubleArray,
               dtype: MatrixType<T>): Matrix<T> 
        = dtype().create(data).asRowVector()

/**
 * Creates a matrix filled with the given set of values in row-major order.
 */
@JsName("createArraySized")
fun create(data: DoubleArray, numRows: Int, numCols: Int): Matrix<Double> =
    create(data, numRows, numCols, dtype=MatrixTypes.DoubleType)
fun <T> create(data: DoubleArray, 
               numRows: Int, 
               numCols: Int,
               dtype: MatrixType<T>): Matrix<T> {
    // TODO: Replace when also exists in kotlin-native
    val out = dtype().zeros(numRows,numCols)
    data.forEachIndexed { idx, value -> out.setDouble(idx,value) }
    return out
} 

/**
 * Creates a matrix filled with the given data, assuming input is row major.
 */
@JsName("create2DArray")
fun create(data: Array<DoubleArray>): Matrix<Double> = create(data, dtype=MatrixTypes.DoubleType)
fun <T> create(data: Array<DoubleArray>,
               dtype: MatrixType<T>): Matrix<T> 
        = dtype().create(data)

/**
 * Creates a one-filled square matrix with the given size
 */
@Deprecated(DEPRECATE_IMPLICIT_2D, ReplaceWith("ones(size, size)"))
fun ones(size: Int): Matrix<Double> = ones(size, dtype=MatrixTypes.DoubleType)
inline fun <reified T> ones(size: Int,
                            dtype: MatrixType<T>): Matrix<T> 
        = dtype().ones(size, size)

/**
 * Creates a one-filled matrix with the given size
 */
@JsName("ones")
fun ones(rows: Int, columns: Int): Matrix<Double> = ones(rows, columns, dtype=MatrixTypes.DoubleType)
fun <T> ones(rows: Int, 
             columns: Int,
             dtype: MatrixType<T>): Matrix<T> 
        = dtype().ones(rows, columns)

/**
 * Creates a square identity matrix with the given size
 */
@JsName("eye")
fun eye(size: Int): Matrix<Double> = eye(size, dtype=MatrixTypes.DoubleType)
fun <T> eye(size: Int,
            dtype: MatrixType<T>): Matrix<T> 
        = dtype().eye(size)

/**
 * Creates an identity matrix with the given size
 */
@JsName("eyeSized")
fun eye(rows: Int, cols: Int): Matrix<Double> = eye(rows, cols, dtype=MatrixTypes.DoubleType)
fun <T> eye(rows: Int, 
            cols: Int,
            dtype: MatrixType<T>): Matrix<T> 
        = dtype().eye(rows, cols)

/**
 * Creates a new matrix that fills all the values with the return values of func(row,val)
 */
@JsName("fill")
fun fill(rows: Int, cols: Int, func: (Int, Int) -> Double) = zeros(rows, cols).fill(func)
fun <T> fill(rows: Int, 
             cols: Int,
             dtype: MatrixType<T>,
             func: (Int, Int) -> T) 
        = zeros(rows, cols, dtype).fill(func)

/**
 * Creates a new matrix that fills all the values with [value]
 */
@JsName("fillScalar")
fun fill(rows: Int, 
         cols: Int, 
         value: Double) = zeros(rows, cols).fill({ _, _ -> value })
fun <T> fill(rows: Int, 
             cols: Int, 
             value: T,
             dtype: MatrixType<T>) 
        = zeros(rows, cols, dtype).fill({ _, _ -> value })

/**
 * Creates an 1x[cols] matrix filled with unit uniform random numbers
 */
@Deprecated(DEPRECATE_IMPLICIT_2D, ReplaceWith("rand(cols, cols)"))
fun rand(cols: Int): Matrix<Double> = rand(cols, dtype=MatrixTypes.DoubleType)
fun <T> rand(cols: Int,
             dtype: MatrixType<T>): Matrix<T> 
        = dtype().rand(1, cols)

/**
 * Creates an matrix filled with unit uniform random numbers
 */
@JsName("rand")
fun rand(rows: Int, cols: Int): Matrix<Double> = rand(rows, cols, dtype=MatrixTypes.DoubleType)
fun <T> rand(rows: Int, 
             cols: Int,
             dtype: MatrixType<T>): Matrix<T> 
        = dtype().rand(rows, cols)

/**
 * Creates an matrix filled with unit normal random numbers, using the given seed for the RNG.
 * Subsequent calls with the same seed will produce identical numbers.
 */
@JsName("randSeed")
fun rand(rows: Int, cols: Int, seed: Long): Matrix<Double> = rand(rows, 
                                                                  cols, 
                                                                  seed, 
                                                                  dtype=MatrixTypes.DoubleType)
fun <T> rand(rows: Int, 
             cols: Int, 
             seed: Long,
             dtype: MatrixType<T>): Matrix<T> 
        = dtype().rand(rows, cols, seed)

/**
 * Creates an 1x[cols] matrix filled with unit normal random numbers
 */
@Deprecated(DEPRECATE_IMPLICIT_2D, ReplaceWith("randn(cols, cols)"))
fun randn(cols: Int): Matrix<Double> = randn(cols, dtype=MatrixTypes.DoubleType)
fun <T> randn(cols: Int,
              dtype: MatrixType<T>): Matrix<T> 
        = dtype().randn(1, cols)

/**
 * Creates an matrix filled with unit normal random numbers
 */
@JsName("randn")
fun randn(rows: Int, cols: Int): Matrix<Double> = randn(rows, 
                                                        cols, 
                                                        dtype=MatrixTypes.DoubleType)
fun <T> randn(rows: Int, 
              cols: Int,
              dtype: MatrixType<T>): Matrix<T> 
        = dtype().randn(rows, cols)

/**
 * Creates an matrix filled with unit normal random numbers, using the given seed for the RNG.
 * Subsequent calls with the same seed will produce identical numbers.
 */
@JsName("randnSeed")
fun randn(rows: Int, cols: Int, seed: Long): Matrix<Double> = randn(rows, 
                                                                    cols, 
                                                                    seed, 
                                                                    dtype=MatrixTypes.DoubleType)
fun <T> randn(rows: Int, 
              cols: Int, 
              seed: Long,
              dtype: MatrixType<T>): Matrix<T> 
        = dtype().randn(rows, cols, seed)

/**
 * Creates an vector filled in by the given range information. The filled values will start at [start] and
 * end at [stop], with the interval between each value [step].
 */
@JsName("arange")
fun arange(start: Double, stop: Double, step: Double): Matrix<Double> = arange(start, 
                                                                               stop, 
                                                                               step,
                                                                               dtype=MatrixTypes.DoubleType)
fun <T> arange(start: Double, 
               stop: Double, 
               step: Double,
               dtype: MatrixType<T>): Matrix<T> 
        = dtype().arange(start, stop, step)

// TODO: Get these versions working
//fun linspace(...) = factory.linspace(lower, upper, num)


