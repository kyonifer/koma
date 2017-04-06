/**
 * A general interface for creating common types of Matrix. A golem backend must both
 * implement this class and Matrix.
 */

package golem.matrix

import golem.*


/**
 * A set of constructors that must be implemented by a golem backend. Generates various types of matrices.
 * Generic parameter is the type of element, i.e. T=Matrix<Double> or T=Matrix<Int>, etc.
 */
interface MatrixFactory<out T> {
    /**
     * Generate a zero initialized matrix of the requested shape.
     */
    fun zeros(rows: Int, cols: Int): T

    /**
     * Generate a zero initialized square matrix of the requested shape.
     */
    @Deprecated(DEPRECATE_IMPLICIT_2D)
    fun zeros(size: Int): T

    /**
     * Creates a row-vector with initial values pulled from an int range, e.g. 1..45
     */
    fun create(data: IntRange): T

    /**
     * Creates a row-vector with initial values pulled from a double array
     */
    fun create(data: DoubleArray): T

    /**
     * Creates a matrix from an array of arrays (row-major)
     */
    fun create(data: Array<DoubleArray>): T

    /**
     * Creates a one initialized square matrix of the requested shape
     */
    @Deprecated(DEPRECATE_IMPLICIT_2D)
    fun ones(size: Int): T

    /**
     * Creates a one initialized matrix of the requested shape
     */
    fun ones(rows: Int, cols: Int): T

    /**
     * Creates an identity matrix of the requested shape
     */
    fun eye(size: Int): T

    /**
     * Creates an identity matrix of the requested shape, with zero padding if the axis lengths arent equal.
     */
    fun eye(rows: Int, cols: Int): T

    /**
     * Creates a vector of [size] many uniform 0-1 random samples
     */
    @Deprecated(DEPRECATE_IMPLICIT_2D)
    fun rand(size: Int): T

    /**
     * Creates a matrix of uniform 0-1 random samples
     */
    fun rand(rows: Int, cols: Int): T

    /**
     * Creates a matrix of rows x cols uniform 0-1 samples using the given seed. Two calls with the same seed
     * will produce identical matrices
     */
    fun rand(rows: Int, cols: Int, seed: Long): T

    /**
     * Creates a vector of [size] many unit-normal random samples
     */
    @Deprecated(DEPRECATE_IMPLICIT_2D)
    fun randn(size: Int): T

    /**
     * Creates a matrix of unit-normal random samples
     */
    fun randn(rows: Int, cols: Int): T

    /**
     * Creates a matrix of rows x cols random samples using the given seed. Two calls with the same seed
     * will produce identical matrices
     */
    fun randn(rows: Int, cols: Int, seed: Long): T

    /**
     * Creates a row-vector with the first value of [start] and the last value of [stop], with [increment] steps
     * between each value.
     */
    fun arange(start: Double, stop: Double, increment: Double): T

    /**
     * Creates a row-vector with the first value of [start] and the last value of [stop], with unit steps
     * between each value.
     */
    fun arange(start: Double, stop: Double): T

    /**
     * Creates a row-vector with the first value of [start] and the last value of [stop], with [increment] steps
     * between each value.
     */
    fun arange(start: Int, stop: Int, increment: Int): T

    /**
     * Creates a row-vector with the first value of [start] and the last value of [stop], with unit steps
     * between each value.
     */
    fun arange(start: Int, stop: Int): T
}