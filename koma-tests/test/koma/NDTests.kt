package koma

import koma.extensions.*
import koma.ndarray.*
import koma.ndarray.default.*
import org.junit.Test
import kotlin.test.assertFails
import kotlin.test.assertFailsWith

class NDTests {
    @Test
    fun testElementMath() {
        for (arr in listOf(DefaultNDArray(2,3,4) { 4.5 }, DefaultDoubleNDArray(2,3,4) { 4.5 })) {
            assert((arr+5.0)[0,0,0] == 9.5)
            assert((arr/2.0)[1,1,1] == 2.25)
            assert((-arr[1,2,2] == -4.5))
            assert((arr*arr)[1,1,1] == 4.5*4.5)
            assert((arr*4.0)[1,2,2] == 4.5*4)
            assert((arr+arr+2.0)[0,0,1] == 4.5*2+2.0)
            val arr2 = DefaultNDArray(2,3,4,5,6) { it[0].toDouble() }
            (arr2 * 4.0 + 5.0 - 7.0).also {
                assert(it[0,1,1,1,1] == 0*4+5-7.0)
                assert(it[1,1,1,1,1] == 1*4+5-7.0)
            }
        }
    }
    @Test
    fun testIndexing() {
        val arr = DefaultNDArray(3, 5, 4) { 1.0 }
        assert(arr[1,2,3] == 1.0)
        arr[1,2,3] = 5.5
        assert(arr[1,2,3] == 5.5)
        assert(arr[1,2,2] == 1.0)
        assert(arr[1,3,0] == 1.0)
    }
    @Test
    fun testObject() {
        val arr = DefaultNDArray(2, 5, 2) { idx -> "str #${idx[0]},${idx[1]},${idx[2]}" }
        assert(arr[1,4,0] == "str #1,4,0")
        arr[0,0,1] = "changed"
        assert(arr[0,0,1] == "changed")
        
        val arr2 = DefaultNDArray<Any?>(5, 5, 5, 2, 2) { null }
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
        val arr = DefaultNDArray(4, 4) { idx -> idx[0] }
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
        val arr = DefaultNDArray(4, 4) { idx -> idx[0] }
        val square = arr[0..2,0..2]
        arr[1, 1] = square
        val square2 = arr[0..3,0..3]
        arr[0, 0] = square2
        assertFails { arr[1, 1] = square2 }
    }

    @Test
    fun testSize() {
        assert(DefaultNDArray<Any?>(2, 3) { 0 }.size == 6)
        assert(DefaultNDArray<Any?>(2, 3, 4) { 0 }.size == 24)
        assert(DefaultNDArray<Any?>(4, 0, 7) { 0 }.size == 0)
    }

    @Test
    fun testShape() {
        assert(DefaultNDArray<Any?>(3, 3) { idx -> idx[0] }.shape() == listOf(3, 3))
        assert(DefaultNDArray<Any?>(5, 3, 1) { idx -> idx[0] }.shape() == listOf(5, 3, 1))
    }
    
    @Test
    fun testCopy() {
        val a = DefaultNDArray<Int>(3, 3) { 5 }
        val b = a.copy()
        assert(a[1,1]==5)
        assert(b[1,1]==5)
        b[1,1]=35
        assert(a[1,1]==5)
        assert(b[1,1]==35)
    }
    
    @Test
    fun testBadIndexing() {
        val a = DefaultNDArray<Int>(3, 3, 3) { 5 }
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
        val a: NDArray<Int> = DefaultNDArray(5, 3, 4) { idx -> idx[0] * 2 + idx[1] * 3 }

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
        val a: NDArray<Int> = DefaultNDArray(5, 3, 4) { idx -> idx[0] * 2 + idx[1] * 3 }

        assert(a[1, 2, 1] == 1 * 2 + 2 * 3)
        assert(a[3, 1, 3] == 3 * 2 + 1 * 3)

        a.forEachIndexedN { idx, ele -> assert(ele == idx[0] * 2 + idx[1] * 3) }
        a.forEachIndexed { idx, ele -> assert(ele == a.getLinear(idx)) }

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
}
