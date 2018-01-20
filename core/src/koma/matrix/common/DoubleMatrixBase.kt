@file:JvmName("Algorithms")

package koma.matrix.common

import koma.ceil
import koma.extensions.*
import koma.logb
import koma.matrix.*
import koma.pow
import koma.polyfill.annotations.*
import koma.zeros

/**
 * Some functionality to help more easily implement double based koma backends. Feel free to not use if
 * your backend has fast implementations of these functions.
 */
abstract class DoubleMatrixBase : MatrixBase<Double>() {

    override fun getGeneric(i: Int) = getDouble(i)
    override fun getGeneric(i: Int, j: Int) = getDouble(i, j)
    override fun setGeneric(i: Int, v: Double) = setDouble(i, v)
    override fun setGeneric(i: Int, j: Int, v: Double) = setDouble(i, j, v)

    override fun getInt(i: Int, j: Int) = this[i, j].toInt()
    override fun getInt(i: Int) = this[i].toInt()
    override fun setInt(i: Int, v: Int) { this[i] = v.toDouble() }
    override fun setInt(i: Int, j: Int, v: Int) { this[i, j] = v.toDouble() }

    override fun getFloat(i: Int, j: Int) = this[i, j].toFloat()
    override fun getFloat(i: Int) = this[i].toFloat()
    override fun setFloat(i: Int, v: Float) { this[i] = v.toDouble() }
    override fun setFloat(i: Int, j: Int, v: Float) { this[i, j] = v.toDouble() }


    override fun setRow(index: Int, row: Matrix<Double>) {
        row.forEachIndexed { _, col, ele ->
            this[index, col] = ele
        }
    }
    override fun setCol(index: Int, col: Matrix<Double>) {
        col.forEachIndexed { row, _, ele ->
            this[row, index] = ele
        }
    }
    override fun getRow(row: Int) = zeros(1, numCols()).mapIndexed { _, col, _ -> 
        this[row, col] 
    } 
    override fun getCol(col: Int) = zeros(numRows(), 1).mapIndexed { row, _, _ -> 
        this[row, col] 
    } 
    override fun elementTimes(other: Matrix<Double>)
        = mapIndexed { row, col, ele ->
        ele*other[row, col]
    }
    override fun elementSum(): Double { 
        var out = 0.0 
        this.forEach { ele -> 
            out += ele 
        } 
        return out 
    } 

    override fun max(): Double {
        var max = Double.NEGATIVE_INFINITY
        this.forEach { ele ->
            if (ele > max) {
                max = ele
            }
        }
        return max
    }
    override fun mean() = elementSum() / (numCols() * numRows())
    override fun min(): Double {
        var min = Double.POSITIVE_INFINITY
        this.forEach { ele ->
            if (ele < min) {
                min = ele
            }
        }
        return min
    }
    override fun argMax(): Int { 
        var max = 0 
        for (i in 0..this.numCols() * this.numRows() - 1) 
            if (this[i] > this[max]) 
                max = i 
        return max 
    }
    override fun argMin(): Int { 
        var min = 0 
        for (i in 0..this.numCols() * this.numRows() - 1) 
            if (this[i] < this[min]) 
                min = i 
        return min 
    } 

    override fun times(other: Double) = map { it*other } 
    override fun unaryMinus() = this * -1
    override fun minus(other: Double) = map { it - other }
    override fun minus(other: Matrix<Double>) = mapIndexed { row, col, ele -> ele - other[row, col] }
    override fun plus(other: Matrix<Double>) = mapIndexed { row, col, ele -> ele + other[row, col]} 
    override fun plus(other: Double) = map{it + other}     
    override fun div(other: Int) = map { it / other }
    override fun div(other: Double) = map { it / other }
    override fun transpose() = zeros(numCols(), numRows()).also {
        it.fill { row, col ->
            this[col,row]
        }
    }
    override fun copy() = map{it}
    override fun epow(other: Double) = map { pow(it, other)} 
    override fun epow(other: Int) = map { pow(it, other)} 

    /**
     * A backend agnostic implementation of the matrix exponential (i.e. e to the matrix).
     */
    override fun expm(): Matrix<Double> {

        val solveProvider = { A: Matrix<Double>, B: Matrix<Double> -> A.solve(B) }
        var A: Matrix<Double> = this
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
            A /= pow(2.0, n_squarings)
            val (U, V) = _pade13(A)
            return dispatchPade(U, V, n_squarings, solveProvider)
        }
    }

    private fun dispatchPade(U: Matrix<Double>,
                             V: Matrix<Double>,
                             n_squarings: Int,
                             solveProvider: (Matrix<Double>, Matrix<Double>) -> Matrix<Double>): Matrix<Double> {
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

    private fun _pade3(A: Matrix<Double>): Pair<Matrix<Double>, Matrix<Double>> {
        val b = koma.mat[120, 60, 12, 1]
        val ident = A.getFactory().eye(A.numRows(), A.numCols())

        val A2 = A * A
        val U = A * (A2 * b[3] + ident * b[1])
        val V = A2 * b[2] + ident * b[0]

        return Pair(U, V)
    }

    private fun _pade5(A: Matrix<Double>): Pair<Matrix<Double>, Matrix<Double>> {
        val b = koma.mat[30240, 15120, 3360, 420, 30, 1]
        val ident = A.getFactory().eye(A.numRows(), A.numCols())
        val A2 = A * A
        val A4 = A2 * A2
        val U = A * (A4 * b[5] + A2 * b[3] + ident * b[1])
        val V = A4 * b[4] + A2 * b[2] + ident * b[0]
        return Pair(U, V)

    }

    private fun _pade7(A: Matrix<Double>): Pair<Matrix<Double>, Matrix<Double>> {
        val b = koma.mat[17297280, 8648640, 1995840, 277200, 25200, 1512, 56, 1]
        val ident = A.getFactory().eye(A.numRows(), A.numCols())
        val A2 = A * A
        val A4 = A2 * A2
        val A6 = A4 * A2
        val U = A * (A6 * b[7] + A4 * b[5] + A2 * b[3] + ident * b[1])
        val V = A6 * b[6] + A4 * b[4] + A2 * b[2] + ident * b[0]
        return Pair(U, V)
    }

    private fun _pade9(A: Matrix<Double>): Pair<Matrix<Double>, Matrix<Double>> {
        val b = koma.mat[17643225600, 8821612800, 2075673600, 302702400, 30270240,
                2162160, 110880, 3960, 90, 1]
        val ident = A.getFactory().eye(A.numRows(), A.numCols())
        val A2 = A * A
        val A4 = A2 * A2
        val A6 = A4 * A2
        val A8 = A6 * A2
        val U = A * (A8 * b[9] + A6 * b[7] + A4 * b[5] + A2 * b[3] + ident * b[1])
        val V = A8 * b[8] + A6 * b[6] + A4 * b[4] + A2 * b[2] + ident * b[0]
        return Pair(U, V)
    }

    private fun _pade13(A: Matrix<Double>): Pair<Matrix<Double>, Matrix<Double>> {
        val b = koma.mat[64764752532480000, 32382376266240000, 7771770303897600,
                1187353796428800, 129060195264000, 10559470521600, 670442572800,
                33522128640, 1323241920, 40840800, 960960, 16380, 182, 1]
        val ident = A.getFactory().eye(A.numRows(), A.numCols())

        val A2 = A * A
        val A4 = A2 * A2
        val A6 = A4 * A2
        val U = A * (A6 * (A6 * b[13] + A4 * b[11] + A2 * b[9]) + A6 * b[7] + A4 * b[5] + A2 * b[3] + ident * b[1])
        val V = A6 * (A6 * b[12] + A4 * b[10] + A2 * b[8]) + A6 * b[6] + A4 * b[4] + A2 * b[2] + ident * b[0]
        return Pair(U, V)
    }


}
