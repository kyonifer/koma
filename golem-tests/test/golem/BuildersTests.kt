package golem

import golem.util.test.*
import org.junit.Test
import kotlin.test.assertFailsWith

class BuildersTests {

    @Test
    fun testBuilder() {
        allBackends {
            var a = mat[1, 2, 3, 4, 5 end
                    1, 2, 3, 4.0, 5]
            var expected = zeros(2, 5).fill { i, j -> j + 1.0 }
            assertMatrixEquals(expected, a)
        }
    }

    @Test
    fun testBuilderFail() {
        allBackends {
            assertFailsWith(IllegalArgumentException::class,
                            "When building matrices with mat[] please give even rows/cols") {
                mat[1, 2, 3 end
                        2]
            }
            assertFailsWith(IllegalArgumentException::class,
                            "When building matrices with mat[] please give even rows/cols") {
                mat[1, 2, 3 end
                        2, 3]
            }
            assertFailsWith(IllegalArgumentException::class,
                            "When building matrices with mat[] please give even rows/cols") {
                mat[1, 2, 3 end
                        2, 3]
            }
        }
    }
}