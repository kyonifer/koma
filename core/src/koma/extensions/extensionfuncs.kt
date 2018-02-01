package koma.extensions

import koma.abs
import koma.matrix.Matrix
import koma.polyfill.annotations.*

/**
 * Checks to see if any element in the matrix causes f to return true.
 *
 * @param f A function which takes in an element from the matrix and returns a Boolean.
 *
 * @return Whether or not any element, when passed into f, causes f to return true.
 */
@JsName("any")
inline fun <T> Matrix<T>.any(f: (T) -> Boolean): Boolean {
    for (row in 0..this.numRows() - 1)
        for (col in 0..this.numCols() - 1)
            if (f(this[row, col]))
                return true
    return false
}
@JsName("anyDouble")
@JvmName("anyDouble")
inline fun Matrix<Double>.any(f: (Double) -> Boolean): Boolean {
    for (row in 0..this.numRows() - 1)
        for (col in 0..this.numCols() - 1)
            if (f(this[row, col]))
                return true
    return false
}

/**
 * Checks to see if all elements cause f to return true.
 *
 * @param f A function which takes in an element from the matrix and returns a Boolean.
 *
 * @return Returns true only if f is true for all elements of the input matrix
 */
@JsName("allExt")
inline fun <T> Matrix<T>.all(f: (T) -> Boolean): Boolean {
    for (row in 0..this.numRows() - 1)
        for (col in 0..this.numCols() - 1)
            if (!f(this[row, col]))
                return false
    return true
}
@JsName("all")
@JvmName("allDouble")
inline fun Matrix<Double>.all(f: (Double) -> Boolean): Boolean {
    for (row in 0..this.numRows() - 1)
        for (col in 0..this.numCols() - 1)
            if (!f(this[row, col]))
                return false
    return true
}

/**
 * Fills the matrix with the values returned by the input function.
 *
 * @param f A function which takes row,col and returns the value to fill. Note that
 * the return type must be the matrix primitive type (e.g. Double).
 */
inline fun <T> Matrix<T>.fill(f: (row: Int, col: Int) -> T): Matrix<T> {
    for (row in 0..this.numRows() - 1)
        for (col in 0..this.numCols() - 1)
            this[row, col] = f(row, col)
    return this
}
@JsName("fillDouble")
@JvmName("fillDouble")
inline fun Matrix<Double>.fill(f: (row: Int, col: Int) -> Double): Matrix<Double> {
    for (row in 0..this.numRows() - 1)
        for (col in 0..this.numCols() - 1)
            this[row, col] = f(row, col)
    return this
}

/**
 * Passes each element in row major order into a function.
 *
 * @param f A function that takes in an element
 *
 */
@JsName("forEach")
inline fun <T> Matrix<T>.forEach(f: (T) -> Unit) {
    for (row in 0..this.numRows() - 1)
        for (col in 0..this.numCols() - 1)
            f(this[row, col])
}
@JsName("forEachDouble")
@JvmName("forEachDouble")
inline fun Matrix<Double>.forEach(f: (Double) -> Unit) {
    for (row in 0..this.numRows() - 1)
        for (col in 0..this.numCols() - 1)
            f(this[row, col])
}

/**
 * Passes each element in row major order into a function along with its index location.
 *
 * @param f A function that takes in a row,col position and an element value
 */
@JsName("forEachIndexed")
inline fun <T> Matrix<T>.forEachIndexed(f: (row: Int, col: Int, ele: T) -> Unit) {
    for (row in 0..this.numRows() - 1)
        for (col in 0..this.numCols() - 1)
            f(row, col, this[row, col])
}
@JsName("forEachIndexedDouble")
@JvmName("forEachIndexedDouble")
inline fun Matrix<Double>.forEachIndexed(f: (row: Int, col: Int, ele: Double) -> Unit) {
    for (row in 0..this.numRows() - 1)
        for (col in 0..this.numCols() - 1)
            f(row, col, this[row, col])
}


// TODO: These need specialized versions for performance
/**
 * Takes each element in a matrix, passes them through f, and puts the output of f into an
 * output matrix. This process is done in row-major order.
 *
 * @param f A function that takes in an element and returns an element
 *
 * @return the new matrix after each element is mapped through f
 */
@JsName("map")
inline fun <T> Matrix<T>.map(f: (T) -> T): Matrix<T> {
    val out = this.getFactory().zeros(this.numRows(), this.numCols())
    for (row in 0..this.numRows() - 1)
        for (col in 0..this.numCols() - 1)
            out[row, col] = f(this[row, col])
    return out
}
@JsName("mapDouble")
@JvmName("mapDouble")
inline fun Matrix<Double>.map(f: (Double) -> Double): Matrix<Double> {
    val out = this.getFactory().zeros(this.numRows(), this.numCols())
    for (row in 0..this.numRows() - 1)
        for (col in 0..this.numCols() - 1)
            out[row, col] = f(this[row, col])
    return out
}

/**
 * Takes each element in a matrix, passes them through f, and puts the output of f into an
 * output matrix. This process is done in row-major order.
 *
 * @param f A function that takes in an element and returns an element. Function also takes
 *      in the row, col index of the element's location.
 *
 * @return the new matrix after each element is mapped through f
 */
@JsName("mapIndexed")
inline fun <T> Matrix<T>.mapIndexed(f: (row: Int, col: Int, ele: T) -> T): Matrix<T> {
    val out = this.getFactory().zeros(this.numRows(), this.numCols())
    for (row in 0..this.numRows() - 1)
        for (col in 0..this.numCols() - 1)
            out[row, col] = f(row, col, this[row, col])
    return out
}
@JsName("mapIndexedDouble")
@JvmName("mapIndexedDouble")
inline fun Matrix<Double>.mapIndexed(f: (row: Int, col: Int, ele: Double) -> Double): Matrix<Double> {
    val out = this.getFactory().zeros(this.numRows(), this.numCols())
    for (row in 0..this.numRows() - 1)
        for (col in 0..this.numCols() - 1)
            out[row, col] = f(row, col, this[row, col])
    return out
}


fun Matrix<Double>.allClose(other: Matrix<Double>, rtol:Double=1e-05, atol:Double=1e-08): Boolean {
    if(other.numRows() != numRows() || other.numCols() != numCols())
        return false
    for(row in 0 until this.numRows()) {
        for (col in 0 until this.numCols()) {
            val err = abs(this[row, col] - other[row, col])
            if (err > atol + rtol * abs(this[row, col]))
                return false
        }
    }
    return true
}

// Getters

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
operator fun Matrix<Double>.set(i: Int, v: Double) = this.setDouble(i, v)
operator fun Matrix<Double>.set(i: Int, j: Int, v: Double) = this.setDouble(i, j, v)
operator fun Matrix<Double>.set(i: Int, v: Int) = this.setDouble(i, v.toDouble())
operator fun Matrix<Double>.set(i: Int, j: Int, v: Int) = this.setDouble(i, j, v.toDouble())

// Other operators

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
