package koma.matrix

import koma.matrix.DoubleMatrix

/**
 * Intended to be used as a parameter to functions, when selection of matrix type is needed.
 * (e.g. creators.kt).
 */
object MatrixTypes {
    val DoubleType: MatrixType<DoubleMatrix> = {koma.factory}
    val IntType: MatrixType<Matrix<Int,*>> = {koma.intFactory }
    val FloatType: MatrixType<Matrix<Float, *>> = {koma.floatFactory} 
}

typealias MatrixType<T> = ()->MatrixFactory<T>











