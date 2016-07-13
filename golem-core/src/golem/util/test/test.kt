/**
 * Helper functions for testing matrices
 */

@file:JvmName("UtilTests")

package golem.util.test

import golem.*
import golem.matrix.*

/**
 * Asserts that a matrix A roughly equals a matrix B. eps is the acceptable numerical error.
 */
fun assertMatrixEquals(A: Matrix<Double>, B: Matrix<Double>, eps: Double = 1e-6) {
    try {
        assert((A - B).all { abs(it) < eps })

    } catch(e: AssertionError) {
        println("Expected: \n$A\nGot: \n$B")
        throw e
    }
}

private var facs = getAvailableFactories()

/**
 * A helper function to run tests against all available backends in sequence. Sets [golem.factory] to each backend
 * consecutively and then runs the passed in block of code. Note that code that manually sets its own backend
 * (e.g. by creating a MTJMatrix instance explicitly) will not be affected by this function. Code that uses
 * top-level functions and generic Matrix<T> functions should work correctly.
 *
 * Note: this function sets golem.factory to an arbitrary backend, so reset it afterwards if needed.
 */
fun allBackends(f: () -> Unit) {
    if (facs.size == 0)
        throw IllegalStateException("Asked to test all backends, but no backends found.")
    for (fac in facs) {
        golem.factory = fac
        f()
    }

}
