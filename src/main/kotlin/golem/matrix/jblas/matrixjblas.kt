package golem.matrix.jblas

import golem.matrix.jblas.backend.*
import golem.matrix.Matrix
import golem.matrix.MatrixFactory
import org.jblas.DoubleMatrix

class MatrixJBlas(var storage: DoubleMatrix): Matrix<MatrixJBlas, Double>
{
    override fun setCol(index: Int, col: MatrixJBlas) {
        throw UnsupportedOperationException()
    }

    override fun setRow(index: Int, row: MatrixJBlas) {
        throw UnsupportedOperationException()
    }

    override fun expm(): MatrixJBlas {
        throw UnsupportedOperationException()
    }

    override fun solve(A: MatrixJBlas, B: MatrixJBlas): MatrixJBlas {
        throw UnsupportedOperationException()
    }

    override fun getFactory(): MatrixFactory<MatrixJBlas> {
        throw UnsupportedOperationException()
    }

    override fun elementTimes(other: MatrixJBlas): MatrixJBlas {
        throw UnsupportedOperationException()
    }

    override fun mod(other: MatrixJBlas): MatrixJBlas {
        throw UnsupportedOperationException()
    }

    override fun transpose(): MatrixJBlas {
        throw UnsupportedOperationException()
    }

    override fun div(other: Int): MatrixJBlas {
        throw UnsupportedOperationException()
    }

    override fun div(other: Double): MatrixJBlas {
        throw UnsupportedOperationException()
    }

    override fun times(other: MatrixJBlas): MatrixJBlas {
        throw UnsupportedOperationException()
    }

    override fun times(other: Double): MatrixJBlas {
        throw UnsupportedOperationException()
    }

    override fun minus(): MatrixJBlas {
        throw UnsupportedOperationException()
    }

    override fun minus(other: Double): MatrixJBlas {
        throw UnsupportedOperationException()
    }

    override fun minus(other: MatrixJBlas): MatrixJBlas {
        throw UnsupportedOperationException()
    }

    override fun plus(other: Double): MatrixJBlas {
        throw UnsupportedOperationException()
    }

    override fun plus(other: MatrixJBlas): MatrixJBlas {
        throw UnsupportedOperationException()
    }

    override fun numRows(): Int {
        throw UnsupportedOperationException()
    }

    override fun numCols(): Int {
        throw UnsupportedOperationException()
    }

    override fun set(i: Int, v: Double) {
        throw UnsupportedOperationException()
    }

    override fun set(i: Int, j: Int, v: Double) {
        throw UnsupportedOperationException()
    }

    override fun get(i: Int, j: Int): Double {
        throw UnsupportedOperationException()
    }

    override fun get(i: Int): Double {
        throw UnsupportedOperationException()
    }

    override fun getInt(i: Int, j: Int): Int {
        throw UnsupportedOperationException()
    }

    override fun getDouble(i: Int, j: Int): Double {
        throw UnsupportedOperationException()
    }

    override fun getFloat(i: Int, j: Int): Float {
        throw UnsupportedOperationException()
    }

    override fun getInt(i: Int): Int {
        throw UnsupportedOperationException()
    }

    override fun getDouble(i: Int): Double {
        throw UnsupportedOperationException()
    }

    override fun getFloat(i: Int): Float {
        throw UnsupportedOperationException()
    }

    override fun setInt(i: Int, v: Int) {
        throw UnsupportedOperationException()
    }

    override fun setDouble(i: Int, v: Double) {
        throw UnsupportedOperationException()
    }

    override fun setFloat(i: Int, v: Float) {
        throw UnsupportedOperationException()
    }

    override fun setInt(i: Int, j: Int, v: Int) {
        throw UnsupportedOperationException()
    }

    override fun setDouble(i: Int, j: Int, v: Double) {
        throw UnsupportedOperationException()
    }

    override fun setFloat(i: Int, j: Int, v: Float) {
        throw UnsupportedOperationException()
    }

    override fun getRow(row: Int): MatrixJBlas {
        throw UnsupportedOperationException()
    }

    override fun getCol(col: Int): MatrixJBlas {
        throw UnsupportedOperationException()
    }

    override fun chol(): MatrixJBlas {
        throw UnsupportedOperationException()
    }

    override fun LU(): Pair<MatrixJBlas, MatrixJBlas> {
        throw UnsupportedOperationException()
    }

    override fun QR(): Pair<MatrixJBlas, MatrixJBlas> {
        throw UnsupportedOperationException()
    }

    override fun inv(): MatrixJBlas {
        throw UnsupportedOperationException()
    }

    override fun det(): Double {
        throw UnsupportedOperationException()
    }

    override fun pinv(): MatrixJBlas {
        throw UnsupportedOperationException()
    }

    override fun normf(): Double {
        throw UnsupportedOperationException()
    }

    override fun elementSum(): Double {
        throw UnsupportedOperationException()
    }

    override fun diag(): MatrixJBlas {
        throw UnsupportedOperationException()
    }

    override fun cumsum(): Double {
        throw UnsupportedOperationException()
    }

    override fun max(): Double {
        throw UnsupportedOperationException()
    }

    override fun mean(): Double {
        throw UnsupportedOperationException()
    }

    override fun min(): Double {
        throw UnsupportedOperationException()
    }

    override fun argMax(): Double {
        throw UnsupportedOperationException()
    }

    override fun argMean(): Double {
        throw UnsupportedOperationException()
    }

    override fun argMin(): Double {
        throw UnsupportedOperationException()
    }

    override fun norm(): Double {
        throw UnsupportedOperationException()
    }

    override fun trace(): Double {
        throw UnsupportedOperationException()
    }

    override fun repr(): String {
        throw UnsupportedOperationException()
    }

}