@file:JvmName("SymmetryValidation")

package koma.util.validation

import koma.*
import koma.extensions.*
import koma.polyfill.annotations.*

fun ValidationContext.symmetric(precision: Double = 1e-5): ValidationContext {
    if (!validateMatrices) return this
    val rows = currentMatrix.numRows()
    val cols = currentMatrix.numCols()
    if (rows != cols) {
        val msg = "${currentMatrixName} must be symmetric, but has dimensions ${rows}x${cols}"
        throw IndexOutOfBoundsException(msg)
    }
    for (row in 0.until(rows))
        for (col in row.until(cols))
            if (abs(currentMatrix[row, col] - currentMatrix[col, row]) > precision) {
                val msg = "${currentMatrixName} must be symmetric, but " +
                          "${currentMatrixName}[${row}, ${col}] != ${currentMatrixName}[${col}, ${row}]."
                throw IllegalArgumentException(msg)
            }
    return this
}

/**
 * Require the current matrix to be symmetric.
 */
val ValidationContext.symmetric: ValidationContext get() = symmetric()
