package koma.internal.default

import koma.internal.signum
import koma.ndarray.*

abstract class NumericalNDArrayFacBase<T: Number>: NumericalNDArrayFactory<T> {

    override fun arange(start: Double, stop: Double): NDArray<T>
            = arange(start, stop, 1.0 * signum(stop - start))
    override fun arange(start: Int, stop: Int, increment: Int): NDArray<T>
            = arange(start.toDouble(), stop.toDouble(), increment.toDouble())
    override fun arange(start: Int, stop: Int): NDArray<T>
            = arange(start.toDouble(), stop.toDouble())
}
