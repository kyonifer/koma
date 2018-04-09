package koma.internal.default.utils

import koma.internal.default.accumulateRight
import koma.ndarray.NDArray

/**
 * Given a ND index into this array, find the corresponding 1D index in the raw underlying
 * 1D storage array.
 */
fun <T> NDArray<T>.nIdxToLinear(indices: IntArray): Int {
    var out = 0
    val widthOfDims = widthOfDims()

    indices.forEachIndexed { i, idxArr ->
        out += idxArr * widthOfDims[i]
    }
    return out
}

/**
 * Given the 1D index of an element in the underlying storage, find the corresponding
 * ND index. Inverse of [nIdxToLinear].
 */
fun <T> NDArray<T>.linearToNIdx(linear:Int): IntArray {
    // TODO: optimize this
    val widthOfDims = widthOfDims()
    var remaining = linear
    val out = IntArray(shape().size, {it})
    out.map { idx ->
        out[idx] = remaining / widthOfDims[idx]
        remaining -= out[idx] * widthOfDims[idx]
    }
    return out
}
fun <T> NDArray<T>.widthOfDims() = shape()
        .toList()
        .accumulateRight { left, right -> left * right }
        .apply {
            add(1)
            removeAt(0)
        }