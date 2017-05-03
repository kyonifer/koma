package koma

import koma.util.test.*
import org.junit.Test

// @formatter:off
class DecompositionTests {
    @Test
    fun testChol() {
        allBackends {
            val a = eye(3) + .01
            val out = a.chol()
            val expected = mat[1.004987562112, 0.000000000000, 0.000000000000 end
                               0.009950371902, 1.004938301638, 0.000000000000 end
                               0.009950371902, 0.009852336291, 1.004890004711]

            // Check lower triangular
            val notLower = out.mapMatIndexed { row, col, value ->
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
}
