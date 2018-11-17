package koma

import koma.extensions.*
import koma.internal.default.generated.ndarray.DefaultIntNDArray
import koma.matrix.Matrix
import koma.ndarray.*
import koma.util.test.assertMatrixEquals
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith

class NDTests {
    @Test
    fun testElementMath() {
        for (arr in listOf(NDArray.createGeneric(2,3,4) { 4.5 }, NDArray(2,3,4) { 4.5 })) {
            assert((arr+5.0)[0,0,0] == 9.5)
            assert((arr/2.0)[1,1,1] == 2.25)
            assert((-arr[1,2,2] == -4.5))
            assert((arr*arr)[1,1,1] == 4.5*4.5)
            assert((arr*4.0)[1,2,2] == 4.5*4)
            assert((arr+arr+2.0)[0,0,1] == 4.5*2+2.0)
            val arr2 = NDArray(2,3,4,5,6) { it[0].toDouble() }
            (arr2 * 4.0 + 5.0 - 7.0).also {
                assert(it[0,1,1,1,1] == 0*4+5-7.0)
                assert(it[1,1,1,1,1] == 1*4+5-7.0)
            }
        }
    }
    @Test
    fun testIndexing() {
        val arr = NDArray(3, 5, 4) { 1.0 }
        assert(arr[1,2,3] == 1.0)
        arr[1,2,3] = 5.5
        assert(arr[1,2,3] == 5.5)
        assert(arr[1,2,2] == 1.0)
        assert(arr[1,3,0] == 1.0)
    }
    @Test
    fun testNegativeIndices() {
        val arr = ndArrayOf(1, 2, 3, 4, shape=intArrayOf(2, 2))
        assert(arr[-2,-2] == 1)
        assert(arr[0,-1] == 2)
        assert(arr[-1,0] == 3)
        arr[-2,-2] = 5
        arr[1,-1] = 6
        assert(arr[0,0] == 5)
        assert(arr[1,1] == 6)
    }
    @Test
    fun testObject() {
        val arr = NDArray(2, 5, 2) { idx -> "str #${idx[0]},${idx[1]},${idx[2]}" }
        assert(arr[1,4,0] == "str #1,4,0")
        arr[0,0,1] = "changed"
        assert(arr[0,0,1] == "changed")
        
        val arr2 = NDArray<Any?>(5, 5, 5, 2, 2) { null }
        arr2[0,0,0,1,1] = "str"
        arr2[0,0,0,1,0] = listOf(1,2,3)
        
        arr2[3,2,3,1,0] = arr2[0,0,0,1,0]
        assert(arr2[3,2,3,1,0] == listOf(1,2,3))

        arr2[3,2,3,1,1] = arr2[0,0,0,1,1]
        assert(arr2[3,2,3,1,1] == "str")

        arr2[3,2,3,1,1] = arr2[3,2,3,1,0]

        assert(arr2[3,2,3,1,1] == listOf(1,2,3))
        
    }
    @Test
    fun testRangeAccess() {
        val arr = NDArray(4, 4) { idx -> idx[0] }
        val col = arr[0..2,0..0]
        val row = arr[0..0,0..1]
        val square = arr[0..2,0..2]
        
        assert(col[0,0] == 0 && col[2,0] == 2)
        assert(row[0,0] == 0 && row[0,1] == 0)

        assert(square[0,0] == 0 && square[0,1] == 0)
        assert(square[2,0] == 2 && square[2,2] == 2)
        
        arr[1, 1] = square

        assert(arr[0,0] == 0 && arr[0,1] == 0)
        assert(arr[1,0] == 1 && arr[2,0] == 2)
        
        assert(arr[1,1] == 0 && arr[1,2] == 0)
        assert(arr[2,1] == 1 && arr[2,2] == 1)
        
    }
    @Test
    fun testRangeAccessFails() {
        val arr = NDArray(4, 4) { idx -> idx[0] }
        val square = arr[0..2,0..2]
        arr[1, 1] = square
        val square2 = arr[0..3,0..3]
        arr[0, 0] = square2
        assertFails { arr[1, 1] = square2 }
    }

    @Test
    fun testSize() {
        assert(NDArray<Any?>(2, 3) { 0 }.size == 6)
        assert(NDArray<Any?>(2, 3, 4) { 0 }.size == 24)
        assert(NDArray<Any?>(4, 0, 7) { 0 }.size == 0)
    }

    @Test
    fun testShape() {
        assert(NDArray<Any?>(3, 3) { idx -> idx[0] }.shape() == listOf(3, 3))
        assert(NDArray<Any?>(5, 3, 1) { idx -> idx[0] }.shape() == listOf(5, 3, 1))
    }
    
    @Test
    fun testCopy() {
        val a = NDArray<Int>(3, 3) { 5 }
        val b = a.copy()
        assert(a[1,1]==5)
        assert(b[1,1]==5)
        b[1,1]=35
        assert(a[1,1]==5)
        assert(b[1,1]==35)
    }
    
    @Test
    fun testBadIndexing() {
        val a = NDArray<Int>(3, 3, 3) { 5 }
        a[1,1,1]
        assertFailsWith<IllegalArgumentException> {
            a[5,6,7]
        }
        assertFailsWith<IllegalArgumentException> {
            a[5,6]
        }
    }

    @Test
    fun testMappers() {
        val a: NDArray<Int> = NDArray(5, 3, 4) { idx -> idx[0] * 2 + idx[1] * 3 }

        assert(a[1,2,1] == 1*2 + 2*3)
        assert(a[3,1,3] == 3*2 + 1*3)

        var b = a.map { it+1 }

        assert(b[1,2,1] == 1*2 + 2*3 + 1)
        assert(b[3,1,3] == 3*2 + 1*3 + 1)
        
        b = a.mapIndexed { idx, _ -> idx}

        assert(b[0,0,1] == 1)
        assert(b[0,1,0] == 4)
        assert(b[0,1,2] == 6)
        
        b = a.mapIndexedN { idx, ele -> idx[0]+idx[1]+idx[2]+ele }

        assert(b[0,0,1] == 1)
        assert(b[0,1,0] == 3+1)
        assert(b[0,1,2] == 3+3)
    }

    @Test
    fun testFors() {
        val a: NDArray<Int> = NDArray(5, 3, 4) { idx -> idx[0] * 2 + idx[1] * 3 }

        assert(a[1, 2, 1] == 1 * 2 + 2 * 3)
        assert(a[3, 1, 3] == 3 * 2 + 1 * 3)

        a.forEachIndexedN { idx, ele -> assert(ele == idx[0] * 2 + idx[1] * 3) }
        a.forEachIndexed { idx, ele -> assert(ele == a.getInt(idx)) }

        var sum = 0
        var count = 0
        a.forEach { sum += it; count+=1 }
        
        var sum2 = 0
        a.forEachIndexedN { _, ele -> sum2 += ele }
        
        assert(sum == sum2)
        assert(count == a.size)
    }
    @Test
    fun testToMatrixOrNull() {
        assert(NDArray.createGeneric<Double>(1,2){5.5}.toMatrixOrNull() != null)
        assert(NDArray.createGeneric<Double>(6){5.5}.toMatrixOrNull() != null)
        assert(NDArray.createGeneric<Long>(1,2){5}.toMatrixOrNull() == null)
        assert(NDArray.createGeneric<String>(1,2){"a"}.toMatrixOrNull() == null)
        assert(NDArray.doubleFactory.zeros(3,3).toMatrixOrNull() != null)
        assert(NDArray.doubleFactory.zeros(3).toMatrixOrNull() != null)
        assert(NDArray.doubleFactory.zeros(3,3,5).toMatrixOrNull() == null)
        assert(NDArray.intFactory.zeros(3,3,5,9).toMatrixOrNull() == null)
        val a = NDArray.createGeneric<Any>(1,2){ it ->
            if(it[1]==0)
                1.1
            else
                "1.1"
        }
        assert(a.toMatrixOrNull() == null)
    }
    @Test
    fun testMagicConstructor() {
        assert(NDArray(1, 2, 3) { it.reduce { a, b -> a + b } } is DefaultIntNDArray)
        assert(NDArray(1, 2, 3) { it.reduce { a, b -> a + b } }[0, 1, 2] == 3)
        assert(NDArray(1, 2, 3) { "${it.toList()}" }[0, 1, 1] == listOf(0,1,1).toString())
    }


    @Test
    fun testReshapeND() {
        var c = 0
        val a = NDArray(1, 2, 3, 4) { ++c }

        c = 0
        val e = NDArray(4, 6) { ++c }
        assertEquals(e.shape(), a.reshape(4, 6).shape())
        assertEquals(e.toList(), a.reshape(4, 6).toList())

        assertFails("cannot be reshaped") { a.reshape(7) }
        assertFails("cannot be reshaped") { a.reshape(9, 9, 9) }
    }

    @Test
    fun testReshapeNDBecomesMatirx() {
        var c = 0
        val a = NDArray(2, 2, 3) { ++c }
        c = 0
        val e = Matrix(6, 2) { _, _ -> ++c }

        assertMatrixEquals(e, a.reshape(6, 2))
    }

    @Test
    fun testReshapeNDDoesNotTryToMatrixNonNumerical() {
        var c = 0
        val a = NDArray(1, 4) { "${c++}" }
        c = 0
        val e = NDArray(2, 2) { "${c++}" }

        assertEquals(e.shape(), e.reshape(2, 2).shape())
        assertEquals(e.toList(), a.toList())
    }
    
    @Test
    fun testMapBetweenTypes() {
        val ints = ndArrayOf(5, 10, 0, 3, shape=intArrayOf(2, 2))
        val doubles = ndArrayOf(5.0, 10.0, 0.0, 3.0, shape=intArrayOf(2, 2))
        val strings = ndArrayOf("5", "10", "0", "3", shape=intArrayOf(2, 2))
        val doublesFromInts = ints.map { it.toDouble() }
        val stringsFromInts = ints.map { it.toString() }
        val intsFromStrings = strings.map { it.toInt() }

        assertEquals(doubles.toList(), doublesFromInts.toList())
        assertEquals(strings.toList(), stringsFromInts.toList())
        assertEquals(ints.toList(), intsFromStrings.toList())
    }

    @Test
    fun testGetSlice() {
        var c = 0
        val a = NDArray(3, 3, 3) { ++c }
        val b = a[0..1, 0, listOf(0, -1)]
        assert(b.shape().toIntArray() contentEquals intArrayOf(2, 2))
        assert(b[0,0] == a[0,0,0])
        assert(b[0,1] == a[0,0,2])
        assert(b[1,0] == a[1,0,0])
        assert(b[1,1] == a[1,0,2])
    }

    @Test
    fun testSetSlice() {
        var c = 0
        val a = NDArray(3, 3, 3) { ++c }
        val b = a.copy()
        b[0..1, 0, listOf(0, -1)] = -1
        for (i in 0..2)
            for (j in 0..2)
                for (k in 0..2) {
                    if ((i == 0 || i == 1) && (j == 0) && (k == 0 || k == 2))
                        assert(b[i,j,k] == -1)
                    else
                        assert(b[i,j,k] == a[i,j,k])
                }
    }

    @Test
    fun testSetSliceToArray() {
        var c = 0
        val a = NDArray(3, 3, 3) { ++c }
        val b = a.copy()
        b[0..1, 0, listOf(0, -1)] = ndArrayOf(-1, -2, -3, -4, shape=intArrayOf(2, 2))
        val d = a.copy()
        assert(a.allClose(d))
        assert(!b.allClose(d))
        d[0,0,0] = -1
        d[0,0,2] = -2
        d[1,0,0] = -3
        d[1,0,2] = -4
        assert(b.allClose(d))
    }
}
