/**
 * Helper functions for testing matrices
 */

package golem.util.test

import golem.abs
import golem.all
import golem.matrix.Matrix

fun assertMatrixEquals(A: Matrix<Double>, B: Matrix<Double>, eps: Double = 1e-6)
{
    try {
        assert((A - B).all {abs(it) < eps})

    }
    catch(e: AssertionError) {
        println("Expected: \n$A\nGot: \n$B")
        throw e
    }
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
