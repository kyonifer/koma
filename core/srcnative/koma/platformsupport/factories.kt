package koma.platformsupport

import koma.matrix.*
import koma.matrix.cblas.CBlasMatrixFactory

fun getFactories(): List<MatrixFactory<Matrix<Double>>> {
    return listOf(CBlasMatrixFactory())
}
