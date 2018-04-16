package koma.internal

import koma.internal.default.generated.ndarray.*
import koma.matrix.*
import koma.ndarray.NumericalNDArrayFactory

internal actual fun getDoubleMatrixFactories(): List<MatrixFactory<Matrix<Double>>>
        = listOf(getDoubleMatrixFactory())

/**
 * js implementations should add themselves to these arrays
 * (This is a poor man's implementation of ServiceLoader on js)
 */

var doubleFac: MatrixFactory<Matrix<Double>>? = null
var floatFac: MatrixFactory<Matrix<Float>>? = null
var intFac: MatrixFactory<Matrix<Int>>? = null

actual fun getDoubleMatrixFactory(): MatrixFactory<Matrix<Double>> = doubleFac ?: error("No koma-core implementations available")
actual fun getFloatMatrixFactory(): MatrixFactory<Matrix<Float>> = floatFac ?: error("No koma-core implementations available")
actual fun getIntMatrixFactory(): MatrixFactory<Matrix<Int>> = intFac ?: error("No koma-core implementations available")

actual fun getDoubleNDArrayFactory(): NumericalNDArrayFactory<Double> = DefaultDoubleNDArrayFactory()
actual fun getFloatNDArrayFactory(): NumericalNDArrayFactory<Float> = DefaultFloatNDArrayFactory()
actual fun getLongNDArrayFactory(): NumericalNDArrayFactory<Long> = DefaultLongNDArrayFactory()
actual fun getIntNDArrayFactory(): NumericalNDArrayFactory<Int> = DefaultIntNDArrayFactory()
actual fun getShortNDArrayFactory(): NumericalNDArrayFactory<Short> = DefaultShortNDArrayFactory()
actual fun getByteNDArrayFactory(): NumericalNDArrayFactory<Byte> = DefaultByteNDArrayFactory()


