package koma

import koma.extensions.*
import koma.ndarray.NDArray
import org.junit.Test

class NumNDTests {
    @Test
    fun testOperators() {
        val testArrs = arrayOf (
                NDArray.doubleFactory.create(3,5,4) { idx -> idx[0].toDouble() },
                NDArray(3,5,4) { idx -> idx[0].toDouble() },
                NDArray.createGeneric<Double>(3,5,4) { idx -> idx[0].toDouble() }
        )

        for (arr in testArrs) {
            assert(arr[0,0,0] == 0.0 && arr[0,0,1] == 0.0 && arr[0,1,0] == 0.0)
            assert(arr[1,0,0] == 1.0 && arr[1,0,1] == 1.0 && arr[1,1,0] == 1.0)

            val out = arr + arr
            assert(out[0,0,0] == 0.0 && out[0,0,1] == 0.0 && out[0,1,0] == 0.0)
            assert(out[1,0,0] == 2.0 && out[1,0,1] == 2.0 && out[1,1,0] == 2.0)

            val out2 = arr - arr*4.0 - 1.5
            assert(out2[0,0,0] == -1.5 && out2[0,0,1] == -1.5 && out2[0,1,0] == -1.5)
            assert(out2[2,0,0] == -7.5 && out2[2,0,1] == -7.5 && out2[2,1,0] == -7.5)
        }
    }
    /*
    TODO: Re-enable when autogen for operators is done.
    @Test
    fun testFloatOperators() {
        val arr = DefaultFloatNDArray(3,5,4) { idx -> idx[0].toFloat() }
        assert(arr[0,0,0] == 0.0f && arr[0,0,1] == 0.0f && arr[0,1,0] == 0.0f)
        assert(arr[1,0,0] == 1.0f && arr[1,0,1] == 1.0f && arr[1,1,0] == 1.0f)

        val out = arr + arr
        assert(out[0,0,0] == 0.0f && out[0,0,1] == 0.0f && out[0,1,0] == 0.0f)
        assert(out[1,0,0] == 2.0f && out[1,0,1] == 2.0f && out[1,1,0] == 2.0f)

        val out2 = arr - arr*4.0f - 1.5f
        assert(out2[0,0,0] == -1.5f && out2[0,0,1] == -1.5f && out2[0,1,0] == -1.5f)
        assert(out2[2,0,0] == -7.5f && out2[2,0,1] == -7.5f && out2[2,1,0] == -7.5f)
    }
    @Test
    fun testIntOperators() {
        val arr = DefaultIntNDArray(3,5,4) { idx -> idx[0] }
        assert(arr[0,0,0] == 0 && arr[0,0,1] == 0 && arr[0,1,0] == 0)
        assert(arr[1,0,0] == 1 && arr[1,0,1] == 1 && arr[1,1,0] == 1)

        val out = arr + arr
        assert(out[0,0,0] == 0 && out[0,0,1] == 0 && out[0,1,0] == 0)
        assert(out[1,0,0] == 2 && out[1,0,1] == 2 && out[1,1,0] == 2)
    }

     */
}
