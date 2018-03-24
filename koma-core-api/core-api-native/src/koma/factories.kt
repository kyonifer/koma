package koma

import koma.matrix.Matrix
import koma.matrix.MatrixFactory
import koma.matrix.default.DefaultFloatMatrixFactory
import koma.matrix.default.DefaultIntMatrixFactory
import koma.matrix.cblas.CBlasMatrixFactory

actual var doubleFactory: MatrixFactory<Matrix<Double>> = CBlasMatrixFactory()
actual var floatFactory: MatrixFactory<Matrix<Float>> = DefaultFloatMatrixFactory()
actual var intFactory: MatrixFactory<Matrix<Int>> = DefaultIntMatrixFactory()
