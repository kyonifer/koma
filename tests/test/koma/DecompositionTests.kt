package koma

import koma.util.test.*
import koma.extensions.*
import org.junit.Test

// @formatter:off
class DecompositionTests {
    @Test
    fun testQR() {
        allBackends {
            val A = eye(3)+.3
            val (Q, R) = A.QR()
            assertMatrixEquals(A, Q*R)
        }
    }
    @Test
    fun testLU() {
        allBackends {
            val A = eye(3)+.3
            val (P, L, U) = A.LU()
            assertMatrixEquals(P*A, L*U)
        }
    }
    @Test
    fun testChol() {
        allBackends {
            val a = eye(3) + .01
            val out = a.chol()
            val expected = mat[1.004987562112, 0.000000000000, 0.000000000000 end
                               0.009950371902, 1.004938301638, 0.000000000000 end
                               0.009950371902, 0.009852336291, 1.004890004711]

            // Check lower triangular
            val notLower = out.mapIndexed { row, col, value ->
                if (row < col && value != 0.0) -1.0 else 1.0
            }.any {
                it == -1.0
            }
            assert(!notLower)
            assertMatrixEquals(out, expected)
            // Check matrix is sqrt
            assertMatrixEquals(out * out.T, a)

        }
    }
    @Test
    fun testSVD() {
        allBackends {
            // Verify identity matrix.
            // Strange capitalization to avoid name collision with 'as'.
            val a = eye(3) + 1e-2
            val (Au, As, Av) = a.SVD()
            val reconstructedA = Au * As * Av.T
            assertMatrixEquals(a, reconstructedA)

            // Verify non-square underdefined case.
            val b = eye(3, 5)
            val (Bu, Bs, Bv) = b.SVD()
            val reconstructedB = Bu * Bs * Bv.T
            assertMatrixEquals(b, reconstructedB)

            // And the non-square overdefined case.
            // Sidenote: MTJMatrixFactor.eye will throw this error if you have more rows than columns:
            // java.lang.IndexOutOfBoundsException: column index >= numColumns (3 >= 3)
            // So we make a 3x5 and transpose it.
            val c = eye(3, 5).transpose()
            val (Cu, Cs, Cv) = c.SVD()
            val reconstructedC = Cu * Cs * Cv.T
        }
    }
}
