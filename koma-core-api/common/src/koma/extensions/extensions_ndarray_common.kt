@file:koma.internal.JvmName("NDArray")
@file:koma.internal.JvmMultifileClass

package koma.extensions

import koma.ndarray.NDArray

/**
 * Determine whether every element of one NDArray is sufficiently close to the corresponding element
 * of another array.  Two elements are considered close if
 *
 * abs(ele1 - ele2) < (atol + rtol * abs(ele1))
 *
 * @param other   the array to compare this one to
 * @param rtol    the maximum relative (i.e. fractional) difference to allow between elements
 * @param atol    the maximum absolute difference to allow between elements
 */
fun <T: Number, R: Number> NDArray<T>.allClose(other: NDArray<R>, rtol: Double=1e-05, atol: Double=1e-08): Boolean {
    if (!(shape().toIntArray() contentEquals other.shape().toIntArray()))
        return false
    for (i in 0 until size) {
        val a = getDouble(i)
        val b = other.getDouble(i)
        val diff = kotlin.math.abs(a - b)
        if (diff > atol + rtol*kotlin.math.abs(a))
            return false
    }
    return true
}
