package golem.ndarray.purekt

import golem.ndarray.*
import golem.platformsupport.*

class DoublePureKtNDArray(vararg shape: Int, init: (IntArray)->Double)
    : PureKtNDArray<Double>(*shape, init=init), NumericalNDArray<Double> {
    override fun copy(): NDArray<Double> = DoublePureKtNDArray(*shape, init={this.get(*it)})
    
    override fun div(other: Int): NumericalNDArray<Double>
            = this.map {it/other}.toNumerical()
    override fun div(other: Double): NumericalNDArray<Double>
            = this.map { it/other }.toNumerical()
    override fun times(other: NDArray<Double>): NumericalNDArray<Double>
            = this.mapIndexed{ idx, ele -> other[idx]*ele}.toNumerical()
    override fun times(other: Double): NumericalNDArray<Double>
            = this.map { other*it }.toNumerical()
    override fun unaryMinus(): NumericalNDArray<Double>
            = this.map { -1*it }.toNumerical()
    override fun minus(other: Double): NumericalNDArray<Double>
            = this.map { it - other }.toNumerical()
    override fun minus(other: NDArray<Double>): NumericalNDArray<Double>
            = this.mapIndexedN { idx, ele -> ele - other.get(*idx)}.toNumerical()
    override fun plus(other: Double): NumericalNDArray<Double>
            = this.map { it + other }.toNumerical()
    override fun plus(other: NDArray<Double>): NumericalNDArray<Double>
            = this.mapIndexedN { idx, ele -> ele+other.get(*idx) }.toNumerical()
    override fun pow(exponent: Int): NumericalNDArray<Double>
            = this.map {Math.pow(it, exponent.toDouble())}.toNumerical()
}

/**
 * Converts a regular [NDArray] with the same primitive type to
 * a NumericalNDArray, attempting to avoid a copy when possible
 */
fun NDArray<Double>.toNumerical(): NumericalNDArray<Double> 
        = when(this) {
    is NumericalNDArray<Double> -> { this }
    else -> { DoublePureKtNDArray(*shape().toIntArray(), init={this.get(*it)}) }
}