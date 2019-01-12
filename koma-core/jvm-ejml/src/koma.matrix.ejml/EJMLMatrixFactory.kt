package koma.matrix.ejml

import koma.*
import koma.matrix.common.*
import koma.util.*
import org.ejml.simple.SimpleMatrix

class EJMLMatrixFactory : DoubleFactoryBase<EJMLMatrix>() {

    override fun zeros(rows: Int, cols: Int) = EJMLMatrix(koma.matrix.ejml.backend.zeros(rows, cols))

    override fun create(data: IntRange): EJMLMatrix {
        val dataArray = fromCollection(data.map { it.toDouble() })
        return EJMLMatrix(SimpleMatrix(1, dataArray.size, true, dataArray))
    }

    override fun create(data: DoubleArray): EJMLMatrix {
        return EJMLMatrix(SimpleMatrix(1, data.size, true, data))
    }

    override fun create(data: Array<DoubleArray>): EJMLMatrix {
        return EJMLMatrix(SimpleMatrix(data))
    }

    override fun ones(rows: Int, cols: Int): EJMLMatrix {
        return EJMLMatrix(koma.matrix.ejml.backend.ones(rows, cols))
    }

    override fun eye(size: Int): EJMLMatrix {
        return EJMLMatrix(koma.matrix.ejml.backend.eye(size))
    }

    override fun eye(rows: Int, cols: Int): EJMLMatrix {
        val out = koma.matrix.ejml.backend.zeros(rows, cols)
        for (i in 0..min(rows, cols) - 1)
            out[i, i] = 1.0
        return EJMLMatrix(out)
    }

}

