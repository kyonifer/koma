package koma.matrix.common

import koma.matrix.*
import koma.extensions.*

abstract class MatrixBase<T>: Matrix<T> {

    override fun equals(other: Any?): Boolean {
        when (other) {
            is Matrix<*> -> {
                if(other.numRows() != numRows() || other.numCols() != numCols()) {
                    return false
                }
                else {
                    for(row in 0 until this.numRows()) {
                        for (col in 0 until this.numCols())
                            if (other[row, col] != this[row,col])
                                return false
                    }
                    return true
                }
            }
            else -> { return super.equals(other) }
        }
    }


    override fun hashCode(): Int {
        return this::class.hashCode()
    }

    override fun toString() = this.repr()

    /**
     * Attempts to downcast a matrix to its specific subclass,
     * accepting both inner wrapped types and outer types.
     * Requires the [TOuter] constructor to be passed in because
     * reified generics don't support ctor calls. If the passed
     * mat cannot be cast, instead copies the data manually into
     * a newly allocated matrix of the correct type.
     *
     * @param mat The matrix we want to cast or copy-- can either be a Matrix<T> or
     *            the output of getBaseMatrix for a backend (inner wrapped type).
     *
     * @param makeOuter A function that takes in an inner type and produces the
     *                  outer matrix type that we are trying to cast or copy to
     *
     * @param outerFac The factory for the outer type we are trying to cast or
     *                 copy to
     *
     */
    protected inline fun
            <DType, reified TOuter: Matrix<DType>, reified TInner>
            castOrCopy(mat: Matrix<DType>,
                       makeOuter: (TInner) -> TOuter,
                       outerFac: MatrixFactory<TOuter>): TOuter {

        return when (mat) {
            is TOuter -> mat
            else      -> {
                val base = mat.getBaseMatrix()
                if (base is TInner)
                    makeOuter(base)
                else {
                    val out = outerFac.zeros(mat.numRows(), mat.numCols())
                    for (row in 0.until(mat.numRows()))
                        for (col in 0.until(mat.numCols()))
                            out[row, col] = mat[row, col]
                    out
                }
            }
        }
    }
}