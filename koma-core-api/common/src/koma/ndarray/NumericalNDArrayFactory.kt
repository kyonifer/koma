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
}


