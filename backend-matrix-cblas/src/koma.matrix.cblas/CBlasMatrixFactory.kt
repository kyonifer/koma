package koma.matrix.cblas

import koma.matrix.common.DoubleFactoryBase
import koma.*
import koma.extensions.*
import koma.DEPRECATE_IMPLICIT_2D
import koma.util.fromCollection
import koma.platformsupport.rng

class CBlasMatrixFactory: DoubleFactoryBase<CBlasMatrix>() {

    override fun zeros(rows: Int, cols: Int)
        = CBlasMatrix(rows, cols)

    @Deprecated(DEPRECATE_IMPLICIT_2D, ReplaceWith("zeros(size, size)"))
    override fun zeros(size: Int) = zeros(size, size)

    override fun create(data: IntRange): CBlasMatrix {
        val dataArray = fromCollection(data.map { it.toDouble() })
        return CBlasMatrix(1, dataArray.size).also {
            dataArray.forEachIndexed { idx, value ->
                it[idx] = value
            }
        }
    }

    override fun create(data: DoubleArray): CBlasMatrix {
        return CBlasMatrix(1, data.size).also {
            data.forEachIndexed { idx, value ->
                it[idx] = value
            }
        }
    }

    override fun create(data: Array<DoubleArray>): CBlasMatrix {
        return CBlasMatrix(data.size, data[0].size).also {
            for (row in 0 until data.size)
                for (col in 0 until data[0].size)
                    it[row, col] = data[row][col]
        }
    }

    override fun ones(rows: Int, cols: Int)
        = CBlasMatrix(rows, cols).also {
            it.forEachIndexed { row, col, ele ->
                it[row, col] = 1.0
            }
        }

    @Deprecated(DEPRECATE_IMPLICIT_2D, ReplaceWith("ones(size, size)"))
    override fun ones(size: Int): CBlasMatrix {
        return ones(size, size)
    }

    override fun eye(size: Int)
        = eye(size, size)

    override fun eye(rows: Int, cols: Int)
        = CBlasMatrix(rows, cols).also {
            it.forEachIndexed { row, col, _ ->
                if(row==col)
                    it[row, col] = 1
                else
                    it[row, col] = 0
            }
        }

    @Deprecated(DEPRECATE_IMPLICIT_2D, ReplaceWith("rand(size, size)"))
    override fun rand(size: Int): CBlasMatrix {
        return rand(size, size)
    }

    override fun rand(rows: Int, cols: Int): CBlasMatrix 
        = zeros(rows, cols).also {
            it.forEachIndexed { row, col, _ ->
                it[row, col] = rng.nextDouble()
            }
        }

    override fun rand(rows: Int, cols: Int, seed: Long): CBlasMatrix {
        rng.setSeed(seed.toInt())
        return rand(rows, cols)
    }

    @Deprecated(DEPRECATE_IMPLICIT_2D, ReplaceWith("randn(size, size)"))
    override fun randn(size: Int): CBlasMatrix {
        return randn(size, size)
    }

    override fun randn(rows: Int, cols: Int): CBlasMatrix 
        = zeros(rows, cols).also {
            it.forEachIndexed { row, col, _ ->
                it[row, col] = rng.nextGaussian()
            }
        }

    override fun randn(rows: Int, cols: Int, seed: Long): CBlasMatrix {
        rng.setSeed(seed.toInt())
        return randn(rows, cols)
    }

}
