/**
 * A general interface for creating common types of Matrix. A golem backend must both
 * implement this class and Matrix.
 */

package golem.matrix

interface MatrixFactory<T:Matrix<*,*>>
{
    fun zeros(rows: Int, cols: Int): T
    fun zeros(size: Int): T

    // Support things like create(1..100)
    fun create(data: IntRange): T
    fun create(data: DoubleRange): T
    fun create(data: DoubleArray): T
    fun create(data: Array<DoubleArray>): T

    fun ones(size: Int): T
    fun ones(rows: Int, cols: Int): T

    fun eye(size: Int): T
    fun eye(rows: Int, cols: Int): T

    fun rand(size: Int): T
    fun rand(rows: Int, cols: Int): T
    fun rand(rows: Int, cols: Int, seed: Long): T

    fun randn(size: Int): T
    fun randn(rows: Int, cols: Int): T
    fun randn(rows: Int, cols: Int, seed: Long): T

    fun arange(start: Double, stop: Double, step: Double): T

}