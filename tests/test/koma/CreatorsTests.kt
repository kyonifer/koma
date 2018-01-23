package koma

import koma.extensions.*
import koma.util.test.*
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFalse

import koma.matrix.MatrixTypes.DoubleType as dbltype
import koma.matrix.MatrixTypes.FloatType as flttype
import koma.matrix.MatrixTypes.IntType as inttype

class CreatorsTests {
    @Test
    fun testZeros() {
        allBackends {
            val a = zeros(5, 1)
            for (i in 0..a.numRows() - 1)
                a[i, 0] = i + 1
            assertMatrixEquals(a, mat[1, 2, 3, 4, 5].T)
        }

    }

    @Test
    fun testCreate() {
        allBackends {
            val a = create(0..4)
            val b = mat[0, 1, 2, 3, 4]

            assertMatrixEquals(a, b)
            assertMatrixEquals(a.T, b.T)
        }
    }

    @Test
    fun testCopy() {
        allBackends {
            val a = mat[1, 2, 3 end 3, 4, 5]
            val b = a.copy()
            assertMatrixEquals(a,b)
            assertEquals(a.numRows(),b.numRows())
            assertEquals(a.numCols(),b.numCols())
        }
    }
    
    @Test
    fun testCreate2DShaped() {
        allBackends { 
            val a = arrayOf(1.0,2.0,3.0,4.0,5.0,6.0).toDoubleArray()
            val out = create(a, numRows = 2, numCols = 3)
            val expected = mat[1,2,3 end 
                               4,5,6]
            assertMatrixEquals(expected=expected,
                               actual=out)
        }
    }

    @Test
    fun testCreateJaggedArray() {
        allBackends {
            var a = arrayOf(doubleArrayOf(1.0, 2.0, 3.0),
                            doubleArrayOf(4.0, 5.0, 6.0))
            var out = create(a)
            assert(out[1, 0] == 4.0)
            assert(out[3] == 4.0)
            assert(out[1, 1] == 5.0)
            assert(out[1] == 2.0)

            a = arrayOf(doubleArrayOf(1.0, 2.0, 3.0))
            out = create(a)
            assert(out[0, 2] == 3.0)
            assertFails { out[2, 0] }

            a = arrayOf(doubleArrayOf(1.0),
                        doubleArrayOf(2.0),
                        doubleArrayOf(3.0))
            out = create(a)
            assert(out[2, 0] == 3.0)
            assertFails { out[0, 2] }
        }
    }

    @Test
    fun testOnes() {
        allBackends {
            val a = ones(3, 5)
            assertEquals(a[4], 1.0)
            assertEquals(a[2, 2], 1.0)
            assertMatrixEquals(zeros(3, 5).fill { _, _ -> 1.0 }, a)
        }
    }

    @Test
    fun testEye() {
        allBackends {
            val a = eye(3)
            val expected = zeros(3, 3).mapIndexed { i, j, _ -> if (i == j) 1.0 else 0.0 }
            assertMatrixEquals(expected, a)
        }
    }

    @Test
    fun testArange() {
        allBackends {
            val a = arange(1.0, 1.5, .1)
            val expected = mat[1.0, 1.1, 1.2, 1.3, 1.4]
            assertMatrixEquals(expected, a)
        }
    }

    @Test
    fun testRandn() {
        allBackends {
            val a = 2 * randn(1, 1000000)
            Assert.assertEquals(0.0, mean(a), .01)
            val aAgg = zeros(1, 1000000).map { 2 * randn(1,1)[0] }
            Assert.assertEquals(0.0, mean(aAgg), .01)

            val b = randn(3, 3)
            val c = randn(3, 3)
            assertFalse { (b-c).any { it == 0.0 } }
        }
    }
    @Test
    fun testRand() {
        allBackends {
            val a = 2 * rand(1, 1000000)
            Assert.assertEquals(1.0, mean(a), .01)
            val aAgg = zeros(1, 1000000).map { 2 * rand(1, 1)[0] }
            Assert.assertEquals(1.0, mean(aAgg), .01)

            val b = rand(3, 3)
            val c = rand(3, 3)
            assertFalse { (b-c).any { it == 0.0 } }
        }
    }

    @Test
    fun testSeed() {
        allBackends {
            setSeed(4)
            val a = randn(30,30)
            val b = randn(30,30)
            setSeed(4)
            val c = randn(30,30)
            val d = randn(30,30)
            setSeed(5)
            val e = randn(30,30)
            val f = randn(30,30)

            assertMatrixEquals(a, c)
            assertMatrixEquals(b, d)

            assertFalse { allclose(a,b) }
            assertFalse { allclose(c,d) }
            assertFalse { allclose(e,f) }

            assertFalse { allclose(a,d) }
            assertFalse { allclose(b,c) }

            assertFalse { allclose(a,e) }
            assertFalse { allclose(b,f) }
        }
    }
}
