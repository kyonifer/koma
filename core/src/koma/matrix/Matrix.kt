package koma.matrix

import koma.polyfill.annotations.*
import koma.extensions.*

/**
 * A general facade for a Matrix type. Allows for various backend to be
 * implemented to actually perform the computation. A koma backend must both
 * implement this class and MatrixFactory. A matrix is guaranteed to be 2D and 
 * to have a numerical type. For storage of arbitrary types and dimensions, see
 * [koma.ndarray.NDArray].
 */
abstract class Matrix<T> {

    /**
     * Whats going on in this class:
     *
     * Nearly everything in `Matrix<T>` is defined as `protected`. The reason is that
     * we instead expose extension functions (e.g. at koma.extensions.*) for all the
     * functionality inside this class. Those extension functions  in turn call
     * into the protected declarations here. There are several advantages to this
     * approach:
     *
     * 1) We can eliminate a lot of primitive boxing. For example, the `matrix[i]` extension
     *    defined on Matrix<Double> knows how to use `getDouble` directly. If we tried to
     *    define `fun get(i:Int):T` on `Matrix<T>`, we'd get boxed values back.
     *    Another workaround is defining `get(i: java.lang.Integer):Double`, but this becomes
     *    a huge mess quickly.
     *
     * 2) It allows us to have override contexts. For example, suppose we
     *    wanted to support:
     *
     *    linalg {
     *      b * c // matrix multiplication
     *    }
     *    elementwise {
     *      b * c // This is now elementwise multiplication
     *    }
     *    inplace(a) {
     *      b * c // No temporaries are allocated, result stored in a
     *    }
     *
     *    We can do so if `times` is defined as an extension function,
     *    by using an extension receiver in the lambda that overrides the `times`
     *    extension function. If `times` were defined as public on `Matrix<T>` then
     *    the overridden behavior would be ignored in favor of the public one.
     *    There's many use cases for override contexts, such as an equivalent
     *    for Python's `numexpr`.
     *
     * One disadvantage of this approach is that extension functions aren't very
     * friendly to other JVM languages. The `internal` methods below expose
     * ordinary non-extension methods to the JVM, which are hidden from kotlin.
     * To do this, we (ab)use `internal` + @JvmName to force a JVM method to
     * appear with the name we want while not allowing access to it from kotlinc.
     * This has the side-effect of needing us to use `_` prefixes for the protected
     * methods, since @JvmName is restricted for open methods.
     */

    protected abstract fun _div(other: Int): Matrix<T>
    protected abstract fun _div(other: T): Matrix<T>
    protected abstract fun _times(other: Matrix<T>): Matrix<T>
    protected abstract fun _times(other: T): Matrix<T>
    protected abstract fun _unaryMinus(): Matrix<T>
    protected abstract fun _minus(other: T): Matrix<T>
    protected abstract fun _minus(other: Matrix<T>): Matrix<T>
    protected abstract fun _plus(other: T): Matrix<T>
    protected abstract fun _plus(other: Matrix<T>): Matrix<T>

    /**
     * Transpose of the matrix
     */

    protected abstract fun _transpose(): Matrix<T>
    /**
     * Element-wise multiplication with another matrix
     */
    protected abstract fun _elementTimes(other: Matrix<T>): Matrix<T>
    /**
     * Element-wise exponentiation of each element in the matrix
     */
    protected abstract fun _epow(other: Int): Matrix<T>
    protected abstract fun _epow(other: T): Matrix<T>

    /**
     * Number of rows in the matrix
     */
    protected abstract fun _numRows(): Int
    /**
     * Number of columns in the matrix
     */

    protected abstract fun _numCols(): Int

    /**
     * Returns a copy of this matrix (same values, new memory)
     */

    protected abstract fun _copy(): Matrix<T>

    // For speed optimized code (if backend isnt chosen type, may throw an exception or incur performance loss)
    protected abstract fun _getInt(i: Int, j: Int): Int
    protected abstract fun _getDouble(i: Int, j: Int): Double
    protected abstract fun _getFloat(i: Int, j: Int): Float
    protected abstract fun _getInt(i: Int): Int
    protected abstract fun _getDouble(i: Int): Double
    protected abstract fun _getFloat(i: Int): Float
    protected abstract fun _setInt(i: Int, v: Int)
    protected abstract fun _setDouble(i: Int, v: Double)
    protected abstract fun _setFloat(i: Int, v: Float)
    protected abstract fun _setInt(i: Int, j: Int, v: Int)
    protected abstract fun _setDouble(i: Int, j: Int, v: Double)
    protected abstract fun _setFloat(i: Int, j: Int, v: Float)
    protected abstract fun _getGeneric(i: Int, j: Int): T
    protected abstract fun _getGeneric(i: Int): T
    protected abstract fun _setGeneric(i: Int, j: Int, v: T)
    protected abstract fun _setGeneric(i: Int, v: T)

    /**
     * Retrieves the data formatted as doubles in row-major order
     * This method is only for performance over potentially boxing get(Double)
     * methods. This method may or may not return a copy, and thus should be
     * treated as read-only unless backend behavior is known.
     */
    protected abstract fun _getDoubleData(): DoubleArray

    protected abstract fun _getRow(row: Int): Matrix<T>
    protected abstract fun _getCol(col: Int): Matrix<T>
    protected abstract fun _setCol(index: Int, col: Matrix<T>)
    protected abstract fun _setRow(index: Int, row: Matrix<T>)

    /**
     * (lower triangular) Cholesky decomposition of the matrix. Matrix must be positive-semi definite.
     */
    protected abstract fun _chol(): Matrix<T>
    /**
     * LU Decomposition. Returns p, l, u matrices as a triple.
     */
    protected abstract fun _LU(): Triple<Matrix<T>, Matrix<T>, Matrix<T>>
    protected abstract fun _QR(): Pair<Matrix<T>, Matrix<T>>
    protected abstract fun _SVD(): Triple<Matrix<T>, Matrix<T>, Matrix<T>> // Returns U, S, V such that A = U * S * V.T

    // TODO: need schur, eig


    // Advanced Functions
    /**
     * Compute the matrix exponential e^x (NOT elementwise)
     */
    protected abstract fun _expm(): Matrix<T>

    /**
     * Solves A*X=B for X, returning X (X is either column vector or a matrix composed of several col vectors).
     * A is the current matrix, B is the passed in [other], and X is the returned matrix.
     */
    protected abstract fun _solve(other: Matrix<T>): Matrix<T>

    // Basic Functions
    /**
      * Matrix inverse (square matrices)
      */
    protected abstract fun _inv(): Matrix<T>
    /**
     * Determinant of the matrix
     */
    protected abstract fun _det(): T
    /**
     * Pseudo-inverse of (non-square) matrix
     */
    protected abstract fun _pinv(): Matrix<T>
    /**
     * Frobenius normal of the matrix
     */
    protected abstract fun _normF(): T

    /**
     * Induced, p=1 normal of the matrix. Equivalent of `norm(matrix,1)` in scipy.
     */
    protected abstract fun _normIndP1(): T
    /**
     * Sum of all the elements in the matrix.
     */
    protected abstract fun _elementSum(): T
    protected abstract fun _diag(): Matrix<T>
    /**
     * Maximum value contained in the matrix
     */
    protected abstract fun _max(): T // add dimension: Int?
    /**
     * Mean (average) of all the elements in the matrix.
     */
    protected abstract fun _mean(): T
    /**
     * Minimum value contained in the matrix
     */
    protected abstract fun _min(): T

    /**
     * Row major 1D index.
     */
    protected abstract fun _argMax(): Int

    /**
     * Row major 1D index.
     */
    protected abstract fun _argMin(): Int

    /**
     * The matrix trace.
     */
    protected abstract fun _trace(): T

    /**
     * Returns the underlying matrix object from the back-end this Matrix is wrapping. This should be used
     * sparingly (as it breaks encapsulation), but it can increase performance by using computation specifically
     * designed for a particular back-end. Code using this method should not rely on a particular back-end, and
     * should always fallback to slow generic code if an unrecognized matrix is returned here (e.g. use [get] and [set])
     * to access the elements generically).
     */
    protected abstract fun _getBaseMatrix(): Any


    /**
     *  Because sometimes all you have is a Matrix, but you really want a MatrixFactory.
     */
    protected abstract fun _getFactory(): MatrixFactory<Matrix<T>>


    @JsName("divInt")
    @JvmName("div")
    internal fun div(other: Int): Matrix<T> = _div(other)
    @JsName("divScalar")
    @JvmName("div")
    internal fun div(other: T): Matrix<T> = _div(other)
    @JsName("times")
    @JvmName("times")
    internal fun times(other: Matrix<T>): Matrix<T> = _times(other)
    @JsName("timesScalar")
    @JvmName("times")
    internal fun times(other: T): Matrix<T> = _times(other)
    @JvmName("unaryMinus")
    internal fun unaryMinus(): Matrix<T> = _unaryMinus()
    @JsName("minusScalar")
    @JvmName("minus")
    internal fun minus(other: T): Matrix<T> = _minus(other)
    @JsName("minus")
    @JvmName("minus")
    internal fun minus(other: Matrix<T>): Matrix<T> = _minus(other)
    @JsName("plusScalar")
    @JvmName("plus")
    internal fun plus(other: T): Matrix<T> = _plus(other)
    @JsName("plus")
    @JvmName("plus")
    internal fun plus(other: Matrix<T>): Matrix<T> = _plus(other)
    @JvmName("transpose")
    internal fun transpose(): Matrix<T> = _transpose()
    @JvmName("elementTimes")
    internal fun elementTimes(other: Matrix<T>): Matrix<T> = _elementTimes(other)
    @JvmName("epow")
    internal fun epow(other: T): Matrix<T> = _epow(other)
    @JvmName("epow")
    internal fun epow(other: Int): Matrix<T> = _epow(other)
    @JvmName("numRows")
    internal fun numRows(): Int = _numRows()
    @JvmName("numCols")
    internal fun numCols(): Int = _numCols()
    internal fun copy(): Matrix<T> = _copy()


    @JsName("getInt")
    @JvmName("getInt")
    internal fun getInt(i: Int, j: Int): Int = _getInt(i, j)
    @JsName("getDouble")
    @JvmName("getDouble")
    internal fun getDouble(i: Int, j: Int): Double = _getDouble(i ,j)
    @JsName("getFloat")
    @JvmName("getFloat")
    internal fun getFloat(i: Int, j: Int): Float = _getFloat(i, j)
    @JsName("getInt1D")
    @JvmName("getInt")
    internal fun getInt(i: Int): Int = _getInt(i)
    @JsName("getDouble1D")
    @JvmName("getDouble")
    internal fun getDouble(i: Int): Double = _getDouble(i)
    @JsName("getFloat1D")
    @JvmName("getFloat")
    internal fun getFloat(i: Int): Float = _getFloat(i)
    @JsName("setInt1D")
    @JvmName("setInt")
    internal fun setInt(i: Int, v: Int) = _setInt(i, v)
    @JsName("setDouble1D")
    @JvmName("setDouble")
    internal fun setDouble(i: Int, v: Double) = _setDouble(i, v)
    @JsName("setFloat1D")
    @JvmName("setFloat")
    internal fun setFloat(i: Int, v: Float) = _setFloat(i, v)
    @JsName("setInt")
    @JvmName("setInt")
    internal fun setInt(i: Int, j: Int, v: Int) = _setInt(i, j, v)
    @JsName("setDouble")
    @JvmName("setDouble")
    internal fun setDouble(i: Int, j: Int, v: Double) = _setDouble(i, j, v)
    @JsName("setFloat")
    @JvmName("setFloat")
    internal fun setFloat(i: Int, j: Int, v: Float) = _setFloat(i, j, v)
    @JsName("getGeneric")
    @JvmName("getGeneric")
    internal fun getGeneric(i: Int, j: Int): T = _getGeneric(i, j)
    @JsName("getGeneric1D")
    @JvmName("getGeneric")
    internal fun getGeneric(i: Int): T = _getGeneric(i)
    @JsName("setGeneric")
    @JvmName("setGeneric")
    internal fun setGeneric(i: Int, j: Int, v: T) = _setGeneric(i, j, v)
    @JsName("setGeneric1D")
    @JvmName("setGeneric")
    internal fun setGeneric(i: Int, v: T) = _setGeneric(i, v)


    internal fun getDoubleData(): DoubleArray = _getDoubleData()

    @JsName("getRow")
    @JvmName("getRow")
    internal fun getRow(row: Int): Matrix<T> = _getRow(row)
    @JsName("getCol")
    @JvmName("getCol")
    internal fun getCol(col: Int): Matrix<T> = _getCol(col)
    @JsName("setCol")
    @JvmName("setCol")
    internal fun setCol(index: Int, col: Matrix<T>) = _setCol(index, col)
    @JsName("setRow")
    @JvmName("setRow")
    internal fun setRow(index: Int, row: Matrix<T>) = _setRow(index, row)


    @JvmName("chol")
    internal fun chol(): Matrix<T> = _chol()
    @JvmName("LU")
    internal fun LU(): Triple<Matrix<T>, Matrix<T>, Matrix<T>> = _LU()
    @JvmName("QR")
    internal fun QR(): Pair<Matrix<T>, Matrix<T>> = _QR()
    @JvmName("SVD")
    internal fun SVD(): Triple<Matrix<T>, Matrix<T>, Matrix<T>> = _SVD()

    internal fun expm(): Matrix<T> = _expm()
    @JsName("solve")
    internal fun solve(other: Matrix<T>): Matrix<T> = _solve(other)
    internal fun inv(): Matrix<T> = _inv()
    internal fun det(): T = det()
    internal fun pinv(): Matrix<T> = _pinv()
    internal fun normF(): T = _normF()
    internal fun normIndP1(): T = _normIndP1()
    internal fun elementSum(): T = _elementSum()
    internal fun diag(): Matrix<T> = _diag()
    internal fun max(): T = _max()
    internal fun mean(): T = _mean()
    internal fun min(): T = _min()
    internal fun argMax(): Int = _argMax()
    internal fun argMin(): Int = _argMin()
    internal fun trace(): T = trace()
    internal fun getBaseMatrix(): Any = _getBaseMatrix()
    internal fun getFactory(): MatrixFactory<Matrix<T>> = _getFactory()

}

