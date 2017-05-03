package golem.ndarray.common

import golem.ndarray.*
import golem.platformsupport.*

abstract class NumericalNDArrayFacBase<out T>: NumericalNDArrayFactory<T> {

    override fun arange(start: Double, stop: Double): T 
            = arange(start, stop, 1.0 * signum(stop - start))
    override fun arange(start: Int, stop: Int, increment: Int): T 
            = arange(start.toDouble(), stop.toDouble(), increment.toDouble())
    override fun arange(start: Int, stop: Int): T 
            = arange(start.toDouble(), stop.toDouble())
}
