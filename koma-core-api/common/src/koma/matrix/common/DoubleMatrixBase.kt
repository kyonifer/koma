@file:KomaJvmName("Algorithms")

package koma.matrix.common

import koma.ceil
import koma.extensions.*
import koma.logb
import koma.matrix.*
import koma.pow
import koma.zeros
import koma.internal.KomaJvmName

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
        for (i in 0 until numCols()) {
            this[index, i] = row[0, i]
        }
    }
    override fun setCol(index: Int, col: Matrix<Double>) {
        for (i in 0 until numRows()) {
            this[i, index] = col[i, 0]
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

    override fun maxInternal(): Double {
        var max = Double.NEGATIVE_INFINITY
        this.forEach { ele ->
            if (ele > max) {
                max = ele
            }
        }
        return max
    }
    override fun mean() = elementSum() / (numCols() * numRows())
    override fun minInternal(): Double {
        var min = Double.POSITIVE_INFINITY
        this.forEach { ele ->
            if (ele < min) {
                min = ele
            }
        }
        return min
    }
    override fun argMaxInternal(): Int { 
        var max = 0 
        for (i in 0 until this.numCols() * this.numRows())
            if (this[i] > this[max]) 
                max = i 
        return max 
    }
    override fun argMinInternal(): Int { 
        var min = 0 
        for (i in 0 until this.numCols() * this.numRows())
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
    override fun expm(): Matrix<Double> = koma.internal.ports.expm(this)
}
