package golem.matrix

import golem.*

// Reified type parameter, intended for selection of matrix type
// in generic functions (e.g. creators.kt which return Matrix<T> for a requested T).
object MatrixTypes {
    val DoubleType = Double::class.java
    val IntType = Int::class.java
    val FloatType = Float::class.java
}

@Suppress("UNCHECKED_CAST")
fun <T> Class<T>.getFactory(): MatrixFactory<Matrix<T>> {
    return when(this) {
        Double::class.java  -> {factory as MatrixFactory<Matrix<T>>}
        Float::class.java   -> {floatFactory as MatrixFactory<Matrix<T>>}
        Integer::class.java -> {intFactory as MatrixFactory<Matrix<T>>}
        else               -> throw IllegalArgumentException("No factories available for $this")
    }
}












