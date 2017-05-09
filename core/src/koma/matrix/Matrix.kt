package koma.matrix

import koma.polyfill.annotations.*

/**
 * A general facade for a Matrix type. Allows for various backend to be
 * implemented to actually perform the computation. A koma backend must both
 * implement this class and MatrixFactory. A matrix is guaranteed to be 2D and 
 * to have a numerical type. For storage of arbitrary types and dimensions, see
 * [koma.ndarray.NDArray].
 */
interface Matrix<T, MatType: Matrix<T, MatType>> {
    // Algebraic Operators
    @JsName("rem")
    operator fun rem(other: MatType): MatType
    @JsName("divScalar")
    operator fun div(other: T): MatType
    @JsName("times")
    operator fun times(other: MatType): MatType
    @JsName("timesScalar")
    operator fun times(other: T): MatType
    operator fun unaryMinus(): MatType
    @JsName("minusScalar")
    operator fun minus(other: T): MatType
    @JsName("minus")
    operator fun minus(other: MatType): MatType
    @JsName("plusScalar")
    operator fun plus(other: T): MatType
    @JsName("plus")
    operator fun plus(other: MatType): MatType
    fun transpose(): MatType
    fun elementTimes(other: MatType): MatType
    fun epow(other: T): MatType
    infix fun epow(other: Int): MatType

    // Dimensions
    fun numRows(): Int
    fun numCols(): Int

    /**
     * Set the ith element in the matrix. If 2D, selects elements in row-major order.
     */
    @JsName("set1D")
    operator fun set(i: Int, v: T)
    @JsName("set")
    operator fun set(i: Int, j: Int, v: T)

    /**
     * Gets the ith element in the matrix. Note that indices are *not* nullable, but must be
     * in the type signature to induce generation of non-boxed indexers.
     */
    @JsName("get")
    operator fun get(i: Int?, j: Int?): T
    /**
     * Gets the ith element in the matrix. If 2D, selects elements in row-major order.
     * Note that indices are *not* nullable, but must be
     * in the type signature to induce generation of non-boxed indexers.
     */
    @JsName("get1D")
    operator fun get(i: Int?): T

    fun copy(): MatType


    /**
     * Retrieves the data formatted as doubles in row-major order
     * This method is only for performance over potentially boxing get(Double)
     * methods. This method may or may not return a copy, and thus should be
     * treated as read-only unless backend behavior is known.
     */
    fun getDoubleData(): DoubleArray

    @JsName("getRow")
    fun getRow(row: Int): MatType
    @JsName("getCol")
    fun getCol(col: Int): MatType
    @JsName("setCol")
    fun setCol(index: Int, col: MatType)
    @JsName("setRow")
    fun setRow(index: Int, row: MatType)

    fun chol(): MatType
    fun LU(): Triple<MatType, MatType, MatType>
    fun QR(): Pair<MatType, MatType>
    // TODO: need schur, svd, eig


    // Advanced Functions
    fun expm(): MatType
    @JsName("solve")
    fun solve(A: MatType, B: MatType): MatType

    // Basic Functions
    fun inv(): MatType
    fun det(): T
    fun pinv(): MatType
    fun normF(): T
    fun normIndP1(): T
    fun elementSum(): T
    fun diag(): MatType
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
    fun T(): MatType // In MATLAB, this appears at foo.T

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
    fun getFactory(): MatrixFactory<out MatType>


    fun repr(): String = koma.platformsupport.repr(this)

    /**
     * Transpose operator.
     */
    @JsName("TProperty")
    val T: MatType
        get() = this.transpose()


    fun toIterable() = object: Iterable<T> {
        override fun iterator(): Iterator<T> {
            class MatrixIterator(var matrix: Matrix<T, MatType>) : Iterator<T> {
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
    infix fun pow(exponent: Int): MatType {
        var out = this.copy()
        for (i in 1..exponent - 1)
            out *= this.toTyped()
        return out
    }


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
    operator fun get(rows: IntRange, cols: IntRange): MatType
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
    operator fun set(rows: IntRange, cols: IntRange, value: MatType)
    {
        val wrows = wrapRange(rows, numRows())
        val wcols = wrapRange(cols, numCols())

        for (i in wrows)
            for (j in wcols)
                this[i, j] = value[i - wrows.start, j - wcols.start]
    }
    @JsName("setRangesScalar")
    operator fun set(rows: IntRange, cols: IntRange, value: T)
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
    operator fun set(rows: Int, cols: IntRange, value: MatType)
    {
        this[rows..rows, cols] = value
    }
    @JsName("setColRangeScalar")
    operator fun set(rows: Int, cols: IntRange, value: T)
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
    operator fun set(rows: IntRange, cols: Int, value: MatType)
    {
        this[rows, cols..cols] = value
    }
    @JsName("setRowRangeScalar")
    operator fun set(rows: IntRange, cols: Int, value: T)
    {
        this[rows, cols..cols] = value
    }

    /**
     * Allows for slicing of the rows and selection of a single column
     */
    @JsName("getRowRange")
    operator fun get(rows: IntRange, cols: Int) = this[rows, cols..cols]

    /**
     * Allows for slicing of the cols and selection of a single row
     */
    @JsName("getColRange")
    operator fun get(rows: Int, cols: IntRange) = this[rows..rows, cols]

    @JsName("wrapRange__")
    private fun wrapRange(range: IntRange, max: Int) =
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
    fun <U: Number> selectCols(idxs: Matrix<U, *>) =
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
    fun <U: Number> selectRows(idxs: Matrix<U, *>) =
            getFactory()
                    .zeros(idxs.numRows()*idxs.numCols(), this.numCols())
                    .fill { row, col -> this[idxs[row].toInt(), col] }


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
    fun forEachRow(f: (Matrix<T, *>) -> Unit) {
        for (row in 0..this.numRows() - 1)
            f(this.getRow(row))
    }
    @Deprecated("Use forEachRow", ReplaceWith("forEachRow(f)"))
    fun eachRow(f: (Matrix<T, *>) -> Unit) = forEachRow(f)
    
    /**
     * Passes each col from left to right into a function.
     *
     * @param f A function that takes in a row (i.e. 1xN matrix)
     */
    @JsName("forEachCol")
    fun forEachCol(f: (Matrix<T, *>) -> Unit) {
        for (col in 0..this.numCols() - 1)
            f(this.getCol(col))
    }
    @Deprecated("Use forEachCol", ReplaceWith("forEachCol(f)"))
    fun eachCol(f: (Matrix<T, *>) -> Unit) = forEachCol(f)


    /**
     * Takes each element in a matrix, passes them through f, and puts the output of f into an
     * output matrix. This process is done in row-major order.
     *
     * @param f A function that takes in an element and returns an element
     *
     * @return the new matrix after each element is mapped through f
     */
    @JsName("map")
    fun map(f: (T) -> T): MatType {
        val out = this.getFactory().zeros(this.numRows(), this.numCols())
        for (row in 0..this.numRows() - 1)
            for (col in 0..this.numCols() - 1)
                out[row, col] = f(this[row, col])
        return out
    }
    @Deprecated("Use map instead", ReplaceWith("map(f)"))
    fun mapMat(f: (T) -> T): MatType = map(f)

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
    fun mapIndexed(f: (row: Int, col: Int, ele: T) -> T): MatType {
        val out = this.getFactory().zeros(this.numRows(), this.numCols())
        for (row in 0..this.numRows() - 1)
            for (col in 0..this.numCols() - 1)
                out[row, col] = f(row, col, this[row, col])
        return out
    }
    @Deprecated("Use mapIndexed", ReplaceWith("mapIndexed(f)"))
    fun mapMatIndexed(f: (row: Int, col: Int, ele: T) -> T): MatType = mapIndexed(f)

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
    fun mapRows(f: (Matrix<T, MatType>) -> Matrix<T, MatType>): MatType {

        val outRows = Array(this.numRows()) {
            f(this.getRow(it))
        }

        val out = this.getFactory().zeros(this.numRows(), outRows[0].numCols())

        outRows.forEachIndexed { i, matrix ->
            if (matrix.numCols() != out.numCols())
                throw RuntimeException("All output rows of mapRows must have same number of columns")
            else
                out.setRow(i, outRows[i].toTyped())
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
    fun <U> mapRowsToList(f: (Matrix<T, *>) -> U): List<U> {
        val a = ArrayList<U>(this.numRows())
        this.eachRow {
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
    fun mapCols(f: (Matrix<T, MatType>) -> Matrix<T, MatType>): MatType {

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
                out.setCol(i, outCols[i].toTyped())
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
    fun <U> mapColsToList(f: (Matrix<T, *>) -> U): List<U> {
        // TODO: Replace if cross-platform set capacity method is added to kotlin
        val a = arrayListOf<U>()
        this.eachCol {
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
    fun asRowVector() = if (this.numRows() != 1 && this.numCols() == 1) this.toTyped().T else this.toTyped()

    /**
     * Returns the given vector as a row vector. Will call transpose() on row vectors
     */
    fun asColVector() = if (this.numRows() == 1 && this.numCols() != 1) this.toTyped().T else this.toTyped()


    /**
     * Fills the matrix with the values returned by the input function.
     *
     * @param f A function which takes row,col and returns the value to fill. Note that
     * the return type must be the matrix primitive type (e.g. Double).
     */
    fun fill(f: (row: Int, col: Int) -> T): MatType {
        for (row in 0..this.numRows() - 1)
            for (col in 0..this.numCols() - 1)
                this[row, col] = f(row, col)
        return this.toTyped()
    }
    
    fun toTyped(): MatType
    fun asDType(data: Number): T

}
