package koma.internal.default.utils

import koma.ndarray.NDArray

fun widthOfDims(shape: List<Int>) = shape
        .accumulateRight { left, right -> left * right }
        .apply {
            add(1)
            removeAt(0)
        }.toIntArray()

fun <T> NDArray<T>.checkIndices(indices: IntArray) = indices.also {
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

fun <T> NDArray<T>.checkLinearIndex(index: Int) = index.also {
    if (index < 0)
        throw IllegalArgumentException("Negative indices are not supported")
    else size.let { n ->
        if (index >= n) {
            val an = when("$n"[0]) {
                '1','8' -> "an"
                else    -> "a"
            }
            throw IllegalArgumentException("Cannot index $an $n-element array with shape " +
                                           "${shape().toList()} at linear position $index " +
                                           "(out of bounds)")
        }
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

/**
 * Given an index into an array, wrap negative values to be relative to the upper limit.
 */
fun wrapIndex(index: Int, size: Int): Int {
    val i = if (index < 0) size + index else index
    if (i < 0 || i >= size)
        throw IllegalArgumentException("Illegal index $index for a dimension of width $size")
    return i
}