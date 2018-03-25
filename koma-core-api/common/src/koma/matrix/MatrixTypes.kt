package koma.matrix

/**
 * Intended to be used as a parameter to functions, when selection of matrix type is needed.
 * (e.g. creators.kt which return Matrix<T> for a requested T).
 */
object MatrixTypes {
    val DoubleType: MatrixType<Double> = {koma.factory}
    val IntType: MatrixType<Int> = {koma.intFactory }
    val FloatType: MatrixType<Float> = {koma.floatFactory} 
}

typealias MatrixType<T> = ()->MatrixFactory<Matrix<T>>











