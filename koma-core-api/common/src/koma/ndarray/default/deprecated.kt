package koma.ndarray.default

import koma.ndarray.doubleFactory

@Deprecated("Use toplevel factory koma.ndarray.getGenericFactory<T>().create()")
typealias DefaultNDArray<T> = koma.internal.default.generated.ndarray.DefaultGenericNDArray<T>
@Deprecated("Use toplevel factory koma.ndarray.intFactory.create()")
typealias DefaultIntNDArray = koma.internal.default.generated.ndarray.DefaultIntNDArray
@Deprecated("Use toplevel factory koma.ndarray.floatFactory.create()")
typealias DefaultFloatNDArray = koma.internal.default.generated.ndarray.DefaultFloatNDArray
@Deprecated("Use toplevel factory koma.ndarray.doubleFactory.create()")
typealias DefaultDoubleNDArray = koma.internal.default.generated.ndarray.DefaultDoubleNDArray
