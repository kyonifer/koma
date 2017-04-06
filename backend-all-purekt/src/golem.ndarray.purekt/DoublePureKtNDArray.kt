package golem.ndarray.purekt

import golem.ndarray.*

class DoublePureKtNDArray(vararg strides: Int, init: (IntArray)->Double)
    : PureKtNDArray<Double>(*strides, init=init), NumericalNDArray<Double> {
    override fun div(other: Int) 
            = this.mapArr {it/other}
    override fun div(other: Double): NDArray<Double> 
            = this.mapArr { it/other }
    override fun times(other: NDArray<Double>): NDArray<Double> 
            = this.mapArrIndexed{ idx,ele -> other[idx]*ele}
    override fun times(other: Double): NDArray<Double> 
            = this.mapArr { other*it }
    override fun unaryMinus(): NDArray<Double> 
            = this.mapArr { -1*it }
    override fun minus(other: Double): NDArray<Double> 
            = this.mapArr { it - other }
    override fun minus(other: NDArray<Double>): NDArray<Double> 
            = this.mapArrIndexed { idx,ele -> ele - other[idx]}
    override fun plus(other: Double): NDArray<Double> 
            = this.mapArr { it + other }
    override fun plus(other: NDArray<Double>): NDArray<Double> 
            = this.mapArrIndexed { idx, ele -> ele+other[idx] }
    override fun pow(exponent: Int): NDArray<Double> 
            = this.mapArr {Math.pow(it, exponent.toDouble())}
}