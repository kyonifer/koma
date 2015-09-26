package golem.matrix.ejml

import golem.matrix.MatrixFactory
import golem.min
import golem.util.fromCollection
import org.ejml.simple.SimpleMatrix
import java.util.*

class MatFactory : MatrixFactory<Mat>
{
    override fun zeros(rows: Int, cols: Int) = Mat(golem.matrix.ejml.backend.zeros(rows,cols))

    override fun zeros(size: Int) = zeros(size,size)

    override fun create(data: IntRange): Mat {
        var dataArray = fromCollection(data.map { it.toDouble() })
        return Mat(SimpleMatrix(1, dataArray.size(), true, *dataArray))
    }

    override fun create(data: DoubleRange): Mat {
        var dataArray = fromCollection(data.toList())
        return Mat(SimpleMatrix(1, dataArray.size(), true, *dataArray))
    }

    override fun create(data: DoubleProgression): Mat{
        var dataArray = fromCollection(data.toList())
        return Mat(SimpleMatrix(1, dataArray.size(), true, *dataArray))
    }

    override fun create(data: DoubleArray): Mat {
        return Mat(SimpleMatrix(1, data.size(), true, *data))
    }

    override fun create(data: Array<DoubleArray>): Mat {
        return Mat(SimpleMatrix(data))
    }

    override fun ones(rows: Int, cols: Int): Mat {
        return Mat(golem.matrix.ejml.backend.ones(rows, cols))
    }

    override fun ones(size: Int): Mat {
        return ones(size, size)
    }

    override fun eye(size: Int): Mat {
        return Mat(golem.matrix.ejml.backend.eye(size))
    }

    override fun eye(rows: Int, cols: Int): Mat {
        var out = golem.matrix.ejml.backend.zeros(rows, cols)
        for (i in 0..min(rows, cols)-1)
            out[i,i]=1.0
        return Mat(out)
    }

    override fun rand(size: Int): Mat {
        return rand(size, size)
    }

    override fun rand(rows: Int, cols: Int): Mat {
        return rand(rows, cols, System.currentTimeMillis())
    }

    override fun rand(rows: Int, cols: Int, seed: Long): Mat {
        return Mat(SimpleMatrix.random(rows, cols, 0.0, 1.0, Random(seed)))
    }

    override fun randn(size: Int): Mat {
        return Mat(golem.matrix.ejml.backend.randn(size))
    }

    override fun randn(rows: Int, cols: Int): Mat {
        return Mat(golem.matrix.ejml.backend.randn(rows, cols))
    }

    override fun randn(rows: Int, cols: Int, seed: Long): Mat {
        return Mat(golem.matrix.ejml.backend.randn(rows, cols, seed))
    }

    // Todo: Add these in
    //fun linspace(...)

    override fun arange(start: Double, stop: Double, increment: Double): Mat {
        val shape = ((stop - start) / increment).toInt()
        if (shape <= 0)
            throw Exception("Invalid Range due to bounds/step")
        var doubleProg: DoubleProgression
        var dataArray: DoubleArray
        if (increment > 0){
            doubleProg = (start..(stop)).step(increment)
            dataArray = fromCollection(doubleProg.toList())
        }
        else{
            doubleProg = (start downTo (stop)).step(java.lang.Math.abs(increment))
            dataArray = fromCollection(doubleProg.toList())
        }
        return create(dataArray.sliceArray(0..(dataArray.size()-2)))
    }

    override fun arange(start: Double, stop: Double): Mat {
        return arange(start, stop, 1.0*java.lang.Math.signum(stop-start))
    }

    override fun arange(start: Int, stop: Int, increment: Int): Mat{
        return arange(start.toDouble(), stop.toDouble(), increment.toDouble())
    }

    override fun arange(start: Int, stop: Int): Mat{
        val inc = 1.0*java.lang.Math.signum(stop.toDouble()-start.toDouble())
        return arange(start.toDouble(), stop.toDouble(), inc)
    }

    //fun zeros(vararg indices : Int): T
    //fun ones(vararg indices : Int): T

}

