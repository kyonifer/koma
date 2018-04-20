package koma.ndarray

import koma.extensions.create
import koma.extensions.fill
import koma.internal.*
import koma.internal.default.generated.ndarray.DefaultGenericNDArrayFactory
import koma.internal.default.utils.safeIdxToLinear
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

        var doubleFactory: NumericalNDArrayFactory<Double>
            get() = _doubleFactory ?: getDoubleNDArrayFactory().also { _doubleFactory = it }
            set(value) { _doubleFactory = value}
        private var _doubleFactory: NumericalNDArrayFactory<Double>? = null

        var floatFactory: NumericalNDArrayFactory<Float>
            get() = _floatFactory ?: getFloatNDArrayFactory().also { _floatFactory = it }
            set(value) { _floatFactory = value}
        private var _floatFactory: NumericalNDArrayFactory<Float>? = null

        var longFactory: NumericalNDArrayFactory<Long>
            get() = _longFactory ?: getLongNDArrayFactory().also { _longFactory = it }
            set(value) { _longFactory = value}
        private var _longFactory: NumericalNDArrayFactory<Long>? = null

        var intFactory: NumericalNDArrayFactory<Int>
            get() = _intFactory ?: getIntNDArrayFactory().also { _intFactory = it }
            set(value) { _intFactory = value}
        private var _intFactory: NumericalNDArrayFactory<Int>? = null

        var shortFactory: NumericalNDArrayFactory<Short>
            get() = _shortFactory ?: getShortNDArrayFactory().also { _shortFactory = it }
            set(value) { _shortFactory = value}
        private var _shortFactory: NumericalNDArrayFactory<Short>? = null

        var byteFactory: NumericalNDArrayFactory<Byte>
            get() = _byteFactory ?: getByteNDArrayFactory().also { _byteFactory = it }
            set(value) { _byteFactory = value}
        private var _byteFactory: NumericalNDArrayFactory<Byte>? = null

        fun <T> createGeneric(vararg dims: Int, filler: (IntArray) -> T) =
            DefaultGenericNDArrayFactory<T>().create(*dims, filler = filler)

        inline operator fun <reified T> invoke(vararg dims: Int,
                                               crossinline filler: (IntArray) -> T) =
            when(T::class) {
                Double::class -> doubleFactory.alloc(dims).fill { filler(it) as Double }
                Float::class  -> floatFactory.alloc(dims).fill { filler(it) as Float }
                Long::class   -> longFactory.alloc(dims).fill { filler(it) as Long }
                Int::class    -> intFactory.alloc(dims).fill { filler(it) as Int }
                Short::class  -> shortFactory.alloc(dims).fill { filler(it) as Short }
                Byte::class   -> byteFactory.alloc(dims).fill { filler(it) as Byte }
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
    fun getGeneric(vararg indices: Int) = getGeneric(safeIdxToLinear(indices))
    @KomaJsName("getGeneric1D")
    fun getGeneric(i: Int): T
    @KomaJsName("setGenericND")
    fun setGeneric(vararg indices: Int, v: T) = setGeneric(safeIdxToLinear(indices), v)
    @KomaJsName("setGeneric1D")
    fun setGeneric(i: Int, v: T)


    //!{{ primitive get/set

    // GENERATED CODE! See build.gradle

    @KomaJsName("getDoubleND")
    fun getDouble(vararg indices: Int) = getDouble(safeIdxToLinear(indices))
    @KomaJsName("getDouble1D")
    fun getDouble(i: Int): Double
    @KomaJsName("setDoubleND")
    fun setDouble(vararg indices: Int, v: Double) = setDouble(safeIdxToLinear(indices), v)
    @KomaJsName("setDouble1D")
    fun setDouble(i: Int, v: Double)


    @KomaJsName("getFloatND")
    fun getFloat(vararg indices: Int) = getFloat(safeIdxToLinear(indices))
    @KomaJsName("getFloat1D")
    fun getFloat(i: Int): Float
    @KomaJsName("setFloatND")
    fun setFloat(vararg indices: Int, v: Float) = setFloat(safeIdxToLinear(indices), v)
    @KomaJsName("setFloat1D")
    fun setFloat(i: Int, v: Float)


    @KomaJsName("getLongND")
    fun getLong(vararg indices: Int) = getLong(safeIdxToLinear(indices))
    @KomaJsName("getLong1D")
    fun getLong(i: Int): Long
    @KomaJsName("setLongND")
    fun setLong(vararg indices: Int, v: Long) = setLong(safeIdxToLinear(indices), v)
    @KomaJsName("setLong1D")
    fun setLong(i: Int, v: Long)


    @KomaJsName("getIntND")
    fun getInt(vararg indices: Int) = getInt(safeIdxToLinear(indices))
    @KomaJsName("getInt1D")
    fun getInt(i: Int): Int
    @KomaJsName("setIntND")
    fun setInt(vararg indices: Int, v: Int) = setInt(safeIdxToLinear(indices), v)
    @KomaJsName("setInt1D")
    fun setInt(i: Int, v: Int)


    @KomaJsName("getShortND")
    fun getShort(vararg indices: Int) = getShort(safeIdxToLinear(indices))
    @KomaJsName("getShort1D")
    fun getShort(i: Int): Short
    @KomaJsName("setShortND")
    fun setShort(vararg indices: Int, v: Short) = setShort(safeIdxToLinear(indices), v)
    @KomaJsName("setShort1D")
    fun setShort(i: Int, v: Short)


    @KomaJsName("getByteND")
    fun getByte(vararg indices: Int) = getByte(safeIdxToLinear(indices))
    @KomaJsName("getByte1D")
    fun getByte(i: Int): Byte
    @KomaJsName("setByteND")
    fun setByte(vararg indices: Int, v: Byte) = setByte(safeIdxToLinear(indices), v)
    @KomaJsName("setByte1D")
    fun setByte(i: Int, v: Byte)

    //!}}
}
