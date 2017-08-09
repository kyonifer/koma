package koma.matrix

/**
 * Provides a view of the provided [matrix] that avoids copying when possible.
 *
 * @param matrix The matrix that
 * @param copyOnWrite
 */
class MatrixView<T>(val matrix: Matrix<T>): Matrix<T> by matrix {
    /**
     * If true, creates a copy of the viewed matrix when something attempts to
     * write to the view. If false, modifications mutate the underlying [matrix].
     */
    var copyOnWrite: Boolean = true

    /**
     * Set which rows of the underlying [matrix] are available in the view.
     */
    var rows: IntRange = 0..matrix.numRows()-1

    /**
     * Set which cols of the underlying [matrix] are available in the view.
     */
    var cols: IntRange = 0..matrix.numCols()-1

    /**
     * If true, swaps the row/col index order (such that this.matrix[i,j] == this[j, i])
     */
    var transposed: Boolean = false

}
