package koma.internal

import koma.matrix.*
import koma.matrix.default.DefaultDoubleMatrixFactory
import koma.matrix.default.DefaultFloatMatrixFactory
import koma.matrix.default.DefaultIntMatrixFactory

internal actual fun getDoubleFactories(): List<MatrixFactory<Matrix<Double>>> {
    val facCandidates = arrayOf("koma.matrix.mtj.MTJMatrixFactory",
                                "koma.matrix.ejml.EJMLMatrixFactory",
                                "koma.matrix.jblas.JBlasMatrixFactory")
    val out: MutableList<MatrixFactory<Matrix<Double>>> = ArrayList()

    facCandidates.forEach {
        try {
            val fac: Class<*> = Class.forName(it)
            fac.constructors.forEach { ctor ->
                if (ctor.parameterTypes.isEmpty()) {
                    @Suppress("UNCHECKED_CAST")
                    val inst = ctor.newInstance() as MatrixFactory<Matrix<Double>>
                    // Actual classpath search
                    inst.zeros(1, 1)
                    // Backend exists
                    out.add(inst)
                }
            }
        }
        catch(e: ClassNotFoundException) {
        }
        catch(e: NoClassDefFoundError) {
        }
    }
    out.add(DefaultDoubleMatrixFactory())
    return out
}


actual fun getDoubleFactory(): MatrixFactory<Matrix<Double>> = getDoubleFactories()[0]
actual fun getFloatFactory(): MatrixFactory<Matrix<Float>> = DefaultFloatMatrixFactory()
actual fun getIntFactory(): MatrixFactory<Matrix<Int>> = DefaultIntMatrixFactory()
