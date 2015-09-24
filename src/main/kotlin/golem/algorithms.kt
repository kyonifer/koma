package golem

import org.ujmp.core.Matrix
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D
import org.ujmp.jblas.JBlasDenseDoubleMatrix2D


fun expm(F: Matrix, t: Double = 1.0): Matrix
{
    when (F) {
        is JBlasDenseDoubleMatrix2D -> {
            var innermat = F.getWrappedObject()
	    return JBlasDenseDoubleMatrix2D(org.jblas.MatrixFunctions.expm(innermat))
        }
	else -> throw Exception("Unsupported matrix type for expm")

    }

    //g:DenseDoubleMatrix2D


}
