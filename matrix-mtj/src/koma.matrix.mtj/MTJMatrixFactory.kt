package koma.matrix.mtj

import koma.*
import koma.matrix.common.*
import koma.util.*
import no.uib.cipr.matrix.DenseMatrix
import java.util.*


class MTJMatrixFactory : DoubleFactoryBase<MTJMatrix>() {
    override fun zeros(rows: Int, cols: Int) = MTJMatrix(DenseMatrix(rows, cols))

    override fun create(data: IntRange): MTJMatrix {
        val dataArray = fromCollection(data.map { it.toDouble() })
        return MTJMatrix(DenseMatrix(1, dataArray.size, dataArray, false))
    }

    override fun create(data: DoubleArray): MTJMatrix {
        return MTJMatrix(DenseMatrix(1, data.size, data, false))
    }

    override fun create(data: Array<DoubleArray>): MTJMatrix {
        return MTJMatrix(DenseMatrix(data))
    }

    override fun ones(rows: Int, cols: Int): MTJMatrix {
        val out = DenseMatrix(rows, cols)
        Arrays.fill(out.data, 1.0)
        return MTJMatrix(out)
    }

    override fun eye(size: Int) = eye(size, size)

    override fun eye(rows: Int, cols: Int): MTJMatrix {
        val out = DenseMatrix(rows, cols)
        for (i in 0..rows - 1)
            out[i, i] = 1.0
        return MTJMatrix(out)
    }

}