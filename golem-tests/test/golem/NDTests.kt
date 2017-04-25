package golem

import golem.ndarray.*
import golem.ndarray.purekt.*
import org.junit.Test
import kotlin.test.assertFails
import kotlin.test.assertFailsWith

class NDTests {
    @Test
    fun testIndexing() {
        val arr = PureKtNDArray(3,5,4) { idxs -> 1.0 }
        assert(arr[1,2,3] == 1.0)
        arr[1,2,3] = 5.5
        assert(arr[1,2,3] == 5.5)
        assert(arr[1,2,2] == 1.0)
        assert(arr[1,3,0] == 1.0)
    }
    @Test
    fun testObject() {
        val arr = PureKtNDArray(2,5,2) { idx -> "str #${idx[0]},${idx[1]},${idx[2]}" }
        assert(arr[1,4,0] == "str #1,4,0")
        arr[0,0,1] = "changed"
        assert(arr[0,0,1] == "changed")
        
        val arr2 = PureKtNDArray<Any?>(5,5,5,2,2) { idx -> null }
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
        val arr = PureKtNDArray(4,4) { idx -> idx[0] }
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
        val arr = PureKtNDArray(4,4) { idx -> idx[0] }
        val square = arr[0..2,0..2]
        arr[1, 1] = square
        val square2 = arr[0..3,0..3]
        arr[0, 0] = square2
        assertFails { arr[1, 1] = square2 }
    }
    
    @Test
    fun testShape() {
        assert(PureKtNDArray<Any?>(3,3) { idx -> idx[0] }.shape() == listOf(3,3))
        assert(PureKtNDArray<Any?>(5,3,1) { idx -> idx[0] }.shape() == listOf(5,3,1))
    }
    
    @Test
    fun testCopy() {
        val a = PureKtNDArray<Int>(3,3) { idx -> 5 }
        val b = a.copy()
        assert(a[1,1]==5)
        assert(b[1,1]==5)
        b[1,1]=35
        assert(a[1,1]==5)
        assert(b[1,1]==35)
    }
    
    @Test
    fun testBadIndexing() {
        val a = PureKtNDArray<Int>(3,3,3) { idx -> 5 }
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
        val a: NDArray<Int> = PureKtNDArray(5, 3, 4) { idx -> idx[0] * 2 + idx[1] * 3 }

        assert(a[1,2,1] == 1*2 + 2*3)
        assert(a[3,1,3] == 3*2 + 1*3)

        var b = a.map { it+1 }

        assert(b[1,2,1] == 1*2 + 2*3 + 1)
        assert(b[3,1,3] == 3*2 + 1*3 + 1)
        
        b = a.mapIndexed { idx, ele -> idx}

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
        val a: NDArray<Int> = PureKtNDArray(5, 3, 4) { idx -> idx[0] * 2 + idx[1] * 3 }

        assert(a[1, 2, 1] == 1 * 2 + 2 * 3)
        assert(a[3, 1, 3] == 3 * 2 + 1 * 3)

        a.forEachIndexedN { idx, ele -> assert(ele == idx[0] * 2 + idx[1] * 3) }
        a.forEachIndexed { idx, ele -> assert(ele == a.getLinear(idx)) }

        var sum = 0
        var count = 0
        a.forEach { sum += it; count+=1 }
        
        var sum2 = 0
        a.forEachIndexedN { idx, ele -> sum2 += ele }
        
        assert(sum == sum2)
        assert(count == a.shape().reduce{ l,r -> l*r })
    }
}
