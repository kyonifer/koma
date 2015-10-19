package golem

import golem.matrix.MatrixFactory
import golem.util.assertMatrixEquals
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Test each back-end for similar compliance with API
 */
class pylab_tests {
    var facs = arrayOf(golem.matrix.ejml.EJMLMatrixFactory(),
                           golem.matrix.mtj.MTJMatrixFactory())

    fun allBackends(f:()->Unit)
    {
        for (fac in facs) {
            println("Testing $f\n")
            golem.factory = fac
            f()
        }

    }


    @Test
    fun testSin()
    {
        var a = zeros(2,2)
        a[0,0] = PI/2
        a[0,1] = 3.0
        a[1,0] = -PI/2

        var expected = mat[ 1.0, 0.14112000805986721 end
                           -1.0, 0.0]

        assertMatrixEquals(sin(a), expected)

    }

    @Test
    fun testRandn()
    {
        var a = 2*randn(1,1000000)

        Assert.assertEquals(mean(a), 0.0, .01)
    }

    @Test
    fun testEPow()
    {
        var a = mat[1,2 end
                    3,4]
        a = a epow 3

        var aE = mat[ 1 pow 3,  2 pow 3 end
                     (3 pow 3), 4 pow 3]

        assertMatrixEquals(a, aE)
    }

    @Test
    fun testPow()
    {
        var a = mat[2,0,0 end
                    0,1,0 end
                    0,0,4]

        var a3 = a pow 3

        assertMatrixEquals(a3, a*a*a)
    }
}

