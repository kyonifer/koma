package koma.internal

import koma.matrix.MatrixFactory
import koma.matrix.Matrix

internal expect fun getDoubleFactories(): List<MatrixFactory<Matrix<Double>>>