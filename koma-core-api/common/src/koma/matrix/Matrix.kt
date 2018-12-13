package koma.matrix

import koma.extensions.*
import koma.internal.*
import koma.ndarray.NDArray

/**
 * A general facade for a Matrix type. Allows for various backend to be
 * implemented to actually perform the computation. A koma backend must both
 * implement this class and MatrixFactory. A matrix is guaranteed to be 2D and 
 * to have a numerical type. For storage of arbitrary types and dimensions, see
 * [koma.ndarray.NDArray].
 */
interface Matrix<T>: NDArray<T> {
    @KomaNonFrozen
    companion object {

        // TODO: Ideally these properties are expect/actual with implementations. However, there's currently
        // a generation issue with kotlin/native that breaks this approach, so as a workaround we'll define
        // getXFactory methods in koma.internal as expect actual and proxy them here. These properties have
        // to be lazily evaluated to avoid a race on startup in js, so we use private nullable fields and
        // initialize on first use

        /**
         *
         * Default factory that all top-level functions use when building new matrices.
         * Double precision.
         *
         * Replace this factory at runtime with e.g. koma.matrix.ejml.EJMLMatrixFactory() to change what
         * backend the top-level functions use for computation.
         *
         */
        var doubleFactory: MatrixFactory<Matrix<Double>>
            get() = _doubleFactory ?: getDoubleMatrixFactory().also { _doubleFactory = it }
            set(value) { _doubleFactory = value}
        private var _doubleFactory: MatrixFactory<Matrix<Double>>? = null

        /**
         *
         * Default factory that all top-level functions use when building new matrices.
         * Single precision.
         *
         * Replace this factory at runtime with another to change what
         * backend the top-level functions use for computation.
         *
         */
        var floatFactory: MatrixFactory<Matrix<Float>>
            get() = _floatFactory ?: getFloatMatrixFactory().also { _floatFactory = it }
            set(value) { _floatFactory = value}
        private var _floatFactory: MatrixFactory<Matrix<Float>>? = null

        /**
         *
         * Default factory that all top-level functions use when building new matrices.
         * Integer matrices.
         *
         * Replace this factory at runtime with another to change what
         * backend the top-level functions use for computation.
         *
         */
        var intFactory: MatrixFactory<Matrix<Int>>
            get() = _intFactory ?: getIntMatrixFactory().also { _intFactory = it }
            set(value) { _intFactory = value}
        private var _intFactory: MatrixFactory<Matrix<Int>>? = null

        @Suppress("UNCHECKED_CAST")
        inline operator fun <reified T> invoke(rows: Int, cols: Int,
                                               crossinline filler: (Int, Int) -> T): Matrix<T> =
                when(T::class) {
                    Double::class -> doubleFactory.zeros(rows, cols).fill { r, c -> filler(r, c) as Double } as Matrix<T>
                    Float::class  -> floatFactory.zeros(rows, cols).fill { r, c -> filler(r, c) as Float } as Matrix<T>
                    Int::class    -> intFactory.zeros(rows, cols).fill { r, c -> filler(r, c) as Int } as Matrix<T>
                    else          -> error("Unsupported Matrix type ${T::class}")
                }

    }
    // Algebraic Operators
    @KomaJsName("divInt")
    operator fun div(other: Int): Matrix<T>
    @KomaJsName("divScalar")
    operator fun div(other: T): Matrix<T>
    @KomaJsName("times")
    operator fun times(other: Matrix<T>): Matrix<T>
    @KomaJsName("timesScalar")
    operator fun times(other: T): Matrix<T>
    operator fun unaryMinus(): Matrix<T>
    @KomaJsName("minusScalar")
    operator fun minus(other: T): Matrix<T>
    @KomaJsName("minus")
    operator fun minus(other: Matrix<T>): Matrix<T>
    @KomaJsName("plusScalar")
    operator fun plus(other: T): Matrix<T>
    @KomaJsName("plus")
    operator fun plus(other: Matrix<T>): Matrix<T>
    /**
     * Transpose of the matrix
     */
    fun transpose(): Matrix<T>
    /**
     * Element-wise multiplication with another matrix
     */
    fun elementTimes(other: Matrix<T>): Matrix<T>
    /**
     * Element-wise exponentiation of each element in the matrix
     */
    fun epow(other: T): Matrix<T>
    infix fun epow(other: Int): Matrix<T>

    /**
     * Number of rows in the matrix
     */
    fun numRows(): Int
    /**
     * Number of columns in the matrix
     */
    fun numCols(): Int

    /**
     * Returns a copy of this matrix (same values, new memory)
     */
    override fun copy(): Matrix<T>

    // For speed optimized code (if backend isnt chosen type, may throw an exception or incur performance loss)
    @KomaJsName("getInt")
    fun getInt(i: Int, j: Int): Int
    @KomaJsName("getDouble")
    fun getDouble(i: Int, j: Int): Double
    @KomaJsName("getFloat")
    fun getFloat(i: Int, j: Int): Float
    @KomaJsName("setInt")
    fun setInt(i: Int, j: Int, v: Int)
    @KomaJsName("setDouble")
    fun setDouble(i: Int, j: Int, v: Double)
    @KomaJsName("setFloat")
    fun setFloat(i: Int, j: Int, v: Float)
    @KomaJsName("getGeneric")
    fun getGeneric(i: Int, j: Int): T
    @KomaJsName("setGeneric")
    fun setGeneric(i: Int, j: Int, v: T)

    /**
     * Retrieves the data formatted as doubles in row-major order
     * This method is only for performance over potentially boxing get(Double)
     * methods. This method may or may not return a copy, and thus should be
     * treated as read-only unless backend behavior is known.
     */
    fun getDoubleData(): DoubleArray

    @KomaJsName("getRow")
    fun getRow(row: Int): Matrix<T>
    @KomaJsName("getCol")
    fun getCol(col: Int): Matrix<T>
    @KomaJsName("setCol")
    fun setCol(index: Int, col: Matrix<T>)
    @KomaJsName("setRow")
    fun setRow(index: Int, row: Matrix<T>)

    /**
     * (lower triangular) Cholesky decomposition of the matrix. Matrix must be positive-semi definite.
     */
    fun chol(): Matrix<T>
    /**
     * LU Decomposition. Returns p, l, u matrices as a triple.
     */
    fun LU(): Triple<Matrix<T>, Matrix<T>, Matrix<T>>
    fun QR(): Pair<Matrix<T>, Matrix<T>>
    fun SVD(): Triple<Matrix<T>, Matrix<T>, Matrix<T>> // Returns U, S, V such that A = U * S * V.T
    // TODO: need schur, eig


    // Advanced Functions
    /**
     * Compute the matrix exponential e^x (NOT elementwise)
     */
    fun expm(): Matrix<T>
    @KomaJsName("solve")
    /**
     * Solves A*X=B for X, returning X (X is either column vector or a matrix composed of several col vectors).
     * A is the current matrix, B is the passed in [other], and X is the returned matrix.
     */
    fun solve(other: Matrix<T>): Matrix<T>

    // Basic Functions
    /**
      * Matrix inverse (square matrices)
      */
    fun inv(): Matrix<T>
    /**
     * Determinant of the matrix
     */
    fun det(): T
    /**
     * Pseudo-inverse of (non-square) matrix
     */
    fun pinv(): Matrix<T>
    /**
     * Frobenius normal of the matrix
     */
    fun normF(): T

    /**
     * Induced, p=1 normal of the matrix. Equivalent of `norm(matrix,1)` in scipy.
     */
    fun normIndP1(): T
    /**
     * Sum of all the elements in the matrix.
     */
    fun elementSum(): T
    fun diag(): Matrix<T>
    /**
     * Mean (average) of all the elements in the matrix.
     */
    fun mean(): T
    
    /**
     * The matrix trace.
     */
    fun trace(): T
    
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

    fun repr(): String = koma.internal.repr(this)

    /**
     * Transpose operator.
     */
    fun T() = transpose()

    /**
     * Transpose operator.
     */
    @KomaJsName("TProperty")
    val T: Matrix<T>
        get() = this.transpose()

    // Implement NDArray
    override fun getGeneric(vararg indices: Int): T
            = when(indices.size) {
        1 -> { this[indices[0]] }
        2 -> { this[indices[0], indices[1]]}
        else -> { dimMismatch(indices.size) }
    }
    override fun getDouble(vararg indices: Int): Double
            = when(indices.size) {
        1 -> { this.getDouble(indices[0]) }
        2 -> { this.getDouble(indices[0], indices[1])}
        else -> { dimMismatch(indices.size) }
    }
    override fun getFloat(vararg indices: Int): Float
            = when(indices.size) {
        1 -> { this.getFloat(indices[0]) }
        2 -> { this.getFloat(indices[0], indices[1])}
        else -> { dimMismatch(indices.size) }
    }
    override fun getInt(vararg indices: Int): Int
            = when(indices.size) {
        1 -> { this.getInt(indices[0]) }
        2 -> { this.getInt(indices[0], indices[1])}
        else -> { dimMismatch(indices.size) }
    }
    override fun getShort(vararg indices: Int): Short
            = error("Matrices do not support Short values")
    override fun getByte(vararg indices: Int): Byte
            = error("Matrices do not support Byte values")
    override fun getLong(vararg indices: Int): Long
            = error("Matrices do not support Long values")

    override fun setGeneric(vararg indices: Int, v: T)
            = when(indices.size) {
        1 -> { this.setGeneric(indices[0], v) }
        2 -> { this.setGeneric(indices[0], indices[1], v) }
        else -> { dimMismatch(indices.size) }
    }
    override fun setDouble(vararg indices: Int, v: Double)
            = when(indices.size) {
        1 -> { this.setDouble(indices[0], v) }
        2 -> { this.setDouble(indices[0], indices[1], v) }
        else -> { dimMismatch(indices.size) }
    }
    override fun setFloat(vararg indices: Int, v: Float)
            = when(indices.size) {
        1 -> { this.setFloat(indices[0], v) }
        2 -> { this.setFloat(indices[0], indices[1], v) }
        else -> { dimMismatch(indices.size) }
    }
    override fun setInt(vararg indices: Int, v: Int)
            = when(indices.size) {
        1 -> { this.setInt(indices[0], v) }
        2 -> { this.setInt(indices[0], indices[1], v) }
        else -> { dimMismatch(indices.size) }
    }
    override fun setShort(vararg indices: Int, v: Short)
            = error("Matrices do not support Short values")
    override fun setByte(vararg indices: Int, v: Byte)
            = error("Matrices do not support Byte values")
    override fun setLong(vararg indices: Int, v: Long)
            = error("Matrices do not support Long values")

    override val size get() = this.numRows() * this.numCols()
    override fun shape(): List<Int> = listOf(this.numRows(), this.numCols())
    override fun getBaseArray(): Any = this.getBaseMatrix()

    // Convenience functions

    override fun toIterable() = object: Iterable<T> {
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
    /**
     * Multiplies the matrix by itself [exponent] times (using matrix multiplication).
     */
    @KomaJsName("pow")
    infix fun pow(exponent: Int): Matrix<T> {
        var out = this.copy()
        for (i in 1..exponent - 1)
            out *= this
        return out
    }


    @KomaJsName("wrapRange__")
    fun wrapRange(range: IntRange, max: Int) =
            if(range.endInclusive >= 0)
                range
            else
                range.start..(max+range.endInclusive)

    /**
     * Passes each row from top to bottom into a function.
     *
     * @param f A function that takes in a row (i.e. 1xN matrix)
     */
    @KomaJsName("forEachRow")
    fun forEachRow(f: (Matrix<T>) -> Unit) {
        for (row in 0 until this.numRows())
            f(this.getRow(row))
    }

    /**
     * Passes each col from left to right into a function.
     *
     * @param f A function that takes in a row (i.e. 1xN matrix)
     */
    @KomaJsName("forEachCol")
    fun forEachCol(f: (Matrix<T>) -> Unit) {
        for (col in 0 until this.numCols())
            f(this.getCol(col))
    }

    /**
     * Select a set of cols from a matrix to form the cols of a new matrix.
     * For example, if you wanted a new matrix consisting of the first, second, and
     * fifth cols of an input matrix, you would write ```input.selectCols(0,1,4)```.
     */
    @KomaJsName("selectCols")
    fun selectCols(vararg idxs: Int) =
            getFactory()
                    .zeros(this.numRows(), idxs.size)
                    .fill { row, col -> this[row, idxs[col]] }

    @KomaJsName("selectColsMatrix")
    fun <U: Number> selectCols(idxs: Matrix<U>) =
            getFactory()
                    .zeros(this.numRows(), idxs.numRows()*idxs.numCols())
                    .fill { row, col -> this[row, idxs[col].toInt()] }


    /**
     * Select a set of rows from a matrix to form the rows of a new matrix.
     * For example, if you wanted a new matrix consisting of the first, second, and
     * fifth rows of an input matrix, you would write ```input.selectRows(0,1,4)```.
     */
    @KomaJsName("selectRows")
    fun selectRows(vararg idxs: Int) =
            getFactory()
                    .zeros(idxs.size, this.numCols())
                    .fill { row, col -> this[idxs[row], col] }

    @KomaJsName("selectRowsMatrix")
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


    /**
     * Takes each row in a matrix, passes them through f, and puts the output of f into a
     * row of an output matrix.
     *
     * @param f A function that takes in a 1xN row and returns a 1xM row. Note that all output
     * rows must be the same length. In addition, the input and output element types must be the same.
     *
     * @return the new matrix after each row is mapped through f
     */
    @KomaJsName("mapRows")
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
    @KomaJsName("mapRowsToList")
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
    @KomaJsName("mapCols")
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
    @KomaJsName("mapColsToList")
    fun <U> mapColsToList(f: (Matrix<T>) -> U): List<U> {
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
     * Builds a new matrix with a subset of the rows of this matrix, using only the rows
     * for which the function f returns true.
     *
     * @param f A function which takes a row index and a row, and returns true if that
     * row should be included in the output matrix.
     */
    fun filterRowsIndexed(f: (rowIndex: Int, row: Matrix<T>) -> Boolean): Matrix<T> {
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
    fun filterRows(f: (row: Matrix<T>) -> Boolean) = filterRowsIndexed { _, row -> f(row) }

    /**
     * Builds a new matrix with a subset of the columns of this matrix, using only the
     * columns for which the function f returns true.
     *
     * @param f A function which takes a column index and a column, and returns true if
     * that column should be included in the output matrix.
     */
    fun filterColsIndexed(f: (colIndex: Int, col: Matrix<T>) -> Boolean): Matrix<T> {
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
    fun filterCols(f: (col: Matrix<T>) -> Boolean) = filterColsIndexed { _, col -> f(col) }
}


private fun dimMismatch(dims: Int): Nothing = error("Dimension mismatch: Matrices have 2 dimensions ($dims requested)")
