package golem.matrix

/**
 * A general facade for a Matrix type. Allows for various backend to be
 * implemented to actually perform the computation. A golem backend must both
 * implement this class and MatrixFactory.
 */

// Recursive generic allows us to specialize for a particular internal implementation
interface Matrix<T>
{
    // Algebraic Operators
    fun mod(other: Matrix<T>) : Matrix<T>
    fun transpose(): Matrix<T>
    fun div(other: Int) : Matrix<T>
    fun div(other: Double) : Matrix<T>
    fun times(other: Matrix<T>) : Matrix<T>
    fun times(other: Double) : Matrix<T>
    fun elementTimes(other: Matrix<T>) : Matrix<T>
    fun minus() : Matrix<T>
    fun minus(other: Double) : Matrix<T>
    fun minus(other: Matrix<T>): Matrix<T>
    fun plus(other: Double) : Matrix<T>
    fun plus(other: Matrix<T>): Matrix<T>
    fun epow(other: Double): Matrix<T>
    fun epow(other: Int): Matrix<T>
    fun pow(exponent: Int): Matrix<T>

    // Dimensions
    fun numRows(): Int
    fun numCols(): Int

    // Index syntax
    fun set(i: Int, v: T)
    fun set(i: Int, j:Int, v:T)

    fun get(i: Int, j: Int) : T
    fun get(i: Int) : T

    fun copy(): Matrix<T>

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


    fun getRow(row: Int): Matrix<T>
    fun getCol(col: Int): Matrix<T>
    fun setCol(index: Int, col: Matrix<T>)
    fun setRow(index: Int, row: Matrix<T>)

    // Decompositions (Already has eig, svd) [expm,schur not available]
    fun chol() : Matrix<T>
    fun LU() : Triple<Matrix<T>,Matrix<T>,Matrix<T>>
    fun QR() : Pair<Matrix<T>,Matrix<T>>
    //need schur, svd, eig


    // Advanced Functions
    fun expm(): Matrix<T>
    fun solve(A: Matrix<T>, B: Matrix<T>): Matrix<T>

    // Basic Functions
    fun inv(): Matrix<T>
    fun det(): T
    fun pinv(): Matrix<T>
    fun normf(): T
    fun elementSum(): T
    fun diag(): Matrix<T>
    fun cumsum(): T
    fun max(): T // add dimension: Int?
    fun mean(): T
    fun min(): T
    fun argMax(): T
    fun argMean(): T
    fun argMin(): T
    fun norm(): T // L2 (Euclidean) norm
    fun trace(): T

    /**
     *  Because sometimes all you have is a Matrix, but you really want a MatrixFactory.
     */
    fun getFactory(): MatrixFactory<Matrix<T>>
    // Print the internal
    fun repr():String

    val T: Matrix<T>
        get() = this.transpose()


}
