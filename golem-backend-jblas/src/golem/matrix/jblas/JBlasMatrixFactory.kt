package golem.matrix.jblas

import golem.matrix.*
import golem.matrix.common.*
import golem.matrix.jblas.backend.*
import org.jblas.DoubleMatrix

class JBlasMatrixFactory : DoubleFactoryBase<JBlasMatrix>() {
    override fun zeros(rows: Int, cols: Int) = JBlasMatrix(golem.matrix.jblas.backend.zeros(rows, cols))
    override fun zeros(size: Int) = JBlasMatrix(golem.matrix.jblas.backend.zeros(size, size))
    override fun create(data: IntRange) = JBlasMatrix(DoubleMatrix(data.map { it.toDouble() }))
    override fun create(data: DoubleArray) = JBlasMatrix(DoubleMatrix(data))
    override fun create(data: Array<DoubleArray>) = JBlasMatrix(DoubleMatrix(data))
    override fun ones(size: Int) = JBlasMatrix(DoubleMatrix(size, size).mapMat { 1.0 })
    override fun ones(rows: Int, cols: Int) = JBlasMatrix(DoubleMatrix(rows, cols).mapMat { 1.0 })
    override fun eye(size: Int) = JBlasMatrix(DoubleMatrix.eye(size))
    override fun eye(rows: Int, cols: Int): JBlasMatrix {
        var out = DoubleMatrix(rows, cols)
        for (i in 0..rows - 1)
            out[i, i] = 1.0
        return JBlasMatrix(out)
    }

    override fun rand(size: Int) = JBlasMatrix(golem.matrix.jblas.backend.rand(size))
    override fun rand(rows: Int, cols: Int) = JBlasMatrix(golem.matrix.jblas.backend.rand(rows, cols))

    override fun rand(rows: Int, cols: Int, seed: Long): JBlasMatrix {
        println("Warning: JBlas RNG doesnt support seeds")
        return JBlasMatrix(golem.matrix.jblas.backend.rand(rows, cols))
    }

    override fun randn(size: Int) = JBlasMatrix(golem.matrix.jblas.backend.randn(size))

    override fun randn(rows: Int, cols: Int) = JBlasMatrix(golem.matrix.jblas.backend.randn(rows, cols))

    override fun randn(rows: Int, cols: Int, seed: Long): JBlasMatrix {
        println("Warning: JBlas RNG doesnt support seeds")
        return JBlasMatrix(golem.matrix.jblas.backend.randn(rows, cols))
    }

}
