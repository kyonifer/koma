package golem.matrix

/**
 * Intended to be used as a parameter to functions, when selection of matrix type is needed.
 * (e.g. creators.kt which return Matrix<T> for a requested T).
 */
object MatrixTypes {
    val DoubleType: MatrixType<Double> = {golem.factory}
    val IntType: MatrixType<Int> = {golem.intFactory }
    val FloatType: MatrixType<Float> = {golem.floatFactory} 
}

typealias MatrixType<T> = ()->MatrixFactory<Matrix<T>>











