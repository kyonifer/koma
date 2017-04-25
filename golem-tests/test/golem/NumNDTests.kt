package golem

import golem.ndarray.purekt.*
import org.junit.Test

class NumNDTests {
    @Test
    fun testOperators() {
        val arr = DoublePureKtNDArray(3,5,4) { idx -> idx[0].toDouble() }
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

