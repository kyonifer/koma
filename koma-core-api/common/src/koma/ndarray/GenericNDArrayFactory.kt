package koma.ndarray

/**
 * A set of constructors that must be implemented by a koma NDArray backend.
 * Generic parameter is the type of the element.
 */
interface GenericNDArrayFactory<in T> {
    fun create(filler: (IntArray)->T)
}
