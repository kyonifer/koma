package koma.matrix.ejml

import koma.*
import koma.matrix.common.*
import koma.util.*
import org.ejml.simple.SimpleMatrix

class EJMLMatrixFactory : DoubleFactoryBase<EJMLMatrix>() {

    override fun zeros(rows: Int, cols: Int) = EJMLMatrix(koma.matrix.ejml.backend.zeros(rows, cols))

    @Deprecated(DEPRECATE_IMPLICIT_2D, ReplaceWith("zeros(size, size)"))
    override fun zeros(size: Int) = zeros(size, size)

    override fun create(data: IntRange): EJMLMatrix {
        val dataArray = fromCollection(data.map { it.toDouble() })
        return EJMLMatrix(SimpleMatrix(1, dataArray.size, true, *dataArray))
    }

    override fun create(data: DoubleArray): EJMLMatrix {
        return EJMLMatrix(SimpleMatrix(1, data.size, true, *data))
    }

    override fun create(data: Array<DoubleArray>): EJMLMatrix {
        return EJMLMatrix(SimpleMatrix(data))
    }

    override fun ones(rows: Int, cols: Int): EJMLMatrix {
        return EJMLMatrix(koma.matrix.ejml.backend.ones(rows, cols))
    }

    @Deprecated(DEPRECATE_IMPLICIT_2D, ReplaceWith("ones(size, size)"))
    override fun ones(size: Int): EJMLMatrix {
        return ones(size, size)
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

    @Deprecated(DEPRECATE_IMPLICIT_2D, ReplaceWith("rand(size, size)"))
    override fun rand(size: Int): EJMLMatrix {
        return rand(size, size)
    }

    override fun rand(rows: Int, cols: Int): EJMLMatrix {
        return EJMLMatrix(koma.matrix.ejml.backend.rand(rows, cols))
    }

    override fun rand(rows: Int, cols: Int, seed: Long): EJMLMatrix {
        return EJMLMatrix(koma.matrix.ejml.backend.rand(rows, cols, seed))
    }

    @Deprecated(DEPRECATE_IMPLICIT_2D, ReplaceWith("randn(size, size)"))
    override fun randn(size: Int): EJMLMatrix {
        return EJMLMatrix(koma.matrix.ejml.backend.randn(size))
    }

    override fun randn(rows: Int, cols: Int): EJMLMatrix {
        return EJMLMatrix(koma.matrix.ejml.backend.randn(rows, cols))
    }

    override fun randn(rows: Int, cols: Int, seed: Long): EJMLMatrix {
        return EJMLMatrix(koma.matrix.ejml.backend.randn(rows, cols, seed))
    }

}

