package koma.matrix.default

import koma.*
import koma.matrix.*
import koma.extensions.*

class Default${dtype}MatrixFactory: MatrixFactory<Matrix<${dtype}>> {
    override fun zeros(rows: Int, cols: Int) 
            = Default${dtype}Matrix(rows, cols)

    override fun create(data: IntRange): Matrix<${dtype}> {
        val input = data.map { it.to${dtype}() }
        val out = Default${dtype}Matrix(1, input.size)
        input.forEachIndexed { idx, ele -> out[idx] = ele }
        return out
    }    
    override fun create(data: DoubleArray): Matrix<${dtype}> {
        val out = Default${dtype}Matrix(1, data.size)
        data.forEachIndexed { idx, ele -> out[idx] = ele.to${dtype}() }
        return out
    }

    override fun create(data: Array<DoubleArray>): Matrix<${dtype}> {
        val out = Default${dtype}Matrix(data.size, data[0].size)
        data.forEachIndexed { rowIdx, row ->
            row.forEachIndexed { colIdx, ele -> 
                out[rowIdx, colIdx] = ele.to${dtype}()
            }
        }
        return out
    }

    override fun ones(rows: Int, cols: Int): Matrix<${dtype}>
            = zeros(rows, cols).fill {_,_-> 1.to${dtype}()}

    override fun eye(size: Int): Matrix<${dtype}> 
            = eye(size, size)
    override fun eye(rows: Int, cols: Int): Matrix<${dtype}>
            = zeros(rows, cols)
            .fill {row,col->if (row==col) 1.to${dtype}() else 0.to${dtype}() }


    override fun rand(rows: Int, cols: Int): Matrix<${dtype}>
            = zeros(rows, cols)
            .fill { _, _ -> koma.platformsupport.rng.nextDouble().to${dtype}()}
    

    override fun randn(rows: Int, cols: Int): Matrix<${dtype}>
            = zeros(rows, cols)
            .fill { _, _ -> koma.platformsupport.rng.nextGaussian().to${dtype}()}
    

    override fun arange(start: Double, stop: Double, increment: Double): Matrix<${dtype}> {
        TODO("not implemented") 
    }

    override fun arange(start: Double, stop: Double): Matrix<${dtype}> {
        TODO("not implemented") 
    }

    override fun arange(start: Int, stop: Int, increment: Int): Matrix<${dtype}> {
        TODO("not implemented") 
    }

    override fun arange(start: Int, stop: Int): Matrix<${dtype}> {
        TODO("not implemented") 
    }
}
