package golem.matrix

/**
 * Intended to be used as a parameter to functions, when selection of matrix type is needed.
 * (e.g. creators.kt which return Matrix<T> for a requested T).
 */
object MatrixTypes {
    val DoubleType by lazy { golem.factory }
    val IntType by lazy { golem.intFactory }
    val FloatType by lazy { golem.floatFactory }
}













