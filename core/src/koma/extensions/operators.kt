package koma.extensions

import koma.matrix.Matrix
import koma.matrix.MatrixFactory

// Algebraic Operators 
operator fun <T> Matrix<T>.div(other: Int): Matrix<T> = this.div(other)
operator fun <T> Matrix<T>.div(other: T): Matrix<T> = this.div(other)
operator fun <T> Matrix<T>.times(other: Matrix<T>): Matrix<T> = this.times(other)
operator fun <T> Matrix<T>.times(other: T): Matrix<T> = this.times(other)
operator fun <T> Matrix<T>.unaryMinus(): Matrix<T> = this.unaryMinus()
operator fun <T> Matrix<T>.minus(other: T): Matrix<T> = this.minus(other)
operator fun <T> Matrix<T>.minus(other: Matrix<T>): Matrix<T> = this.minus(other)
operator fun <T> Matrix<T>.plus(other: T): Matrix<T> = this.plus(other)
operator fun <T> Matrix<T>.plus(other: Matrix<T>): Matrix<T> = this.plus(other)

/**
 * Transpose of the matrix
 */
fun <T> Matrix<T>.transpose(): Matrix<T> = this.transpose()
/**
 * Element-wise multiplication with another matrix
 */
fun <T> Matrix<T>.elementTimes(other: Matrix<T>): Matrix<T> = this.elementTimes(other)
/**
 * Element-wise exponentiation of each element in the matrix
 */
infix fun <T> Matrix<T>.epow(other: Int): Matrix<T> = this.epow(other)

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