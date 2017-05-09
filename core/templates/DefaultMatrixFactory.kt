package koma.matrix.default

import koma.*
import koma.matrix.*

class Default${dtype}MatrixFactory: MatrixFactory<${dtype}Matrix> {
    override fun zeros(rows: Int, cols: Int) 
            = Default${dtype}Matrix(rows, cols)
    @Deprecated(DEPRECATE_IMPLICIT_2D, ReplaceWith("zeros(size, size)"))
    override fun zeros(size: Int): ${dtype}Matrix 
            = zeros(size, size)

    override fun create(data: IntRange): ${dtype}Matrix {
        val input = data.map { it.to${dtype}() }
        val out = Default${dtype}Matrix(1, input.size)
        input.forEachIndexed { idx, ele -> out[idx] = ele }
        return out
    }    
    override fun create(data: DoubleArray): ${dtype}Matrix {
        val out = Default${dtype}Matrix(1, data.size)
        data.forEachIndexed { idx, ele -> out[idx] = ele.to${dtype}() }
        return out
    }

    override fun create(data: Array<DoubleArray>): ${dtype}Matrix {
        val out = Default${dtype}Matrix(data.size, data[0].size)
        data.forEachIndexed { rowIdx, row ->
            row.forEachIndexed { colIdx, ele -> 
                out[rowIdx, colIdx] = ele.to${dtype}()
            }
        }
        return out
    }

    @Deprecated(DEPRECATE_IMPLICIT_2D, ReplaceWith("ones(size, size)"))
    override fun ones(size: Int): ${dtype}Matrix 
            = ones(size,size)
    override fun ones(rows: Int, cols: Int): ${dtype}Matrix
            = zeros(rows, cols).fill {_,_-> 1.to${dtype}()}

    override fun eye(size: Int): ${dtype}Matrix 
            = eye(size, size)
    override fun eye(rows: Int, cols: Int): ${dtype}Matrix
            = zeros(rows, cols)
            .fill {row,col->if (row==col) 1.to${dtype}() else 0.to${dtype}() }

    @Deprecated(DEPRECATE_IMPLICIT_2D, ReplaceWith("rand(size, size)"))
    override fun rand(size: Int): ${dtype}Matrix 
            = rand(size, size)
    
    override fun rand(rows: Int, cols: Int): ${dtype}Matrix
            = zeros(rows, cols)
            .fill { r,c->koma.platformsupport.rng.nextDouble().to${dtype}()}
    
    override fun rand(rows: Int, cols: Int, seed: Long): ${dtype}Matrix {
        if(koma.platformsupport.seed != seed) {
            koma.platformsupport.seed = seed
        }
        return rand(rows, cols)
    }

    @Deprecated(DEPRECATE_IMPLICIT_2D, ReplaceWith("randn(size, size)"))
    override fun randn(size: Int): ${dtype}Matrix 
            = randn(size, size)
    
    override fun randn(rows: Int, cols: Int): ${dtype}Matrix
            = zeros(rows, cols)
            .fill { r,c->koma.platformsupport.rng.nextGaussian().to${dtype}()}
    
    
    override fun randn(rows: Int, cols: Int, seed: Long): ${dtype}Matrix {
        if(koma.platformsupport.seed != seed) {
            koma.platformsupport.seed = seed
        }
        return randn(rows, cols)
    }

    override fun arange(start: Double, stop: Double, increment: Double): ${dtype}Matrix {
        TODO("not implemented") 
    }

    override fun arange(start: Double, stop: Double): ${dtype}Matrix {
        TODO("not implemented") 
    }

    override fun arange(start: Int, stop: Int, increment: Int): ${dtype}Matrix {
        TODO("not implemented") 
    }

    override fun arange(start: Int, stop: Int): ${dtype}Matrix {
        TODO("not implemented") 
    }
}
