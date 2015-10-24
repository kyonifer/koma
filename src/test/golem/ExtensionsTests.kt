package golem

import golem.util.test.allBackends
import golem.util.test.assertMatrixEquals
import org.junit.Test

class ExtensionsTests {

    @Test
    fun testCumSum() {
        allBackends {
            var a = mat[1,2,3,4,1]
            var expected = mat[1,3,6,10,11]
            assertMatrixEquals(expected, cumsum(a))

            a = mat[1,2 end
                    3,4]
            expected= mat[1,3,6,10]
            var zz = cumsum(a)
            assertMatrixEquals(expected, cumsum(a))
        }
    }

    @Test
    fun testMapRows() {
        allBackends {
            var a = mat[1, 2, 3 end
                    4, 5, 6 end
                    7, 8, 9]

            var expected = mat[3, 2, 1, 3 end
                    6, 5, 4, 6 end
                    9, 8, 7, 9]

            assertMatrixEquals(expected, a.mapRows { mat[it[2], it[1], it[0], it[2]] })
        }
    }

    @Test
    fun testMapCols() {
        allBackends {
            var a = mat[1, 2, 3 end
                        4, 5, 6 end
                        7, 8, 9]

            var expected = mat[7, 8, 9 end
                               4, 5, 6 end
                               1, 2, 3 end
                               7, 8, 9]

            assertMatrixEquals(expected, a.mapCols { mat[it[2], it[1], it[0], it[2]] })
        }
    }

    @Test
    fun testMapElements() {
        allBackends {
            var a = mat[1,2,3 end
                        4,5,6 end
                        7,8,9]

            var expected = mat[.5,  1,   1.5 end
                               2,   2.5, 3   end
                               3.5, 4,   4.5]
            assertMatrixEquals(expected, a.mapElements{it/2})
        }
    }

    @Test
    fun testMapIterator() {
        allBackends {
            var a = mat[1,2,3 end
                        4,5,6 end
                        7,8,9]
            var out = a.map{it.toString()}
            assert(out[0].equals("1.0"))
            assert(out[4].equals("5.0"))
        }
    }
}