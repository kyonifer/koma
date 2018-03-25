package koma.internal

import koma.matrix.*

internal actual fun getDoubleFactories(): List<MatrixFactory<Matrix<Double>>>
        = listOf(getDoubleFactory())

/**
 * js implementations should add themselves to these arrays
 * (This is a poor man's implementation of ServiceLoader on js)
 */

var doubleFac: MatrixFactory<Matrix<Double>>? = null
var floatFac: MatrixFactory<Matrix<Float>>? = null
var intFac: MatrixFactory<Matrix<Int>>? = null

actual fun getDoubleFactory(): MatrixFactory<Matrix<Double>> = doubleFac ?: error("No koma-core implementations available")
actual fun getFloatFactory(): MatrixFactory<Matrix<Float>> = floatFac ?: error("No koma-core implementations available")
actual fun getIntFactory(): MatrixFactory<Matrix<Int>> = intFac ?: error("No koma-core implementations available")
