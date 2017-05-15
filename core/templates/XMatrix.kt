package koma.matrix

import koma.*
import koma.matrix.*
import koma.polyfill.annotations.*

/**
 * Some functionality to help more easily implement ${dtype} based koma backends. Adds non-boxed 
 * access for critical methods.
 */
abstract class ${dtype}Matrix : MatrixBase<${dtype}, ${dtype}Matrix>() {

    /**
     * Converts from the generic Matrix<*,*> type to the recursive DoubleMatrix one. Should 
     * be rarely needed, as outside of koma users should always receive a DoubleMatrix.
     */
    override fun toTyped(): ${dtype}Matrix = this

    /**
     * Converts a primitive to the primitive type T this container uses. Useful in functions
     * operating on generic matrices where T isn't known.
     */
    override fun asDType(data: Number) = data.to${dtype}()

    // FIXME: Remove these if Kotlin generates primitive getters/setters without them in the future 

    /**
     * Intercept the nullable boxed get and implement with non-boxed getter
     */
    override operator fun get(i: Int?) = get(i ?: throw Exception(NULL_INDICES))
    override operator fun get(i: Int?, j: Int?) = get(i?: throw Exception(NULL_INDICES), 
                                                      j?: throw Exception(NULL_INDICES))

    /**
     * Non-boxed getter. This method will be selected by Kotlin code over the boxed one
     * if you have a known DoubleMatrix reference.
     */
    abstract operator fun get(i: Int) : ${dtype}
    abstract operator fun get(i: Int, j: Int) : ${dtype}
    
    /**
     * An empty overridden set method to force Kotlin to generate a non-boxed
     * setter. 
     */
    abstract override fun set(i: Int, v: ${dtype})
    abstract override fun set(i: Int, j: Int, v: ${dtype}) 

    /**
     * Calculates the cumulative (ongoing) sum of a matrix's elements. For example,
     * ```cumsum(mat[1,2,3])``` would return ```mat[1,3,6]```. Assumes matrix type is convertible to
     * double.
     *
     * @return A 1xarr.numRows*arr.numCols vector storing the ongoing cumsum.
     *
     */
    override fun cumSum(): ${dtype}Matrix {
        val out = this.getFactory().zeros(1, this.numRows() * this.numCols())
        for (i in 0..(this.numRows() * this.numCols() - 1)) {
            val ele = this[i]
            out[i] = if (i == 0) ele.to${dtype}() else (ele + out[i - 1]).to${dtype}()
        }
        return out
    }

    /**
     * Returns a Matrix as a double 2D array. Intended for MATLAB interop.
     *
     * @return a 2D array copy of the matrix.
     */
    override fun to2DArray(): Array<DoubleArray> {
        val out = Array(numRows(), { DoubleArray(numCols()) })
        for (row in 0..this.numRows() - 1)
            for (col in 0..this.numCols() - 1)
                out[row][col] = this[row, col].toDouble()
        return out
    }
    
    /**
     * A backend agnostic implementation of the matrix exponential (i.e. e to the matrix).
     */
    override fun expm(): ${dtype}Matrix {

        val solveProvider = { A: ${dtype}Matrix, B: ${dtype}Matrix -> this.solve(A, B) }
        var A: ${dtype}Matrix = this
        val A_L1 = A.normIndP1()
        var n_squarings = 0

        // Spread returns so we can val(U,V) here (TODO: Fix this when Kotlin allows)

        if (A_L1 < 1.495585217958292e-002) {
            val (U, V) = _pade3(A)
            return dispatchPade(U, V, n_squarings, solveProvider)
        } else if (A_L1 < 2.539398330063230e-001) {
            val (U, V) = _pade5(A)
            return dispatchPade(U, V, n_squarings, solveProvider)
        } else if (A_L1 < 9.504178996162932e-001) {
            val (U, V) = _pade7(A)
            return dispatchPade(U, V, n_squarings, solveProvider)
        } else if (A_L1 < 2.097847961257068e+000) {
            val (U, V) = _pade9(A)
            return dispatchPade(U, V, n_squarings, solveProvider)
        } else {

            val maxnorm = 5.371920351148152
            n_squarings = koma.max(0, ceil(logb(2, A_L1 / maxnorm)))
            A /= pow(2.0, n_squarings).to${dtype}()
            val (U, V) = _pade13(A)
            return dispatchPade(U, V, n_squarings, solveProvider)
        }
    }

    private fun dispatchPade(U: ${dtype}Matrix,
                             V: ${dtype}Matrix,
                             n_squarings: Int,
                             solveProvider: (${dtype}Matrix, ${dtype}Matrix) -> ${dtype}Matrix): ${dtype}Matrix {
        val P = U + V
        val Q = -U + V
        //var R = solve(Q,P)
        var R = U.getFactory().zeros(Q.numCols(), P.numCols())
        for (i in 0..P.numCols() - 1) {
            R.setCol(i, solveProvider(Q, P.getCol(i)))

        }
        for (i in 0..n_squarings - 1)
            R *= R
        return R
    }

    private fun _pade3(A: ${dtype}Matrix): Pair<${dtype}Matrix, ${dtype}Matrix> {
        val b = koma.mat[120, 60, 12, 1].to${dtype}Matrix()
        val ident = A.getFactory().eye(A.numRows(), A.numCols())

        val A2 = A * A
        val U = A * (A2 * b[3] + ident * b[1])
        val V = A2 * b[2] + ident * b[0]

        return Pair(U, V)
    }

    private fun _pade5(A: ${dtype}Matrix): Pair<${dtype}Matrix, ${dtype}Matrix> {
        val b = koma.mat[30240, 15120, 3360, 420, 30, 1].to${dtype}Matrix()
        val ident = A.getFactory().eye(A.numRows(), A.numCols())
        val A2 = A * A
        val A4 = A2 * A2
        val U = A * (A4 * b[5] + A2 * b[3] + ident * b[1])
        val V = A4 * b[4] + A2 * b[2] + ident * b[0]
        return Pair(U, V)

    }

    private fun _pade7(A: ${dtype}Matrix): Pair<${dtype}Matrix, ${dtype}Matrix> {
        val b = koma.mat[17297280, 8648640, 1995840, 277200, 25200, 1512, 56, 1].to${dtype}Matrix()
        val ident = A.getFactory().eye(A.numRows(), A.numCols())
        val A2 = A * A
        val A4 = A2 * A2
        val A6 = A4 * A2
        val U = A * (A6 * b[7] + A4 * b[5] + A2 * b[3] + ident * b[1])
        val V = A6 * b[6] + A4 * b[4] + A2 * b[2] + ident * b[0]
        return Pair(U, V)
    }

    private fun _pade9(A: ${dtype}Matrix): Pair<${dtype}Matrix, ${dtype}Matrix> {
        val b = koma.mat[17643225600, 8821612800, 2075673600, 302702400, 30270240,
                2162160, 110880, 3960, 90, 1].to${dtype}Matrix()
        val ident = A.getFactory().eye(A.numRows(), A.numCols())
        val A2 = A * A
        val A4 = A2 * A2
        val A6 = A4 * A2
        val A8 = A6 * A2
        val U = A * (A8 * b[9] + A6 * b[7] + A4 * b[5] + A2 * b[3] + ident * b[1])
        val V = A8 * b[8] + A6 * b[6] + A4 * b[4] + A2 * b[2] + ident * b[0]
        return Pair(U, V)
    }

    private fun _pade13(A: ${dtype}Matrix): Pair<${dtype}Matrix, ${dtype}Matrix> {
        val b = koma.mat[64764752532480000, 32382376266240000, 7771770303897600,
                1187353796428800, 129060195264000, 10559470521600, 670442572800,
                33522128640, 1323241920, 40840800, 960960, 16380, 182, 1].to${dtype}Matrix()
        val ident = A.getFactory().eye(A.numRows(), A.numCols())

        val A2 = A * A
        val A4 = A2 * A2
        val A6 = A4 * A2
        val U = A * (A6 * (A6 * b[13] + A4 * b[11] + A2 * b[9]) + A6 * b[7] + A4 * b[5] + A2 * b[3] + ident * b[1])
        val V = A6 * (A6 * b[12] + A4 * b[10] + A2 * b[8]) + A6 * b[6] + A4 * b[4] + A2 * b[2] + ident * b[0]
        return Pair(U, V)
    }
    override fun rem(other: Matrix<${dtype}>): Matrix<${dtype}> = this.rem(other.toRMatrix().toTyped())
    override fun times(other: Matrix<${dtype}>): Matrix<${dtype}> = this.times(other.toRMatrix().toTyped())
    override fun minus(other: Matrix<${dtype}>): Matrix<${dtype}> = this.minus(other.toRMatrix().toTyped())
    override fun plus(other: Matrix<${dtype}>): Matrix<${dtype}> = this.plus(other.toRMatrix().toTyped())
    override fun elementTimes(other: Matrix<${dtype}>): Matrix<${dtype}> = this.elementTimes(other.toRMatrix().toTyped())
    override fun setCol(index: Int, col: Matrix<${dtype}>) { this.setCol(index, col.toRMatrix().toTyped()) }
    override fun setRow(index: Int, row: Matrix<${dtype}>) { this.setRow(index, row.toRMatrix().toTyped()) }
    override fun solve(A: Matrix<${dtype}>, B: Matrix<${dtype}>): Matrix<${dtype}> = this.solve(A.toRMatrix().toTyped(), 
                                                                                                B.toRMatrix().toTyped())


}

// TODO: Assumes all implementors will follow this convention
fun Matrix<${dtype}>.toRMatrix(): RMatrix<${dtype}, ${dtype}Matrix> = this as ${dtype}Matrix