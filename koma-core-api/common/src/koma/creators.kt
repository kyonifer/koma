@file:koma.internal.JvmName("Koma")
@file:koma.internal.JvmMultifileClass

package koma

import koma.extensions.fill
import koma.internal.KomaJsName
import koma.matrix.Matrix
import koma.matrix.MatrixTypes
import koma.matrix.MatrixType
import koma.ndarray.NDArray

/**
 * This file defines a set of functions to create new matrices. Follows
 * numpy conventions as closely as possible. For example, a 3x3 zero
 * matrix can be created via zeros(3,3)
 */

/**
 * Creates a zero-filled matrix with the given size
 */
@KomaJsName("zeros")
fun zeros(rows: Int, cols: Int): Matrix<Double> = zeros(rows, cols, dtype= MatrixTypes.DoubleType)
fun <T> zeros(rows:Int,
              cols:Int,
              dtype: MatrixType<T>): Matrix<T>
        = dtype().zeros(rows,cols)


/**
 * Creates a matrix filled with the given range of values.
 */
@KomaJsName("createRange")
fun create(data: IntRange) = create(data, dtype= MatrixTypes.DoubleType)
fun <T> create(data: IntRange,
               dtype: MatrixType<T>): Matrix<T>
        = dtype().create(data)

/**
 * Creates a matrix filled with the given set of values as a row-vector.
 */
@KomaJsName("createArray")
fun create(data: DoubleArray) = create(data, dtype= MatrixTypes.DoubleType)
fun <T> create(data: DoubleArray,
               dtype: MatrixType<T>): Matrix<T>
        = dtype().create(data).asRowVector()

/**
 * Creates a matrix filled with the given set of values in row-major order.
 */
@KomaJsName("createArraySized")
fun create(data: DoubleArray, numRows: Int, numCols: Int): Matrix<Double> =
        create(data, numRows, numCols, dtype= MatrixTypes.DoubleType)
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
@KomaJsName("create2DArray")
fun create(data: Array<DoubleArray>): Matrix<Double> = create(data, dtype= MatrixTypes.DoubleType)
fun <T> create(data: Array<DoubleArray>,
               dtype: MatrixType<T>): Matrix<T>
        = dtype().create(data)


/**
 * Creates a one-filled matrix with the given size
 */
@KomaJsName("ones")
fun ones(rows: Int, columns: Int): Matrix<Double> = ones(rows, columns, dtype= MatrixTypes.DoubleType)
fun <T> ones(rows: Int,
             columns: Int,
             dtype: MatrixType<T>): Matrix<T>
        = dtype().ones(rows, columns)

/**
 * Creates a square identity matrix with the given size
 */
@KomaJsName("eye")
fun eye(size: Int): Matrix<Double> = eye(size, dtype= MatrixTypes.DoubleType)
fun <T> eye(size: Int,
            dtype: MatrixType<T>): Matrix<T>
        = dtype().eye(size)

/**
 * Creates an identity matrix with the given size
 */
@KomaJsName("eyeSized")
fun eye(rows: Int, cols: Int): Matrix<Double> = eye(rows, cols, dtype= MatrixTypes.DoubleType)
fun <T> eye(rows: Int,
            cols: Int,
            dtype: MatrixType<T>): Matrix<T>
        = dtype().eye(rows, cols)

/**
 * Creates a new matrix that fills all the values with the return values of func(row,val)
 */
@KomaJsName("fill")
fun fill(rows: Int, cols: Int, func: (Int, Int) -> Double) = zeros(rows, cols).fill(func)
fun <T> fill(rows: Int,
             cols: Int,
             dtype: MatrixType<T>,
             func: (Int, Int) -> T)
        = zeros(rows, cols, dtype).fill(func)

/**
 * Creates a new matrix that fills all the values with [value]
 */
@KomaJsName("fillScalar")
fun fill(rows: Int,
         cols: Int,
         value: Double) = zeros(rows, cols).fill({ _, _ -> value })
fun <T> fill(rows: Int,
             cols: Int,
             value: T,
             dtype: MatrixType<T>)
        = zeros(rows, cols, dtype).fill({ _, _ -> value })


/**
 * Creates an matrix filled with unit uniform random numbers
 */
@KomaJsName("rand")
fun rand(rows: Int, cols: Int): Matrix<Double> = rand(rows, cols, dtype= MatrixTypes.DoubleType)
fun <T> rand(rows: Int,
             cols: Int,
             dtype: MatrixType<T>): Matrix<T>
        = dtype().rand(rows, cols)

/**
 * Creates an matrix filled with unit normal random numbers
 */
@KomaJsName("randn")
fun randn(rows: Int, cols: Int): Matrix<Double> = randn(rows,
        cols,
        dtype= MatrixTypes.DoubleType)
fun <T> randn(rows: Int,
              cols: Int,
              dtype: MatrixType<T>): Matrix<T>
        = dtype().randn(rows, cols)

/**
 * Creates an vector filled in by the given range information. The filled values will start at [start] and
 * end at [stop], with the interval between each value [step].
 */
@KomaJsName("arange")
fun arange(start: Double, stop: Double, step: Double): Matrix<Double> = arange(start,
        stop,
        step,
        dtype= MatrixTypes.DoubleType)
fun <T> arange(start: Double,
               stop: Double,
               step: Double,
               dtype: MatrixType<T>): Matrix<T>
        = dtype().arange(start, stop, step)


/**
 * Create a new NDArray from a series of elements.  By default this creates a 1D array.  To create a multidimensional
 * array, list the elements in flattened order and include the shape argument.
 */
inline fun <reified T> ndArrayOf(vararg elements: T, shape: IntArray? = null): NDArray<T> =
        NDArray.createLinear(dims= *shape ?: intArrayOf(elements.size), filler = { elements[it] })

@Suppress("ReplaceGetOrSet")
/**
 * A helper function that allows for quick construction of matrix literals.
 * Values are read in row-major order.
 *
 * @param rows The number of rows in the constructed matrix
 * @param cols The number of columns in the constructed matrix
 *
 * For example, one can write
 * ```
 * val a = matrixOf(1,2,3,4,5,6, rows=2, cols=3) # yields a 2x3 where the first row is [1,2,3]
 * val b = matrixOf(1,2,3,4,5,6) # yields a 6x1 with the top element `1` and the bottom element `6`
 * ```
 *
 */
inline fun <reified T> matrixOf(vararg ts: T,
             rows: Int = ts.size,
             cols: Int = 1): Matrix<T> =
        Matrix(rows, cols) { row, col -> ts[col + cols * row]}


@Suppress("ReplaceGetOrSet")
/**
 * A helper function that allows for quick construction of a vertical vector implemented as a Matrix.
 *
 * For example, one can write
 * ```
 * val a = colVectorOf(1,2,3)
 * ```
 */
fun colVectorOf(vararg ts: Any): Matrix<Double>  {
    if (ts.any { it is Pair<*,*> }) throw Exception("There can only be one row in a vector!")
    return koma.mat.get(*ts).T
}


@Suppress("ReplaceGetOrSet")
/**
 * A helper function that allows for quick construction of a horizontal vector implemented as a Matrix.
 *
 * For example, one can write
 * ```
 * val a = rowVectorOf(1,2,3)
 * ```
 */
fun rowVectorOf(vararg ts: Any): Matrix<Double>  {
    if (ts.any { it is Pair<*,*> }) throw Exception("There can only be one column in a vector!")
    return koma.mat.get(*ts)
}

// TODO: Get these versions working
//fun linspace(...) = factory.linspace(lower, upper, num)
