package koma.ndarray

import koma.matrix.*

/**
 * A set of constructors for a koma NDArray containing numerical data. For generic
 * factories, see [GenericNDArrayFactory].
 */
interface NumericalNDArrayFactory<T: Number>: GenericNDArrayFactory<T> {
    /**
     * Generate a zero initialized ND container of the requested shape
     */
    fun zeros(vararg lengths: Int): NDArray<T>

    /**
     * Creates a 1 initialized ND container of the requested shape
     */
    fun ones(vararg lengths: Int): NDArray<T>

    /**
     * Creates a ND container of uniform 0-1 random samples
     */
    fun rand(vararg lengths: Int): NDArray<T>

    /**
     * Creates a ND container of unit-normal random samples
     */
    fun randn(vararg lengths: Int): NDArray<T>

    /**
     * Creates a 1D vector with initial values pulled from an int range, e.g. 1..45
     */
    fun create(data: IntRange): NDArray<T>

    /**
     * Creates a 1D vector with initial values pulled from a double array
     */
    fun create(data: DoubleArray): NDArray<T>

    /**
     * Creates a ND container from an array of arrays (row-major, 2D)
     */
    fun create(data: Array<DoubleArray>): NDArray<T>

    /**
     * Creates a 1D vector with the first value of [start] and the last value of [stop], with [increment] steps
     * between each value.
     */
    fun arange(start: Double, stop: Double, increment: Double): NDArray<T>

    /**
     * Creates a 1D vector with the first value of [start] and the last value of [stop], with unit steps
     * between each value.
     */
    fun arange(start: Double, stop: Double): NDArray<T>

    /**
     * Creates a 1D vector with the first value of [start] and the last value of [stop], with [increment] steps
     * between each value.
     */
    fun arange(start: Int, stop: Int, increment: Int): NDArray<T>

    /**
     * Creates a 1D vector with the first value of [start] and the last value of [stop], with unit steps
     * between each value.
     */
    fun arange(start: Int, stop: Int): NDArray<T>
}


