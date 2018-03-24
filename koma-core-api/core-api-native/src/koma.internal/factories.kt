package koma.internal

import koma.matrix.*
import koma.matrix.cblas.CBlasMatrixFactory
import koma.matrix.default.DefaultFloatMatrixFactory
import koma.matrix.default.DefaultIntMatrixFactory

internal actual fun getDoubleFactories(): List<MatrixFactory<Matrix<Double>>> {
    return listOf(CBlasMatrixFactory())
}

actual fun getDoubleFactory(): MatrixFactory<Matrix<Double>> = CBlasMatrixFactory()
actual fun getFloatFactory(): MatrixFactory<Matrix<Float>> = DefaultFloatMatrixFactory()
actual fun getIntFactory(): MatrixFactory<Matrix<Int>> = DefaultIntMatrixFactory()
