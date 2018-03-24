package koma.internal

import koma.matrix.MatrixFactory
import koma.matrix.Matrix

internal expect fun getDoubleFactories(): List<MatrixFactory<Matrix<Double>>>

expect fun getDoubleFactory(): MatrixFactory<Matrix<Double>>
expect fun getFloatFactory(): MatrixFactory<Matrix<Float>>
expect fun getIntFactory(): MatrixFactory<Matrix<Int>>

