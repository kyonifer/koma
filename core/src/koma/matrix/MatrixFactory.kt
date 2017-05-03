/**
 * A general interface for creating common types of Matrix. A koma backend must both
 * implement this class and Matrix.
 */

package koma.matrix

import koma.*
import koma.polyfill.annotations.*


/**
 * A set of constructors that must be implemented by a koma backend. Generates various types of matrices.
 * Generic parameter is the type of element, i.e. T=Matrix<Double> or T=Matrix<Int>, etc.
 */
interface MatrixFactory<out T> {
    /**
     * Generate a zero initialized matrix of the requested shape.
     */
    @JsName("zeros")
    fun zeros(rows: Int, cols: Int): T

    /**
     * Generate a zero initialized square matrix of the requested shape.
     */
    @Deprecated(DEPRECATE_IMPLICIT_2D)
    @JsName("zeros__Deprecated")
    fun zeros(size: Int): T

    /**
     * Creates a row-vector with initial values pulled from an int range, e.g. 1..45
     */
    @JsName("createRange")
    fun create(data: IntRange): T

    /**
     * Creates a row-vector with initial values pulled from a double array
     */
    @JsName("createArray")
    fun create(data: DoubleArray): T

    /**
     * Creates a matrix from an array of arrays (row-major)
     */
    @JsName("create2DArray")
    fun create(data: Array<DoubleArray>): T

    /**
     * Creates a one initialized square matrix of the requested shape
     */
    @JsName("ones__Deprecated")
    @Deprecated(DEPRECATE_IMPLICIT_2D)
    fun ones(size: Int): T

    /**
     * Creates a one initialized matrix of the requested shape
     */
    @JsName("ones")
    fun ones(rows: Int, cols: Int): T

    /**
     * Creates an identity matrix of the requested shape
     */
    @JsName("eyeSquare")
    fun eye(size: Int): T

    /**
     * Creates an identity matrix of the requested shape, with zero padding if the axis lengths arent equal.
     */
    @JsName("eye")
    fun eye(rows: Int, cols: Int): T

    /**
     * Creates a vector of [size] many uniform 0-1 random samples
     */
    @JsName("rand__")
    @Deprecated(DEPRECATE_IMPLICIT_2D)
    fun rand(size: Int): T

    /**
     * Creates a matrix of uniform 0-1 random samples
     */
    @JsName("rand")
    fun rand(rows: Int, cols: Int): T

    /**
     * Creates a matrix of rows x cols uniform 0-1 samples using the given seed. Two calls with the same seed
     * will produce identical matrices
     */
    @JsName("randSeed")
    fun rand(rows: Int, cols: Int, seed: Long): T

    /**
     * Creates a vector of [size] many unit-normal random samples
     */
    @JsName("randn__Deprecated")
    @Deprecated(DEPRECATE_IMPLICIT_2D)
    fun randn(size: Int): T

    /**
     * Creates a matrix of unit-normal random samples
     */
    @JsName("randn")
    fun randn(rows: Int, cols: Int): T

    /**
     * Creates a matrix of rows x cols random samples using the given seed. Two calls with the same seed
     * will produce identical matrices
     */
    @JsName("randnSeed")
    fun randn(rows: Int, cols: Int, seed: Long): T

    /**
     * Creates a row-vector with the first value of [start] and the last value of [stop], with [increment] steps
     * between each value.
     */
    @JsName("arangeIncrements")
    fun arange(start: Double, stop: Double, increment: Double): T

    /**
     * Creates a row-vector with the first value of [start] and the last value of [stop], with unit steps
     * between each value.
     */
    @JsName("arange")
    fun arange(start: Double, stop: Double): T

    /**
     * Creates a row-vector with the first value of [start] and the last value of [stop], with [increment] steps
     * between each value.
     */
    @JsName("arangeIntIncrements")
    fun arange(start: Int, stop: Int, increment: Int): T

    /**
     * Creates a row-vector with the first value of [start] and the last value of [stop], with unit steps
     * between each value.
     */
    @JsName("arangeInt")
    fun arange(start: Int, stop: Int): T
}