package koma.matrix

/**
 * Intended to be used as a parameter to functions, when selection of matrix type is needed.
 * (e.g. creators.kt which return Matrix<T> for a requested T).
 */
object MatrixTypes {
    val DoubleType: MatrixType<Double> = {Matrix.doubleFactory}
    val IntType: MatrixType<Int> = {Matrix.intFactory }
    val FloatType: MatrixType<Float> = {Matrix.floatFactory}
}

typealias MatrixType<T> = ()->MatrixFactory<Matrix<T>>











