package koma.ndarray

import koma.internal.*
import koma.internal.default.generated.ndarray.DefaultGenericNDArrayFactory


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