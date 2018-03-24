package koma.internal

import koma.matrix.*
import koma.matrix.cblas.CBlasMatrixFactory

internal actual fun getDoubleFactories(): List<MatrixFactory<Matrix<Double>>> {
    return listOf(CBlasMatrixFactory())
}
