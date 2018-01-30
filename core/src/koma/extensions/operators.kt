package koma.extensions

import koma.abs
import koma.matrix.Matrix
import koma.polyfill.annotations.JsName

// Algebraic Operators 
operator fun <T> Matrix<T>.div(other: Int): Matrix<T> = this.div(other)
operator fun <T> Matrix<T>.div(other: T): Matrix<T> = this.div(other)

operator fun <T> Matrix<T>.times(other: Matrix<T>): Matrix<T> = this.times(other)
operator fun <T> Matrix<T>.times(other: T): Matrix<T> = this.times(other)
operator fun Double.times(other: Matrix<Double>) = other.times(this)
operator fun Int.times(other: Matrix<Double>) = other.times(this.toDouble())
operator fun Matrix<Double>.times(other: Int) = this * other.toDouble()

operator fun <T> Matrix<T>.unaryMinus(): Matrix<T> = this.unaryMinus()

operator fun <T> Matrix<T>.minus(other: T): Matrix<T> = this.minus(other)
operator fun <T> Matrix<T>.minus(other: Matrix<T>): Matrix<T> = this.minus(other)
operator fun Double.minus(other: Matrix<Double>) = other * -1 + this
operator fun Int.minus(other: Matrix<Double>) = other * -1 + this
operator fun Matrix<Double>.minus(value: Int) = this.minus(value.toDouble())

operator fun <T> Matrix<T>.plus(other: T): Matrix<T> = this.plus(other)
operator fun <T> Matrix<T>.plus(other: Matrix<T>): Matrix<T> = this.plus(other)
operator fun Double.plus(other: Matrix<Double>) = other.plus(this)
operator fun Int.plus(other: Matrix<Double>) = other.plus(this)
operator fun Matrix<Double>.plus(value: Int) = this.plus(value.toDouble())

/**
 * Element-wise exponentiation of each element in the matrix
 */
infix fun <T> Matrix<T>.epow(other: Int): Matrix<T> = this.epow(other)

/**
 * Allow infix operator "a emul b" to be element-wise multiplication of two matrices.
 */
infix fun Matrix<Double>.emul(other: Matrix<Double>) = this.elementTimes(other)



// Getters

/**
 * Get the element in the ith row and jth column.
 */
@JsName("get")
operator fun <T> Matrix<T>.get(i: Int, j: Int): T = getGeneric(i, j)

/**
 * Gets the ith element in the matrix. If 2D, selects elements in row-major order.
 */
@JsName("get1D")
operator fun <T> Matrix<T>.get(i: Int): T = getGeneric(i)

/**
 * Allow slicing, e.g. ```matrix[1..2, 3..4]```. Note that the range 1..2 is inclusive, so
 * it will retrieve row 1 and 2. Use 1.until(2) for a non-inclusive range.
 *
 * @param rows the set of rows to select
 * @param cols the set of columns to select
 *
 * @return a new matrix containing the submatrix.
 */
@JsName("getRanges")
operator fun <T> Matrix<T>.get(rows: IntRange, cols: IntRange): Matrix<T>
{
    val wrows = wrapRange(rows, numRows())
    val wcols = wrapRange(cols, numCols())

    val out = this.getFactory().zeros(wrows.endInclusive - wrows.start + 1,
            wcols.endInclusive - wcols.start + 1)
    for (row in wrows)
        for (col in wcols)
            out[row - wrows.start, col - wcols.start] = this[row, col]
    return out
}

/**
 * Allows for slicing of the rows and selection of a single column
 */
@JsName("getRowRange")
operator fun <T> Matrix<T>.get(rows: IntRange, cols: Int) = this[rows, cols..cols]

/**
 * Allows for slicing of the cols and selection of a single row
 */
@JsName("getColRange")
operator fun <T> Matrix<T>.get(rows: Int, cols: IntRange) = this[rows..rows, cols]

operator fun Matrix<Double>.get(i: Int) = this.getDouble(i)
operator fun Matrix<Double>.get(i: Int, j: Int) = this.getDouble(i, j)




// Setters

/**
 * Set the ith element in the matrix. If 2D, selects elements in row-major order.
 */
@JsName("set1D")
operator fun <T> Matrix<T>.set(i: Int, v: T) = setGeneric(i, v)
@JsName("set")
operator fun <T> Matrix<T>.set(i: Int, j: Int, v: T) = setGeneric(i, j, v)

/**
 * Allow assignment to a slice, e.g. ```matrix[1..2, 3..4]```=something. Note that the range 1..2 is inclusive, so
 * it will retrieve row 1 and 2. Use 1.until(2) for a non-inclusive range.
 *
 * @param rows the set of rows to select
 * @param cols the set of columns to select
 * @param value the matrix to set the subslice to
 *
 */
@JsName("setRanges")
operator fun <T> Matrix<T>.set(rows: IntRange, cols: IntRange, value: Matrix<T>)
{
    val wrows = wrapRange(rows, numRows())
    val wcols = wrapRange(cols, numCols())

    for (i in wrows)
        for (j in wcols)
            this[i, j] = value[i - wrows.start, j - wcols.start]
}
@JsName("setRangesScalar")
operator fun <T> Matrix<T>.set(rows: IntRange, cols: IntRange, value: T)
{
    val wrows = wrapRange(rows, numRows())
    val wcols = wrapRange(cols, numCols())

    for (i in wrows)
        for (j in wcols)
            this[i, j] = value
}
/**
 * Allow assignment to a slice, e.g. ```matrix[2, 3..4]```=something. Note that the range 3..4 is inclusive, so
 * it will retrieve col 3 and 4. Use 1.until(2) for a non-inclusive range.
 *
 * @param rows the row to select
 * @param cols the set of columns to select
 * @param value the matrix to set the subslice to
 *
 */
@JsName("setColRange")
operator fun <T> Matrix<T>.set(rows: Int, cols: IntRange, value: Matrix<T>)
{
    this[rows..rows, cols] = value
}
@JsName("setColRangeScalar")
operator fun <T> Matrix<T>.set(rows: Int, cols: IntRange, value: T)
{
    this[rows..rows, cols] = value
}
/**
 * Allow assignment to a slice, e.g. ```matrix[1..2, 3]```=something. Note that the range 1..2 is inclusive, so
 * it will retrieve row 1 and 2. Use 1.until(2) for a non-inclusive range.
 *
 * @param rows the set of rows to select
 * @param cols the column to select
 * @param value the matrix to set the subslice to
 *
 */
@JsName("setRowRange")
operator fun <T> Matrix<T>.set(rows: IntRange, cols: Int, value: Matrix<T>) {
    this[rows, cols..cols] = value
}
@JsName("setRowRangeScalar")
operator fun <T> Matrix<T>.set(rows: IntRange, cols: Int, value: T) {
    this[rows, cols..cols] = value
}
operator fun Matrix<Double>.set(i: Int, v: Int) = this.setDouble(i, v.toDouble())
operator fun Matrix<Double>.set(i: Int, v: Double) = this.setDouble(i, v)
operator fun Matrix<Double>.set(i: Int, j: Int, v: Int) = this.setDouble(i, j, v.toDouble())
operator fun Matrix<Double>.set(i: Int, j: Int, v: Double) = this.setDouble(i, j, v)

