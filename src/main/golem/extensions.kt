/**
 * This file contains Kotlin extension functions for Matrix. Allows for things
 * like supporting the for loop protocol, so one can write "for (e in matrix)"
 * In general, these are algorithms that are back-end agnostic, and thus arent
 * included in the Matrix<T> interface.
 */

@file:JvmName("Golem")
@file:JvmMultifileClass

package golem

import golem.matrix.Matrix
import java.util.*

/**
 * Calculates the cumulative (ongoing) sum of a matrix's elements. For example,
 * cumsum(mat[1,2,3]) would return mat[1,3,6]. Assumes matrix type is convertible to
 * double.
 *
 * @return A 1xarr.numRows*arr.numCols vector storing the ongoing cumsum.
 *
 */
fun <T> Matrix<T>.cumSum(): Matrix<T> {
    var out = this.getFactory().zeros(1,this.numRows()*this.numCols())
    for (i in 0..(this.numRows()*this.numCols()-1))
    {
        val ele = this.getDouble(i)
        out.setDouble(i, if (i==0) ele else ele + out.getDouble(i-1))
    }
    return out
}

/**
 * Fills the matrix with the values returned by the input function.
 *
 * @param f A function which takes row,col and returns the value to fill. Note that
 * the return type must be the matrix primitive type (e.g. Double).
 */
fun <T:Matrix<U>,U> T.fill( f:(row: Int, col: Int) -> U): T {
    for (row in 0..this.numRows()-1)
        for (col in 0..this.numCols()-1)
            this[row, col] = f(row, col)
    return this
}

/**
 * Passes each element in row major order into a function.
 *
 * @param f A function that takes in an element
 */
fun <T> Matrix<T>.each( f: (T)->Unit ) {
    for (row in 0..this.numRows()-1)
        for (col in 0..this.numCols()-1)
            f(this[row,col])
}
/**
 * Passes each element in row major order into a function along with its index location.
 *
 * @param f A function that takes in a row,col position and an element value
 */
fun <T> Matrix<T>.eachIndexed( f: (row:Int, col:Int, ele:T)->Unit ) {
    for (row in 0..this.numRows()-1)
        for (col in 0..this.numCols()-1)
            f(row, col, this[row,col])
}

/**
 * Passes each row from top to bottom into a function.
 *
 * @param f A function that takes in a row (i.e. 1xN matrix)
 */
fun <T> Matrix<T>.eachRow( f: (Matrix<T>)->Unit ) {
    for (row in 0..this.numRows()-1)
        f(this.getRow(row))
}
/**
 * Passes each col from left to right into a function.
 *
 * @param f A function that takes in a row (i.e. 1xN matrix)
 */
fun <T> Matrix<T>.eachCol( f: (Matrix<T>)->Unit ) {
    for (col in 0..this.numCols()-1)
        f(this.getCol(col))
}
/**
 * Takes each element in a matrix, passes them through f, and puts the output of f into an
 * output matrix. This process is done in row-major order.
 *
 * @param f A function that takes in an element and returns an element
 *
 * @return the new matrix after each element is mapped through f
 */
fun <T> Matrix<T>.mapMat( f: (T)-> T): Matrix<T> {
    var out = this.getFactory().zeros(this.numRows(), this.numCols())
    for (row in 0..this.numRows()-1)
        for (col in 0..this.numCols()-1)
            out[row,col] = f(this[row,col])
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
fun <T> Matrix<T>.mapMatIndexed( f: (row: Int, col: Int, ele: T)-> T): Matrix<T> {
    var out = this.getFactory().zeros(this.numRows(), this.numCols())
    for (row in 0..this.numRows()-1)
        for (col in 0..this.numCols()-1)
            out[row,col] = f(row, col, this[row,col])
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
fun <T> Matrix<T>.mapRows(f: (Matrix<T>)-> Matrix<T>): Matrix<T>{

    var outRows = Array(this.numRows()) {
        f(this.getRow(it))
    }

    var out = this.getFactory().zeros(this.numRows(), outRows[0].numCols())

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
fun <T, U> Matrix<T>.mapRowsToList(f: (Matrix<T>)-> U): List<U>{
    var a = ArrayList<U>(this.numRows())
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
fun <T> Matrix<T>.mapCols(f: (Matrix<T>)-> Matrix<T>): Matrix<T>{

    var outCols = Array(this.numCols()) {
        var out = f(this.getCol(it))
        // If user creates a row vector auto convert to column for them
        if (out.numRows()==1) out.T else out
    }

    var out = this.getFactory().zeros(outCols[0].numRows(), this.numCols())

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

fun <T, U> Matrix<T>.mapColsToList(f: (Matrix<T>)-> U): List<U>{
    var a = ArrayList<U>(this.numCols())
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
fun <T> Matrix<T>.any( f: (T)-> Boolean): Boolean {
    for (row in 0..this.numRows()-1)
        for (col in 0..this.numCols()-1)
            if (f(this[row,col]))
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
fun <T> Matrix<T>.all( f: (T)-> Boolean): Boolean {
    for (row in 0..this.numRows()-1)
        for (col in 0..this.numCols()-1)
            if (!f(this[row,col]))
                return false
    return true
}

/**
 * Returns a Matrix as a 2D array. Intended for MATLAB interop.
 *
 * @return a 2D array copy of the matrix.
 */
fun Matrix<Double>.to2DArray(): Array<DoubleArray> {
    var out = Array(numRows(),{DoubleArray(numCols())})
    for (row in 0..this.numRows()-1)
        for (col in 0..this.numCols()-1)
            out[row][col] = this.getDouble(row,col)
    return out
}

fun Matrix<Double>.asRowVector() = if (this.numRows()!=1 && this.numCols()==1) this.T else this
fun Matrix<Double>.asColVector() = if (this.numRows()==1 && this.numCols()!=1) this.T else this

// TODO:
//fun Matrix.extend(other: Matrix, dimension: Int = 0) = concat(dimension, this, other)
//fun Matrix.extend(other: Matrix, dimension: Int = 0) = concat(dimension, this, other)
