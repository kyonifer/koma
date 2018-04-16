/**
 * A general interface for creating common types of Matrix. A koma backend must both
 * implement this class and Matrix.
 */

package koma.matrix

import koma.internal.KomaJsName

/**
 * A set of constructors that must be implemented by a koma backend. Generates various types of matrices.
 * Generic parameter is the type of element, i.e. T=Matrix<Double> or T=Matrix<Int>, etc.
 */
interface MatrixFactory<out T: Matrix<*>> {
    /**
     * Generate a zero initialized matrix of the requested shape.
     */
    @KomaJsName("zeros")
    fun zeros(rows: Int, cols: Int): T

    /**
     * Creates a row-vector with initial values pulled from an int range, e.g. 1..45
     */
    @KomaJsName("createRange")
    fun create(data: IntRange): T

    /**
     * Creates a row-vector with initial values pulled from a double array
     */
    @KomaJsName("createArray")
    fun create(data: DoubleArray): T

    /**
     * Creates a matrix from an array of arrays (row-major)
     */
    @KomaJsName("create2DArray")
    fun create(data: Array<DoubleArray>): T

    /**
     * Creates a one initialized matrix of the requested shape
     */
    @KomaJsName("ones")
    fun ones(rows: Int, cols: Int): T

    /**
     * Creates an identity matrix of the requested shape
     */
    @KomaJsName("eyeSquare")
    fun eye(size: Int): T

    /**
     * Creates an identity matrix of the requested shape, with zero padding if the axis lengths arent equal.
     */
    @KomaJsName("eye")
    fun eye(rows: Int, cols: Int): T

    /**
     * Creates a matrix of uniform 0-1 random samples
     */
    @KomaJsName("rand")
    fun rand(rows: Int, cols: Int): T

    /**
     * Creates a matrix of unit-normal random samples
     */
    @KomaJsName("randn")
    fun randn(rows: Int, cols: Int): T

    /**
     * Creates a row-vector with the first value of [start] and the last value of [stop], with [increment] steps
     * between each value.
     */
    @KomaJsName("arangeIncrements")
    fun arange(start: Double, stop: Double, increment: Double): T

    /**
     * Creates a row-vector with the first value of [start] and the last value of [stop], with unit steps
     * between each value.
     */
    @KomaJsName("arange")
    fun arange(start: Double, stop: Double): T

    /**
     * Creates a row-vector with the first value of [start] and the last value of [stop], with [increment] steps
     * between each value.
     */
    @KomaJsName("arangeIntIncrements")
    fun arange(start: Int, stop: Int, increment: Int): T

    /**
     * Creates a row-vector with the first value of [start] and the last value of [stop], with unit steps
     * between each value.
     */
    @KomaJsName("arangeInt")
    fun arange(start: Int, stop: Int): T
}