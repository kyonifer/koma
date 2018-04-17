package koma.ndarray.default

import koma.ndarray.NDArray

@Deprecated("Use NDArray.getGenericFactory<T>().create(...)")
typealias DefaultNDArray<T> = koma.internal.default.generated.ndarray.DefaultGenericNDArray<T>
@Deprecated("Use NDArray.intFactory", ReplaceWith("NDArray.intFactory.create"))
typealias DefaultIntNDArray = koma.internal.default.generated.ndarray.DefaultIntNDArray
@Deprecated("Use NDArray.floatFactory", ReplaceWith("NDArray.floatFactory.create"))
typealias DefaultFloatNDArray = koma.internal.default.generated.ndarray.DefaultFloatNDArray
@Deprecated("Use NDArray.doubleFactory", ReplaceWith("NDArray.doubleFactory.create"))
typealias DefaultDoubleNDArray = koma.internal.default.generated.ndarray.DefaultDoubleNDArray
