/**
 * This file contains operators definitions to support overloading Kotlin operators.
 *
 * Allows for things like
 *
 * a[1,2] = 3
 * a*b
 * a[1..3,4] = 5
 * a[0..1,2..3] = mat[1,2 end 3,4]
 */

package golem

import golem.matrix.ejml.Mat

// Already has plus, minus, times for double/Mat, add a few extra
fun Mat.plus(value: Int) = this.plus(value.toDouble())
fun Mat.minus(value: Int) = this.minus(value.toDouble())
fun Mat.minus() = this.times(-1.0)
fun Mat.mod(other: Mat) = this.elementTimes(other)

// Allow index overloading (double-valued get/set already implemented in base type)
fun Mat.set(index: Int, value: Int) = this.set(index, value.toDouble())
fun Mat.set(row: Int, col: Int, value: Int) = this.set(row, col, value.toDouble())

// Allows 4*b, 4.0*b, b*4
fun Double.times(other: Mat) = other.times(this)
fun Int.times(other: Mat) = other.times(this.toDouble())
fun Mat.times(other: Int) = this*other.toDouble()

val Mat.T: Mat
    get() = this.transpose()

// Allow slicing
fun Mat.get(rows: IntRange, cols: IntRange): Mat {
    var out = zeros(rows.end-rows.start, cols.end - cols.start)
    for (row in rows-1)
        for (col in cols-1)
            out[row,col] = this[row,col]
    return out
}
fun Mat.set(rows: IntRange, cols: IntRange, value: Mat) {
    for(i in rows-1)
        for (j in cols-1)
            this[i,j] = value[i-rows.start,j-cols.start]
}
fun Mat.get(rows: IntRange, cols: Int) = this.get(rows, cols..cols)
fun Mat.get(rows: Int, cols: IntRange) = this.get(rows..rows, cols)


// Todo: ND array:
//fun Mat.get(vararg index: Long) = this.
//fun Mat.get(vararg index: Int) =this.
