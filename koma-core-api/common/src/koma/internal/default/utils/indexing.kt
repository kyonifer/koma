package koma.internal.default.utils

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

fun <T> NDArray<T>.checkIndices(indices: IntArray) {
    val shape = shape()
    if (indices.size != shape.size)
        throw IllegalArgumentException("Cannot index an array with shape ${shape.toList()} with " +
                "anything other than ${shape.size} indices (${indices.size} given)")
    indices.forEachIndexed{ i, idx ->
        if (idx >= shape[i])
            throw IllegalArgumentException("Cannot index an array with shape ${shape.toList()} at " +
                    "${indices.toList()} (out of bounds)")
    }
}


/**
 * Similar to reduceRight, except the results of each stage are stored off into
 * the output list instead of just the final result.
 */
fun <T> List<T>.accumulateRight(f: (T, T) -> T)
        = this.foldRight(ArrayList<T>()) { ele, accum ->
    if (accum.isEmpty())
        accum.add(ele)
    else
        accum.add(0, f(ele, accum.first()))
    accum
}