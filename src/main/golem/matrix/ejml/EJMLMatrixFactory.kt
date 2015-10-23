package golem.matrix.ejml

import golem.matrix.Matrix
import golem.matrix.MatrixFactory
import golem.min
import golem.round
import golem.util.fromCollection
import org.ejml.simple.SimpleMatrix
import java.util.*

class EJMLMatrixFactory : MatrixFactory<Matrix<Double>>
{
    override fun zeros(rows: Int, cols: Int) = EJMLMatrix(golem.matrix.ejml.backend.zeros(rows,cols))

    override fun zeros(size: Int) = zeros(size,size)

    override fun create(data: IntRange): EJMLMatrix {
        var dataArray = fromCollection(data.map { it.toDouble() })
        return EJMLMatrix(SimpleMatrix(1, dataArray.size, true, *dataArray))
    }

    override fun create(data: DoubleArray): EJMLMatrix {
        return EJMLMatrix(SimpleMatrix(1, data.size, true, *data))
    }

    override fun create(data: Array<DoubleArray>): EJMLMatrix {
        return EJMLMatrix(SimpleMatrix(data))
    }

    override fun ones(rows: Int, cols: Int): EJMLMatrix {
        return EJMLMatrix(golem.matrix.ejml.backend.ones(rows, cols))
    }

    override fun ones(size: Int): EJMLMatrix {
        return ones(size, size)
    }

    override fun eye(size: Int): EJMLMatrix {
        return EJMLMatrix(golem.matrix.ejml.backend.eye(size))
    }

    override fun eye(rows: Int, cols: Int): EJMLMatrix {
        var out = golem.matrix.ejml.backend.zeros(rows, cols)
        for (i in 0..min(rows, cols)-1)
            out[i,i]=1.0
        return EJMLMatrix(out)
    }

    override fun rand(size: Int): EJMLMatrix {
        return rand(size, size)
    }

    override fun rand(rows: Int, cols: Int): EJMLMatrix {
        return rand(rows, cols, System.currentTimeMillis())
    }

    override fun rand(rows: Int, cols: Int, seed: Long): EJMLMatrix {
        return EJMLMatrix(SimpleMatrix.random(rows, cols, 0.0, 1.0, Random(seed)))
    }

    override fun randn(size: Int): EJMLMatrix {
        return EJMLMatrix(golem.matrix.ejml.backend.randn(size))
    }

    override fun randn(rows: Int, cols: Int): EJMLMatrix {
        return EJMLMatrix(golem.matrix.ejml.backend.randn(rows, cols))
    }

    override fun randn(rows: Int, cols: Int, seed: Long): EJMLMatrix {
        return EJMLMatrix(golem.matrix.ejml.backend.randn(rows, cols, seed))
    }

    // Todo: Add these in
    //fun linspace(...)
    override fun arange(start: Double, stop: Double, increment: Double): EJMLMatrix {
        var len = round((stop-start)/increment).toInt()
        var out = this.zeros(1,len)
        for (i in 0 until len)
            out[i] = start + i*increment

        return out
    }

    override fun arange(start: Double, stop: Double): EJMLMatrix {
        return arange(start, stop, 1.0*java.lang.Math.signum(stop-start))
    }

    override fun arange(start: Int, stop: Int, increment: Int): EJMLMatrix {
        return arange(start.toDouble(), stop.toDouble(), increment.toDouble())
    }

    override fun arange(start: Int, stop: Int): EJMLMatrix {
        val inc = 1.0*java.lang.Math.signum(stop.toDouble()-start.toDouble())
        return arange(start.toDouble(), stop.toDouble(), inc)
    }

}

