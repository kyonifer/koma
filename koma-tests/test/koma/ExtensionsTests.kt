package koma

import koma.extensions.*
import koma.util.test.*
import org.junit.Test
import kotlin.test.assertFails

//@formatter:off

class ExtensionsTests {

    @Test
    fun testSelectRows() {
        allBackends { 
            val a = mat[1, 2, 3, 4 end 
                        5, 6, 7, 8 end 
                        9,10,11,12]
            val exp = mat[1, 2, 3, 4 end 
                          9,10,11,12 end 
                          9,10,11,12 end 
                          1, 2, 3, 4]
            assertMatrixEquals(expected=exp,
                               actual=a.selectRows(0,2,2,0))
            assertMatrixEquals(expected=exp,
                               actual=a.selectRows(mat[0,2,2,0]))
            assertMatrixEquals(expected=exp,
                               actual=a.selectRows(mat[0,2 end
                                                       2,0]))
            
        }
    }
    @Test
    fun testSelectCols() {
        allBackends { 
            val a = mat[1, 2, 3, 4 end 
                        5, 6, 7, 8 end 
                        9,10,11,12]
            val exp = mat[1, 4, 3, 3 end 
                          5, 8, 7, 7 end 
                          9,12,11,11]
            assertMatrixEquals(expected=exp,
                               actual=a.selectCols(0,3,2,2))
            assertMatrixEquals(expected=exp,
                               actual=a.selectCols(mat[0,3,2,2]))
            assertMatrixEquals(expected=exp,
                               actual=a.selectCols(mat[0,3 end
                                                       2,2]))
        }
    }

    @Test
    fun testFilterRows() {
        allBackends {
            val a = mat[1, 2, 3, 4 end
                    5, 6, 7, 8 end
                    9,10,11,12]
            val exp = mat[1, 2, 3, 4 end
                    9,10,11,12]

            assertMatrixEquals(expected=exp,
                    actual=a.filterRows { it[0] != 5.0 })

        }
    }

    @Test
    fun testFilterRowsIndexed() {
        allBackends {
            val a = mat[1, 2, 3, 4 end
                    5, 6, 7, 8 end
                    9,10,11,12]
            val exp = mat[5, 6, 7, 8]

            assertMatrixEquals(expected=exp,
                    actual=a.filterRowsIndexed { idx, row ->
                        idx != 2 && row[2] != 3.0
                    })
        }
    }

    @Test
    fun testFilterCols() {
        allBackends {
            val a = mat[1, 2,   3, 4 end
                        5, 6,   7, 8 end
                        9, 10, 11, 12]

            val exp = mat[1,    3, 4 end
                          5,    7, 8 end
                          9,   11, 12]

            assertMatrixEquals(expected=exp,
                    actual=a.filterCols { it[0] != 2.0 })
        }
    }

    @Test
    fun testFilterColsIndexed() {
        allBackends {
            val a = mat[1, 2,   3, 4 end
                        5, 6,   7, 8 end
                        9, 10, 11, 12]

            val exp = mat[1,    3 end
                          5,    7 end
                          9,   11]

            assertMatrixEquals(expected=exp,
                    actual=a.filterColsIndexed { idx, col ->
                        col[0] != 2.0 && idx != 3
                    })
        }
    }
    
    @Test
    fun testCumSum() {
        allBackends {
            var a = mat[1, 2, 3, 4, 1]
            var expected = mat[1, 3, 6, 10, 11]
            assertMatrixEquals(expected, cumsum(a))

            a = mat[1, 2 end
                    3, 4]
            expected = mat[1, 3, 6, 10]
            assertMatrixEquals(expected, cumsum(a))
        }
    }

    @Test
    fun testFill() {
        allBackends {
            val a = zeros(2, 2)
            a.fill { i, j -> i + j * 1.0 }
            val expected = mat[0, 1 end
                               1, 2]

            assertMatrixEquals(expected, a)
        }
    }

    @Test
    fun testEachRow() {
        allBackends {
            val a = mat[1, 0, 3 end
                        5, 1, 6]

            val out = arrayOf(0, 0)

            a.forEachRow { out[it[1].toInt()] = it[0].toInt() }

            assert(out[0] == 1)
            assert(out[1] == 5)
        }
    }

    @Test
    fun testEachCol() {
        allBackends {
            val a = mat[1, 0, 2 end
                        -2, 2, 6]

            val out = arrayOf(0, 0, 0)

            a.forEachCol { out[it[0].toInt()] = it[1].toInt() }

            assert(out[0] == 2)
            assert(out[1] == -2)
            assert(out[2] == 6)

        }
    }

    @Test
    fun testEach() {
        allBackends {
            val a = mat[1, 0, 2 end
                        -2, 2, 6]

            var out = 0.0

            a.forEach { out = it + 3.0 }

            assert(out == 9.0)
        }
    }

    @Test
    fun testEachIndexed() {
        allBackends {
            val a = mat[1, 2, 3 end
                        4, 5, 6]
            val out = zeros(2, 3)
            a.forEachIndexed { row, col, ele ->
                out[row, col] = ele + 3
            }
            assertMatrixEquals(a + 3, out)
        }
    }

    @Test
    fun testMapElements() {
        allBackends {
            val a = mat[1, 2, 3 end
                        4, 5, 6 end
                        7, 8, 9]

            val expected = mat[.5, 1, 1.5 end
                               2, 2.5, 3   end
                               3.5, 4, 4.5]
            assertMatrixEquals(expected, a.map { it / 2 })
        }
    }

    @Test
    fun testMapElementsIndexed() {
        allBackends {
            val a = mat[1, 2, 3 end
                        4, 5, 6 end
                        7, 8, 9]

            val expected = mat[0, 0, 0   end
                               0, 2.5, 6   end
                               0, 8, 18]
            val out = a.mapIndexed { row, col, ele ->
                ele / 2 * row * col
            }
            assertMatrixEquals(expected, out)
        }
    }

    @Test
    fun testMapRows() {
        allBackends {
            val a = mat[1, 2, 3 end
                        4, 5, 6 end
                        7, 8, 9]

            val expected = mat[3, 2, 1, 3 end
                               6, 5, 4, 6 end
                               9, 8, 7, 9]

            assertMatrixEquals(expected, a.mapRows { mat[it[2], it[1], it[0], it[2]] })
        }
    }

    @Test
    fun testMapRowsToList() {
        allBackends {
            val a = mat[1, 2, 3 end
                        4, 5, 6]
            val out = a.mapRowsToList {
                it[0].toString()
            }

            assert(out[1] == "4.0")

        }
    }

    @Test
    fun testMapCols() {
        allBackends {
            val a = mat[1, 2, 3 end
                        4, 5, 6 end
                        7, 8, 9]

            val expected = mat[7, 8, 9 end
                               4, 5, 6 end
                               1, 2, 3 end
                               7, 8, 9]

            assertMatrixEquals(expected, a.mapCols { mat[it[2], it[1], it[0], it[2]] })
        }
    }

    @Test
    fun testMapColsToList() {
        allBackends {
            val a = mat[1, 2, 3 end
                        4, 5, 6]
            val out = a.mapColsToList {
                it[0].toString()
            }

            assert(out[1] == "2.0")

        }
    }

    @Test
    fun testTo2DArray() {
        allBackends {
            val a = mat[1, 2, 3 end
                        4, 5, 6]
            val expected = arrayOf(doubleArrayOf(1.0, 2.0, 3.0),
                                   doubleArrayOf(4.0, 5.0, 6.0))

            val out = a.to2DArray()
            for (row in 0..1)
                for (col in 0..2)
                    assert(out[row][col] == expected[row][col])

            assert(out[1][0] == 4.0)
        }
    }

    @Test
    fun testAny() {
        allBackends {
            val a = mat[1, 2, 3 end
                        4, 5, 6 end
                        7, 8, 9]
            assert(a.any { it > 8.5 })
            assert(a.any { it > 7 })
            assert(!a.any { it > 9 })
        }
    }

    @Test
    fun testAll() {
        allBackends {
            val a = mat[1, 2, 3 end
                        4, 5, 6 end
                        7, 8, 9]
            assert(a.all { it > 0.9 })
            assert(!a.all { it > 1.0 })
            assert(!a.all { it > 8 })
        }
    }

    @Test
    fun testMapFromIterable() {
        allBackends {
            val a = mat[1, 2, 3 end
                        4, 5, 6 end
                        7, 8, 9]
            val out = a.toIterable().map { it.toString() }
            assert(out[0] == "1.0")
            assert(out[4] == "5.0")
        }
    }

    @Test
    fun testReshape() {
        allBackends {
            val a = mat[1,   2,  3 end
                        4,   5,  6 end
                        7,   8,  9 end
                        10, 11, 12]
            assertMatrixEquals(mat[ 1,  2 end
                                    3,  4 end
                                    5,  6 end
                                    7,  8 end
                                    9, 10 end
                                   11, 12], a.reshape(6, 2))

            assertFails("cannot be reshaped") {
                a.reshape(6, 3)
            }

            assertFails("cannot be reshaped") {
                a.reshape(2, 2)
            }
        }
    }

}
