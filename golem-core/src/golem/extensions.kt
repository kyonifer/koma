/**
 * This file contains Kotlin extension functions for Matrix. Allows for things
 * like supporting the for loop protocol, so one can write "for (e in matrix)"
 * In general, these are algorithms that are back-end agnostic, and thus arent
 * included in the Matrix<T> interface.
 */

@file:JvmName("Golem")
@file:JvmMultifileClass

package golem

import golem.matrix.*
import java.util.*

/**
 * Fills the matrix with the values returned by the input function.
 *
 * @param f A function which takes row,col and returns the value to fill. Note that
 * the return type must be the matrix primitive type (e.g. Double).
 */
fun <T : Matrix<U>, U> T.fill(f: (row: Int, col: Int) -> U): T {
    for (row in 0..this.numRows() - 1)
        for (col in 0..this.numCols() - 1)
            this[row, col] = f(row, col)
    return this
}


/**
 * Returns a Matrix as a 2D array. Intended for MATLAB interop.
 *
 * @return a 2D array copy of the matrix.
 */
fun <T: Number> Matrix<T>.to2DArray(): Array<DoubleArray> {
    val out = Array(numRows(), { DoubleArray(numCols()) })
    for (row in 0..this.numRows() - 1)
        for (col in 0..this.numCols() - 1)
            out[row][col] = this.getDouble(row, col)
    return out
}

/**
 * Returns the given vector as a row vector. Will call transpose() on column vectors
 */
fun Matrix<Double>.asRowVector() = if (this.numRows() != 1 && this.numCols() == 1) this.T else this

/**
 * Returns the given vector as a row vector. Will call transpose() on row vectors
 */
fun Matrix<Double>.asColVector() = if (this.numRows() == 1 && this.numCols() != 1) this.T else this

