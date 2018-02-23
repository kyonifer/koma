package koma.matrix.default

import koma.*
import koma.matrix.*
import koma.extensions.*

class DefaultLongMatrixFactory: MatrixFactory<Matrix<Long>> {
    override fun zeros(rows: Int, cols: Int) 
            = DefaultLongMatrix(rows, cols)

    override fun create(data: IntRange): Matrix<Long> {
        val input = data.map { it.toLong() }
        val out = DefaultLongMatrix(1, input.size)
        input.forEachIndexed { idx, ele -> out[idx] = ele }
        return out
    }    
    override fun create(data: DoubleArray): Matrix<Long> {
        val out = DefaultLongMatrix(1, data.size)
        data.forEachIndexed { idx, ele -> out[idx] = ele.toLong() }
        return out
    }

    override fun create(data: Array<DoubleArray>): Matrix<Long> {
        val out = DefaultLongMatrix(data.size, data[0].size)
        data.forEachIndexed { rowIdx, row ->
            row.forEachIndexed { colIdx, ele -> 
                out[rowIdx, colIdx] = ele.toLong()
            }
        }
        return out
    }

    override fun ones(rows: Int, cols: Int): Matrix<Long>
            = zeros(rows, cols).fill {_,_-> 1.toLong()}

    override fun eye(size: Int): Matrix<Long> 
            = eye(size, size)
    override fun eye(rows: Int, cols: Int): Matrix<Long>
            = zeros(rows, cols)
            .fill {row,col->if (row==col) 1.toLong() else 0.toLong() }


    override fun rand(rows: Int, cols: Int): Matrix<Long>
            = zeros(rows, cols)
            .fill { _, _ -> koma.internal.rng.nextDouble().toLong()}
    

    override fun randn(rows: Int, cols: Int): Matrix<Long>
            = zeros(rows, cols)
            .fill { _, _ -> koma.internal.rng.nextGaussian().toLong()}
    

    override fun arange(start: Double, stop: Double, increment: Double): Matrix<Long> {
        TODO("not implemented") 
    }

    override fun arange(start: Double, stop: Double): Matrix<Long> {
        TODO("not implemented") 
    }

    override fun arange(start: Int, stop: Int, increment: Int): Matrix<Long> {
        TODO("not implemented") 
    }

    override fun arange(start: Int, stop: Int): Matrix<Long> {
        TODO("not implemented") 
    }
}
