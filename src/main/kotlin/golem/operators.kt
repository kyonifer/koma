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

import org.ujmp.core.Matrix
import org.ujmp.core.calculation.Calculation

// Already has plus, minus, times for double/Matrix, add a few extra
fun Matrix.plus(value: Int) = this.plus(value.toDouble())
fun Matrix.minus(value: Int) = this.minus(value.toDouble())
fun Matrix.minus() = this.times(-1.0)
fun Matrix.mod(other: Matrix) = this.mtimes(other)

// Allow index overloading
fun Matrix.get(vararg index: Long) = this.getAsDouble(*index)
fun Matrix.get(vararg index: Int) =this.getAsDouble(*index.map { it.toLong() }.toLongArray())

// Can't vararg this in Kotlin
fun Matrix.set(index: Long, value: Double) = this.setAsDouble(value, index)
fun Matrix.set(index: Int, value: Double) = this.setAsDouble(value, index.toLong())
fun Matrix.set(index: Long, value: Int) = this.setAsDouble(value.toDouble(), index)
fun Matrix.set(index: Int, value: Int) = this.setAsDouble(value.toDouble(), index.toLong())

fun Matrix.set(row: Long, col: Long, value: Double) = this.setAsDouble(value, row, col)
fun Matrix.set(row: Int, col: Int, value: Double) = this.setAsDouble(value, row.toLong(), col.toLong())
fun Matrix.set(row: Long, col: Long, value: Int) = this.setAsDouble(value.toDouble(), row, col)
fun Matrix.set(row: Int, col: Int, value: Int) = this.setAsDouble(value.toDouble(), row.toLong(), col.toLong())

val Matrix.T: Matrix
    get() = this.transpose()

// Allow slicing
fun Matrix.get(rows: IntRange, cols: IntRange): Matrix {
    return this.subMatrix(Calculation.Ret.NEW,
            rows.start.toLong(),
            cols.start.toLong(),
            rows.end.toLong(),
            cols.end.toLong())
}
fun Matrix.set(rows: IntRange, cols: IntRange, value: Matrix) {
    for(i in rows)
        for (j in cols)
            this[i,j] = value[i-rows.start,j-cols.start]
}
fun Matrix.get(rows: IntRange, cols: Int) = this.get(rows, cols..cols)
fun Matrix.get(rows: Int, cols: IntRange) = this.get(rows..rows, cols)