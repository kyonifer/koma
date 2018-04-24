package koma.ndarray

/**
 * A set of constructors that must be implemented by a koma NDArray backend.
 * Generic parameter is the type of the element.
 */
interface GenericNDArrayFactory<T> {
    /**
     * Generate an ND container of the requested shape without initializing
     * its contents.
     *
     * Depending on backend and platform, the resulting array may be,
     * equivalent to zeros(*lengths), or may be initialized to memory garbage.
     */
    fun createGeneric(lengths: IntArray, filler: (IntArray)->T): NDArray<T>
}
