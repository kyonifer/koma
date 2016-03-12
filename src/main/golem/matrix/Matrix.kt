package golem.matrix

import golem.*
import java.io.*
import java.text.DecimalFormat

/**
 * A general facade for a Matrix type. Allows for various backend to be
 * implemented to actually perform the computation. A golem backend must both
 * implement this class and MatrixFactory.
 */

// Recursive generic allows us to specialize for a particular internal implementation
interface Matrix<T> : Iterable<T>, Serializable {
    // Algebraic Operators
    operator fun mod(other: Matrix<T>): Matrix<T>

    operator fun div(other: Int): Matrix<T>
    operator fun div(other: Double): Matrix<T>
    operator fun times(other: Matrix<T>): Matrix<T>
    operator fun times(other: Double): Matrix<T>
    operator fun unaryMinus(): Matrix<T>
    operator fun minus(other: Double): Matrix<T>
    operator fun minus(other: Matrix<T>): Matrix<T>
    operator fun plus(other: Double): Matrix<T>
    operator fun plus(other: Matrix<T>): Matrix<T>
    fun transpose(): Matrix<T>
    fun elementTimes(other: Matrix<T>): Matrix<T>
    fun epow(other: Double): Matrix<T>
    infix fun epow(other: Int): Matrix<T>
    infix fun pow(exponent: Int): Matrix<T>

    // Dimensions
    fun numRows(): Int

    fun numCols(): Int

    // Index syntax
    operator fun set(i: Int, v: T)

    operator fun set(i: Int, j: Int, v: T)

    operator fun get(i: Int, j: Int): T
    operator fun get(i: Int): T

    fun copy(): Matrix<T>

    // For speed optimized code (if backend isnt chosen type, may incur performance loss)
    // We can get rid of this when Java 10 generic specialization comes!
    fun getInt(i: Int, j: Int): Int

    fun getDouble(i: Int, j: Int): Double
    fun getFloat(i: Int, j: Int): Float
    fun getInt(i: Int): Int
    fun getDouble(i: Int): Double
    fun getFloat(i: Int): Float
    fun setInt(i: Int, v: Int)
    fun setDouble(i: Int, v: Double)
    fun setFloat(i: Int, v: Float)
    fun setInt(i: Int, j: Int, v: Int)
    fun setDouble(i: Int, j: Int, v: Double)
    fun setFloat(i: Int, j: Int, v: Float)

    /**
     * Retrieves the data formatted as doubles in row-major order
     * This method is only for performance over potentially boxing get(Double)
     * methods. This method may or may not return a copy, and thus should be
     * treated as read-only unless backend behavior is known.
     */
    fun getDoubleData(): DoubleArray

    fun getRow(row: Int): Matrix<T>
    fun getCol(col: Int): Matrix<T>
    fun setCol(index: Int, col: Matrix<T>)
    fun setRow(index: Int, row: Matrix<T>)

    // Decompositions (Already has eig, svd) [expm,schur not available]
    fun chol(): Matrix<T>

    fun LU(): Triple<Matrix<T>, Matrix<T>, Matrix<T>>
    fun QR(): Pair<Matrix<T>, Matrix<T>>
    // TODO: need schur, svd, eig


    // Advanced Functions
    fun expm(): Matrix<T>

    fun solve(A: Matrix<T>, B: Matrix<T>): Matrix<T>

    // Basic Functions
    fun inv(): Matrix<T>

    fun det(): T
    fun pinv(): Matrix<T>
    fun normF(): T
    fun normIndP1(): T
    fun elementSum(): T
    fun diag(): Matrix<T>
    fun max(): T // add dimension: Int?
    fun mean(): T
    fun min(): T
    fun argMax(): Int // Row major 1D index
    fun argMin(): Int // Row major 1D index
    fun norm(): T // L2 (Euclidean) norm
    fun trace(): T
    fun T(): Matrix<T> // In MATLAB, this appears at foo.T

    /**
     * Returns the underlying matrix object from the back-end this Matrix is wrapping. This should be used
     * sparingly (as it breaks encapsulation), but it can increase performance by using computation specifically
     * designed for a particular back-end. Code using this method should not rely on a particular back-end, and
     * should always fallback to slow generic code if an unrecognized matrix is returned here (e.g. use [get] and [set])
     * to access the elements generically).
     */
    fun getBaseMatrix(): Any


    /**
     *  Because sometimes all you have is a Matrix, but you really want a MatrixFactory.
     */
    fun getFactory(): MatrixFactory<Matrix<T>>


    fun repr(): String {

        val fmtString = when (matFormat) {
            SHORT_NUMBER                -> "0.00##"
            LONG_NUMBER                 -> "0.00############"
            VERY_LONG_NUMBER            -> "0.00#############################"
            SCIENTIFIC_NUMBER           -> "0.00#####E0#"
            SCIENTIFIC_LONG_NUMBER      -> "0.00############E0#"
            SCIENTIFIC_VERY_LONG_NUMBER -> "0.00############################E0#"
            else                        -> "0.00############"
        }

        var formatter = DecimalFormat(fmtString)

        val lens = IntArray(numCols())
        eachIndexed { row, col, element ->
            val formatted = formatter.format(element)
            if (lens[col] < formatted.length) lens[col] = formatted.length
        }
        val bstream = ByteArrayOutputStream()
        val pstream = PrintStream(bstream)

        var indent = "mat["
        eachIndexed { row, col, element ->
            var formatted = formatter.format(element)
            if (col == 0) {
                if (row > 0)
                    pstream.append("end\n")
                pstream.append(indent)
                indent = "    "
            }
            if (formatted[0] != '-')
                formatted = " " + formatted
            pstream.append(formatted)
            if (col != lens.size - 1)
                pstream.append(",")
            (-1..(lens[col] - formatted.length)).forEach { pstream.append(" ") }
        }
        pstream.append("]")

        return bstream.toString()
    }

    val T: Matrix<T>
        get() = this.transpose()

    fun serializeObject(out: ObjectOutputStream): Unit {
        out.writeObject(this.numRows())
        out.writeObject(this.numCols())
        this.forEach { out.writeObject(it) }
    }

    fun deserializeObjectNoData() {
        throw StreamCorruptedException("No Data for Matrix In Stream")
    }

}
