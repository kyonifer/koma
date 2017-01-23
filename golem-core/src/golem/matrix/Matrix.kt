package golem.matrix

import golem.*

/**
 * A general facade for a Matrix type. Allows for various backend to be
 * implemented to actually perform the computation. A golem backend must both
 * implement this class and MatrixFactory.
 */

interface Matrix<T> : Iterable<T> {
    // Algebraic Operators
    operator fun mod(other: Matrix<T>): Matrix<T>
    operator fun div(other: Int): Matrix<T>
    operator fun div(other: T): Matrix<T>
    operator fun times(other: Matrix<T>): Matrix<T>
    operator fun times(other: T): Matrix<T>
    operator fun unaryMinus(): Matrix<T>
    operator fun minus(other: T): Matrix<T>
    operator fun minus(other: Matrix<T>): Matrix<T>
    operator fun plus(other: T): Matrix<T>
    operator fun plus(other: Matrix<T>): Matrix<T>
    fun transpose(): Matrix<T>
    fun elementTimes(other: Matrix<T>): Matrix<T>
    fun epow(other: Double): Matrix<T>
    infix fun epow(other: Int): Matrix<T>

    // Dimensions
    fun numRows(): Int
    fun numCols(): Int

    /**
     * Set the ith element in the matrix. If 2D, selects elements in row-major order.
     */
    operator fun set(i: Int, v: T)
    operator fun set(i: Int, j: Int, v: T)

    /**
     * Gets the ith element in the matrix. If 2D, selects elements in row-major order.
     */
    operator fun get(i: Int, j: Int): T
    operator fun get(i: Int): T

    fun copy(): Matrix<T>

    // For speed optimized code (if backend isnt chosen type, may throw an exception or incur performance loss)
    // We can get rid of this when Java 10 generic specialization comes!
    fun getInt(i: Int, j: Int): Int
    fun getDouble(i: Int, j: Int): Double
    fun getFloat(i: Int, j: Int): Float
    fun getInt(i: Int): Int
    fun getDouble(i: Int): Double
    fun getFloat(i: Int): Float
    fun setInt(i: Int, v: Int)
    fun setDouble(i: Int, v: Double)
    fun setFloat(i: Int, v: Float)
    fun setInt(i: Int, j: Int, v: Int)
    fun setDouble(i: Int, j: Int, v: Double)
    fun setFloat(i: Int, j: Int, v: Float)

    /**
     * Retrieves the data formatted as doubles in row-major order
     * This method is only for performance over potentially boxing get(Double)
     * methods. This method may or may not return a copy, and thus should be
     * treated as read-only unless backend behavior is known.
     */
    fun getDoubleData(): DoubleArray

    fun getRow(row: Int): Matrix<T>
    fun getCol(col: Int): Matrix<T>
    fun setCol(index: Int, col: Matrix<T>)
    fun setRow(index: Int, row: Matrix<T>)

    // Decompositions (Already has eig, svd) [expm,schur not available]
    fun chol(): Matrix<T>
    fun LU(): Triple<Matrix<T>, Matrix<T>, Matrix<T>>
    fun QR(): Pair<Matrix<T>, Matrix<T>>
    // TODO: need schur, svd, eig


    // Advanced Functions
    fun expm(): Matrix<T>
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
    fun getFactory(): MatrixFactory<out Matrix<T>>


    fun repr(): String = golem.platformsupport.repr(this)

    /**
     * Transpose operator.
     */
    val T: Matrix<T>
        get() = this.transpose()


    override fun iterator(): Iterator<T> {
        class MatrixIterator<T>(var matrix: Matrix<T>) : Iterator<T> {
            private var cursor = 0
            override fun next(): T {
                cursor += 1
                return matrix[cursor - 1]
            }
            override fun hasNext() = cursor < matrix.numCols() * matrix.numRows()
        }
        return MatrixIterator(this)
    }
    infix fun pow(exponent: Int): Matrix<T> {
        var out = this.copy()
        for (i in 1..exponent - 1)
            out *= this
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
    operator fun get(rows: IntRange, cols: IntRange): Matrix<T>
    {
        val wrows = wrapRange(rows, numRows())
        val wcols = wrapRange(cols, numCols())

        var out = this.getFactory().zeros(wrows.endInclusive - wrows.start + 1,
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
    operator fun set(rows: IntRange, cols: IntRange, value: Matrix<T>)
    {
        val wrows = wrapRange(rows, numRows())
        val wcols = wrapRange(cols, numCols())

        for (i in wrows)
            for (j in wcols)
                this[i, j] = value[i - wrows.start, j - wcols.start]
    }
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
    operator fun set(rows: Int, cols: IntRange, value: Matrix<T>)
    {
        this[rows..rows, cols] = value
    }
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
    operator fun set(rows: IntRange, cols: Int, value: Matrix<T>)
    {
        this[rows, cols..cols] = value
    }
    operator fun set(rows: IntRange, cols: Int, value: T)
    {
        this[rows, cols..cols] = value
    }

    /**
     * Allows for slicing of the rows and selection of a single column
     */
    operator fun get(rows: IntRange, cols: Int) = this[rows, cols..cols]

    /**
     * Allows for slicing of the cols and selection of a single row
     */
    operator fun get(rows: Int, cols: IntRange) = this[rows..rows, cols]

    private fun wrapRange(range: IntRange, max: Int) =
            if(range.endInclusive >= 0)
                range
            else
                range.start..(max+range.endInclusive)
}
