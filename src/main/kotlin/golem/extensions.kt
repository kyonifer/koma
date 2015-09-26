/**
 * This file contains Kotlin extension functions for Matrix. Allows for things
 * like supporting the for loop protocol, so one can write "for (e in matrix)"
 */
package golem

import golem.matrix.Matrix

fun <T> Matrix<T>.eachRow( f: (Matrix<T>)->Unit ) {
    for (row in 0..this.numRows()-1)
        f(this.getRow(row))
}
fun <T> Matrix<T>.eachCol( f: (Matrix<T>)->Unit ) {
    for (col in 0..this.numCols()-1)
        f(this.getCol(col))
}
fun <T> Matrix<T>.each( f: (T)->Unit ) {
    for (row in 0..this.numRows()-1)
        for (col in 0..this.numCols()-1)
            f(this[row,col])
}
fun <T> Matrix<T>.map( f: (T)-> T): Matrix<T> {
    var out = this.getFactory().zeros(this.numRows(), this.numCols())
    for (row in 0..this.numRows()-1)
        for (col in 0..this.numCols()-1)
            out[row,col] = f(this[row,col])
    return out
}

fun <T:Matrix<Double>,Double> Matrix<Double>.to2DArray(): Array<DoubleArray> {
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
