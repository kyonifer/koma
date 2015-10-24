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

fun <T> Matrix<T>.cumSum(): Matrix<T> {
    var out = this.getFactory().zeros(1,this.numRows()*this.numCols())
    for (i in 0..(this.numRows()*this.numCols()-1))
    {
        val ele = this.getDouble(i)
        out.setDouble(i, if (i==0) ele else ele + out.getDouble(i-1))
    }
    return out
}

fun <T> Matrix<T>.fill( f:(row: Int, col: Int) -> T) {
    for (row in 0..this.numRows()-1)
        for (col in 0..this.numCols()-1)
            this[row, col] = f(row, col)
}

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

fun <T> Matrix<T>.eachIndexed( f: (row:Int, col:Int, ele:T)->Unit ) {
    for (row in 0..this.numRows()-1)
        for (col in 0..this.numCols()-1)
            f(row, col, this[row,col])
}

fun <T> Matrix<T>.mapElements( f: (T)-> T): Matrix<T> {
    var out = this.getFactory().zeros(this.numRows(), this.numCols())
    for (row in 0..this.numRows()-1)
        for (col in 0..this.numCols()-1)
            out[row,col] = f(this[row,col])
    return out
}

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

fun <T, U> Matrix<T>.mapRowsToList(f: (Matrix<T>)-> U): List<U>{
    var a = ArrayList<U>(this.numRows())
    this.eachRow {
        a.add(f(it))
    }
    return a
}

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

fun <T, U> Matrix<T>.mapColsToList(f: (Matrix<T>)-> U): List<U>{
    var a = ArrayList<U>(this.numCols())
    this.eachCol {
        a.add(f(it))
    }
    return a
}

fun <T> Matrix<T>.mapIndexed( f: (row: Int, col: Int, ele: T)-> T): Matrix<T> {
    var out = this.getFactory().zeros(this.numRows(), this.numCols())
    for (row in 0..this.numRows()-1)
        for (col in 0..this.numCols()-1)
            out[row,col] = f(row, col, this[row,col])
    return out
}

fun <T> Matrix<T>.any( f: (T)-> Boolean): Boolean {
    for (row in 0..this.numRows()-1)
        for (col in 0..this.numCols()-1)
            if (f(this[row,col]))
                return true
    return false
}

fun <T> Matrix<T>.all( f: (T)-> Boolean): Boolean {
    for (row in 0..this.numRows()-1)
        for (col in 0..this.numCols()-1)
            if (!f(this[row,col]))
                return false
    return true
}

fun Matrix<Double>.to2DArray(): Array<DoubleArray> {
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
