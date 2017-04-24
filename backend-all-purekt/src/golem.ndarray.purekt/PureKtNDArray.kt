package golem.ndarray.purekt

import golem.*
import golem.ndarray.*

/**
 * An implementation of [NDArray] in pure Kotlin, for portability between the 
 * different platforms golem supports. 
 * 
 * @param shape A vararg specifying the size of each dimension, e.g. a 3D array with size 4x6x8 would pass in 4,6,8)
 * @param init A function that takes a location in the new array and returns its initial value.
 */
open class PureKtNDArray<T>(vararg val shape: Int, init: (IntArray)->T): NDArray<T> {

    /**
     * Underlying storage. PureKt backend uses a simple array.
     */
    private val storage: Array<T>

    init {
        @Suppress("UNCHECKED_CAST")
        storage = Array(shape.reduce{ a, b-> a * b},
                        {init(linearToNIdx(it)) as Any}) as Array<T>
    }


    override fun get(vararg indices: Int): T = storage[findIdx(indices)]
    override fun get(vararg indices: IntRange): NDArray<T> {
        return PureKtNDArray<T>(shape =*indices
                .map { it.last - it.first }
                .toIntArray()) { newIdxs ->
            val offsets = indices.map { it.first }
            val oldIdxs = newIdxs.zip(offsets).map { it.first + it.second }
            this.get(*oldIdxs.toIntArray())
        }
        
    }
    override operator fun set(vararg indices: Int, value: T) {
        storage[findIdx(indices)] = value
    }
    override fun set(vararg indices: IntRange, value: NDArray<T>) {
        TODO()
    }
    override fun shape(): List<Int> = shape.toList()
    override fun copy(): NDArray<T> = PureKtNDArray(*shape, init={this.get(*it)})
    override fun getBaseArray(): Any = storage

    /**
     * Given a ND index into this array, find the corresponding 1D index in the raw underlying 
     * 1D storage array.
     */
    private fun findIdx(indices: IntArray): Int {
        var out = 0
        val widthOfDims = widthOfDims()

        indices.forEachIndexed { i, idxArr ->
            out += idxArr * widthOfDims[i]
        }
        return out
    }
    
    /**
     * Given the 1D index of an element in the underlying storage, find the corresponding
     * ND index. Inverse of [findIdx].
     */
    fun linearToNIdx(linear:Int): IntArray {
        // TODO: optimize this
        val widthOfDims = widthOfDims()
        var remaining = linear
        val out = IntArray(shape.size, {it})
        out.map { idx ->
            out[idx] = remaining / widthOfDims[idx]
            remaining -= out[idx] * widthOfDims[idx]
        }
        return out
    }
    private fun widthOfDims() = shape
            .toList()
            .accumulateRight { left, right -> left * right }
            .apply {
                add(1)
                removeAt(0)
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
