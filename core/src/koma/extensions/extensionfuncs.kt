package koma.extensions

import koma.abs
import koma.matrix.Matrix
import koma.matrix.MatrixFactory
import koma.polyfill.annotations.*

// Workaround for weird kotlin bug where ext funcs
// arent overriding internal methods when used from
// inline funcs located in the same module
import koma.extensions.getRow as getrow
import koma.extensions.getCol as getcol
import koma.extensions.numRows as numrows
import koma.extensions.numCols as numcols



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

/**
 * Transpose of the matrix
 */
fun <T> Matrix<T>.transpose(): Matrix<T> = this.transpose()
/**
 * Element-wise multiplication with another matrix
 */
fun <T> Matrix<T>.elementTimes(other: Matrix<T>): Matrix<T> = this.elementTimes(other)

/**
 * Number of rows in the matrix
 */
fun <T> Matrix<T>.numRows(): Int = this.numRows()
/**
 * Number of columns in the matrix
 */
fun <T> Matrix<T>.numCols(): Int = this.numCols()

/**
 * Returns a copy of this matrix (same values, new memory)
 */

fun <T> Matrix<T>.copy(): Matrix<T> = this.copy()

/**
 * Retrieves the data formatted as doubles in row-major order
 * This method is only for performance over potentially boxing get(Double)
 * methods. This method may or may not return a copy, and thus should be
 * treated as read-only unless backend behavior is known.
 */
fun <T> Matrix<T>.getDoubleData(): DoubleArray = this.getDoubleData()

fun <T> Matrix<T>.getRow(row: Int): Matrix<T> = this.getRow(row)
fun <T> Matrix<T>.getCol(col: Int): Matrix<T> = this.getCol(col)
fun <T> Matrix<T>.setCol(index: Int, col: Matrix<T>) = this.setCol(index, col)
fun <T> Matrix<T>.setRow(index: Int, row: Matrix<T>) = this.setRow(index, row)

/**
 * (lower triangular) Cholesky decomposition of the matrix. Matrix must be positive-semi definite.
 */
fun <T> Matrix<T>.chol(): Matrix<T> = this.chol()
/**
 * LU Decomposition. Returns p, l, u matrices as a triple.
 */
fun <T> Matrix<T>.LU(): Triple<Matrix<T>, Matrix<T>, Matrix<T>> = this.LU()
fun <T> Matrix<T>.QR(): Pair<Matrix<T>, Matrix<T>> = this.QR()
/**
 * Returns U, S, V such that A = U * S * V.T
 */
fun <T> Matrix<T>.SVD(): Triple<Matrix<T>, Matrix<T>, Matrix<T>> = this.SVD()

// TODO: need schur, eig


// Advanced Functions
/**
 * Compute the matrix exponential e^x (NOT elementwise)
 */
fun <T> Matrix<T>.expm(): Matrix<T> = this.expm()

/**
 * Solves A*X=B for X, returning X (X is either column vector or a matrix composed of several col vectors).
 * A is the current matrix, B is the passed in [other], and X is the returned matrix.
 */
fun <T> Matrix<T>.solve(other: Matrix<T>): Matrix<T> = this.solve(other)

// Basic Functions
/**
 * Matrix inverse (square matrices)
 */
fun <T> Matrix<T>.inv(): Matrix<T> = this.inv()
/**
 * Determinant of the matrix
 */
fun <T> Matrix<T>.det(): T = this.det()
/**
 * Pseudo-inverse of (non-square) matrix
 */
fun <T> Matrix<T>.pinv(): Matrix<T> = this.pinv()
/**
 * Frobenius normal of the matrix
 */
fun <T> Matrix<T>.normF(): T = this.normF()

/**
 * Induced, p=1 normal of the matrix. Equivalent of `norm(matrix,1)` in scipy.
 */
fun <T> Matrix<T>.normIndP1(): T = this.normIndP1()
/**
 * Sum of all the elements in the matrix.
 */
fun <T> Matrix<T>.elementSum(): T = this.elementSum()
fun <T> Matrix<T>.diag(): Matrix<T> = this.diag()
/**
 * Maximum value contained in the matrix
 */
fun <T> Matrix<T>.max(): T = this.max()
/**
 * Mean (average) of all the elements in the matrix.
 */
fun <T> Matrix<T>.mean(): T = this.mean()
/**
 * Minimum value contained in the matrix
 */
fun <T> Matrix<T>.min(): T = this.min()

/**
 * Row major 1D index.
 */
fun <T> Matrix<T>.argMax(): Int = this.argMax()

/**
 * Row major 1D index.
 */
fun <T> Matrix<T>.argMin(): Int = this.argMin()

/**
 * The matrix trace.
 */
fun <T> Matrix<T>.trace(): T = this.trace()

/**
 * Returns the underlying matrix object from the back-end this Matrix is wrapping. This should be used
 * sparingly (as it breaks encapsulation), but it can increase performance by using computation specifically
 * designed for a particular back-end. Code using this method should not rely on a particular back-end, and
 * should always fallback to slow generic code if an unrecognized matrix is returned here (e.g. use [get] and [set])
 * to access the elements generically).
 */
fun <T> Matrix<T>.getBaseMatrix(): Any = this.getBaseMatrix()


/**
 *  Because sometimes all you have is a Matrix, but you really want a MatrixFactory.
 */
fun <T> Matrix<T>.getFactory(): MatrixFactory<Matrix<T>> = this.getFactory()