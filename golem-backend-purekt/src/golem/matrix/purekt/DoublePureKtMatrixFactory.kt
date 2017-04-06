package golem.matrix.purekt

import golem.*
import golem.matrix.*

class DoublePureKtMatrixFactory: MatrixFactory<Matrix<Double>> {
    override fun zeros(rows: Int, cols: Int) 
            = DoublePureKtMatrix(rows, cols)
    @Deprecated(DEPRECATE_IMPLICIT_2D, ReplaceWith("zeros(size, size)"))
    override fun zeros(size: Int): Matrix<Double> 
            = zeros(size, size)

    override fun create(data: IntRange): Matrix<Double> {
        val input = data.map { it.toDouble() }
        val out = DoublePureKtMatrix(1, input.size)
        input.forEachIndexed { idx, ele -> out[idx] = ele }
        return out
    }    
    override fun create(data: DoubleArray): Matrix<Double> {
        val out = DoublePureKtMatrix(1, data.size)
        data.forEachIndexed { idx, ele -> out[idx] = ele }
        return out
    }

    override fun create(data: Array<DoubleArray>): Matrix<Double> {
        val out = DoublePureKtMatrix(data.size, data[0].size)
        data.forEachIndexed { rowIdx, row ->
            row.forEachIndexed { colIdx, ele -> 
                out[rowIdx, colIdx] = ele
            }
        }
        return out
    }

    @Deprecated(DEPRECATE_IMPLICIT_2D, ReplaceWith("ones(size, size)"))
    override fun ones(size: Int): Matrix<Double> 
            = ones(size,size)
    override fun ones(rows: Int, cols: Int): Matrix<Double>
            = zeros(rows, cols).fill {_,_->1.0}

    override fun eye(size: Int): Matrix<Double> 
            = eye(size, size)
    override fun eye(rows: Int, cols: Int): Matrix<Double>
            = zeros(rows, cols)
            .fill {row,col->if (row==col) 1.0 else 0.0 }

    @Deprecated(DEPRECATE_IMPLICIT_2D, ReplaceWith("rand(size, size)"))
    override fun rand(size: Int): Matrix<Double> 
            = rand(size, size)
    
    override fun rand(rows: Int, cols: Int): Matrix<Double>
            = zeros(rows, cols)
            .fill { r,c->golem.platformsupport.rng.nextDouble()}
    
    override fun rand(rows: Int, cols: Int, seed: Long): Matrix<Double> {
        if(golem.platformsupport.seed != seed) {
            golem.platformsupport.seed = seed
        }
        return rand(rows, cols)
    }

    @Deprecated(DEPRECATE_IMPLICIT_2D, ReplaceWith("randn(size, size)"))
    override fun randn(size: Int): Matrix<Double> 
            = randn(size, size)
    
    override fun randn(rows: Int, cols: Int): Matrix<Double>
            = zeros(rows, cols)
            .fill { r,c->golem.platformsupport.rng.nextGaussian()}
    
    
    override fun randn(rows: Int, cols: Int, seed: Long): Matrix<Double> {
        if(golem.platformsupport.seed != seed) {
            golem.platformsupport.seed = seed
        }
        return randn(rows, cols)
    }

    override fun arange(start: Double, stop: Double, increment: Double): Matrix<Double> {
        TODO("not implemented") 
    }

    override fun arange(start: Double, stop: Double): Matrix<Double> {
        TODO("not implemented") 
    }

    override fun arange(start: Int, stop: Int, increment: Int): Matrix<Double> {
        TODO("not implemented") 
    }

    override fun arange(start: Int, stop: Int): Matrix<Double> {
        TODO("not implemented") 
    }
}
