package koma

import koma.internal.getDoubleFactories
import koma.matrix.Matrix
import koma.matrix.MatrixFactory
import koma.matrix.default.DefaultFloatMatrixFactory
import koma.matrix.default.DefaultIntMatrixFactory

actual var doubleFactory: MatrixFactory<Matrix<Double>> = getDoubleFactories()[0]
actual var floatFactory: MatrixFactory<Matrix<Float>> = DefaultFloatMatrixFactory()
actual var intFactory: MatrixFactory<Matrix<Int>> = DefaultIntMatrixFactory()
