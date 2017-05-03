package koma.platformsupport

import koma.matrix.*

fun getFactories(): List<MatrixFactory<Matrix<Double>>> {
    val purektLoader = {
        js("var g = require('koma-backend-purekt')")
        js("new g.koma.matrix.purekt.DoublePureKtMatrixFactory")
    }
    
    val facCandidates = arrayOf(purektLoader)
    val facs = mutableListOf<MatrixFactory<Matrix<Double>>>()

    // Find available modules at runtime
    facCandidates.forEach { loader ->
        try {
            facs.add(loader())
        } catch (e:Throwable) {}
    }
    return facs
}
