package koma.ndarray

import koma.internal.*
import koma.internal.default.generated.ndarray.DefaultGenericNDArrayFactory
import koma.matrix.*

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

        fun <T> getGenericFactory(): GenericNDArrayFactory<T> = DefaultGenericNDArrayFactory<T>()

        fun <T> createGeneric(vararg dims: Int, filler: (IntArray) -> T) =
            getGenericFactory<T>().create(*dims, filler = filler)
    }
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
    fun getByte(vararg indices: Int): Byte
    fun getDouble(vararg indices: Int): Double
    fun getFloat(vararg indices: Int): Float
    fun getInt(vararg indices: Int): Int
    fun getLong(vararg indices: Int): Long
    fun getShort(vararg indices: Int): Short

    fun setGeneric(vararg indices: Int, value: T)
    fun setByte(vararg indices: Int, value: Byte)
    fun setDouble(vararg indices: Int, value: Double)
    fun setFloat(vararg indices: Int, value: Float)
    fun setInt(vararg indices: Int, value: Int)
    fun setLong(vararg indices: Int, value: Long)
    fun setShort(vararg indices: Int, value: Short)
}
