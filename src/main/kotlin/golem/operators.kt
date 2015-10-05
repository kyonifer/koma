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

import golem.matrix.Matrix

// Already has plus, minus, times for double/Mat, add a few extra
operator fun Matrix<Double>.plus(value: Int) = this.plus(value.toDouble())
operator fun Matrix<Double>.minus(value: Int) = this.minus(value.toDouble())
operator fun Matrix<Double>.minus() = this.times(-1.0)
operator fun Matrix<Double>.mod(other: Matrix<Double>) = this.elementTimes(other)

// Allow index overloading (double-valued get/set already implemented in base type)
operator fun Matrix<Double>.set(index: Int, value: Int) = this.set(index, value.toDouble())
operator fun Matrix<Double>.set(row: Int, col: Int, value: Int) = this.set(row, col, value.toDouble())

// Allows 4*b, 4.0*b, b*4
operator fun Double.times(other: Matrix<Double>) = other.times(this)
operator fun Int.times(other: Matrix<Double>) = other.times(this.toDouble())
operator fun Matrix<Double>.times(other: Int) = this*other.toDouble()

val Matrix<Double>.T: Matrix<Double>
    get() = this.transpose()

// Allow slicing
operator fun Matrix<Double>.get(rows: IntRange, cols: IntRange): Matrix<Double> {
    var out = zeros(rows.end-rows.start, cols.end - cols.start)
    for (row in rows-1)
        for (col in cols-1)
            out[row,col] = this[row,col]
    return out
}
operator fun Matrix<Double>.set(rows: IntRange, cols: IntRange, value: Matrix<Double>) {
    for(i in rows-1)
        for (j in cols-1)
            this[i,j] = value[i-rows.start,j-cols.start]
}
operator fun Matrix<Double>.get(rows: IntRange, cols: Int) = this.get(rows, cols..cols)
operator fun Matrix<Double>.get(rows: Int, cols: IntRange) = this.get(rows..rows, cols)


// Todo: ND array:
//fun Mat.get(vararg index: Long) = this.
//fun Mat.get(vararg index: Int) =this.
