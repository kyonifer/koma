package golem

import golem.util.test.assertMatrixEquals
import golem.util.test.allBackends
import org.junit.Test

class MatrixFuncsTests {
    @Test
    fun testExpm()
    {
        allBackends {
            assertMatrixEquals(expm(eye(3)), E*eye(3))

            val expected = mat[20.08553692318771,   0,                 0 end
                               0,                   20.08553692318771, 0 end
                               0,                   0,                 20.08553692318771]
            assertMatrixEquals(expm(eye(3)*3), expected)

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

}