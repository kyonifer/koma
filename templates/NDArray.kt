package koma.ndarray

import koma.extensions.create
import koma.extensions.fill
import koma.internal.*
import koma.internal.default.generated.ndarray.DefaultGenericNDArrayFactory
import koma.internal.default.utils.safeNIdxToLinear
import koma.matrix.*
import kotlin.reflect.KClass
import koma.util.IndexIterator

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
    companion object {

        // TODO: Ideally these properties are expect/actual with implementations. However, there's currently
        // a generation issue with kotlin/native that breaks this approach, so as a workaround we'll define
        // getXFactory methods in koma.internal as expect actual and proxy them here. These properties have
        // to be lazily evaluated to avoid a race on startup in js, so we use private nullable fields and
        // initialize on first use

        $factories

        fun <T> createGeneric(vararg dims: Int, filler: (IntArray) -> T) =
            DefaultGenericNDArrayFactory<T>().createGeneric(*dims, filler = filler)

        fun <T> createGenericNulls(vararg dims: Int) =
                DefaultGenericNDArrayFactory<T?>().createGeneric(*dims, filler = {null})

        inline operator fun <reified T> invoke(vararg dims: Int,
                                               crossinline filler: (IntArray) -> T) =
            when(T::class) {
                $typeCheckClauses
                else          -> createGeneric(*dims) { filler(it) }
            }
    }

    @Deprecated("Use NDArray.getGeneric", ReplaceWith("getGeneric"))
    fun getLinear(index: Int): T = getGeneric(index)
    @Deprecated("Use NDArray.getGeneric", ReplaceWith("setGeneric"))
    fun setLinear(index: Int, value: T) = setGeneric(index, v = value)

    val size: Int get() = shape().reduce { a, b -> a * b }
    fun shape(): List<Int>
    fun copy(): NDArray<T>

    fun getBaseArray(): Any

    fun toIterable(): Iterable<T> {
        return object: Iterable<T> {
            override fun iterator(): Iterator<T> = object: Iterator<T> {
                private var cursor = 0
                private val size = this@NDArray.size
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

    // Iterator over the indices of this NDArray, simultaneously in array and linear form.
    // Not intended to be used directly, but instead used by ext funcs in `koma.extensions`
    fun iterateIndices() = IndexIterator { shape().toIntArray() }


    // Primitive optimized getter/setters to avoid boxing. Not intended
    // to be used directly, but instead are used by ext funcs in `koma.extensions`.

    @KomaJsName("getGenericND")
    fun getGeneric(vararg indices: Int) = getGeneric(safeNIdxToLinear(indices))
    @KomaJsName("getGeneric1D")
    fun getGeneric(i: Int): T
    @KomaJsName("setGenericND")
    fun setGeneric(vararg indices: Int, v: T) = setGeneric(safeNIdxToLinear(indices), v)
    @KomaJsName("setGeneric1D")
    fun setGeneric(i: Int, v: T)

    $primitiveGetSet
}
