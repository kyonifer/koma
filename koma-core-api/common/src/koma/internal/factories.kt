package koma.internal

import koma.matrix.MatrixFactory
import koma.matrix.Matrix
import koma.ndarray.NumericalNDArrayFactory

internal expect fun getDoubleMatrixFactories(): List<MatrixFactory<Matrix<Double>>>

expect fun getDoubleMatrixFactory(): MatrixFactory<Matrix<Double>>
expect fun getFloatMatrixFactory(): MatrixFactory<Matrix<Float>>
expect fun getIntMatrixFactory(): MatrixFactory<Matrix<Int>>

expect fun getDoubleNDArrayFactory(): NumericalNDArrayFactory<Double>
expect fun getFloatNDArrayFactory(): NumericalNDArrayFactory<Float>
expect fun getLongNDArrayFactory(): NumericalNDArrayFactory<Long>
expect fun getIntNDArrayFactory(): NumericalNDArrayFactory<Int>
expect fun getShortNDArrayFactory(): NumericalNDArrayFactory<Short>
expect fun getByteNDArrayFactory(): NumericalNDArrayFactory<Byte>
