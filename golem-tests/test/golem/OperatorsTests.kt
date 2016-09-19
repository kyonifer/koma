package golem

import golem.util.test.*
import org.junit.Test
import org.junit.rules.ExpectedException
import kotlin.test.assertEquals
import kotlin.test.assertFails

//@formatter:off

/**
 * Test each back-end for similar compliance with API
 */
class OperatorsTests {


    @Test
    fun testRanges() {
        allBackends {
            var a = mat[1, 2, 3 end
                        4, 5, 6 end
                        7, 8, 9]
            assertMatrixEquals(mat[1, 4, 7].T, a[0..2, 0])
            assertMatrixEquals(mat[2, 5, 8].T, a[0..2, 1])
            assertMatrixEquals(mat[4, 5], a[1, 0..1])
            assertMatrixEquals(mat[5, 6 end 8, 9], a[1..2, 1..2])

        }
    }

    @Test
    fun test1DIndex() {
        allBackends {
            var a = mat[0, 1, 2, 3, 4]
            for (i in 0..a.numRows() - 1)
                assertEquals(a[i], a[0, i])

            a = mat[0, 1, 2, 3, 4].T
            for (i in 0..a.numRows() - 1)
                assertEquals(a[i], a[i, 0])

            a = mat[1,2,3,4,5 end
                    6,7,8,9,0 end
                    3,4,5,6,7]

            assertEquals(a[0], a[0, 0])
            assertEquals(a[1], a[0, 1])
            assertEquals(a[5], a[1, 0])
            assertEquals(a[6], a[1, 1])


            a = mat[1, 2, 3 end
                    4, 5, 6 end
                    7, 8, 9]

            assertEquals(4.0, a[3])

            a = eye(3)

            assertEquals(0.0, a[3])

        }

    }

    @Test
    fun test1DSet() {
        allBackends {
            var a = mat[1, 2, 3 end
                        3, 4, 5]

            a[1] = 1.1
            a[3] = 1.2

            var expected = mat[1, 1.1, 3 end
                               1.2, 4, 5]

            assertMatrixEquals(expected, a)

            a = mat[1,2,3,4,5 end
                    6,7,8,9,0 end
                    3,4,5,6,7]
            expected = mat[1,2, 3,4,5 end
                           6,-1,8,9,0 end
                           3,4, 5,6,7]
            a[6] = -1
            assertMatrixEquals(expected, a)


        }
    }

    @Test
    fun test2DSet() {
        allBackends {
            var a = mat[1, 2, 3 end
                        3, 4, 5]

            a[1, 1] = -1.1
            a[0, 2] = 4.5

            var expected = mat[1, 2, 4.5 end
                               3, -1.1, 5]

            assertMatrixEquals(expected, a)

        }
    }

    @Test
    fun test2DIndex() {
        allBackends {
            var a = mat[0, 1 end 2, 3]
            assertEquals(0.0, a[0, 0])
            assertEquals(1.0, a[0, 1])
            assertEquals(2.0, a[1, 0])
            assertEquals(3.0, a[1, 1])
        }
    }

    @Test
    fun testScalarMultiplication() {
        allBackends {
            var a = mat[1, 2, 3 end
                        4, 5, 6 end
                        7, 8, 9]
            var out = a.mapMat { it * 30 }
            assertMatrixEquals(out, a * 30)
        }

    }

    @Test
    fun testMultiplication() {
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
    fun testAddition() {
        allBackends {
            var a = mat[1, 2, 3, 2, 1]
            var other = mat[0, 1, -1, .5, 4.5]
            var expected = mat[5, 6, 7, 6, 5]
            assertMatrixEquals(expected, a + 4)
            expected = mat[1, 3, 2, 2.5, 5.5]
            assertMatrixEquals(expected, a + other)
        }
    }

    @Test
    fun testMinus() {
        allBackends {
            var a = mat[1, 2, 3, 2, 1]
            var other = mat[0, 1.1, 0, -1, 4]
            var expected = mat[-1, -2, -3, -2, -1]
            assertMatrixEquals(expected, -a)

            expected = mat[1, .9, 3, 3, -3]
            assertMatrixEquals(expected, a - other)

        }
    }

    @Test
    fun testTranspose() {
        allBackends {
            var a = randn(2, 4)
            assertMatrixEquals(a, a.T.T)
            assertEquals(a[1, 0], a.T[0, 1])

            a = randn(5, 1)
            assertEquals(a[3], a.T[3])
            assertEquals(a[3, 0], a.T[0, 3])
        }

    }

    @Test
    fun test2DIndexFail() {
        allBackends {
            var a = mat[0, 1 end 2, 3]
            assertFails {
                a[2, 0]
            }
            assertFails {
                a[0, 2]
            }
            assertFails {
                a[-1, 0]
            }
            assertFails {
                a[-1, -1]
            }
        }
    }


    val exception = ExpectedException.none()

}

