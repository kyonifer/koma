package koma.internal.default.generated.ndarray

import koma.ndarray.GenericNDArrayFactory
import koma.ndarray.NDArray

class DefaultGenericNDArrayFactory<T>: GenericNDArrayFactory<T> {
    override fun alloc(lengths: IntArray) = DefaultGenericNDArray<T>(shape = *lengths)
}
