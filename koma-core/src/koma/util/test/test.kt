/**
 * Helper functions for testing matrices
 */

@file:JvmName("UtilTests")

package golem.util.test
import golem.polyfill.annotations.*

import golem.*
import golem.matrix.*
import golem.platformsupport.assert

/**
 * Asserts that a matrix [expected] roughly equals a matrix [actual]. eps is the acceptable numerical error.
 */
fun <T: Number> assertMatrixEquals(expected: Matrix<T>, actual: Matrix<T>, eps: Double = 1e-6) {
    try {
        assert((expected - actual).all { abs(it.toDouble()) < eps })

    } catch(e: AssertionError) {
        println("Expected: \n$expected\nGot: \n$actual")
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
    if (facs.isEmpty())
        throw IllegalStateException("Asked to test all backends, but no backends found.")
    if (facs.size != 3)
        println("Warning: only testing against ${facs.size} backends (3 expected)")
    for (fac in facs) {
        golem.factory = fac
        f()
    }

}
