package golem.matrix.purekt

import golem.matrix.*

class DoublePureKtMatrix (val rows: Int, 
                          val cols: Int): Matrix<Double> {
    val storage = DoubleArray(rows*cols)

    override fun rem(other: Matrix<Double>): Matrix<Double> 
            = elementTimes(other)
    override fun div(other: Int): Matrix<Double> 
            = this.mapMatIndexed { row, col, ele -> ele/other}

    override fun div(other: Double): Matrix<Double>
            = this.mapMatIndexed { row, col, ele -> ele/other}


    override fun times(other: Matrix<Double>): Matrix<Double> {
        val out = getFactory().zeros(this.numRows(), other.numCols())
        out.eachIndexed { row, col, _ ->
            for (cursor in 0 until this.numCols())
                out[row,col] += this[row, cursor]*other[cursor, col]
        }
        return out
    }
    
    override fun times(other: Double): Matrix<Double> 
            = this.mapMat { it*other }

    override fun unaryMinus(): Matrix<Double> 
            = this.mapMat { it*-1 }

    override fun minus(other: Double): Matrix<Double>
            = this.mapMat { it - other }
    
    override fun minus(other: Matrix<Double>): Matrix<Double> 
            = this.mapMatIndexed { row, col, ele -> ele - other[row,col] }

    override fun plus(other: Double): Matrix<Double> 
            = this.mapMat { it + other }

    override fun plus(other: Matrix<Double>): Matrix<Double> 
            = this.mapMatIndexed { row, col, ele -> ele + other[row,col] }

    override fun transpose(): Matrix<Double> 
            = getFactory()
            .zeros(numCols(),numRows())
            .fill { row, col -> this[col,row] }

    override fun elementTimes(other: Matrix<Double>): Matrix<Double> 
            = this.mapMatIndexed { row, col, ele -> ele*other[row,col] }


    override fun epow(other: Double): Matrix<Double> 
            = this.mapMatIndexed { row, col, ele -> Math.pow(ele, other.toDouble()) }
    override fun epow(other: Int): Matrix<Double>
            = this.mapMatIndexed { row, col, ele -> Math.pow(ele, other.toDouble()) }

    override fun numRows(): Int = this.rows
    override fun numCols(): Int = this.cols

    override fun set(i: Int, v: Double) {
        storage[i] = v
    }
    override fun set(i: Int, j: Int, v: Double) {
        checkBounds(i,j)
        storage[this.cols*i+j] = v
    }

    override fun get(i: Int, j: Int): Double 
            = storage[this.cols*i+j].also { checkBounds(i,j) }
    
    override fun get(i: Int): Double 
            = storage[i]
    
    override fun copy(): Matrix<Double> 
            = this.mapMat { it }
    
    
    override fun getInt(i: Int, j: Int): Int = this[i,j].toInt()
    override fun getDouble(i: Int, j: Int): Double = this[i,j].toDouble()
    override fun getFloat(i: Int, j: Int): Float = this[i,j].toFloat()
    override fun getInt(i: Int): Int = this[i].toInt()
    override fun getDouble(i: Int): Double = this[i].toDouble()
    override fun getFloat(i: Int): Float = this[i].toFloat()
    override fun setInt(i: Int, v: Int) { this[i] = v.toDouble()}
    override fun setDouble(i: Int, v: Double) { this[i] = v.toDouble()}
    override fun setFloat(i: Int, v: Float) { this[i] = v.toDouble()}
    override fun setInt(i: Int, j: Int, v: Int) { this[i,j] = v.toDouble()}
    override fun setDouble(i: Int, j: Int, v: Double) { this[i,j] = v.toDouble()}
    override fun setFloat(i: Int, j: Int, v: Float) { this[i,j] = v.toDouble()}
    override fun getDoubleData(): DoubleArray = storage
    override fun getRow(row: Int): Matrix<Double> {
        checkBounds(row, 0)
        val out = getFactory().zeros(1,cols)
        for (i in 0 until cols)
            out[i] = this[row, i]
        return out
    }
    override fun getCol(col: Int): Matrix<Double> {
        checkBounds(0,col)
        val out = getFactory().zeros(rows,1)
        for (i in 0 until rows)
            out[i] = this[i, col]
        return out
    }

    override fun setCol(index: Int, col: Matrix<Double>) {
        checkBounds(0,index)
        for (i in 0 until rows)
            this[i, index] = col[i]
    }

    override fun setRow(index: Int, row: Matrix<Double>) {
        checkBounds(index, 0)
        for (i in 0 until cols)
            this[index, i] = row[i]
    }

    override fun chol(): Matrix<Double> {
        TODO("not implemented")
    }

    override fun LU(): Triple<Matrix<Double>, Matrix<Double>, Matrix<Double>> {
        TODO("not implemented")
    }

    override fun QR(): Pair<Matrix<Double>, Matrix<Double>> {
        TODO("not implemented")
    }

    override fun expm(): Matrix<Double> {
        TODO("not implemented")
    }

    override fun solve(A: Matrix<Double>, B: Matrix<Double>): Matrix<Double> {
        TODO("not implemented")
    }

    override fun inv(): Matrix<Double> {
        TODO("not implemented")
    }

    override fun det(): Double {
        TODO("not implemented")
    }

    override fun pinv(): Matrix<Double> {
        TODO("not implemented")
    }

    override fun normF(): Double {
        TODO("not implemented")
    }

    override fun normIndP1(): Double {
        TODO("not implemented")
    }

    override fun elementSum(): Double {
        TODO("not implemented")
    }

    override fun diag(): Matrix<Double> {
        TODO("not implemented")
    }

    override fun max(): Double {
        TODO("not implemented")
    }

    override fun mean(): Double {
        TODO("not implemented")
    }

    override fun min(): Double {
        TODO("not implemented")
    }

    override fun argMax(): Int {
        TODO("not implemented")
    }

    override fun argMin(): Int {
        TODO("not implemented")
    }

    override fun norm(): Double {
        TODO("not implemented")
    }

    override fun trace(): Double {
        TODO("not implemented")
    }

    override fun T(): Matrix<Double> 
            = this.transpose()
    override fun getBaseMatrix(): Any 
            = storage
    override fun getFactory(): MatrixFactory<Matrix<Double>> 
            = DoublePureKtMatrixFactory()
    
    private fun checkBounds(row: Int, col: Int) {
        if (row >= rows || col >= cols)
            throw IllegalArgumentException("row/col index out of bounds")
    }
}