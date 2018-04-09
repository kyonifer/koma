package koma.internal.default

import koma.extensions.forEachIndexedN
import koma.ndarray.*
import koma.internal.KomaJsName
import koma.internal.default.utils.*


/**
 * An (unoptimized) implementation of [NDArray] in pure Kotlin, for portability between the 
 * different platforms koma supports. 
 * 
 * @param shape A vararg specifying the size of each dimension, e.g. a 3D array with size 4x6x8 would pass in 4,6,8)
 * @param init A function that takes a location in the new array and returns its initial value.
 */
open class DefaultNDArray<T>(@KomaJsName("shape_private") vararg protected val shape: Int,
                             init: (IntArray)->T): NDArray<T> {

    /**
     * Underlying storage. PureKt backend uses a simple array.
     */
    private val storage: Array<T>

    init {
        @Suppress("UNCHECKED_CAST")
        storage = Array(shape.reduce{ a, b-> a * b},
                        {init(linearToNIdx(it)) as Any?}) as Array<T>
    }


    fun checkIndices(indices: IntArray) {
        if (indices.size != shape.size)
            throw IllegalArgumentException("Cannot index an array with shape ${shape.toList()} with " +
                                           "anything other than ${shape.size} indices (${indices.size} given)")
        indices.forEachIndexed{ i, idx -> 
            if (idx >= shape[i])
                throw IllegalArgumentException("Cannot index an array with shape ${shape.toList()} at " +
                                               "${indices.toList()} (out of bounds)")
        }
    }
    override fun getGeneric(vararg indices: Int): T {
        checkIndices(indices)
        return storage[nIdxToLinear(indices)]
    }
    override fun getGeneric(vararg indices: IntRange): NDArray<T> {
        checkIndices(indices.map { it.last }.toIntArray())
        return DefaultNDArray<T>(shape = *indices
                .map { it.last - it.first + 1 }
                .toIntArray()) { newIdxs ->
            val offsets = indices.map { it.first }
            val oldIdxs = newIdxs.zip(offsets).map { it.first + it.second }
            this.getGeneric(*oldIdxs.toIntArray())
        }
        
    }
    override fun getLinear(index: Int): T = storage[index]
    override fun setLinear(index: Int, value: T) { storage[index] = value }
    
    override fun setGeneric(vararg indices: Int, value: T) {
        checkIndices(indices)
        storage[nIdxToLinear(indices)] = value
    }
    override fun setGeneric(vararg indices: Int, value: NDArray<T>) {
        val lastIndex = indices.mapIndexed { i, range -> range + value.shape()[i] }
        val outOfBounds = lastIndex.withIndex().any { it.value > shape()[it.index] }
        if (outOfBounds)
            throw IllegalArgumentException("NDArray with shape ${shape()} cannot be " +
                                           "set at ${indices.toList()} by a ${value.shape()} array " +
                                           "(out of bounds)")
        
        val offset = indices.map { it }.toIntArray()
        value.forEachIndexedN { idx, ele ->
            val newIdx = offset.zip(idx).map { it.first + it.second }.toIntArray()
            this.setGeneric(indices=*newIdx, value=ele)
        }
    }
    // TODO: cache this
    override fun shape(): List<Int> = shape.toList()
    override fun copy(): NDArray<T> = DefaultNDArray(*shape, init = { this.getGeneric(*it) })
    override fun getBaseArray(): Any = storage

    private val wrongType = "Double methods not implemented for generic NDArray"
    override fun getDouble(vararg indices: Int): Double { error(wrongType) }
    override fun getDouble(vararg indices: IntRange): NDArray<Double> { error(wrongType) }
    override fun setDouble(vararg indices: Int, value: Double) { error(wrongType) }
    override fun setDouble(vararg indices: Int, value: NDArray<Double>) { error(wrongType) }
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
