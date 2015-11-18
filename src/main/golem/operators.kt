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

@file:JvmName("Golem")
@file:JvmMultifileClass

package golem

import golem.matrix.Matrix

// Already has plus, minus, times for double/Mat, add a few extra
/**
 * Allow operator overloading with non-Double scalars
 */
operator fun Matrix<Double>.plus(value: Int) = this.plus(value.toDouble())
/**
 * Allow operator overloading with non-Double scalars
 */
operator fun Matrix<Double>.minus(value: Int) = this.minus(value.toDouble())
/**
 * Allow operator overloading with non-Double scalars
 */
operator fun Matrix<Double>.minus() = this.times(-1.0)
/**
 * Allow operator overloading via % of element-wise multiplication.
 */
operator fun Matrix<Double>.mod(other: Matrix<Double>) = this.elementTimes(other)
/**
 * Allow infix operator "a emul b" to be element-wise multiplication of two matrices.
 */
infix fun Matrix<Double>.emul(other:Matrix<Double>) = this.elementTimes(other)

/**
 * Allow set-index overloading for integers. See [golem.matrix.Matrix<T>.set] for doubles.
 */
operator fun Matrix<Double>.set(index: Int, value: Int) = this.set(index, value.toDouble())
/**
 * Allow set-index overloading for integers. See [golem.matrix.Matrix<T>.set] for doubles.
 */
operator fun Matrix<Double>.set(row: Int, col: Int, value: Int) = this.set(row, col, value.toDouble())


/**
 * Add a scalar to a matrix
 */
operator fun Double.plus(other: Matrix<Double>) = other.plus(this)
/**
 * Add a scalar to a matrix
 */
operator fun Int.plus(other: Matrix<Double>) = other.plus(this)
/**
 * Subtract a matrix from a scala
 */
operator fun Double.minus(other: Matrix<Double>) = other.minus(this)
/**
 * Subtract a matrix from a scala
 */
operator fun Int.minus(other: Matrix<Double>) = other.minus(this)
/**
 * Multiply a scalar by a matrix
 */
operator fun Double.times(other: Matrix<Double>) = other.times(this)
/**
 * Multiply a scalar by a matrix
 */
operator fun Int.times(other: Matrix<Double>) = other.times(this.toDouble())
/**
 * Multiply a scalar by a matrix
 */
operator fun Matrix<Double>.times(other: Int) = this*other.toDouble()

/**
 * Calculates the transpose of the matrix.
 */
val Matrix<Double>.T: Matrix<Double>
    // TODO: Make this copy on write.
    get() = this.transpose()

/**
 * Allow slicing, e.g. ```matrix[1..2, 3..4]```. Note that the range 1..2 is inclusive, so
 * it will retrieve row 1 and 2. Use 1.until(2) for a non-inclusive range.
 *
 * @param rows the set of rows to select
 * @param cols the set of columns to select
 *
 * @return a new matrix containing the submatrix.
 */
operator fun Matrix<Double>.get(rows: IntRange, cols: IntRange): Matrix<Double> {
    var out = zeros(rows.endInclusive-rows.start+1, cols.endInclusive - cols.start+1)
    for (row in rows)
        for (col in cols)
            out[row-rows.start,col-cols.start] = this[row,col]
    return out
}
/**
 * Allow assignment to a slice, e.g. ```matrix[1..2, 3..4]```=something. Note that the range 1..2 is inclusive, so
 * it will retrieve row 1 and 2. Use 1.until(2) for a non-inclusive range.
 *
 * @param rows the set of rows to select
 * @param cols the set of columns to select
 * @param value the matrix to set the subslice to
 *
 */
operator fun Matrix<Double>.set(rows: IntRange, cols: IntRange, value: Matrix<Double>) {
    for(i in rows-1)
        for (j in cols-1)
            this[i,j] = value[i-rows.start,j-cols.start]
}

/**
 * Allows for slicing of the rows and selection of a single column
 */
operator fun Matrix<Double>.get(rows: IntRange, cols: Int) = this[rows, cols..cols]
/**
 * Allows for slicing of the cols and selection of a single row
 */
operator fun Matrix<Double>.get(rows: Int, cols: IntRange) = this[rows..rows, cols]


// Todo: ND array:
//fun Mat.get(vararg index: Long) = this.
//fun Mat.get(vararg index: Int) =this.
