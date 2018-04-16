package koma.internal

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

actual fun getDoubleNDArrayFactory(): NumericalNDArrayFactory<Double> = TODO()
actual fun getFloatNDArrayFactory(): NumericalNDArrayFactory<Float> = TODO()
actual fun getLongNDArrayFactory(): NumericalNDArrayFactory<Long> = TODO()
actual fun getIntNDArrayFactory(): NumericalNDArrayFactory<Int> = TODO()
actual fun getShortNDArrayFactory(): NumericalNDArrayFactory<Short> = TODO()
actual fun getByteNDArrayFactory(): NumericalNDArrayFactory<Byte> = TODO()
