/**
 * This file contains Kotlin extension functions for Matrix. Allows for things
 * like supporting the for loop protocol, so one can write "for (e in matrix)"
 */
package golem

import golem.matrix.Matrix

fun <T:Matrix<T,U>,U> Matrix<T,U>.eachRow( f: (T)->Unit ) {
    for (row in 0..this.numRows()-1)
        f(this.getRow(row))
}
fun <T:Matrix<T,U>,U> Matrix<T,U>.eachCol( f: (T)->Unit ) {
    for (col in 0..this.numCols()-1)
        f(this.getCol(col))
}
fun <T:Matrix<T,U>,U> Matrix<T,U>.each( f: (U)->Unit ) {
    for (row in 0..this.numRows()-1)
        for (col in 0..this.numCols()-1)
            f(this[row,col])
}
fun <T:Matrix<T,U>,U> Matrix<T,U>.map( f: (U)-> U) {
    for (row in 0..this.numRows()-1)
        for (col in 0..this.numCols()-1)
            this[row,col] = f(this[row,col])
}

fun <T:Matrix<T,Double>,Double> Matrix<T,Double>.to2DArray(): Array<DoubleArray> {
    var out = Array(numRows(),{DoubleArray(numCols())})
    for (row in 0..this.numRows()-1)
        for (col in 0..this.numCols()-1)
            out[row][col] = this.getDouble(row,col)
    return out
}

// TODO:
//fun Matrix.iterator() = Iterator(this)

//fun Matrix.extend(other: Matrix, dimension: Int = 0) = concat(dimension, this, other)
//fun Matrix.extend(other: Matrix, dimension: Int = 0) = concat(dimension, this, other)
