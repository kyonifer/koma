package golem.matrix.jblas

import golem.matrix.jblas.backend.*
import golem.matrix.Matrix
import golem.matrix.MatrixFactory
import org.jblas.DoubleMatrix

class JBlasMatrix(var storage: DoubleMatrix): Matrix<Double>
{
    override fun normIndP1(): Double {
        throw UnsupportedOperationException()
    }

    override fun normF(): Double {
        throw UnsupportedOperationException()
    }

    override fun getDoubleData(): DoubleArray {
        throw UnsupportedOperationException()
    }

    override fun copy(): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun epow(other: Double): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun epow(other: Int): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun pow(exponent: Int): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun mod(other: Matrix<Double>): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun transpose(): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun div(other: Int): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun div(other: Double): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun times(other: Matrix<Double>): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun times(other: Double): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun elementTimes(other: Matrix<Double>): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun minus(): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun minus(other: Double): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun minus(other: Matrix<Double>): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun plus(other: Double): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun plus(other: Matrix<Double>): Matrix<Double> {
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

    override fun getRow(row: Int): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun getCol(col: Int): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun setCol(index: Int, col: Matrix<Double>) {
        throw UnsupportedOperationException()
    }

    override fun setRow(index: Int, row: Matrix<Double>) {
        throw UnsupportedOperationException()
    }

    override fun chol(): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun LU(): Triple<Matrix<Double>, Matrix<Double>, Matrix<Double>> {
        throw UnsupportedOperationException()
    }

    override fun QR(): Pair<Matrix<Double>, Matrix<Double>> {
        throw UnsupportedOperationException()
    }

    override fun expm(): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun solve(A: Matrix<Double>, B: Matrix<Double>): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun inv(): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun det(): Double {
        throw UnsupportedOperationException()
    }

    override fun pinv(): Matrix<Double> {
        throw UnsupportedOperationException()
    }

    override fun elementSum(): Double {
        throw UnsupportedOperationException()
    }

    override fun diag(): Matrix<Double> {
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

    override fun getFactory(): MatrixFactory<Matrix<Double>> {
        throw UnsupportedOperationException()
    }

    override fun repr(): String {
        throw UnsupportedOperationException()
    }


}