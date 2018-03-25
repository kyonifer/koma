package koma.ndarray.default

import koma.ndarray.*

class DefaultFloatNDArray(vararg shape: Int, init: (IntArray)->Float)
    : DefaultNDArray<Float>(*shape, init=init), NumericalNDArray<Float> {
    override fun copy(): NDArray<Float> = DefaultFloatNDArray(*shape, init={this.get(*it)})
    
    override fun div(other: Float): NumericalNDArray<Float>
            = this.map { it/other }.toNumerical()
    override fun times(other: NDArray<Float>): NumericalNDArray<Float>
            = this.mapIndexed{ idx, ele -> other[idx]*ele}.toNumerical()
    override fun times(other: Float): NumericalNDArray<Float>
            = this.map { other*it }.toNumerical()
    override fun unaryMinus(): NumericalNDArray<Float>
            = this.map { -1*it }.toNumerical()
    override fun minus(other: Float): NumericalNDArray<Float>
            = this.map { it - other }.toNumerical()
    override fun minus(other: NDArray<Float>): NumericalNDArray<Float>
            = this.mapIndexedN { idx, ele -> ele - other.get(*idx)}.toNumerical()
    override fun plus(other: Float): NumericalNDArray<Float>
            = this.map { it + other }.toNumerical()
    override fun plus(other: NDArray<Float>): NumericalNDArray<Float>
            = this.mapIndexedN { idx, ele -> ele+other.get(*idx) }.toNumerical()
    override fun pow(exponent: Int): NumericalNDArray<Float>
            = this.map {koma.pow(it.toDouble(), exponent.toDouble()).toFloat()}.toNumerical()
}

/**
 * Converts a regular [NDArray] with the same primitive type to
 * a NumericalNDArray, attempting to avoid a copy when possible
 */
fun NDArray<Float>.toNumerical(): NumericalNDArray<Float> 
        = when(this) {
    is NumericalNDArray<Float> -> { this }
    else -> { DefaultFloatNDArray(*shape().toIntArray(), init={this.get(*it)}) }
}