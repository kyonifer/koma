package koma.matrix

@Deprecated("Use Matrix.doubleFactory", ReplaceWith("Matrix.doubleFactory"))
var doubleFactory: MatrixFactory<Matrix<Double>> = Matrix.doubleFactory
@Deprecated("Use Matrix.intFactory", ReplaceWith("Matrix.intFactory"))
var intFactory: MatrixFactory<Matrix<Int>> = Matrix.intFactory
@Deprecated("Use Matrix.floatFactory", ReplaceWith("Matrix.floatFactory"))
var floatFactory: MatrixFactory<Matrix<Float>> = Matrix.floatFactory
