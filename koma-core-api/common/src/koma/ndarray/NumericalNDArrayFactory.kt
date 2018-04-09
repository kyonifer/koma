package koma.ndarray

import koma.matrix.*

/**
 * A set of constructors that must be implemented by a koma NDArray backend.
 * Generic parameter is the type of element, i.e. T=NDArray<Double> or T=NDArray<Int>, etc.
 */
@Deprecated("Use NDArray<Double> and the math extensions in koma.extension instead of NumericalNDArray")
interface NumericalNDArrayFactory<out T> {
    /**
     * Generate a zero initialized ND container of the requested shape
     */
    fun zeros(vararg lengths: Int): T

    /**
     * Creates a 1 initialized ND container of the requested shape
     */
    fun ones(vararg lengths: Int): T

    /**
     * Creates a ND container of uniform 0-1 random samples
     */
    fun rand(vararg lengths: Int): T

    /**
     * Creates a ND container of unit-normal random samples
     */
    fun randn(vararg lengths: Int): T

    /**
     * Creates a 1D vector with initial values pulled from an int range, e.g. 1..45
     */
    fun create(data: IntRange): T

    /**
     * Creates a 1D vector with initial values pulled from a double array
     */
    fun create(data: DoubleArray): T

    /**
     * Creates a ND container from an array of arrays (row-major, 2D)
     */
    fun create(data: Array<DoubleArray>): T

    /**
     * Creates a 1D vector with the first value of [start] and the last value of [stop], with [increment] steps
     * between each value.
     */
    fun arange(start: Double, stop: Double, increment: Double): T

    /**
     * Creates a 1D vector with the first value of [start] and the last value of [stop], with unit steps
     * between each value.
     */
    fun arange(start: Double, stop: Double): T

    /**
     * Creates a 1D vector with the first value of [start] and the last value of [stop], with [increment] steps
     * between each value.
     */
    fun arange(start: Int, stop: Int, increment: Int): T

    /**
     * Creates a 1D vector with the first value of [start] and the last value of [stop], with unit steps
     * between each value.
     */
    fun arange(start: Int, stop: Int): T    
}


