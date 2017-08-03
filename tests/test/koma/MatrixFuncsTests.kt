package koma

import koma.util.test.*
import org.junit.Test

//@formatter:off

class MatrixFuncsTests {

    @Test
    fun testArgMin() {
        allBackends {
            val a = mat[1, 2, 3 end
                    4, 3, -1]

            assert(a.argMin() == 5)
            a[1, 2] = 5.5
            assert(a.argMin() == 0)
        }
    }

    @Test
    fun testArgMax() {
        allBackends {
            val a = mat[1, 2, 3 end
                        4, 3, -1]

            assert(a.argMax() == 3)
            a[0, 2] = 44
            assert(a.argMax() == 2)
            a[1, 1] = 45
            assert(a.argMax() == 4)
        }
    }

    @Test
    fun testExpm() {
        allBackends {
            assertMatrixEquals(expm(eye(3)), E * eye(3))

            val expected = mat[20.08553692318771, 0, 0 end
                               0, 20.08553692318771, 0 end
                               0, 0, 20.08553692318771]
            assertMatrixEquals(expm(eye(3) * 3), expected)

        }
    }

    @Test
    fun testInverse() {
        allBackends {
            val a = eye(5, 5) * 4.5
            assertMatrixEquals(eye(5, 5) * (1 / 4.5), a.inv())
        }
    }

    @Test
    fun testEPow() {
        allBackends {
            var a = mat[1, 2 end
                        3, 4]
            a = a epow 3

            val aE = mat[1 pow 3, 2 pow 3 end
                         (3 pow 3), 4 pow 3]

            assertMatrixEquals(a, aE)
        }
    }

    @Test
    fun testPow() {
        allBackends {
            val a = mat[2, 0, 0 end
                        0, 1, 0 end
                        0, 0, 4]

            val a3 = a pow 3

            assertMatrixEquals(a3, a * a * a)
        }
    }

    @Test
    fun testSin() {
        allBackends {
            val a = zeros(2, 2)
            a[0, 0] = PI / 2
            a[0, 1] = 3.0
            a[1, 0] = -PI / 2

            val expected = mat[1.0, 0.14112000805986721 end
                               -1.0, 0.0]

            assertMatrixEquals(expected, sin(a))
        }

    }

    @Test
    fun testAbs() {
        allBackends {
            val a = mat[1, -2, 3 end
                        4, 5, -6]
            val expected = mat[1, 2, 3 end
                               4, 5, 6]
            assertMatrixEquals(expected, abs(a))
        }
    }

    @Test
    fun testCeil() {
        allBackends {
            val a = mat[1.1, 2.2, 3, 4.9, -1.1]
            val expected = mat[2, 3, 3, 5, -1]

            assertMatrixEquals(expected, ceil(a))

        }
    }

    @Test
    fun testCos() {
        allBackends {
            val a = zeros(2, 2)
            a[0, 0] = PI / 2
            a[0, 1] = 3.0
            a[1, 0] = -PI / 4
            a[1, 1] = 0

            val expected = mat[0.0, -0.98999249660044542 end
                               1 / sqrt(2), 1.0]

            assertMatrixEquals(expected, cos(a))
        }
    }

    @Test
    fun testExp() {
        allBackends {
            val a = mat[0, 1, 2]
            val expected = mat[1, E, E pow 2]
            assertMatrixEquals(expected, exp(a))

        }
    }

    @Test
    fun testLog() {
        allBackends {
            val a = mat[3.3, 5.4, 1.1]
            val expected = a.map { log(it) }
            val expected2 = mat[1.19392247, 1.68639895, 0.09531018]
            assertMatrixEquals(expected, log(a))
            assertMatrixEquals(expected2, log(a))
        }
    }

    @Test
    fun testLogFail() {
        allBackends {
            val a = mat[3.3, 5.4, -1.1]
            assertMatrixEquals(mat[log(3.3), log(5.4)], log(a[0, 0..1]))
            assert(log(a[2]).equals(Double.NaN))
        }
    }

    @Test
    fun testSign() {
        allBackends {
            val a = mat[1, -2.2, 3, 0]
            val expected = mat[1, -1, 1, 0]
            assertMatrixEquals(expected, sign(a))
        }
    }

    @Test
    fun testSqrt() {
        allBackends {
            val a = mat[4, 2, 3]
            val expected = a.map { sqrt(it) }
            assertMatrixEquals(expected, sqrt(a))
        }
    }

    @Test
    fun testTan() {
        allBackends {
            val a = mat[1, -1, 35]
            val expected = a.map { tan(it) }
            val expected2 = mat[1.55740772, -1.55740772, 0.47381472]
            assertMatrixEquals(expected, tan(a))
            assertMatrixEquals(expected2, tan(a))

        }
    }

    @Test
    fun testRound() {
        allBackends {
            val a = mat[1.1, 1.5, 1.6, 1.9, -1.1, -1.5, -1.7]
            val expected = mat[1, 2, 2, 2, -1, -1, -2]
            assertMatrixEquals(expected, round(a))
        }
    }

    @Test
    fun testFloor() {
        allBackends {
            val a = mat[1.1, 1.5, 1.6, 1.9, -1.1, -1.5, -1.7]
            val expected = mat[1, 1, 1, 1, -2, -2, -2]
            assertMatrixEquals(expected, floor(a))

        }
    }

    @Test
    fun testLogb() {
        allBackends {
            val a = mat[.5, 0.1, 5.5]
            val expected = a.map { logb(4, it) }
            val expected2 = mat[-1.0, -3.32192809, 2.45943162]
            val expected3 = mat[-0.30103, -1.0, 0.74036269]
            assertMatrixEquals(expected, logb(4, a))
            assertMatrixEquals(expected2, logb(2, a))
            assertMatrixEquals(expected3, logb(10, a))
        }
    }

    @Test
    fun testVHstack() {
        val a = mat[1, 2 end
                    3, 4]
        val b = mat[-1, -2 end
                    -3, -4]
        val out = vstack(hstack(a, b), hstack(-2 * a, 0 * b))
        val expected = mat[1, 2, -1, -2 end
                           3, 4, -3, -4 end
                           -2, -4, 0, 0 end
                           -6, -8, 0, 0]
        assertMatrixEquals(expected, out)
    }
}