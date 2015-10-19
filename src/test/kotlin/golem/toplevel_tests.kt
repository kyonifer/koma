package golem

import golem.util.test.assertMatrixEquals
import golem.util.test.allBackends
import org.junit.Assert
import org.junit.Test
import org.junit.rules.ExpectedException
import kotlin.test.*

/**
 * Test each back-end for similar compliance with API
 */
class toplevel_tests {

    @Test
    fun testBuilder()
    {
        allBackends {
            var a = zeros(5,1)
            for (i in 0..a.numRows()-1)
                a[i,0]=i+1
            assertMatrixEquals(a, mat[1,2,3,4,5].T)
        }

    }

    fun testCreate()
    {
        var a = create(0..4)
        var b = mat[0,1,2,3,4]

        assertMatrixEquals(a, b)
        assertMatrixEquals(a.T, b.T)

    }

    @Test
    fun test1DIndex() {
        allBackends {
            var a = mat[0,1,2,3,4]
            for (i in 0..a.numRows()-1)
                assertEquals(a[i], a[0,i])

            a = mat[0,1,2,3,4].T
            for (i in 0..a.numRows()-1)
                assertEquals(a[i], a[i,0])

            a = mat[0,1 end 2,3]
            assertEquals(a[0], a[0,0])
            assertEquals(a[1], a[0,1])
            assertEquals(a[2], a[1,0])
            assertEquals(a[3], a[1,1])

        }

    }

    @Test
    fun test2DIndex()
    {
        var a = mat[0,1 end 2,3]
        assertEquals(0.0, a[0,0])
        assertEquals(1.0, a[0,1])
        assertEquals(2.0, a[1,0])
        assertEquals(3.0, a[1,1])
    }

    @Test
    fun testMultiplication()
    {
        allBackends {
            var a = mat[1, 2, 3 end
                    4, 5, 6 end
                    7, 8, 9]
            var out = mat[30, 36, 42 end
                    66, 81, 96 end
                    102, 126, 150]
            assertMatrixEquals(out, a * a)
        }

    }


    @Test
    fun testTranspose()
    {
        allBackends {
            var a = randn(2, 4)
            assertMatrixEquals(a, a.T.T)
            assertEquals(a[1,0], a.T[0,1])

            a = randn(5,1)
            assertEquals(a[3], a.T[3])
            assertEquals(a[3,0], a.T[0,3])
        }

    }

    @Test
    fun testInverse()
    {
        allBackends {
            var a = eye(5, 5) * 4.5
            assertMatrixEquals(eye(5, 5) * (1 / 4.5), a.inv())
        }
    }


    @Test
    fun testSin()
    {
        allBackends {
            var a = zeros(2, 2)
            a[0, 0] = PI / 2
            a[0, 1] = 3.0
            a[1, 0] = -PI / 2

            var expected = mat[1.0, 0.14112000805986721 end
                    -1.0, 0.0]

            assertMatrixEquals(expected, sin(a))
        }

    }

    @Test
    fun testRandn()
    {
        allBackends {
            var a = 2 * randn(1, 1000000)

            Assert.assertEquals(0.0, mean(a), .01)
        }
    }

    @Test
    fun testEPow()
    {
        allBackends {
            var a = mat[1, 2 end
                        3, 4]
            a = a epow 3

            var aE = mat[1 pow 3, 2 pow 3 end
                         (3 pow 3), 4 pow 3]

            assertMatrixEquals(a, aE)
        }
    }

    @Test
    fun testPow()
    {
        allBackends {
            var a = mat[2, 0, 0 end
                        0, 1, 0 end
                        0, 0, 4]

            var a3 = a pow 3

            assertMatrixEquals(a3, a * a * a)
        }
    }

    @Test
    fun test2DIndexFail()
    {
        var a = mat[0,1 end 2,3]
        assertFailsWith(IndexOutOfBoundsException::class) {
            a[2,0]
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            a[0,2]
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            a[-1,0]
        }
        assertFailsWith(IndexOutOfBoundsException::class) {
            a[-1,-1]
        }

    }
    @Test
    fun testBuilderFail()
    {
        allBackends {
            assertFailsWith(IllegalArgumentException::class,
                    "When building matrices with mat[] please give even rows/cols") {
                mat[1,2,3 end
                        2]
            }
            assertFailsWith(IllegalArgumentException::class,
                    "When building matrices with mat[] please give even rows/cols") {
                mat[1,2,3 end
                        2,3]
            }
            assertFailsWith(IllegalArgumentException::class,
                    "When building matrices with mat[] please give even rows/cols") {
                mat[1,2,3 end
                        2,3]
            }
        }
    }


    val exception = ExpectedException.none()

}

