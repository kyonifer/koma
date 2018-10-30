package koma.matrix.cblas

import koma.matrix.common.DoubleFactoryBase
import koma.extensions.*
import koma.util.fromCollection

class CBlasMatrixFactory: DoubleFactoryBase<CBlasMatrix>() {

    override fun zeros(rows: Int, cols: Int)
        = CBlasMatrix(rows, cols)

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
}
