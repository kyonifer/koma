package golem.matrix

/**
 * A general facade for a Matrix type. Allows for various backend to be
 * implemented to actually perform the computation. A golem backend must both
 * implement this class and MatrixFactory.
 */

// Recursive generic allows us to specialize for a particular internal implementation
interface Matrix<T:Matrix<T,U>, U>
{
    // Algebraic Operators
    fun mod(other: T) : T
    fun transpose(): T
    fun div(other: Int) : T
    fun div(other: Double) : T
    fun times(other: T) : T
    fun times(other: Double) : T
    fun elementTimes(other: T) : T
    fun minus() : T
    fun minus(other: Double) : T
    fun minus(other: T): T
    fun plus(other: Double) : T
    fun plus(other: T): T

    // Dimensions
    fun numRows(): Int
    fun numCols(): Int

    // Index syntax
    fun set(i: Int, v: U)
    fun set(i: Int, j:Int, v:U)

    fun get(i: Int, j: Int) : U
    fun get(i: Int) : U

    // For speed optimized code (if backend isnt chosen type, may incur performance loss)
    // We can get rid of this when Java 10 generic specialization comes!
    fun getInt(i: Int, j: Int) : Int
    fun getDouble(i: Int, j: Int) : Double
    fun getFloat(i: Int, j: Int) : Float
    fun getInt(i: Int) : Int
    fun getDouble(i: Int) : Double
    fun getFloat(i: Int) : Float
    fun setInt(i: Int, v: Int)
    fun setDouble(i: Int, v: Double)
    fun setFloat(i: Int, v: Float)
    fun setInt(i: Int, j:Int, v:Int)
    fun setDouble(i: Int, j:Int, v:Double)
    fun setFloat(i: Int, j:Int, v:Float)


    fun getRow(row: Int): T
    fun getCol(col: Int): T
    fun setCol(index: Int, col: T)
    fun setRow(index: Int, row: T)

    // Decompositions (Already has eig, svd) [expm,schur not available]
    fun chol() : T
    fun LU() : Pair<T,T>
    fun QR() : Pair<T,T>

    // Basic Functions
    fun inv(): T
    fun det(): U
    fun pinv(): T
    fun normf(): U
    fun elementSum(): U
    fun diag(): T
    fun cumsum(): U
    fun max(): U // add dimension: Int?
    fun mean(): U
    fun min(): U
    fun argMax(): U
    fun argMean(): U
    fun argMin(): U
    fun norm(): U // L2 (Euclidean) norm
    fun trace(): U

    // Print the internal
    fun repr():String

    val T: Matrix<T,U>
        get() = this.transpose()


}
