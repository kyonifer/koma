package koma.internal.default

import koma.extensions.forEachIndexedN
import koma.extensions.get
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

    override fun getGeneric(vararg indices: Int): T {
        checkIndices(indices)
        return storage[nIdxToLinear(indices)]
    }
    override fun getLinear(index: Int): T = storage[index]
    override fun setLinear(index: Int, value: T) { storage[index] = value }
    
    override fun setGeneric(vararg indices: Int, value: T) {
        checkIndices(indices)
        storage[nIdxToLinear(indices)] = value
    }
    // TODO: cache this
    override fun shape(): List<Int> = shape.toList()
    override fun copy(): NDArray<T> = DefaultNDArray(*shape, init = { this.getGeneric(*it) })
    override fun getBaseArray(): Any = storage

    private val wrongType = "Double methods not implemented for generic NDArray"
    override fun getDouble(vararg indices: Int): Double {
        val ele = getGeneric(*indices)
        if (ele is Double)
            return ele
        else
            error(wrongType)
    }
    override fun setDouble(vararg indices: Int, value: Double) {
        setGeneric(indices=*indices, value=value as T)
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
