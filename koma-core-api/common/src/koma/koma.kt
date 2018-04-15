package koma

import koma.extensions.*
import koma.internal.KomaJsName
import koma.internal.KomaJvmName
import koma.internal.signum
import koma.matrix.Matrix
import koma.matrix.MatrixFactory
import koma.matrix.MatrixType
import koma.matrix.MatrixTypes
import kotlin.math.pow as kotlinPow

// This file used to be split into several .kt files for readability. However,
// due to a bug in multiplatform @file:JvmName("Koma") wasn't honored in
// common modules (even with an expect typealias MyJvmName = JvmName) so as a
// workaround these files are temporarily concatenated here.

// ========================= creators.kt ======================================

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
 * Creates an matrix filled with unit normal random numbers, using the given seed for the RNG.
 * Subsequent calls with the same seed will produce identical numbers.
 */
@KomaJsName("randSeed")
@Deprecated("Call setSeed and randn separately")
fun rand(rows: Int, cols: Int, seed: Long): Matrix<Double> = rand(rows,
        cols,
        seed,
        dtype= MatrixTypes.DoubleType)
@Deprecated("Call setSeed and randn separately")
fun <T> rand(rows: Int,
             cols: Int,
             seed: Long,
             dtype: MatrixType<T>): Matrix<T> {
    setSeed(seed)
    return dtype().rand(rows, cols)
}

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
 * Creates an matrix filled with unit normal random numbers, using the given seed for the RNG.
 * Subsequent calls with the same seed will produce identical numbers.
 */
@KomaJsName("randnSeed")
@Deprecated("Call setSeed and randn separately")
fun randn(rows: Int, cols: Int, seed: Long): Matrix<Double> = randn(rows,
        cols,
        seed,
        dtype= MatrixTypes.DoubleType)
@Deprecated("Call setSeed and randn separately")
fun <T> randn(rows: Int,
              cols: Int,
              seed: Long,
              dtype: MatrixType<T>): Matrix<T> {
    setSeed(seed)
    return dtype().randn(rows, cols)
}

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

// TODO: Get these versions working
//fun linspace(...) = factory.linspace(lower, upper, num)


// ========================= constants.kt ======================================

val PI = kotlin.math.PI
val E = kotlin.math.E

// ========================= builders.kt ======================================


/**
 *   A DSL for writing matrix literals. You can define a new matrix via e.g.
 *
 *   var a = mat[1, 2 end
 *               3, 4]
 */


/**
 * A helper object that allows for quick construction of matrix literals.
 *
 * For example, one can write
 * ```
 * var a = mat[1,2,3 end
 *             4,5,6]
 * ```
 *
 * to get a 2x3 [Matrix<Double>] with the given values. end is a helper object that indicates the end of a row
 * to this object. Note that one currently cannot use this function to generate a column vector:
 *
 * ```// ERROR:```
 *
 * ```mat[1 end 2 end 3]```
 *
 * Instead do this:
 *
 * ```// Define a column vector by transposing a row-vector```
 *
 * ```mat[1 2 3].T```
 */
object mat {
    /**
     * See [mat] description.
     */
    operator fun get(vararg ts: Any): Matrix<Double> {
        // Todo: check for malformed inputs to avoid ambiguous out of bounds exceptions

        val numStops = ts.filter { it is Pair<*, *> }.count()
        val numRows = numStops + 1
        val numElements = ts.count() - numStops + 2 * numStops
        val numCols = numElements / numRows

        if (numRows * numCols != numElements)
            throw IllegalArgumentException("When building matrices with mat[] please give even rows/cols")

        val out = zeros(numRows, numCols)
        var curRow = 0
        var curCol = 0

        for (ele in ts) {
            if (curCol >= numCols)
                throw IllegalArgumentException("When building matrices with mat[] please give even rows/cols")
            when (ele) {
                is Number -> {
                    out[curRow, curCol] = ele.toDouble()
                    curCol += 1
                }
                is Pair<*, *> -> {
                    out[curRow, curCol] = ele.first as Double
                    out[curRow + 1, 0] = ele.second as Double
                    curRow += 1
                    curCol = 1
                }
                else -> throw Exception("Invalid initial value to matrix builder: \n$ele")
            }
        }
        return out
    }
}

// @formatter:on
/**
 * A helper object for the builder DSL. See [mat].
 */
infix fun Double.end(other: Double) = Pair(this, other)

/**
 * A helper object for the builder DSL. See [mat].
 */
infix fun Double.end(other: Int) = Pair(this, other.toDouble())

/**
 * A helper object for the builder DSL. See [mat].
 */
infix fun Int.end(other: Double) = Pair(this.toDouble(), other)

/**
 * A helper object for the builder DSL. See [mat].
 */
infix fun Int.end(other: Int) = Pair(this.toDouble(), other.toDouble())

// ========================= factories.kt ======================================


@Deprecated("Use koma.matrix property instead", ReplaceWith("koma.matrix.doubleFactory"))
var doubleFactory: MatrixFactory<Matrix<Double>>
    get() = koma.matrix.doubleFactory
    set(value) { koma.matrix.doubleFactory = value }

@Deprecated("Use koma.matrix property instead", ReplaceWith("koma.matrix.floatFactory"))
var floatFactory: MatrixFactory<Matrix<Float>>
    get() = koma.matrix.floatFactory
    set(value) { koma.matrix.floatFactory = value }

@Deprecated("Use koma.matrix property instead", ReplaceWith("koma.matrix.intFactory"))
var intFactory: MatrixFactory<Matrix<Int>>
    get() = koma.matrix.intFactory
    set(value) { koma.matrix.intFactory = value }


// ========================= matrixfuncs.kt ======================================

/**
 * This file contains top-level common mathematical functions that operate on
 * Matrices. These definitions follow numpy as close as possible, and allow
 * one to do things like cos(randn(5,5))
 */

/**
 * Returns true if all elements are close to equal, as defined by
 * two tolerance values. The matrices are considered equal if
 *
 * abs(ele1 - ele2) < (atol + rtol * abs(ele1))
 *
 * is true elementwise for all elements ele1 in [arr1]
 * and all elements ele2 in [arr2].
 *
 * @param arr1 The first matrix to compare
 * @param arr2 The second matrix to compare
 * @param rtol The relative tolerance value
 * @param atol The absolute tolerance value
 */
fun allclose(arr1: Matrix<Double>,
             arr2: Matrix<Double>,
             rtol: Double = 1e-05,
             atol: Double = 1e-08) = arr1.allClose(arr2, rtol=rtol, atol=atol)

/**
 * Returns a matrix of the arccos of each element in the input matrix.
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 */
fun acos(arr: Matrix<Double>) = arr.map { kotlin.math.acos(it) }

/**
 * Returns a matrix of the arcsin of each element in the input matrix.
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 */
fun asin(arr: Matrix<Double>) = arr.map { kotlin.math.asin(it) }

/**
 * Returns a matrix of the arctan of each element in the input matrix.
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 */
fun atan(arr: Matrix<Double>) = arr.map { kotlin.math.atan(it) }

/**
 * Returns a matrix of the absolute value of each element in the input matrix.
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 */
fun abs(arr: Matrix<Double>) = arr.map { kotlin.math.abs(it) }

/**
 * Rounds each element to the integer which is nearest to the element and still less than the
 * element (i.e. truncation).
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 */
// explicit toDouble() for javascript
fun ceil(arr: Matrix<Double>) = arr.map { kotlin.math.ceil(it).toDouble() }

/**
 * Returns a matrix of the cos of each element in the input matrix.
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 *
 */
fun cos(arr: Matrix<Double>) = arr.map { kotlin.math.cos(it) }

/**
 * Returns a matrix of E.pow(element) for each element in the input matrix.
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 *
 */
fun exp(arr: Matrix<Double>) = arr.map { kotlin.math.exp(it) }


/**
 * Returns a matrix consisting of each element in the input matrix raised to the given power.
 *
 * @param arr An arbitrarily sized matrix
 * @param num the power to raise the matrix to.
 *
 * @return A matrix consisting of the operation performed element-wise.
 *
 */
fun epow(arr: Matrix<Double>, num: Double) = arr.epow(num)

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
fun pow(arr: Matrix<Double>, num: Int) = arr.pow(num)

/**
 * Calculates a matrix consisting of the sign of each element in the input matrix.
 * Returns -1 for positive values, -1 for negative values, 0 for 0.
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 */
fun sign(arr: Matrix<Double>) = arr.map { koma.internal.signum(it) }

/**
 * Returns a matrix of the sin of each element in the input matrix
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 *
 */
fun sin(arr: Matrix<Double>) = arr.map { kotlin.math.sin(it) }

/**
 * Returns a matrix of the sqrt of each element in the input matrix. Does
 * not yet support complex numbers.
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 *
 */
fun sqrt(arr: Matrix<Double>) = arr.map { kotlin.math.sqrt(it) }

/**
 * Returns a matrix of the tan of each element in the input matrix
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 *
 */
fun tan(arr: Matrix<Double>) = arr.map { kotlin.math.tan(it) }

/**
 * Rounds each element to the nearest integer value. For elements exactly between integers,
 * choose the highest value.
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 */
fun round(arr: Matrix<Double>) = arr.map { kotlin.math.round(it).toDouble() }

/**
 * Rounds each element to the integer which is nearest to the element and still less than the
 * element (i.e. truncation).
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 */
// explicit toDouble() for javascript
fun floor(arr: Matrix<Double>) = arr.map { kotlin.math.floor(it).toDouble() }

/**
 * Returns a matrix of the natural logarithm of each element in the input matrix.
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return A matrix consisting of the operation performed element-wise.
 *
 */
fun ln(arr: Matrix<Double>) = arr.map { ln(it) }

@Deprecated("Use ln()", ReplaceWith("ln(arr)"))
fun log(arr: Matrix<Double>) = ln(arr)

/**
 * Returns a matrix of the log-base-b of each element in the input matrix
 *
 * @param arr An arbitrarily sized matrix
 * @param base the base of the log (i.e. performs log-base-[base] of [arr]
 *
 * @return A matrix consisting of the operation performed element-wise.
 *
 */
fun logb(base: Int, arr: Matrix<Double>) = arr.map { logb(base, it) }

/**
 * Extracts the diagonal of the matrix.
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return a Nx1 column vector.
 */
fun diag(arr: Matrix<Double>) = arr.diag().asColVector()

/**
 * Calculates the cumulative (ongoing) sum of a matrix's elements. For example,
 * ```cumsum(mat[1,2,3])``` would return ```mat[1,3,6]```.
 *
 * @param arr The matrix to calculate the cumsum on. Sum will be computed in row-major order.
 *
 * @return A 1x(arr.numRows*arr.numCols) vector storing the ongoing cumsum.
 *
 */
fun cumsum(arr: Matrix<Double>) = arr.cumSum()

/**
 * Returns the max element in the input matrix
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return The maximum value
 *
 */
fun max(arr: Matrix<Double>) = arr.max()

/**
 * Returns the mean element in the input matrix
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return The maximum value
 *
 */
fun mean(arr: Matrix<Double>) = arr.mean()

/**
 * Returns the min element in the input matrix
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return The maximum value
 *
 */
fun min(arr: Matrix<Double>) = arr.min()

/**
 * Returns the index of the max element in the input matrix
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return The maximum value
 *
 */
fun argMax(arr: Matrix<Double>) = arr.argMax()

/**
 * Returns the index of the min element in the input matrix
 *
 * @param arr An arbitrarily sized matrix
 *
 * @return The maximum value
 *
 */
fun argMin(arr: Matrix<Double>) = arr.argMin()

/**
 * Calculates the matrix exponential of the input matrix. Note that this is
 * NOT the same thing as the elementwise exponential function.
 *
 * @param A The input matrix
 * @return e to the A
 */
fun expm(A: Matrix<Double>) = A.expm()

/**
 * Converts a 3x1 or 1x3 vector of angles into the skew symmetric matrix
 * equivalent.
 *
 * @param angles The input matrix
 * @Return 3x3 skew symmetric matrix
 */
fun skew(angles: Matrix<Double>): Matrix<Double> {
    val s = mat [0, -angles[2], angles[1] end
            angles[2], 0, -angles[0] end
            -angles[1], angles[0], 0]
    return s
}

/**
 * Calculates the cross product of two vectors
 */
fun cross(vec1: Matrix<Double>, vec2: Matrix<Double>) = skew(vec1) * vec2

/**
 * Calculates the cross product of two vectors
 */
fun dot(vec1: Matrix<Double>, vec2: Matrix<Double>) = (vec1.asRowVector() * vec2.asColVector())[0]

fun hstack(vararg arrs: Matrix<Double>): Matrix<Double> {
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

fun vstack(vararg arrs: Matrix<Double>): Matrix<Double> {
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



// ========================= misc.kt ======================================

var matFormat: String = "L"

val LONG_NUMBER = "L"
val VERY_LONG_NUMBER = "VL"
val SHORT_NUMBER = "S"
val SCIENTIFIC_NUMBER = "SciNot"
val SCIENTIFIC_LONG_NUMBER = "SciNotLong"
val SCIENTIFIC_VERY_LONG_NUMBER = "SciNotVLong"

val end = -1
val all = 0..end

fun setSeed(seed: Long) = koma.internal.rng.setSeed(seed)

/**
 * Sets the format for Koma to display numbers in. For example, calling
 *
 * format("long")
 * println(randn(3))
 *
 * will cause println(someMatrix) to show more decimals (precision) than
 *
 * format("short")
 * println(randn(3))
 *
 * format() must be called before a .toString() request to a Koma matrix
 * in order to have any effect, but setting it will persist for all printing
 * of matrices until called again with a different format string. The default
 * setting is "long". Supported options are "long", "short", "verylong".
 */
fun format(formatStr: String) {
    // TODO: use enum
    when (formatStr) {
        "LONG", "long", "Long" -> matFormat = LONG_NUMBER
        "SHORT", "short", "Short" -> matFormat = SHORT_NUMBER
        "VLONG", "VERYLONG", "VeryLong", "VLong", "verylong", "vl" -> matFormat = VERY_LONG_NUMBER
        "scientific", "sci", "SciNot", "scinot" -> matFormat = SCIENTIFIC_NUMBER
        "scilong", "SciLong", "SciNotLong" -> matFormat = SCIENTIFIC_LONG_NUMBER
        "scivlong", "SciVLong", "SciNotVLong" -> matFormat = SCIENTIFIC_VERY_LONG_NUMBER
    }
}

// ========================= options.kt ======================================


@Deprecated("Use doubleFactory instead", replaceWith=ReplaceWith("doubleFactory"))
var factory: MatrixFactory<Matrix<Double>>
    get() = doubleFactory
    set(value) { doubleFactory = value }

/**
 * Whether to validate the dimensions, symmetry, and values of input matrices. false is faster, and should be
 * used for realtime applications. true gives you much more useful errors when your matrices are shaped
 * differently than your code expects.
 */
var validateMatrices = true

// ========================= scalarfuncs.kt ======================================


// Scalar funcs

/**
 * A convenience function performing the abs operation via the [kotlin.math] equivalent call.
 */
fun abs(arr: Double) = kotlin.math.abs(arr)

/**
 * A convenience function performing the ceil via the [kotlin.math] equivalent call.
 */
fun ceil(num: Double) = kotlin.math.ceil(num).toInt()

/**
 * A convenience function performing the cos operation via the [kotlin.math] equivalent call.
 */
fun cos(arr: Double) = kotlin.math.cos(arr)

/**
 * A convenience function performing the exp operation via the [kotlin.math] equivalent call.
 */
fun exp(arr: Double) = kotlin.math.exp(arr)

/**
 * A convenience function performing the sign operation via the [kotlin.math] equivalent call.
 */
fun sign(arr: Double) = signum(arr)

/**
 * A convenience function performing the sin operation via the [kotlin.math] equivalent call.
 */
fun sin(arr: Double) = kotlin.math.sin(arr)

/**
 * A convenience function performing the sqrt operation via the [kotlin.math] equivalent call.
 */
fun sqrt(arr: Double) = kotlin.math.sqrt(arr)

/**
 * A convenience function performing the tan operation via the [kotlin.math] equivalent call.
 */
fun tan(arr: Double) = kotlin.math.tan(arr)

/**
 * A convenience function performing the round operation via the [kotlin.math] equivalent call.
 */
fun round(arr: Double) = kotlin.math.round(arr)

/**
 * A convenience function performing the floor operation via the [kotlin.math] equivalent call.
 */
fun floor(arr: Double) = kotlin.math.floor(arr).toInt()

/**
 * A convenience function performing the acos operation via the [kotlin.math] equivalent call.
 */
fun acos(arr: Double) = kotlin.math.acos(arr)

/**
 * A convenience function performing the asin operation via the [kotlin.math] equivalent call.
 */
fun asin(arr: Double) = kotlin.math.asin(arr)

/**
 * A convenience function performing the atan operation via the [kotlin.math] equivalent call.
 */
fun atan(arr: Double) = kotlin.math.atan(arr)

/**
 * A convenience function performing the atan2 operation via the [kotlin.math] equivalent call.
 */
fun atan2(x: Double, y: Double) = kotlin.math.atan2(x, y)

/**
 * A convenience function performing the acos operation via the [kotlin.math] equivalent call.
 */
fun acos(arr: Int) = kotlin.math.acos(arr.toDouble())

/**
 * A convenience function performing the asin operation via the [kotlin.math] equivalent call.
 */
fun asin(arr: Int) = kotlin.math.asin(arr.toDouble())

/**
 * A convenience function performing the atan operation via the [kotlin.math] equivalent call.
 */
fun atan(arr: Int) = kotlin.math.atan(arr.toDouble())

/**
 * A convenience function performing the atan2 operation via the [kotlin.math] equivalent call.
 */
fun atan2(x: Int, y: Int) = kotlin.math.atan2(x.toDouble(), y.toDouble())

/**
 * A convenience function performing the max operation via the [kotlin.math] equivalent call.
 */
fun max(num1: Double, num2: Double) = if (num1 > num2) num1 else num2

/**
 * A convenience function performing the min operation via the [kotlin.math] equivalent call.
 */
fun min(num1: Double, num2: Double) = if (num2 > num1) num1 else num2

// Int versions
/**
 * A convenience function performing the abs via the [kotlin.math] equivalent call.
 */
fun abs(arr: Int) = kotlin.math.abs(arr.toDouble())

/**
 * A convenience function performing the ceil operation via the [kotlin.math] equivalent call.
 */
fun ceil(num: Int) = kotlin.math.ceil(num.toDouble())

/**
 * A convenience function performing the cos operation via the [kotlin.math] equivalent call.
 */
fun cos(arr: Int) = kotlin.math.cos(arr.toDouble())

/**
 * A convenience function performing the exp operation via the [kotlin.math] equivalent call.
 */
fun exp(arr: Int) = kotlin.math.exp(arr.toDouble())



/**
 * A convenience function performing the sign operation via the [kotlin.math] equivalent call.
 */
fun sign(arr: Int) = signum(arr.toDouble())

/**
 * A convenience function performing the sin operation via the [kotlin.math] equivalent call.
 */
fun sin(arr: Int) = kotlin.math.sin(arr.toDouble())

/**
 * A convenience function performing the sqrt operation via the [kotlin.math] equivalent call.
 */
fun sqrt(arr: Int) = kotlin.math.sqrt(arr.toDouble())

/**
 * A convenience function performing the tan operation via the [kotlin.math] equivalent call.
 */
fun tan(arr: Int) = kotlin.math.tan(arr.toDouble())

/**
 * A convenience function performing the round operation via the [kotlin.math] equivalent call.
 */
fun round(arr: Int) = kotlin.math.round(arr.toDouble())

/**
 * A convenience function performing the floor operation via the [kotlin.math] equivalent call.
 */
fun floor(arr: Int) = kotlin.math.floor(arr.toDouble())

/**
 * A convenience function performing the max operation via the [kotlin.math] equivalent call.
 */
fun max(num1: Int, num2: Int) = if (num1 > num2) num1 else num2

/**
 * A convenience function performing the max operation via the [kotlin.math] equivalent call.
 */
fun max(num1: Double, num2: Int) = if (num1 > num2) num1 else num2.toDouble()

/**
 * A convenience function performing the max operation via the [kotlin.math] equivalent call.
 */
fun max(num1: Int, num2: Double) = if (num1 > num2) num1.toDouble() else num2

/**
 * A convenience function performing the min operation via the [kotlin.math] equivalent call.
 */
fun min(num1: Int, num2: Int) = if (num2 > num1) num1 else num2

/**
 * A convenience function performing the min operation via the [kotlin.math] equivalent call.
 */
fun min(num1: Int, num2: Double) = if (num2 > num1) num1.toDouble() else num2

/**
 * A convenience function performing the min operation via the [kotlin.math] equivalent call.
 */
fun min(num1: Double, num2: Int) = if (num2 > num1) num1 else num2.toDouble()


/**
 * A convenience function performing the log operation via the [kotlin.math] equivalent call.
 */
fun ln(arr: Double) = kotlin.math.ln(arr)

@Deprecated("Please use ln() instead", ReplaceWith("ln(arr)"))
fun log(arr: Double) = ln(arr)

/**
 * A convenience function performing the log operation via the [kotlin.math] equivalent call.
 */
fun ln(arr: Int) = ln(arr.toDouble())

@Deprecated("Use ln()", ReplaceWith("ln(arr)"))
fun log(arr: Int) = ln(arr)

/**
 * A convenience function performing the logb operation via the [kotlin.math] equivalent call.
 */
fun logb(base: Double, num: Double) = kotlin.math.log(num, base)

/**
 * A convenience function performing the logb operation via the [kotlin.math] equivalent call.
 */
fun logb(base: Int, num: Int) = logb(base.toDouble(), num.toDouble())

/**
 * A convenience function performing the logb operation via the [kotlin.math] equivalent call.
 */
fun logb(base: Int, num: Double) = logb(base.toDouble(), num)

/**
 * A convenience function performing the logb operation via the [kotlin.math] equivalent call.
 */
fun logb(base: Double, num: Int) = logb(base, num.toDouble())

/**
 * A convenience function performing the pow operation via the [kotlin.math] equivalent call.
 */
fun pow(num: Double, exp: Double) = num.kotlinPow(exp) // Picks up kotlin.math extension

/**
 * A convenience function performing the pow operation via the [kotlin.math] equivalent call.
 */
fun pow(num: Int, exp: Int) = koma.pow(num.toDouble(), exp.toDouble())

/**
 * A convenience function performing the pow operation via the [kotlin.math] equivalent call.
 */
fun pow(num: Double, exp: Int) = koma.pow(num, exp.toDouble())

/**
 * A convenience function performing the pow operation via the [kotlin.math] equivalent call.
 */
fun pow(num: Int, exp: Double) = koma.pow(num.toDouble(), exp)

/**
 * A convenience ext function performing the pow operation via the [kotlin.math] equivalent call.
 */
@KomaJvmName("powDoubleDouble")
infix fun Double.pow(exp: Double) = koma.pow(this, exp)

/**
 * A convenience ext function performing the pow operation via the [kotlin.math] equivalent call.
 */
@KomaJvmName("powIntInt")
infix fun Int.pow(exp: Int) = koma.pow(this.toDouble(), exp.toDouble())

/**
 * A convenience ext function performing the pow operation via the [kotlin.math] equivalent call.
 */
@KomaJvmName("powDoubleInt")
infix fun Double.pow(exp: Int) = koma.pow(this, exp.toDouble())

/**
 * A convenience ext function performing the pow operation via the [kotlin.math] equivalent call.
 */
@KomaJvmName("powIntDouble")
infix fun Int.pow(exp: Double) = koma.pow(this.toDouble(), exp)


// ========================= factories.kt ======================================


/**
 * An implementation of an unsigned byte. Still a WIP.
 */
class UInt8 {
    constructor(init: Short) {
        value = init.toByte()

    }

    constructor(init: Byte) {
        value = init
    }

    var value: Byte

    operator fun plus(other: UInt8): UInt8 {
        return UInt8((this.value + other.value).toByte())

    }

    operator fun minus(other: UInt8): UInt8 {
        return UInt8((this.value - other.value).toByte())
    }

    fun divide(other: UInt8): UInt8 {
        return UInt8((value.toInt() / other.value.toInt()).toByte())
    }

    operator fun times(other: UInt8): UInt8 {
        return UInt8((value * other.value).toByte())
    }

    fun read(): Int = value.toInt() and 0xFF
}
