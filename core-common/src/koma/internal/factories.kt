package koma.internal

import koma.matrix.MatrixFactory
import koma.matrix.Matrix

internal expect fun getFactories(): List<MatrixFactory<Matrix<Double>>>