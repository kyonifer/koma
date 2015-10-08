/**
 * Helper functions for testing matrices
 */

package golem.util

import golem.*
import golem.matrix.Matrix

fun assertMatrixEquals(A: Matrix<Double>, B: Matrix<Double>, eps: Double = 1e-6)
{
    assert((A - B).any {abs(it) < eps})
}