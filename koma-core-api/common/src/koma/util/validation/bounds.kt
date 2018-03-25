@file:KomaJvmName("BoundsValidation")

package koma.util.validation
import koma.validateMatrices
import koma.extensions.*
import koma.internal.KomaJvmName

/**
 * Maximum value for individual elements in the matrix.
 */
var ValidationContext.max : Double
    get() { throw UnsupportedOperationException() }
    set(value) { max(value) }

/**
 * Ensure all of the elements in the current matrix are >= the given value.
 * @param value The maximum value required of all elements in the matrix.
 */
fun ValidationContext.max(value: Double) : ValidationContext {
    if (!validateMatrices) return this
    currentMatrix.forEachIndexed { row, col, element ->
        if (element > value) {
            val msg = "${currentMatrixName}[${row}, ${col}] > ${value} (value was ${element})"
            throw IllegalArgumentException(msg)
        }
    }
    return this
}

/**
 * Minimum value for individual elements in the matrix.
 */
var ValidationContext.min : Double
    get() { throw UnsupportedOperationException() }
    set(value) { min(value) }

/**
 * Ensure all of the elements in the current matrix are >= the given value.
 * @param value The minimum value required of all elements in the matrix.
 */
fun ValidationContext.min(value: Double) : ValidationContext {
    if (!validateMatrices) return this
    currentMatrix.forEachIndexed { row, col, element ->
        if (element < value) {
            val msg = "${currentMatrixName}[${row}, ${col}] < ${value} (value was ${element})"
            throw IllegalArgumentException(msg)
        }
    }
    return this
}
