package golem

import golem.util.test.allBackends
import golem.util.test.assertMatrixEquals
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertEquals

class CreatorsTests {
    @Test
    fun testZeros()
    {
        allBackends {
            var a = zeros(5, 1)
            for (i in 0..a.numRows() - 1)
                a[i, 0] = i + 1
            assertMatrixEquals(a, mat[1, 2, 3, 4, 5].T)
        }

    }

    @Test
    fun testCreate()
    {
        allBackends {
            var a = create(0..4)
            var b = mat[0, 1, 2, 3, 4]

            assertMatrixEquals(a, b)
            assertMatrixEquals(a.T, b.T)
        }
    }
    @Test
    fun testOnes()
    {
        allBackends {
            var a = ones(3, 5)
            assertEquals(a[4], 1.0)
            assertEquals(a[2, 2], 1.0)
            assertMatrixEquals(zeros(3, 5).fill { i, j -> 1.0 }, a)
        }
    }
    @Test
    fun testEye()
    {
        allBackends {
            var a = eye(3)
            var expected = zeros(3, 3).mapMatIndexed { i, j, d -> if (i == j) 1.0 else 0.0 }
            assertMatrixEquals(expected, a)
        }
    }
    @Test
    fun testArange()
    {
        allBackends {
            var a = arange(1.0, 1.5, .1)
            var expected = mat[1.0, 1.1, 1.2, 1.3, 1.4]
            assertMatrixEquals(expected, a)
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
}
