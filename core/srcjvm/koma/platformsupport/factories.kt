package koma.platformsupport

import koma.matrix.*
import koma.matrix.DoubleMatrix

fun getFactories(): List<MatrixFactory<DoubleMatrix>> {
    val facCandidates = arrayOf("koma.matrix.mtj.MTJMatrixFactory",
                                "koma.matrix.ejml.EJMLMatrixFactory",
                                "koma.matrix.jblas.JBlasMatrixFactory")
    val out: MutableList<MatrixFactory<DoubleMatrix>> = ArrayList()

    facCandidates.forEach {
        try {
            val fac: Class<*> = Class.forName(it)
            fac.constructors.forEach { ctor ->
                if (ctor.parameterTypes.isEmpty()) {
                    @Suppress("UNCHECKED_CAST")
                    val inst = ctor.newInstance() as MatrixFactory<DoubleMatrix>
                    // Actual classpath search
                    inst.zeros(1)
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
    return out
}
