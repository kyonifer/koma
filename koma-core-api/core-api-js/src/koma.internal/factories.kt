package koma.internal

import koma.matrix.*
import koma.matrix.default.DefaultDoubleMatrixFactory

internal actual fun getDoubleFactories(): List<MatrixFactory<Matrix<Double>>>
        = listOf(DefaultDoubleMatrixFactory())
