package koma

import koma.extensions.get
import org.junit.Assert
import org.junit.Test

class ArrayProductsTests {
    @Test
    fun testDot() {
        assert((ndArrayOf(1, 2, 3) dot ndArrayOf(4, 5, 6)) == 32L)
        Assert.assertEquals((ndArrayOf(1, 2, 3) dot ndArrayOf(4f, 5f, 6f)), 32f, 1e-5f)
        Assert.assertEquals((ndArrayOf(0.1, 0.2, 0.3) dot ndArrayOf(4, 5, 6)), 3.2, 1e-14)
        Assert.assertEquals((ndArrayOf(0.1f, 0.2f, 0.3f) dot ndArrayOf(4L, 5L, 6L)), 3.2, 1e-5)
    }
    
    @Test
    fun testInner() {
        val intVec = ndArrayOf(10, 20)
        val floatVec = ndArrayOf(10f, 20f)
        val doubleVec = ndArrayOf(10.0, 20.0)
        val intArr = ndArrayOf(1, 2, 3, 4, shape=intArrayOf(2, 2))
        val floatArr = ndArrayOf(1f, 2f, 3f, 4f, shape=intArrayOf(2, 2))
        val doubleArr = ndArrayOf(1.0, 2.0, 3.0, 4.0, shape=intArrayOf(2, 2))
        assert(allclose(intArr inner intVec, ndArrayOf(50, 110)))
        assert(allclose(intArr inner floatVec, ndArrayOf(50f, 110f)))
        assert(allclose(doubleArr inner floatVec, ndArrayOf(50.0, 110.0)))
        assert(allclose(intArr inner intArr, ndArrayOf(7, 10, 15, 22, shape=intArrayOf(2, 2))))
        assert(allclose(intArr.inner(intVec, 0, 0), ndArrayOf(70, 100)))
        assert(allclose(doubleArr.inner(floatArr, 0, 0), ndArrayOf(10.0, 14.0, 14.0, 20.0, shape=intArrayOf(2, 2))))
        assert(allclose(doubleVec.inner(intArr, 0, 0), ndArrayOf(70.0, 100.0)))
        assert(allclose(doubleVec.inner(intArr, 0, 1), ndArrayOf(50.0, 110.0)))
    }
    
    @Test
    fun testOuter() {
        val intVec = ndArrayOf(1, 2, 3)
        val floatVec = ndArrayOf(1f, 2f, 3f)
        val doubleVec = ndArrayOf(0.1, 0.2, 0.3)
        assert(allclose(intVec outer intVec, ndArrayOf(1, 2, 3, 2, 4, 6, 3, 6, 9, shape=intArrayOf(3, 3))))
        assert(allclose(intVec outer floatVec, ndArrayOf(1f, 2f, 3f, 2f, 4f, 6f, 3f, 6f, 9f, shape=intArrayOf(3, 3))))
        assert(allclose(floatVec outer doubleVec, ndArrayOf(0.1, 0.2, 0.3, 0.2, 0.4, 0.6, 0.3, 0.6, 0.9, shape=intArrayOf(3, 3))))
        val arr1 = ndArrayOf(1, 2, 3, 4, shape=intArrayOf(2, 2))
        val arr2 = ndArrayOf(1.0, 10.0, 100.0, 1000.0, shape=intArrayOf(2, 2))
        val prod = arr1 outer arr2
        assert(prod.shape().toIntArray() contentEquals intArrayOf(2, 2, 2, 2))
        for (i in 0..1)
            for (j in 0..1)
                for (k in 0..1)
                    for (m in 0..1)
                        assert(prod[i, j, k, m] == arr1[i, j]*arr2[k, m])
    }
}