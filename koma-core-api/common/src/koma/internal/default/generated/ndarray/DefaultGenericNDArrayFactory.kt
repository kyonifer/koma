package koma.internal.default.generated.ndarray

import koma.ndarray.GenericNDArrayFactory
import koma.ndarray.NDArray

class DefaultGenericNDArrayFactory<T>: GenericNDArrayFactory<T> {
    override fun createGeneric(vararg lengths: Int, filler: (IntArray)->T) = DefaultGenericNDArray<T>(shape = *lengths, init=filler)
}
