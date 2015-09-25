/**
 * Common algorithms that may only be available for a limited
 * set of matrix provider back-ends.
 */

package golem

import golem.matrix.Matrix
import golem.matrix.jblas.MatrixJBlas

fun expm(F: Matrix<*,*>, t: Double = 1.0): Matrix<*,*>
{
    when (F) {
        is MatrixJBlas -> {
            var innermat = F.storage
	    return MatrixJBlas(org.jblas.MatrixFunctions.expm(innermat))
        }
	else -> throw Exception("Unsupported matrix type for expm")
    }
}
