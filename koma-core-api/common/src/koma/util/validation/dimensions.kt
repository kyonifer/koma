@file:KomaJvmName("DimensionValidation")

package koma.util.validation
import koma.*
import koma.matrix.*
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set
import koma.internal.KomaJvmName

private val ValidationContext.meta : DimensionValidator get() {
    return this.metadata("dim") {
        val instance = DimensionValidator()
        addValidator(instance)
        instance
    }
}

private class DimensionValidator: Validator {
    // For named variables, the possible values they might take based on scanned matrix dimensions.
    private val possibleValues = mutableMapOf<Char, MutableSet<Int>>()
    // Possible valid dimensions for a given matrix
    private val dimensions = mutableMapOf<Matrix<Double>, Pair<MutableSet<Int>, MutableSet<Int>>>()
    // The declared dimensions for the matrix with the given name. Strings in the pair will either be
    // numeric or a single character.
    private val declared = mutableMapOf<String, Pair<String, String>>()
    // Matrix names we've already complained about while building our error string.
    private val seenInError = mutableSetOf<String>()
    // Matrices that have been declared transposable.
    private val transposable = mutableSetOf<Matrix<Double>>()

    private fun inferValues(letter: Char, vararg dimensions: Int) : MutableSet<Int> {
        val possible = possibleValues.getOrPut(letter) { dimensions.toMutableSet() }
        possible.retainAll(dimensions.asIterable())
        return possible
    }

    fun dim(name: String, matrix: Matrix<Double>, rows: Int, cols: Int) {
        declared[name] = Pair("$rows", "$cols")
        dimensions[matrix] = Pair(mutableSetOf(rows), mutableSetOf(cols))
    }

    fun dim(name: String, matrix: Matrix<Double>, rows: Int, cols: Char) {
        declared[name] = Pair("$rows", "$cols")
        val inferenceSource = if (matrix.numCols() == rows) {
            matrix.numRows()
        } else {
            matrix.numCols()
        }
        dimensions[matrix] = Pair(mutableSetOf(rows), inferValues(cols, inferenceSource))
    }

    fun dim(name: String, matrix: Matrix<Double>, rows: Char, cols: Int) {
        declared[name] = Pair("$rows", "$cols")
        val inferenceSource = if (matrix.numRows() == cols) {
            matrix.numCols()
        } else {
            matrix.numRows()
        }
        dimensions[matrix] = Pair(inferValues(rows, inferenceSource), mutableSetOf(cols))
    }

    fun dim(name: String, matrix: Matrix<Double>, rows: Char, cols: Char) {
        declared[name] = Pair("$rows", "$cols")
        dimensions[matrix] = Pair(inferValues(rows, matrix.numRows(), matrix.numCols()),
                                  inferValues(cols, matrix.numRows(), matrix.numCols()))
    }

    fun markTransposable(matrix: Matrix<Double>) {
        transposable.add(matrix)
    }

    override fun performValidation(context: ValidationContext) {
        val failed = mutableListOf<Pair<String, Matrix<Double>>>()
        for ((index, matrix) in context.matrices.withIndex()) {
            dimensions[matrix]?.let { matrixDimension ->
                val (expectedRows, expectedCols) = matrixDimension
                val actualRows = matrix.numRows()
                val actualCols = matrix.numCols()
                if (actualRows in expectedRows && actualCols in expectedCols) {
                    expectedRows.retainAll(arrayOf(actualRows))
                    expectedCols.retainAll(arrayOf(actualCols))
                } else if (actualRows in expectedCols
                        && actualCols in expectedRows
                        && matrix in transposable) {
                    expectedCols.retainAll(arrayOf(actualRows))
                    expectedRows.retainAll(arrayOf(actualCols))
                } else {
                    failed.add(Pair(context.matrixNames[index], matrix))
                }
            }
        }
        if (failed.size > 0) {
            throw IndexOutOfBoundsException(concoctExceptionMessage(context, failed))
        }
    }


    // Append text to the StringBuilder, padded out to the given width with the given characters.
    private fun StringBuilder.writeColumn(width: Int, text: String, lpad: Char? = null, rpad: Char? = ' ',
                                        gap: Char? = ' ') {
        val nrpad = if (rpad == null) 0 else (width - text.length) / (if (lpad == null) 1 else 2)
        val nlpad = if (lpad == null) 0 else (width - nrpad - text.length)
        if (lpad != null)
            for (x in 1..nlpad)
                append(lpad)
        append(text)
        if (rpad != null)
            for (x in 1..nrpad)
                append(rpad)
        if (gap != null)
            append(gap)
    }

    // Build an english-description of numeric dimensions
    private fun concoctFixedDescription(rows: String, cols: String): String {
        if (rows == "1")
            return "a ${cols}-vector"
        if (cols == "1")
            return "a ${rows}-vector"
        return "$rows by $cols"
    }

    // Build a list of things compareTo needs to be the same as. Looks up other things that have
    // the same character-based dimension defined.
    private fun concoctComparison(name: String, compareTo: String): List<String> {
        val out = mutableListOf<String>()
        if (compareTo !in seenInError) {
            for ((name2, dims) in declared) {
                val (rows, cols) = dims
                if (name2 != name) {
                    if (rows == compareTo) out.add("as $name2 has rows")
                    if (cols == compareTo) out.add("as $name2 has columns")
                }
            }
            seenInError.add(compareTo)
        }
        return out
    }

    // Build our pretty table of dimensions real vs actual.
    private fun concoctExceptionMessage(context: ValidationContext, failed: List<Pair<String,
    Matrix<Double>>>): String {
        val strings = mutableListOf<String>()
        for ((index, matrix) in context.matrices.withIndex()) {
            val name = context.matrixNames[index]
            declared[name]?.let { decl ->
                strings.add(name)
                val (r, c) = decl
                strings.add("${r}x${c}")
                strings.add("${matrix.numRows()}x${matrix.numCols()}")
            }
        }
        val headers = arrayOf("Matrix", "Required", "Actual")
        val widths = mutableListOf(headers[0].length, headers[1].length, headers[2].length)
        for ((index, str) in strings.withIndex())
            if (str.length > widths[index % widths.size])
                widths[index % widths.size] = str.length
        return buildString {
            append("Invalid matrix dimensions.\n\n")
            for ((index, header) in headers.withIndex())
                writeColumn(widths[index], header, ' ', ' ')
            append("\n")
            for (width in widths)
                writeColumn(width, "", '=', null)
            for ((index, str) in strings.withIndex()) {
                val windex = index % widths.size
                if (windex == 0) append("\n")
                writeColumn(widths[windex], str, if (windex == 0) null else ' ')
            }
            append("\n")
            for ((name, matrix) in failed) {
                declared[name]?.let { decl ->
                    val (rows, cols) = decl
                    if (rows.matches(Regex("^\\d+$")) && cols.matches(Regex("^\\d+$"))) {
                        append("\n$name must be ${concoctFixedDescription(rows, cols)}, got ")
                        append(concoctFixedDescription("${matrix.numRows()}",
                                                       "${matrix.numCols()}"))
                        // do nothing
                    }
                    if (rows.matches(Regex("^\\d+$"))) {
                        append("\n$name must have exactly $rows rows (has ${matrix.numRows()})")
                    } else {
                        for (comparison in concoctComparison(name, rows))
                            append("\n$name must have the same number of rows $comparison")
                    }
                    if (cols.matches(Regex("^\\d+$"))) {
                        append("\n$name must have exactly $cols columns (has ${matrix.numCols()})")
                    } else {
                        for (comparison in concoctComparison(name, rows))
                            append("\n$name must have the same number of columns $comparison")
                    }
                }
            }
        }
    }
}

/**
 * Require the current matrix to have exactly the given number of rows and columns.
 * @param rows Fixed number of rows to require.
 * @param cols Fixed number of columns to require.
 */
fun ValidationContext.dim(rows: Int,  cols:  Int) : ValidationContext {
    if (validateMatrices)
        meta.dim(currentMatrixName, currentMatrix, rows, cols)
    return this
}

/**
 * Require the current matrix to have exactly the given number of rows and match the number of columns with
 * other dimensions in other matrices based on the cols variable name.
 * @param rows Fixed number of rows to require.
 * @param cols Variable name for the number of cols in the matrix
 */
fun ValidationContext.dim(rows: Int,  cols: Char) : ValidationContext {
    if (validateMatrices)
        meta.dim(currentMatrixName, currentMatrix, rows, cols)
    return this
}

/**
 * Require the current matrix to have exactly the given number of columns and match the number of rows with
 * other dimensions in other matrices based on the rows variable name.
 * @param rows Variable name for the number of rows in the matrix
 * @param cols Fixed number of columns to require.
 */
fun ValidationContext.dim(rows: Char, cols:  Int) : ValidationContext {
    if (validateMatrices)
        meta.dim(currentMatrixName, currentMatrix, rows, cols)
    return this
}

/**
 * Require the current matrix's dimensions to correspond to the given variable names. Compares with other
 * dimensions in other matrices in the context that are assigned the same variable name and raises an error if
 * they don't all match.
 * @param rows Variable name for the number of rows in the matrix
 * @param cols Variable name for the number of cols in the matrix
 */
fun ValidationContext.dim(rows: Char, cols: Char) : ValidationContext {
    if (validateMatrices)
        meta.dim(currentMatrixName, currentMatrix, rows, cols)
    return this
}

/**
 * Accept a transposed version of the matrix as satisfying the dimensions check. For example, allow a 1 x 3
 * matrix when dimensions are declared as 3 x 1.
 */
val ValidationContext.transposable : ValidationContext get() {
    meta.markTransposable(currentMatrix)
    return this
}
