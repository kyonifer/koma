package golem.matrix.ejml

import golem.*
import golem.matrix.*
import golem.matrix.common.*
import golem.util.*
import org.ejml.simple.SimpleMatrix
import java.util.*

class EJMLMatrixFactory : MatrixFactory<Matrix<Double>>, DoubleFactoryBase() {

    override fun zeros(rows: Int, cols: Int) = EJMLMatrix(golem.matrix.ejml.backend.zeros(rows, cols))

    override fun zeros(size: Int) = zeros(size, size)

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
        for (i in 0..min(rows, cols) - 1)
            out[i, i] = 1.0
        return EJMLMatrix(out)
    }

    override fun rand(size: Int): EJMLMatrix {
        return rand(size, size)
    }

    override fun rand(rows: Int, cols: Int): EJMLMatrix {
        return EJMLMatrix(golem.matrix.ejml.backend.rand(rows, cols))
    }

    override fun rand(rows: Int, cols: Int, seed: Long): EJMLMatrix {
        return EJMLMatrix(golem.matrix.ejml.backend.rand(rows, cols, seed))
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

}

