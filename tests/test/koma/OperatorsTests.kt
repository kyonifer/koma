package koma

import koma.extensions.*
import koma.matrix.Matrix
import koma.matrix.ejml.EJMLMatrixFactory
import koma.matrix.jblas.JBlasMatrixFactory
import koma.matrix.mtj.MTJMatrixFactory
import koma.util.test.*
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
            val a = mat[1, 2, 3 end
                        4, 5, 6 end
                        7, 8, 9]
            assertMatrixEquals(mat[1, 4, 7].T, a[0..2, 0])
            assertMatrixEquals(mat[2, 5, 8].T, a[0..2, 1])
            assertMatrixEquals(mat[4, 5], a[1, 0..1])
            assertMatrixEquals(mat[5, 6 end 8, 9], a[1..2, 1..2])
            assertMatrixEquals(a, a[all,all])
            assertMatrixEquals(a, a[0..end,0..end])
            assertMatrixEquals(mat[5, 6 end 8, 9], a[1..end,1..end])
            assertMatrixEquals(mat[5], a[1..end-1,1..end-1])
            assertMatrixEquals(mat[2, 3 end 5, 6], a[0..end-1,1..end])
            assertMatrixEquals(mat[4, 5 end 7, 8], a[1..end,0..end-1])


        }
    }

    @Test
    fun testNegativeRanges() {
        allBackends {
            val a = mat[1, 2, 3 end
                        4, 5, 6 end
                        7, 8, 9]
            assertMatrixEquals(mat[5, 6 end 8, 9], a[1..2, 1..2])
            assertMatrixEquals(mat[5, 6 end 8, 9], a[1..-1, 1..-1])
            assertMatrixEquals(mat[4, 5 end 7, 8], a[1..-1, 0..-2])
            assertMatrixEquals(mat[4, 5], a[1..-2, 0..-2])
            assertMatrixEquals(mat[9], a[2..-1, 2..-1])


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
            val a = mat[1, 2, 3 end
                        3, 4, 5]

            a[1, 1] = -1.1
            a[0, 2] = 4.5

            val expected = mat[1, 2, 4.5 end
                               3, -1.1, 5]

            assertMatrixEquals(expected, a)

        }
    }

    @Test
    fun test2DIndex() {
        allBackends {
            val a = mat[0, 1 end 2, 3]
            assertEquals(0.0, a[0, 0])
            assertEquals(1.0, a[0, 1])
            assertEquals(2.0, a[1, 0])
            assertEquals(3.0, a[1, 1])
        }
    }

    @Test
    fun testScalarMultiplication() {
        allBackends {
            val a = mat[1, 2, 3 end
                        4, 5, 6 end
                        7, 8, 9]
            val out = a.map { it * 30 }
            assertMatrixEquals(out, a * 30)
        }

    }

    @Test
    fun testMultiplication() {
        allBackends {
            val a = mat[1, 2, 3 end
                        4, 5, 6 end
                        7, 8, 9]
            val out = mat[30, 36, 42 end
                          66, 81, 96 end
                          102, 126, 150]
            assertMatrixEquals(out, a * a)
        }

    }

    @Test
    fun testMultiplicationMultiBackend() {
        val a = MTJMatrixFactory().eye(3)*2
        val b = EJMLMatrixFactory().eye(3)*3
        val c = JBlasMatrixFactory().eye(3)*4

        assertMatrixEquals(expected=eye(3)*6, actual=a*b)
        assertMatrixEquals(expected=eye(3)*8, actual=a*c)
        assertMatrixEquals(expected=eye(3)*12, actual=b*c)

        assertMatrixEquals(expected=eye(3)*5, actual=a+b)
        assertMatrixEquals(expected=eye(3)*6, actual=a+c)
        assertMatrixEquals(expected=eye(3)*7, actual=b+c)

        val d = MTJMatrixFactory().zeros(1,3).fill { _, col -> (col+1)*2.0 }
        val e = EJMLMatrixFactory().zeros(3,1).fill { row, _ -> (row+1)*-3.0 }

        assertMatrixEquals(expected=mat[2*-3 + 4*-6 + 6*-9], actual=d*e)
        assertMatrixEquals(expected=mat[2*-3, 4*-6, 6*-9], actual=d.elementTimes(e.T))
    }

    @Test
    fun testAddition() {
        allBackends {
            val a = mat[1, 2, 3, 2, 1]
            val other = mat[0, 1, -1, .5, 4.5]
            var expected = mat[5, 6, 7, 6, 5]
            assertMatrixEquals(expected, a + 4)
            expected = mat[1, 3, 2, 2.5, 5.5]
            assertMatrixEquals(expected, a + other)
        }
    }

    @Test
    fun testMinus() {
        allBackends {
            val a = mat[1, 2, 3, 2, 1]
            val other = mat[0, 1.1, 0, -1, 4]
            var expected = mat[-1, -2, -3, -2, -1]
            assertMatrixEquals(expected, -a)

            expected = mat[1, .9, 3, 3, -3]
            assertMatrixEquals(expected, a - other)

        }
    }

    @Test
    fun testAdditionScalar() {
        allBackends {
            val a = 3
            val a2 = 3.0
            val other = mat[3, 5, 7 end
                            1, 2, 3]
            val expected = mat[6, 8, 10 end
                               4, 5, 6 ]
            assertMatrixEquals(expected, a + other)
            assertMatrixEquals(expected, a2 + other)

        }
    }

    @Test
    fun testMinusScalar() {
        allBackends {
            val a = 5
            val a2 = 5.0
            val other = mat[1,2,3,4 end
                            1,5,9,5]
            val expected = mat[4, 3,  2, 1 end
                               4, 0, -4, 0]
            assertMatrixEquals(expected, a - other)
            assertMatrixEquals(expected, a2 - other)
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
            val a = mat[0, 1 end 2, 3]
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

            assertFails {
                a[2, 0]=1.1
            }
            assertFails {
                a[0, 2]=1.1
            }
            assertFails {
                a[-1, 0]=1.1
            }
            assertFails {
                a[-1, -1]=1.1
            }            
            
        }
    }

    @Test
    fun testSubclassesFindInner() {
        val a = object: Matrix<Double> by zeros(2,2){}
        a[0,0]=5
        var b = eye(2)*a
        var expected = mat[5,0 end 0,0]
        assertMatrixEquals(b, expected)
        b = eye(2)+a
        expected = mat[6,0 end 0,1]
        assertMatrixEquals(b, expected)
    }

    val exception = ExpectedException.none()

}

