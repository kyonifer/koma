package koma.ndarray.default

import koma.ndarray.*
import koma.platformsupport.*

class Default${dtype}NDArray(vararg shape: Int, init: (IntArray)->${dtype})
    : DefaultNDArray<${dtype}>(*shape, init=init), NumericalNDArray<${dtype}> {
    override fun copy(): NDArray<${dtype}> = Default${dtype}NDArray(*shape, init={this.get(*it)})
    
    override fun div(other: ${dtype}): NumericalNDArray<${dtype}>
            = this.map { it/other }.toNumerical()
    override fun times(other: NDArray<${dtype}>): NumericalNDArray<${dtype}>
            = this.mapIndexed{ idx, ele -> other[idx]*ele}.toNumerical()
    override fun times(other: ${dtype}): NumericalNDArray<${dtype}>
            = this.map { other*it }.toNumerical()
    override fun unaryMinus(): NumericalNDArray<${dtype}>
            = this.map { -1*it }.toNumerical()
    override fun minus(other: ${dtype}): NumericalNDArray<${dtype}>
            = this.map { it - other }.toNumerical()
    override fun minus(other: NDArray<${dtype}>): NumericalNDArray<${dtype}>
            = this.mapIndexedN { idx, ele -> ele - other.get(*idx)}.toNumerical()
    override fun plus(other: ${dtype}): NumericalNDArray<${dtype}>
            = this.map { it + other }.toNumerical()
    override fun plus(other: NDArray<${dtype}>): NumericalNDArray<${dtype}>
            = this.mapIndexedN { idx, ele -> ele+other.get(*idx) }.toNumerical()
    override fun pow(exponent: Int): NumericalNDArray<${dtype}>
            = this.map {Math.pow(it.toDouble(), exponent.toDouble()).to${dtype}()}.toNumerical()
}

/**
 * Converts a regular [NDArray] with the same primitive type to
 * a NumericalNDArray, attempting to avoid a copy when possible
 */
fun NDArray<${dtype}>.toNumerical(): NumericalNDArray<${dtype}> 
        = when(this) {
    is NumericalNDArray<${dtype}> -> { this }
    else -> { Default${dtype}NDArray(*shape().toIntArray(), init={this.get(*it)}) }
}