package koma.matrix.common

import koma.matrix.*
import koma.*

abstract class MatrixBase<T>: Matrix<T> {
    /**
     * Attempts to downcast a matrix to its specific subclass,
     * accepting both inner wrapped types and outer types.
     * Requires the [TOuter] constructor to be passed in because
     * reified generics don't support ctor calls. If the passed
     * mat cannot be cast, instead copies the data manually into
     * a newly allocated matrix of the correct type.
     */
    protected inline fun
            <DType, reified TOuter: Matrix<DType>, reified TInner>
            castOrCopy(mat: Matrix<DType>,
                       makeOuter: (TInner) -> TOuter,
                       outerFac: MatrixFactory<TOuter>): TOuter {

        when (mat) {
            is TOuter -> return mat
            else      -> {
                val base = mat.getBaseMatrix()
                if (base is TInner)
                    return makeOuter(base)
                else {
                    val out = outerFac.zeros(mat.numRows(), mat.numCols())
                    for (row in 0.until(mat.numRows()))
                        for (col in 0.until(mat.numCols()))
                            out[row, col] = mat[row, col]
                    return out
                }
            }
        }
    }
}