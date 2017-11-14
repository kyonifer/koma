package koma.matrix

import koma.polyfill.annotations.*
import koma.*

/**
 * A general facade for a Matrix type. Allows for various backend to be
 * implemented to actually perform the computation. A koma backend must both
 * implement this class and MatrixFactory. A matrix is guaranteed to be 2D and 
 * to have a numerical type. For storage of arbitrary types and dimensions, see
 * [koma.ndarray.NDArray].
 */
interface Matrix<T> {
    // Algebraic Operators
    @JsName("rem")
    operator fun rem(other: Matrix<T>): Matrix<T>
    @JsName("divInt")
    operator fun div(other: Int): Matrix<T>
    @JsName("divScalar")
    operator fun div(other: T): Matrix<T>
    @JsName("times")
    operator fun times(other: Matrix<T>): Matrix<T>
    @JsName("timesScalar")
    operator fun times(other: T): Matrix<T>
    operator fun unaryMinus(): Matrix<T>
    @JsName("minusScalar")
    operator fun minus(other: T): Matrix<T>
    @JsName("minus")
    operator fun minus(other: Matrix<T>): Matrix<T>
    @JsName("plusScalar")
    operator fun plus(other: T): Matrix<T>
    @JsName("plus")
    operator fun plus(other: Matrix<T>): Matrix<T>
    fun transpose(): Matrix<T>
    fun elementTimes(other: Matrix<T>): Matrix<T>
    fun epow(other: T): Matrix<T>
    infix fun epow(other: Int): Matrix<T>

    // Dimensions
    fun numRows(): Int
    fun numCols(): Int

    fun copy(): Matrix<T>

    // For speed optimized code (if backend isnt chosen type, may throw an exception or incur performance loss)
    @JsName("getInt")
    fun getInt(i: Int, j: Int): Int
    @JsName("getDouble")
    fun getDouble(i: Int, j: Int): Double
    @JsName("getFloat")
    fun getFloat(i: Int, j: Int): Float
    @JsName("getInt1D")
    fun getInt(i: Int): Int
    @JsName("getDouble1D")
    fun getDouble(i: Int): Double
    @JsName("getFloat1D")
    fun getFloat(i: Int): Float
    @JsName("setInt1D")
    fun setInt(i: Int, v: Int)
    @JsName("setDouble1D")
    fun setDouble(i: Int, v: Double)
    @JsName("setFloat1D")
    fun setFloat(i: Int, v: Float)
    @JsName("setInt")
    fun setInt(i: Int, j: Int, v: Int)
    @JsName("setDouble")
    fun setDouble(i: Int, j: Int, v: Double)
    @JsName("setFloat")
    fun setFloat(i: Int, j: Int, v: Float)
    @JsName("getGeneric")
    fun getGeneric(i: Int, j: Int): T
    @JsName("getGeneric1D")
    fun getGeneric(i: Int): T
    @JsName("setGeneric")
    fun setGeneric(i: Int, j: Int, v: T)
    @JsName("setGeneric1D")
    fun setGeneric(i: Int, v: T)

    /**
     * Retrieves the data formatted as doubles in row-major order
     * This method is only for performance over potentially boxing get(Double)
     * methods. This method may or may not return a copy, and thus should be
     * treated as read-only unless backend behavior is known.
     */
    fun getDoubleData(): DoubleArray

    @JsName("getRow")
    fun getRow(row: Int): Matrix<T>
    @JsName("getCol")
    fun getCol(col: Int): Matrix<T>
    @JsName("setCol")
    fun setCol(index: Int, col: Matrix<T>)
    @JsName("setRow")
    fun setRow(index: Int, row: Matrix<T>)

    fun chol(): Matrix<T>
    fun LU(): Triple<Matrix<T>, Matrix<T>, Matrix<T>>
    fun QR(): Pair<Matrix<T>, Matrix<T>>
    fun SVD(): Triple<Matrix<T>, Matrix<T>, Matrix<T>> // Returns U, S, V such that A = U * S * V.T
    // TODO: need schur, eig


    // Advanced Functions
    fun expm(): Matrix<T>
    @JsName("solve")
    fun solve(A: Matrix<T>, B: Matrix<T>): Matrix<T>

    // Basic Functions
    fun inv(): Matrix<T>
    fun det(): T
    fun pinv(): Matrix<T>
    fun normF(): T
    fun normIndP1(): T
    fun elementSum(): T
    fun diag(): Matrix<T>
    fun max(): T // add dimension: Int?
    fun mean(): T
    fun min(): T
    
    /**
     * Row major 1D index.
     */
    fun argMax(): Int
    
    /**
     * Row major 1D index.
     */
    fun argMin(): Int
    fun norm(): T // L2 (Euclidean) norm
    fun trace(): T
    
    /**
     * Transpose operator.
     */
    fun T(): Matrix<T> // In MATLAB, this appears at foo.T

    /**
     * Returns the underlying matrix object from the back-end this Matrix is wrapping. This should be used
     * sparingly (as it breaks encapsulation), but it can increase performance by using computation specifically
     * designed for a particular back-end. Code using this method should not rely on a particular back-end, and
     * should always fallback to slow generic code if an unrecognized matrix is returned here (e.g. use [get] and [set])
     * to access the elements generically).
     */
    fun getBaseMatrix(): Any


    /**
     *  Because sometimes all you have is a Matrix, but you really want a MatrixFactory.
     */
    fun getFactory(): MatrixFactory<Matrix<T>>


    fun repr(): String = koma.platformsupport.repr(this)

    /**
     * Transpose operator.
     */
    @JsName("TProperty")
    val T: Matrix<T>
        get() = this.transpose()


    fun toIterable() = object: Iterable<T> {
        override fun iterator(): Iterator<T> {
            class MatrixIterator(var matrix: Matrix<T>) : Iterator<T> {
                private var cursor = 0
                override fun next(): T {
                    cursor += 1
                    return matrix[cursor - 1]
                }
                override fun hasNext() = cursor < matrix.numCols() * matrix.numRows()
            }
            return MatrixIterator(this@Matrix)
        }
    }
    @JsName("pow")
    infix fun pow(exponent: Int): Matrix<T> {
        var out = this.copy()
        for (i in 1..exponent - 1)
            out *= this
        return out
    }


    @JsName("wrapRange__")
    fun wrapRange(range: IntRange, max: Int) =
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
    fun selectCols(vararg idxs: Int) =
            getFactory()
                    .zeros(this.numRows(), idxs.size)
                    .fill { row, col -> this[row, idxs[col]] }

    @JsName("selectColsMatrix")
    fun <U: Number> selectCols(idxs: Matrix<U>) =
            getFactory()
                    .zeros(this.numRows(), idxs.numRows()*idxs.numCols())
                    .fill { row, col -> this[row, idxs[col].toInt()] }


    /**
     * Select a set of rows from a matrix to form the rows of a new matrix.
     * For example, if you wanted a new matrix consisting of the first, second, and
     * fifth rows of an input matrix, you would write ```input.selectRows(0,1,4)```.
     */
    @JsName("selectRows")
    fun selectRows(vararg idxs: Int) =
            getFactory()
                    .zeros(idxs.size, this.numCols())
                    .fill { row, col -> this[idxs[row], col] }

    @JsName("selectRowsMatrix")
    fun <U: Number> selectRows(idxs: Matrix<U>) =
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
    fun cumSum(): Matrix<T> {
        val out = this.getFactory().zeros(1, this.numRows() * this.numCols())
        for (i in 0..(this.numRows() * this.numCols() - 1)) {
            val ele = this.getDouble(i)
            out.setDouble(i, if (i == 0) ele else ele + out.getDouble(i - 1))
        }
        return out
    }

    // TODO: Remove all these superfluous jsnames for 
    // solo funcs if kotlin-js ever gets less mangle-happy.

    /**
     * Passes each element in row major order into a function.
     *
     * @param f A function that takes in an element
     *
     */
    @JsName("forEach")
    fun forEach(f: (T) -> Unit) {
        for (row in 0..this.numRows() - 1)
            for (col in 0..this.numCols() - 1)
                f(this[row, col])
    }
    @Deprecated("Use forEach", ReplaceWith("forEach(f)"))
    fun each(f: (T) -> Unit) = forEach(f)

    /**
     * Passes each element in row major order into a function along with its index location.
     *
     * @param f A function that takes in a row,col position and an element value
     */
    @JsName("forEachIndexed")
    fun forEachIndexed(f: (row: Int, col: Int, ele: T) -> Unit) {
        for (row in 0..this.numRows() - 1)
            for (col in 0..this.numCols() - 1)
                f(row, col, this[row, col])
    }
    @Deprecated("Use forEachIndexed", ReplaceWith("forEachIndexed(f)"))
    fun eachIndexed(f: (row: Int, col: Int, ele: T) -> Unit) = forEachIndexed(f)

    /**
     * Passes each row from top to bottom into a function.
     *
     * @param f A function that takes in a row (i.e. 1xN matrix)
     */
    @JsName("forEachRow")
    fun forEachRow(f: (Matrix<T>) -> Unit) {
        for (row in 0..this.numRows() - 1)
            f(this.getRow(row))
    }
    @Deprecated("Use forEachRow", ReplaceWith("forEachRow(f)"))
    fun eachRow(f: (Matrix<T>) -> Unit) = forEachRow(f)
    
    /**
     * Passes each col from left to right into a function.
     *
     * @param f A function that takes in a row (i.e. 1xN matrix)
     */
    @JsName("forEachCol")
    fun forEachCol(f: (Matrix<T>) -> Unit) {
        for (col in 0..this.numCols() - 1)
            f(this.getCol(col))
    }
    @Deprecated("Use forEachCol", ReplaceWith("forEachCol(f)"))
    fun eachCol(f: (Matrix<T>) -> Unit) = forEachCol(f)


    /**
     * Takes each element in a matrix, passes them through f, and puts the output of f into an
     * output matrix. This process is done in row-major order.
     *
     * @param f A function that takes in an element and returns an element
     *
     * @return the new matrix after each element is mapped through f
     */
    @JsName("map")
    fun map(f: (T) -> T): Matrix<T> {
        val out = this.getFactory().zeros(this.numRows(), this.numCols())
        for (row in 0..this.numRows() - 1)
            for (col in 0..this.numCols() - 1)
                out[row, col] = f(this[row, col])
        return out
    }
    @Deprecated("Use map instead", ReplaceWith("map(f)"))
    fun mapMat(f: (T) -> T): Matrix<T> = map(f)

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
    fun mapIndexed(f: (row: Int, col: Int, ele: T) -> T): Matrix<T> {
        val out = this.getFactory().zeros(this.numRows(), this.numCols())
        for (row in 0..this.numRows() - 1)
            for (col in 0..this.numCols() - 1)
                out[row, col] = f(row, col, this[row, col])
        return out
    }
    @Deprecated("Use mapIndexed", ReplaceWith("mapIndexed(f)"))
    fun mapMatIndexed(f: (row: Int, col: Int, ele: T) -> T): Matrix<T> = mapIndexed(f)

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
    fun mapRows(f: (Matrix<T>) -> Matrix<T>): Matrix<T> {

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
     * Takes each row in a matrix, passes them through f, and puts the outputs into a List.
     * In contrast to this#mapRows, the usage of a list as the output container allows for
     * arbitrary output types, such as taking a double matrix and returning a list of strings.
     *
     * @param f A function that takes in a 1xN row and returns a 1xM row. Note that all output
     * rows must be the same length.
     */
    @JsName("mapRowsToList")
    fun <U> mapRowsToList(f: (Matrix<T>) -> U): List<U> {
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
    fun mapCols(f: (Matrix<T>) -> Matrix<T>): Matrix<T> {

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
    fun <U> mapColsToList(f: (Matrix<T>) -> U): List<U> {
        // TODO: Replace if cross-platform set capacity method is added to kotlin
        val a = arrayListOf<U>()
        this.forEachCol {
            a.add(f(it))
        }
        return a
    }

    /**
     * Checks to see if any element in the matrix causes f to return true.
     *
     * @param f A function which takes in an element from the matrix and returns a Boolean.
     *
     * @return Whether or not any element, when passed into f, causes f to return true.
     */
    @JsName("any")
    fun any(f: (T) -> Boolean): Boolean {
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
    @JsName("all")
    fun all(f: (T) -> Boolean): Boolean {
        for (row in 0..this.numRows() - 1)
            for (col in 0..this.numCols() - 1)
                if (!f(this[row, col]))
                    return false
        return true
    }


    /**
     * Returns the given vector as a row vector. Will call transpose() on column vectors
     */
    fun asRowVector() = if (this.numRows() != 1 && this.numCols() == 1) this.T else this

    /**
     * Returns the given vector as a row vector. Will call transpose() on row vectors
     */
    fun asColVector() = if (this.numRows() == 1 && this.numCols() != 1) this.T else this


    /**
     * Returns a Matrix as a double 2D array. Intended for MATLAB interop.
     *
     * @return a 2D array copy of the matrix.
     */
    fun to2DArray(): Array<DoubleArray> {
        val out = Array(numRows(), { DoubleArray(numCols()) })
        for (row in 0..this.numRows() - 1)
            for (col in 0..this.numCols() - 1)
                out[row][col] = this.getDouble(row, col)
        return out
    }

    /**
     * Fills the matrix with the values returned by the input function.
     *
     * @param f A function which takes row,col and returns the value to fill. Note that
     * the return type must be the matrix primitive type (e.g. Double).
     */
    fun fill(f: (row: Int, col: Int) -> T): Matrix<T> {
        for (row in 0..this.numRows() - 1)
            for (col in 0..this.numCols() - 1)
                this[row, col] = f(row, col)
        return this
    }

}

