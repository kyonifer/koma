package koma.matrix.default

import koma.*
import koma.matrix.*
import koma.extensions.*

class DefaultIntMatrixFactory: MatrixFactory<Matrix<Int>> {
    override fun zeros(rows: Int, cols: Int) 
            = DefaultIntMatrix(rows, cols)

    override fun create(data: IntRange): Matrix<Int> {
        val input = data.map { it.toInt() }
        val out = DefaultIntMatrix(1, input.size)
        input.forEachIndexed { idx, ele -> out[idx] = ele }
        return out
    }    
    override fun create(data: DoubleArray): Matrix<Int> {
        val out = DefaultIntMatrix(1, data.size)
        data.forEachIndexed { idx, ele -> out[idx] = ele.toInt() }
        return out
    }

    override fun create(data: Array<DoubleArray>): Matrix<Int> {
        val out = DefaultIntMatrix(data.size, data[0].size)
        data.forEachIndexed { rowIdx, row ->
            row.forEachIndexed { colIdx, ele -> 
                out[rowIdx, colIdx] = ele.toInt()
            }
        }
        return out
    }

    override fun ones(rows: Int, cols: Int): Matrix<Int>
            = zeros(rows, cols).fill {_,_-> 1.toInt()}

    override fun eye(size: Int): Matrix<Int> 
            = eye(size, size)
    override fun eye(rows: Int, cols: Int): Matrix<Int>
            = zeros(rows, cols)
            .fill {row,col->if (row==col) 1.toInt() else 0.toInt() }


    override fun rand(rows: Int, cols: Int): Matrix<Int>
            = zeros(rows, cols)
            .fill { _, _ -> koma.internal.getRng().nextDouble().toInt()}


    override fun randn(rows: Int, cols: Int): Matrix<Int>
            = zeros(rows, cols)
            .fill { _, _ -> koma.internal.getRng().nextGaussian().toInt()}


    override fun arange(start: Double, stop: Double, increment: Double): Matrix<Int> {
        TODO("not implemented")
    }

    override fun arange(start: Double, stop: Double): Matrix<Int> {
        TODO("not implemented")
    }

    override fun arange(start: Int, stop: Int, increment: Int): Matrix<Int> {
        TODO("not implemented")
    }

    override fun arange(start: Int, stop: Int): Matrix<Int> {
        TODO("not implemented")
    }
}
