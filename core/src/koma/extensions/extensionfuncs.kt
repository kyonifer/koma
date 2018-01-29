package koma.extensions

import koma.abs
import koma.matrix.Matrix
import koma.polyfill.annotations.*

// Workaround for weird kotlin bug where ext funcs
// arent overriding internal methods when used from
// inline funcs located in the same module
import koma.extensions.getRow as getrow
import koma.extensions.getCol as getcol
import koma.extensions.numRows as numrows
import koma.extensions.numCols as numcols

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



/**
 * Checks to see if any element in the matrix causes f to return true.
 *
 * @param f A function which takes in an element from the matrix and returns a Boolean.
 *
 * @return Whether or not any element, when passed into f, causes f to return true.
 */
@JsName("any")
fun <T> Matrix<T>.any(f: (T) -> Boolean): Boolean {
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
fun <T> Matrix<T>.all(f: (T) -> Boolean): Boolean {
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
fun <T> Matrix<T>.fill(f: (row: Int, col: Int) -> T): Matrix<T> {
    for (row in 0..this.numRows() - 1)
        for (col in 0..this.numCols() - 1)
            this[row, col] = f(row, col)
    return this
}
@JsName("fillDouble")
@JvmName("fillDouble")
fun Matrix<Double>.fill(f: (row: Int, col: Int) -> Double): Matrix<Double> {
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
    for (row in 0..this.numrows() - 1)
        for (col in 0..this.numcols() - 1)
            f(this[row, col])
}
@JsName("forEachDouble")
@JvmName("forEachDouble")
inline fun Matrix<Double>.forEach(f: (Double) -> Unit) {
    for (row in 0..this.numrows() - 1)
        for (col in 0..this.numcols() - 1)
            f(this[row, col])
}

/**
 * Passes each element in row major order into a function along with its index location.
 *
 * @param f A function that takes in a row,col position and an element value
 */
@JsName("forEachIndexed")
inline fun <T> Matrix<T>.forEachIndexed(f: (row: Int, col: Int, ele: T) -> Unit) {
    for (row in 0..this.numrows() - 1)
        for (col in 0..this.numcols() - 1)
            f(row, col, this[row, col])
}
@JsName("forEachIndexedDouble")
@JvmName("forEachIndexedDouble")
inline fun Matrix<Double>.forEachIndexed(f: (row: Int, col: Int, ele: Double) -> Unit) {
    for (row in 0..this.numrows() - 1)
        for (col in 0..this.numcols() - 1)
            f(row, col, this[row, col])
}
/**
 * Passes each row from top to bottom into a function.
 *
 * @param f A function that takes in a row (i.e. 1xN matrix)
 */
@JsName("forEachRow")
inline fun <T> Matrix<T>.forEachRow(f: (Matrix<T>) -> Unit) {
    for (row in 0..this.numrows() - 1)
        f(this.getrow(row))
}

/**
 * Passes each col from left to right into a function.
 *
 * @param f A function that takes in a row (i.e. 1xN matrix)
 */
@JsName("forEachCol")
inline fun <T> Matrix<T>.forEachCol(f: (Matrix<T>) -> Unit) {
    for (col in 0..this.numcols() - 1)
        f(this.getcol(col))
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
fun <T> Matrix<T>.map(f: (T) -> T): Matrix<T> {
    val out = this.getFactory().zeros(this.numRows(), this.numCols())
    for (row in 0..this.numRows() - 1)
        for (col in 0..this.numCols() - 1)
            out[row, col] = f(this[row, col])
    return out
}
@JsName("mapDouble")
@JvmName("mapDouble")
fun Matrix<Double>.map(f: (Double) -> Double): Matrix<Double> {
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
fun <T> Matrix<T>.mapIndexed(f: (row: Int, col: Int, ele: T) -> T): Matrix<T> {
    val out = this.getFactory().zeros(this.numRows(), this.numCols())
    for (row in 0..this.numRows() - 1)
        for (col in 0..this.numCols() - 1)
            out[row, col] = f(row, col, this[row, col])
    return out
}
@JsName("mapIndexedDouble")
@JvmName("mapIndexedDouble")
fun Matrix<Double>.mapIndexed(f: (row: Int, col: Int, ele: Double) -> Double): Matrix<Double> {
    val out = this.getFactory().zeros(this.numRows(), this.numCols())
    for (row in 0..this.numRows() - 1)
        for (col in 0..this.numCols() - 1)
            out[row, col] = f(row, col, this[row, col])
    return out
}

/**
 * Takes each row in a matrix, passes them through f, and puts the output of f into a
 * row of an output matrix.
 *
 * @param f A function that takes in a 1xN row and returns a 1xM row. Note that all output
 * rows must be the same length. In addition, the input and output element types must be the same.
 *
 * @return the new matrix after each row is mapped through f
 */
@JsName("mapRows")
fun <T> Matrix<T>.mapRows(f: (Matrix<T>) -> Matrix<T>): Matrix<T> {

    val outRows = Array(this.numRows()) {
        f(this.getRow(it))
    }

    val out = this.getFactory().zeros(this.numRows(), outRows[0].numCols())

    outRows.forEachIndexed { i, matrix ->
        if (matrix.numCols() != out.numCols())
            throw RuntimeException("All output rows of mapRows must have same number of columns")
        else
            out.setRow(i, outRows[i])
    }
    return out
}






/**
 * Set the ith element in the matrix. If 2D, selects elements in row-major order.
 */
@JsName("set1D")
operator fun <T> Matrix<T>.set(i: Int, v: T) = setGeneric(i, v)
@JsName("set")
operator fun <T> Matrix<T>.set(i: Int, j: Int, v: T) = setGeneric(i, j, v)
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


// Efficient unboxed access to get/set if element type is known to be Double
operator fun Matrix<Double>.set(i: Int, v: Double) = this.setDouble(i, v)
operator fun Matrix<Double>.set(i: Int, j: Int, v: Double) = this.setDouble(i, j, v)
operator fun Matrix<Double>.get(i: Int) = this.getDouble(i)
operator fun Matrix<Double>.get(i: Int, j: Int) = this.getDouble(i, j)
operator fun Matrix<Double>.set(i: Int, v: Int) = this.setDouble(i, v.toDouble())
operator fun Matrix<Double>.set(i: Int, j: Int, v: Int) = this.setDouble(i, j, v.toDouble())

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

fun <T> Matrix<T>.repr(): String = koma.platformsupport.repr(this)

/**
 * Transpose operator.
 */
fun <T> Matrix<T>.T() = transpose()

/**
 * Transpose operator.
 */
@JsName("TProperty")
val <T> Matrix<T>.T: Matrix<T>
    get() = this.transpose()


fun <T> Matrix<T>.toIterable() = object: Iterable<T> {
    override fun iterator(): Iterator<T> {
        class MatrixIterator(var matrix: Matrix<T>) : Iterator<T> {
            private var cursor = 0
            override fun next(): T {
                cursor += 1
                return matrix[cursor - 1]
            }
            override fun hasNext() = cursor < matrix.numCols() * matrix.numRows()
        }
        return MatrixIterator(this@toIterable)
    }
}
/**
 * Multiplies the matrix by itself [exponent] times (using matrix multiplication).
 */
@JsName("pow")
infix fun <T> Matrix<T>.pow(exponent: Int): Matrix<T> {
    var out = this.copy()
    for (i in 1..exponent - 1)
        out = out.times(this)
    return out
}


@JsName("wrapRange__")
fun <T> Matrix<T>.wrapRange(range: IntRange, max: Int) =
        if(range.endInclusive >= 0)
            range
        else
            range.start..(max+range.endInclusive)

/**
 * Select a set of cols from a matrix to form the cols of a new matrix.
 * For example, if you wanted a new matrix consisting of the first, second, and
 * fifth cols of an input matrix, you would write ```input.selectCols(0,1,4)```.
 */
@JsName("selectCols")
fun <T> Matrix<T>.selectCols(vararg idxs: Int) =
        getFactory()
                .zeros(this.numRows(), idxs.size)
                .fill { row, col -> this[row, idxs[col]] }

@JsName("selectColsMatrix")
fun <T, U: Number> Matrix<T>.selectCols(idxs: Matrix<U>) =
        getFactory()
                .zeros(this.numRows(), idxs.numRows()*idxs.numCols())
                .fill { row, col -> this[row, idxs[col].toInt()] }


/**
 * Select a set of rows from a matrix to form the rows of a new matrix.
 * For example, if you wanted a new matrix consisting of the first, second, and
 * fifth rows of an input matrix, you would write ```input.selectRows(0,1,4)```.
 */
@JsName("selectRows")
fun <T> Matrix<T>.selectRows(vararg idxs: Int) =
        getFactory()
                .zeros(idxs.size, this.numCols())
                .fill { row, col -> this[idxs[row], col] }

@JsName("selectRowsMatrix")
fun <T, U: Number> Matrix<T>.selectRows(idxs: Matrix<U>) =
        getFactory()
                .zeros(idxs.numRows()*idxs.numCols(), this.numCols())
                .fill { row, col -> this[idxs[row].toInt(), col] }



/**
 * Calculates the cumulative (ongoing) sum of a matrix's elements. For example,
 * ```cumsum(mat[1,2,3])``` would return ```mat[1,3,6]```. Assumes matrix type is convertible to
 * double.
 *
 * @return A 1xarr.numRows*arr.numCols vector storing the ongoing cumsum.
 *
 */
fun <T> Matrix<T>.cumSum(): Matrix<T> {
    val out = this.getFactory().zeros(1, this.numRows() * this.numCols())
    for (i in 0..(this.numRows() * this.numCols() - 1)) {
        val ele = this.getDouble(i)
        out.setDouble(i, if (i == 0) ele else ele + out.getDouble(i - 1))
    }
    return out
}



/**
 * Takes each row in a matrix, passes them through f, and puts the outputs into a List.
 * In contrast to this#mapRows, the usage of a list as the output container allows for
 * arbitrary output types, such as taking a double matrix and returning a list of strings.
 *
 * @param f A function that takes in a 1xN row and returns a 1xM row. Note that all output
 * rows must be the same length.
 */
@JsName("mapRowsToList")
fun <T, U> Matrix<T>.mapRowsToList(f: (Matrix<T>) -> U): List<U> {
    val a = ArrayList<U>(this.numRows())
    this.forEachRow {
        a.add(f(it))
    }
    return a
}

/**
 * Takes each col in a matrix, passes them through f, and puts the output of f into a
 * col of an output matrix.
 *
 * @param f A function that takes in a Nx1 col and returns a Mx1 col. Note that all output
 * cols must be the same length. In addition, the input and output element types must be the same.
 *
 * @return the new matrix after each col is mapped through f
 */
@JsName("mapCols")
fun <T> Matrix<T>.mapCols(f: (Matrix<T>) -> Matrix<T>): Matrix<T> {

    val outCols = Array(this.numCols()) {
        val out = f(this.getCol(it))
        // If user creates a row vector auto convert to column for them
        if (out.numRows() == 1) out.T else out
    }

    val out = this.getFactory().zeros(outCols[0].numRows(), this.numCols())

    outCols.forEachIndexed { i, matrix ->
        if (matrix.numRows() != out.numRows())
            throw RuntimeException("All output rows of mapCols must have same number of columns")
        else
            out.setCol(i, outCols[i])
    }
    return out
}

/**
 * Takes each col in a matrix, passes them through f, and puts the outputs into a List.
 * In contrast to this#mapCols, the usage of a list as the output container allows for
 * arbitrary output types, such as taking a double matrix and returning a list of strings.
 *
 * @param f A function that takes in a Nx1 col and returns a Mx1 col. Note that all output
 * cols must be the same length.
 */
@JsName("mapColsToList")
fun <T, U> Matrix<T>.mapColsToList(f: (Matrix<T>) -> U): List<U> {
    // TODO: Replace if cross-platform set capacity method is added to kotlin
    val a = arrayListOf<U>()
    this.forEachCol {
        a.add(f(it))
    }
    return a
}

/**
 * Returns the given vector as a row vector. Will call transpose() on column vectors
 */
fun <T> Matrix<T>.asRowVector() = if (this.numRows() != 1 && this.numCols() == 1) this.T else this

/**
 * Returns the given vector as a row vector. Will call transpose() on row vectors
 */
fun <T> Matrix<T>.asColVector() = if (this.numRows() == 1 && this.numCols() != 1) this.T else this


/**
 * Returns a Matrix as a double 2D array. Intended for MATLAB interop.
 *
 * @return a 2D array copy of the matrix.
 */
fun <T> Matrix<T>.to2DArray(): Array<DoubleArray> {
    val out = Array(numRows(), { DoubleArray(numCols()) })
    for (row in 0..this.numRows() - 1)
        for (col in 0..this.numCols() - 1)
            out[row][col] = this.getDouble(row, col)
    return out
}

/**
 * Builds a new matrix with a subset of the rows of this matrix, using only the rows
 * for which the function f returns true.
 *
 * @param f A function which takes a row index and a row, and returns true if that
 * row should be included in the output matrix.
 */
fun <T> Matrix<T>.filterRowsIndexed(f: (rowIndex: Int, row: Matrix<T>) -> Boolean): Matrix<T> {
    var rowIndex = 0
    val rowList = mapRowsToList { row -> if (f(rowIndex++, row)) rowIndex - 1 else null }.filterNotNull()
    return selectRows(*(rowList.toIntArray()))
}

/**
 * Builds a new matrix with a subset of the rows of this matrix, using only the rows
 * for which the function f returns true.
 *
 * @param f A function which takes a row and returns true if that row should be
 * be included in the output matrix.
 */
fun <T> Matrix<T>.filterRows(f: (row: Matrix<T>) -> Boolean) = filterRowsIndexed { n, row -> f(row) }

/**
 * Builds a new matrix with a subset of the columns of this matrix, using only the
 * columns for which the function f returns true.
 *
 * @param f A function which takes a column index and a column, and returns true if
 * that column should be included in the output matrix.
 */
fun <T> Matrix<T>.filterColsIndexed(f: (colIndex: Int, col: Matrix<T>) -> Boolean): Matrix<T> {
    var colIndex = 0
    val colList = mapColsToList { col -> if (f(colIndex++, col)) colIndex - 1 else null }.filterNotNull()
    return selectCols(*(colList.toIntArray()))
}

/**
 * Builds a new matrix with a subset of the columns of this matrix, using only the
 * columns for which the function f returns true.
 *
 * @param f A function which takes a column and returns true if that column should
 * be included in the output matrix.
 */
fun <T> Matrix<T>.filterCols(f: (col: Matrix<T>) -> Boolean) = filterColsIndexed { n, col -> f(col) }
