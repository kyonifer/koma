/**
 * Helper functions for testing matrices
 */

package golem.util.test

import golem.*
import golem.matrix.Matrix

fun assertMatrixEquals(A: Matrix<Double>, B: Matrix<Double>, eps: Double = 1e-6)
{
    assert((A - B).all {abs(it) < eps})
}

private var facs = arrayOf(golem.matrix.ejml.EJMLMatrixFactory(),
        golem.matrix.mtj.MTJMatrixFactory())

fun allBackends(f:()->Unit)
{
    for (fac in facs) {
        golem.factory = fac
        f()
    }

}
