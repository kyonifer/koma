package koma.internal.default.generated.ndarray

import koma.ndarray.GenericNDArrayFactory
import koma.ndarray.NDArray

class DefaultGenericNDArrayFactory<T>: GenericNDArrayFactory<T> {
    override fun create(vararg lengths: Int, filler: (IntArray) -> T): NDArray<T> {
        return DefaultGenericNDArray(shape=*lengths, init=filler)
    }
}