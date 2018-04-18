package koma.matrix

@Deprecated("Use Matrix.doubleFactory", ReplaceWith("Matrix.doubleFactory"))
var doubleFactory: MatrixFactory<Matrix<Double>>
    get() = Matrix.doubleFactory
    set(value) {Matrix.doubleFactory = value}
@Deprecated("Use Matrix.intFactory", ReplaceWith("Matrix.intFactory"))
var intFactory: MatrixFactory<Matrix<Int>>
    get() = Matrix.intFactory
    set(value) {Matrix.intFactory = value}
@Deprecated("Use Matrix.floatFactory", ReplaceWith("Matrix.floatFactory"))
var floatFactory: MatrixFactory<Matrix<Float>>
    get() = Matrix.floatFactory
    set(value) {Matrix.floatFactory = value}
