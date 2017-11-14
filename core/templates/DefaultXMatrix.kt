package koma.matrix.default

import koma.*
import koma.matrix.*
import koma.platformsupport.*
import koma.polyfill.*

class Default${dtype}Matrix (val rows: Int, 
                          val cols: Int): Matrix<${dtype}> {
    val storage = ${dtype}Array(rows*cols)

    override fun rem(other: Matrix<${dtype}>): Matrix<${dtype}> 
            = elementTimes(other)
    
    ${div}
    
    override fun div(other: Int): Matrix<${dtype}>
            = this.mapIndexed { _, _, ele -> ele/other}


    override fun times(other: Matrix<${dtype}>): Matrix<${dtype}> {
        val out = getFactory().zeros(this.numRows(), other.numCols())
        out.forEachIndexed { row, col, _ ->
            for (cursor in 0 until this.numCols())
                out[row,col] += this[row, cursor]*other[cursor, col]
        }
        return out
    }
    
    override fun times(other: ${dtype}): Matrix<${dtype}> 
            = this.map { it*other }

    override fun unaryMinus(): Matrix<${dtype}> 
            = this.map { it*-1 }

    override fun minus(other: ${dtype}): Matrix<${dtype}>
            = this.map { it - other }
    
    override fun minus(other: Matrix<${dtype}>): Matrix<${dtype}> 
            = this.mapIndexed { row, col, ele -> ele - other[row,col] }

    override fun plus(other: ${dtype}): Matrix<${dtype}> 
            = this.map { it + other }

    override fun plus(other: Matrix<${dtype}>): Matrix<${dtype}> 
            = this.mapIndexed { row, col, ele -> ele + other[row,col] }

    override fun transpose(): Matrix<${dtype}> 
            = getFactory()
            .zeros(numCols(),numRows())
            .fill { row, col -> this[col,row] }

    override fun elementTimes(other: Matrix<${dtype}>): Matrix<${dtype}> 
            = this.mapIndexed { row, col, ele -> ele*other[row,col] }

    ${epow}
    
    override fun epow(other: Int): Matrix<${dtype}>
            = this.mapIndexed { _, _, ele -> Math.pow(ele.toDouble(), other.toDouble()).to${dtype}() }

    override fun numRows(): Int = this.rows
    override fun numCols(): Int = this.cols

    private fun setStorage(i: Int, v: ${dtype}) {
        storage[i] = v
    }
    private fun setStorage(i: Int, j: Int, v: ${dtype}) {
        checkBounds(i,j)
        storage[this.cols*i+j] = v
    }

    private fun getStorage(i: Int, j: Int): ${dtype} {
        checkBounds(i,j)
        return storage[this.cols*i+j]
    }

    private fun getStorage(i: Int): ${dtype} 
            = storage[i]
    
    override fun copy(): Matrix<${dtype}> 
            = this.map { it }
    
    
    override fun getInt(i: Int, j: Int): Int = this.getStorage(i,j).toInt()
    override fun getDouble(i: Int, j: Int): Double = this.getStorage(i,j).toDouble()
    override fun getFloat(i: Int, j: Int): Float = this.getStorage(i,j).toFloat()
    override fun getGeneric(i: Int, j: Int): ${dtype} = this.getStorage(i,j)
    override fun getInt(i: Int): Int = this.getStorage(i).toInt()
    override fun getDouble(i: Int): Double = this.getStorage(i).toDouble()
    override fun getFloat(i: Int): Float = this.getStorage(i).toFloat()
    override fun getGeneric(i: Int): ${dtype} = this.getStorage(i)
    override fun setInt(i: Int, v: Int) { this.setStorage(i, v.to${dtype}())}
    override fun setDouble(i: Int, v: Double) { this.setStorage(i, v.to${dtype}())}
    override fun setFloat(i: Int, v: Float) { this.setStorage(i, v.to${dtype}())}
    override fun setGeneric(i: Int, v: ${dtype}) { this.setStorage(i, v)}
    override fun setInt(i: Int, j: Int, v: Int) { this.setStorage(i, j, v.to${dtype}())}
    override fun setDouble(i: Int, j: Int, v: Double) { this.setStorage(i, j, v.to${dtype}())}
    override fun setFloat(i: Int, j: Int, v: Float) { this.setStorage(i, j, v.to${dtype}())}
    override fun setGeneric(i: Int, j: Int, v: ${dtype}) { this.setStorage(i, j, v)}
    override fun getDoubleData(): DoubleArray = storage.map { it.toDouble() }.toDoubleArray()
    override fun getRow(row: Int): Matrix<${dtype}> {
        checkBounds(row, 0)
        val out = getFactory().zeros(1,cols)
        for (i in 0 until cols)
            out[i] = this[row, i]
        return out
    }
    override fun getCol(col: Int): Matrix<${dtype}> {
        checkBounds(0,col)
        val out = getFactory().zeros(rows,1)
        for (i in 0 until rows)
            out[i] = this[i, col]
        return out
    }

    override fun setCol(index: Int, col: Matrix<${dtype}>) {
        checkBounds(0,index)
        for (i in 0 until rows)
            this[i, index] = col[i]
    }

    override fun setRow(index: Int, row: Matrix<${dtype}>) {
        checkBounds(index, 0)
        for (i in 0 until cols)
            this[index, i] = row[i]
    }

    override fun chol(): Matrix<${dtype}> {
        TODO("not implemented")
    }

    override fun LU(): Triple<Matrix<${dtype}>, Matrix<${dtype}>, Matrix<${dtype}>> {
        TODO("not implemented")
    }

    override fun QR(): Pair<Matrix<${dtype}>, Matrix<${dtype}>> {
        TODO("not implemented")
    }

    override fun SVD(): Triple<Matrix<${dtype}>, Matrix<${dtype}>, Matrix<${dtype}>> {
        TODO("not implemented")
    }

	override fun expm(): Matrix<${dtype}> {
        TODO("not implemented")
    }

    override fun solve(A: Matrix<${dtype}>, B: Matrix<${dtype}>): Matrix<${dtype}> {
        TODO("not implemented")
    }

    override fun inv(): Matrix<${dtype}> {
        TODO("not implemented")
    }

    override fun det(): ${dtype} {
        TODO("not implemented")
    }

    override fun pinv(): Matrix<${dtype}> {
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

    override fun diag(): Matrix<${dtype}> 
            = getFactory()
            .zeros(numRows(),1)
            .fill{ row, _ -> this[row,row] }

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

    override fun T(): Matrix<${dtype}> = this.transpose()

    override fun getBaseMatrix(): Any 
            = storage
    override fun getFactory(): MatrixFactory<Matrix<${dtype}>> 
            = Default${dtype}MatrixFactory()
    
    private fun checkBounds(row: Int, col: Int) {
        if (row >= rows || col >= cols)
            throw IllegalArgumentException("row/col index out of bounds")
    }
}
