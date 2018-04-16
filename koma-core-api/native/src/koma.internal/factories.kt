package koma.internal

import koma.matrix.*
import koma.matrix.cblas.CBlasMatrixFactory
import koma.internal.default.generated.matrix.DefaultFloatMatrixFactory
import koma.internal.default.generated.matrix.DefaultIntMatrixFactory
import koma.ndarray.NumericalNDArrayFactory

internal actual fun getDoubleMatrixFactories(): List<MatrixFactory<Matrix<Double>>> {
    return listOf(CBlasMatrixFactory())
}

actual fun getDoubleMatrixFactory(): MatrixFactory<Matrix<Double>> = CBlasMatrixFactory()
actual fun getFloatMatrixFactory(): MatrixFactory<Matrix<Float>> = DefaultFloatMatrixFactory()
actual fun getIntMatrixFactory(): MatrixFactory<Matrix<Int>> = DefaultIntMatrixFactory()

actual fun getDoubleNDArrayFactory(): NumericalNDArrayFactory<Double> = TODO()
actual fun getFloatNDArrayFactory(): NumericalNDArrayFactory<Float> = TODO()
actual fun getLongNDArrayFactory(): NumericalNDArrayFactory<Long> = TODO()
actual fun getIntNDArrayFactory(): NumericalNDArrayFactory<Int> = TODO()
actual fun getShortNDArrayFactory(): NumericalNDArrayFactory<Short> = TODO()
actual fun getByteNDArrayFactory(): NumericalNDArrayFactory<Byte> = TODO()
