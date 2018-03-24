package koma.internal

import koma.matrix.*
import koma.matrix.default.DefaultDoubleMatrixFactory
import koma.matrix.default.DefaultFloatMatrixFactory
import koma.matrix.default.DefaultIntMatrixFactory

internal actual fun getDoubleFactories(): List<MatrixFactory<Matrix<Double>>>
        = listOf(DefaultDoubleMatrixFactory())

actual fun getDoubleFactory(): MatrixFactory<Matrix<Double>> = DefaultDoubleMatrixFactory()
actual fun getFloatFactory(): MatrixFactory<Matrix<Float>> = DefaultFloatMatrixFactory()
actual fun getIntFactory(): MatrixFactory<Matrix<Int>> = DefaultIntMatrixFactory()

