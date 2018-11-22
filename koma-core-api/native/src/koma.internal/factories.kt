package koma.internal

import koma.internal.default.generated.matrix.DefaultDoubleMatrixFactory
import koma.internal.default.generated.ndarray.*
import koma.matrix.*
import koma.internal.default.generated.matrix.DefaultFloatMatrixFactory
import koma.internal.default.generated.matrix.DefaultIntMatrixFactory
import koma.ndarray.NumericalNDArrayFactory

internal actual fun getDoubleMatrixFactories(): List<MatrixFactory<Matrix<Double>>> {
    return listOf()
}

actual fun getDoubleMatrixFactory(): MatrixFactory<Matrix<Double>> = DefaultDoubleMatrixFactory()
actual fun getFloatMatrixFactory(): MatrixFactory<Matrix<Float>> = DefaultFloatMatrixFactory()
actual fun getIntMatrixFactory(): MatrixFactory<Matrix<Int>> = DefaultIntMatrixFactory()

actual fun getDoubleNDArrayFactory(): NumericalNDArrayFactory<Double> = DefaultDoubleNDArrayFactory()
actual fun getFloatNDArrayFactory(): NumericalNDArrayFactory<Float> = DefaultFloatNDArrayFactory()
actual fun getLongNDArrayFactory(): NumericalNDArrayFactory<Long> = DefaultLongNDArrayFactory()
actual fun getIntNDArrayFactory(): NumericalNDArrayFactory<Int> = DefaultIntNDArrayFactory()
actual fun getShortNDArrayFactory(): NumericalNDArrayFactory<Short> = DefaultShortNDArrayFactory()
actual fun getByteNDArrayFactory(): NumericalNDArrayFactory<Byte> = DefaultByteNDArrayFactory()

