package golem

import golem.ndarray.purekt.*
import org.junit.Test

class NDTests {
    @Test
    fun testIndexing() {
        val arr = PureKtNDArray<Double>(3,5,4) { idxs -> 1.0 }
        assert(arr[1,2,3] == 1.0)
        arr[1,2,3] = 5.5
        assert(arr[1,2,3] == 5.5)
        assert(arr[1,2,2] == 1.0)
        assert(arr[1,3,0] == 1.0)
    }
    @Test
    fun testObject() {
        val arr = PureKtNDArray<String>(2,5,2) { idxs -> "str #${idxs[0]},${idxs[1]},${idxs[2]}" }
        assert(arr[1,4,0] == "str #1,4,0")
        arr[0,0,1] = "changed"
        assert(arr[0,0,1] == "changed")
    }
}
