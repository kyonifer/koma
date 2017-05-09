package koma.matrix.default

import koma.matrix.*
import koma.platformsupport.*
import koma.polyfill.*

class Default${dtype}Matrix (val rows: Int, 
                          val cols: Int): ${dtype}Matrix() {
    val storage = ${dtype}Array(rows*cols)

    override fun rem(other: ${dtype}Matrix): ${dtype}Matrix 
            = elementTimes(other)
    
    override fun div(other: ${dtype}): ${dtype}Matrix
            = this.mapMatIndexed { row, col, ele -> ele/other}
    
    override fun times(other: ${dtype}Matrix): ${dtype}Matrix {
        val out = getFactory().zeros(this.numRows(), other.numCols())
        out.eachIndexed { row, col, _ ->
            for (cursor in 0 until this.numCols())
                out[row,col] += this[row, cursor]*other[cursor, col]
        }
        return out
    }
    
    override fun times(other: ${dtype}): ${dtype}Matrix 
            = this.mapMat { it*other }

    override fun unaryMinus(): ${dtype}Matrix 
            = this.mapMat { it*-1 }

    override fun minus(other: ${dtype}): ${dtype}Matrix
            = this.mapMat { it - other }
    
    override fun minus(other: ${dtype}Matrix): ${dtype}Matrix 
            = this.mapMatIndexed { row, col, ele -> ele - other[row,col] }

    override fun plus(other: ${dtype}): ${dtype}Matrix 
            = this.mapMat { it + other }

    override fun plus(other: ${dtype}Matrix): ${dtype}Matrix 
            = this.mapMatIndexed { row, col, ele -> ele + other[row,col] }

    override fun transpose(): ${dtype}Matrix 
            = getFactory()
            .zeros(numCols(),numRows())
            .fill { row, col -> this[col,row] }

    override fun elementTimes(other: ${dtype}Matrix): ${dtype}Matrix 
            = this.mapMatIndexed { row, col, ele -> ele*other[row,col] }

    ${epow}
    
    override fun epow(other: Int): ${dtype}Matrix
            = this.mapMatIndexed { row, col, ele -> Math.pow(ele.toDouble(), other.toDouble()).to${dtype}() }

    override fun numRows(): Int = this.rows
    override fun numCols(): Int = this.cols

    override fun set(i: Int, v: ${dtype}) {
        storage[i] = v
    }
    override fun set(i: Int, j: Int, v: ${dtype}) {
        checkBounds(i,j)
        storage[this.cols*i+j] = v
    }

    override fun get(i: Int, j: Int): ${dtype} {
        checkBounds(i,j)
        return storage[this.cols*i+j]
    }
    
    override fun get(i: Int): ${dtype} 
            = storage[i]
    
    override fun copy(): ${dtype}Matrix 
            = this.mapMat { it }
    
    
    override fun getDoubleData(): DoubleArray = storage.map { it.toDouble() }.toDoubleArray()
    override fun getRow(row: Int): ${dtype}Matrix {
        checkBounds(row, 0)
        val out = getFactory().zeros(1,cols)
        for (i in 0 until cols)
            out[i] = this[row, i]
        return out
    }
    override fun getCol(col: Int): ${dtype}Matrix {
        checkBounds(0,col)
        val out = getFactory().zeros(rows,1)
        for (i in 0 until rows)
            out[i] = this[i, col]
        return out
    }

    override fun setCol(index: Int, col: ${dtype}Matrix) {
        checkBounds(0,index)
        for (i in 0 until rows)
            this[i, index] = col[i]
    }

    override fun setRow(index: Int, row: ${dtype}Matrix) {
        checkBounds(index, 0)
        for (i in 0 until cols)
            this[index, i] = row[i]
    }

    override fun chol(): ${dtype}Matrix {
        TODO("not implemented")
    }

    override fun LU(): Triple<${dtype}Matrix, ${dtype}Matrix, ${dtype}Matrix> {
        TODO("not implemented")
    }

    override fun QR(): Pair<${dtype}Matrix, ${dtype}Matrix> {
        TODO("not implemented")
    }

    override fun expm(): ${dtype}Matrix {
        TODO("not implemented")
    }

    override fun solve(A: ${dtype}Matrix, B: ${dtype}Matrix): ${dtype}Matrix {
        TODO("not implemented")
    }

    override fun inv(): ${dtype}Matrix {
        TODO("not implemented")
    }

    override fun det(): ${dtype} {
        TODO("not implemented")
    }

    override fun pinv(): ${dtype}Matrix {
        TODO("not implemented")
    }

    override fun normF(): ${dtype} {
        TODO("not implemented")
    }

    override fun normIndP1(): ${dtype} {
        TODO("not implemented")
    }

    override fun elementSum(): ${dtype} 
            = this.toIterable().reduce { a, b -> a + b }

    override fun diag(): ${dtype}Matrix 
            = getFactory()
            .zeros(numRows(),1)
            .fill{ row, col -> this[row,row] }

    override fun max(): ${dtype} = this[argMax()]
    override fun mean(): ${dtype} = elementSum()/(numRows()*numCols())
    override fun min(): ${dtype} = this[argMin()]

    override fun argMax(): Int {
        var highest= ${dtype}.MIN_VALUE
        var highestIdx = -1
        for (i in 0 until numRows()*numCols())
            if(this[i] > highest) {
                highest = this[i]
                highestIdx = i
            }
        return highestIdx
    }

    override fun argMin(): Int {
        var lowest = ${dtype}.MAX_VALUE
        var lowestIdx = -1
        for (i in 0 until numRows()*numCols())
            if(this[i] < lowest) {
                lowest = this[i]
                lowestIdx = i
            }
        return lowestIdx
    }

    override fun norm(): ${dtype} {
        TODO("not implemented")
    }

    override fun trace(): ${dtype} {
        TODO("not implemented")
    }

    override fun T(): ${dtype}Matrix = this.transpose()

    override fun getBaseMatrix(): Any 
            = storage
    override fun getFactory(): MatrixFactory<${dtype}Matrix> 
            = Default${dtype}MatrixFactory()
    
    private fun checkBounds(row: Int, col: Int) {
        if (row >= rows || col >= cols)
            throw IllegalArgumentException("row/col index out of bounds")
    }
}
