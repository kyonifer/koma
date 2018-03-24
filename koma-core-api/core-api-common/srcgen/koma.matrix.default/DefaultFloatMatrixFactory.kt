package koma.matrix.default

import koma.*
import koma.matrix.*
import koma.extensions.*

class DefaultFloatMatrixFactory: MatrixFactory<Matrix<Float>> {
    override fun zeros(rows: Int, cols: Int) 
            = DefaultFloatMatrix(rows, cols)

    override fun create(data: IntRange): Matrix<Float> {
        val input = data.map { it.toFloat() }
        val out = DefaultFloatMatrix(1, input.size)
        input.forEachIndexed { idx, ele -> out[idx] = ele }
        return out
    }    
    override fun create(data: DoubleArray): Matrix<Float> {
        val out = DefaultFloatMatrix(1, data.size)
        data.forEachIndexed { idx, ele -> out[idx] = ele.toFloat() }
        return out
    }

    override fun create(data: Array<DoubleArray>): Matrix<Float> {
        val out = DefaultFloatMatrix(data.size, data[0].size)
        data.forEachIndexed { rowIdx, row ->
            row.forEachIndexed { colIdx, ele -> 
                out[rowIdx, colIdx] = ele.toFloat()
            }
        }
        return out
    }

    override fun ones(rows: Int, cols: Int): Matrix<Float>
            = zeros(rows, cols).fill {_,_-> 1.toFloat()}

    override fun eye(size: Int): Matrix<Float> 
            = eye(size, size)
    override fun eye(rows: Int, cols: Int): Matrix<Float>
            = zeros(rows, cols)
            .fill {row,col->if (row==col) 1.toFloat() else 0.toFloat() }


    override fun rand(rows: Int, cols: Int): Matrix<Float>
            = zeros(rows, cols)
            .fill { _, _ -> koma.internal.rng.nextDouble().toFloat()}
    

    override fun randn(rows: Int, cols: Int): Matrix<Float>
            = zeros(rows, cols)
            .fill { _, _ -> koma.internal.rng.nextGaussian().toFloat()}
    

    override fun arange(start: Double, stop: Double, increment: Double): Matrix<Float> {
        TODO("not implemented") 
    }

    override fun arange(start: Double, stop: Double): Matrix<Float> {
        TODO("not implemented") 
    }

    override fun arange(start: Int, stop: Int, increment: Int): Matrix<Float> {
        TODO("not implemented") 
    }

    override fun arange(start: Int, stop: Int): Matrix<Float> {
        TODO("not implemented") 
    }
}
