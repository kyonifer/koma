package golem.matrix.mtj

import golem.matrix.Matrix
import golem.matrix.MatrixFactory
import golem.util.fromCollection
import golem.matrix.mtj.backend.set
import no.uib.cipr.matrix.DenseMatrix
import java.util.*


public class MTJMatrixFactory : MatrixFactory<Matrix<Double>> {
    override fun zeros(rows: Int, cols: Int) = MTJMatrix(DenseMatrix(rows, cols))

    override fun zeros(size: Int) = zeros(size, size)

    override fun create(data: IntRange): MTJMatrix {
        var dataArray = fromCollection(data.map { it.toDouble() })
        return MTJMatrix(DenseMatrix(1, dataArray.size, dataArray, false))
    }

    override fun create(data: DoubleRange): MTJMatrix {
        var dataArray = fromCollection(data.toList())
        return MTJMatrix(DenseMatrix(1, dataArray.size, dataArray, false))
    }

    override fun create(data: DoubleProgression): MTJMatrix {
        var dataArray = fromCollection(data.toList())
        return MTJMatrix(DenseMatrix(1, dataArray.size, dataArray, false))
    }

    override fun create(data: DoubleArray): MTJMatrix {
        return MTJMatrix(DenseMatrix(1, data.size, data, false))
    }

    override fun create(data: Array<DoubleArray>): MTJMatrix {
        return MTJMatrix(DenseMatrix(data))
    }

    override fun ones(size: Int) = ones(size, size)

    override fun ones(rows: Int, cols: Int): MTJMatrix {
        var out = DenseMatrix(rows, cols)
        Arrays.fill(out.data, 1.0)
        return MTJMatrix(out)
    }

    override fun eye(size: Int) = eye(size, size)

    override fun eye(rows: Int, cols: Int): MTJMatrix {
        var out = DenseMatrix(rows, cols)
        for (i in 0..rows - 1)
            out[i, i] = 1.0
        return MTJMatrix(out)
    }

    override fun rand(size: Int) = rand(size, size)
    override fun rand(rows: Int, cols: Int) = MTJMatrix(golem.matrix.mtj.backend.rand(rows, cols))
    override fun rand(rows: Int, cols: Int, seed: Long)= MTJMatrix(golem.matrix.mtj.backend.rand(rows, cols, seed))

    override fun randn(size: Int) = randn(size, size)
    override fun randn(rows: Int, cols: Int) = MTJMatrix(golem.matrix.mtj.backend.randn(rows, cols))
    override fun randn(rows: Int, cols: Int, seed: Long) = MTJMatrix(golem.matrix.mtj.backend.randn(rows, cols, seed))
    override fun arange(start: Double, stop: Double, increment: Double): MTJMatrix {
        val shape = ((stop - start) / increment).toInt()
        if (shape <= 0)
            throw Exception("Invalid Range due to bounds/step")
        var doubleProg: DoubleProgression
        var dataArray: DoubleArray
        if (increment > 0) {
            doubleProg = (start..(stop)).step(increment)
            dataArray = fromCollection(doubleProg.toList())
        } else {
            doubleProg = (start downTo (stop)).step(java.lang.Math.abs(increment))
            dataArray = fromCollection(doubleProg.toList())
        }
        return create(dataArray.sliceArray(0..(dataArray.size - 2)))
    }

    override fun arange(start: Double, stop: Double): MTJMatrix {
        return arange(start, stop, 1.0 * java.lang.Math.signum(stop - start))
    }

    override fun arange(start: Int, stop: Int, increment: Int): MTJMatrix {
        return arange(start.toDouble(), stop.toDouble(), increment.toDouble())
    }

    override fun arange(start: Int, stop: Int): MTJMatrix {
        val inc = 1.0 * java.lang.Math.signum(stop.toDouble() - start.toDouble())
        return arange(start.toDouble(), stop.toDouble(), inc)
    }
}