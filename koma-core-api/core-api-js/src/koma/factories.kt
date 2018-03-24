package koma

import koma.matrix.Matrix
import koma.matrix.MatrixFactory
import koma.matrix.default.DefaultDoubleMatrixFactory
import koma.matrix.default.DefaultFloatMatrixFactory
import koma.matrix.default.DefaultIntMatrixFactory

actual var doubleFactory: MatrixFactory<Matrix<Double>> = DefaultDoubleMatrixFactory()
actual var floatFactory: MatrixFactory<Matrix<Float>> = DefaultFloatMatrixFactory()
actual var intFactory: MatrixFactory<Matrix<Int>> = DefaultIntMatrixFactory()
