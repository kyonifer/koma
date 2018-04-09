package koma.ndarray

import koma.matrix.*
import koma.internal.KomaJsName

// TODO: broadcasting, iteration by selected dims, views, reshape
/**
 * A general N-dimensional container for arbitrary types. For this container to be
 * useful, you'll probably want to import koma.extensions.*, which includes e.g.
 * element getter/setters which are non boxed for primitives.
 *
 * If you are looking for a 2D container supporting linear algebra, please look at
 * [Matrix].
 */
interface NDArray<T> {
    fun getLinear(index: Int): T
    fun setLinear(index: Int, value: T)
    
    fun shape(): List<Int>
    fun copy(): NDArray<T>

    fun getBaseArray(): Any

    fun toIterable(): Iterable<T> {
        return object: Iterable<T> {
            override fun iterator(): Iterator<T> = object: Iterator<T> {
                private var cursor = 0
                private val size = this@NDArray.shape().reduce{a,b->a*b}
                override fun next(): T {
                    cursor += 1
                    // TODO: Either make 1D access work like Matrix or fix this
                    // to not use the largest dimension.
                    return this@NDArray.getLinear(cursor - 1)
                }
                override fun hasNext() = cursor < size
            }
        }
    }

    // Primitive optimized getter/setters to avoid boxing. Not intended
    // to be used directly, but instead are used by ext funcs in `koma.extensions`.

    fun getGeneric(vararg indices: Int): T
    fun getGeneric(vararg indices: IntRange): NDArray<T>
    fun getDouble(vararg indices: Int): Double
    fun getDouble(vararg indices: IntRange): NDArray<Double>
    fun setGeneric(vararg indices: Int, value: T)
    fun setGeneric(vararg indices: Int, value: NDArray<T>)
    fun setDouble(vararg indices: Int, value: Double)
    fun setDouble(vararg indices: Int, value: NDArray<Double>)
}
