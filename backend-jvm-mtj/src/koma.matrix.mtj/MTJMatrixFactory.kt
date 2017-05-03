package koma.matrix.mtj

import koma.*
import koma.matrix.common.*
import koma.util.*
import no.uib.cipr.matrix.DenseMatrix
import java.util.*


class MTJMatrixFactory : DoubleFactoryBase<MTJMatrix>() {
    override fun zeros(rows: Int, cols: Int) = MTJMatrix(DenseMatrix(rows, cols))

    @Deprecated(DEPRECATE_IMPLICIT_2D, ReplaceWith("zeros(size, size)"))
    override fun zeros(size: Int) = zeros(size, size)

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

    @Deprecated(DEPRECATE_IMPLICIT_2D, ReplaceWith("ones(size, size)"))
    override fun ones(size: Int) = ones(size, size)

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

    @Deprecated(DEPRECATE_IMPLICIT_2D, ReplaceWith("rand(size, size)"))
    override fun rand(size: Int) = rand(size, size)
    override fun rand(rows: Int, cols: Int) = MTJMatrix(koma.matrix.mtj.backend.rand(rows, cols))
    override fun rand(rows: Int, cols: Int, seed: Long) = MTJMatrix(koma.matrix.mtj.backend.rand(rows, cols, seed))

    @Deprecated(DEPRECATE_IMPLICIT_2D, ReplaceWith("randn(size, size)"))
    override fun randn(size: Int) = randn(size, size)
    override fun randn(rows: Int, cols: Int) = MTJMatrix(koma.matrix.mtj.backend.randn(rows, cols))
    override fun randn(rows: Int, cols: Int, seed: Long) = MTJMatrix(koma.matrix.mtj.backend.randn(rows, cols, seed))

}