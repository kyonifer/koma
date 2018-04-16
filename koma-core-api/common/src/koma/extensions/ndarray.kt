package koma.extensions

import koma.internal.default.generated.ndarray.DefaultGenericNDArray
import koma.internal.default.utils.checkIndices
import koma.internal.default.utils.linearToNIdx
import koma.matrix.doubleFactory
import koma.ndarray.NDArray
import koma.pow
import koma.matrix.Matrix

fun NDArray<Double>.toMatrix(): Matrix<Double> {
    val dims = this.shape()
    return when (dims.size) {
        1 -> { doubleFactory.zeros(dims[0], 1).fill { row, _ -> this[row] } }
        2 -> { doubleFactory.zeros(dims[0], dims[1]).fill { row, col -> this[row, col] } }
        else -> error("Cannot convert NDArray with ${dims.size} dimensions to matrix (must be 1 or 2)")
    }
}

inline fun <T> NDArray<T>.fill(f: (idx: IntArray) -> T): NDArray<T> {
    this.forEachIndexedN { idx, ele ->
        this.set(indices=*idx, value = f(idx))
    }
    return this
}

operator fun <T> NDArray<T>.get(vararg indices: IntRange): NDArray<T> {
    checkIndices(indices.map { it.last }.toIntArray())
    return DefaultGenericNDArray<T>(shape = *indices
            .map { it.last - it.first + 1 }
            .toIntArray()) { newIdxs ->
        val offsets = indices.map { it.first }
        val oldIdxs = newIdxs.zip(offsets).map { it.first + it.second }
        this.getGeneric(*oldIdxs.toIntArray())
    }
}

operator fun <T> NDArray<T>.set(vararg indices: Int, value: NDArray<T>) {
    val shape = shape()
    val lastIndex = indices.mapIndexed { i, range -> range + value.shape()[i] }
    val outOfBounds = lastIndex.withIndex().any { it.value > shape()[it.index] }
    if (outOfBounds)
        throw IllegalArgumentException("NDArray with shape ${shape()} cannot be " +
                "set at ${indices.toList()} by a ${value.shape()} array " +
                "(out of bounds)")

    val offset = indices.map { it }.toIntArray()
    value.forEachIndexedN { idx, ele ->
        val newIdx = offset.zip(idx).map { it.first + it.second }.toIntArray()
        this.setGeneric(indices=*newIdx, value=ele)
    }
}


operator fun <T> NDArray<T>.get(vararg indices: Int) = getGeneric(*indices)
operator fun NDArray<Double>.get(vararg indices: Int) = getDouble(*indices)
operator fun <T> NDArray<T>.set(vararg indices: Int, value: T) = setGeneric(indices=*indices, value=value)
operator fun NDArray<Double>.set(vararg indices: Int, value: Double) = setDouble(indices=*indices, value=value)

operator fun NDArray<Double>.div(other: Double) = map { it/other }
operator fun NDArray<Double>.times(other: NDArray<Double>) = mapIndexedN { idx, ele -> ele*other.get(*idx) }
operator fun NDArray<Double>.times(other: Double) = map { it * other }
operator fun NDArray<Double>.unaryMinus() = map { -it }
operator fun NDArray<Double>.minus(other: Double) = map { it - other }
operator fun NDArray<Double>.minus(other: NDArray<Double>) = mapIndexedN { idx, ele -> ele - other.get(*idx) }
operator fun NDArray<Double>.plus(other: Double) = map { it + other }
operator fun NDArray<Double>.plus(other: NDArray<Double>) = mapIndexedN { idx, ele -> ele + other.get(*idx) }
infix fun NDArray<Double>.pow(exponent: Int) = map { pow(it, exponent) }


/**
 * Takes each element in a NDArray, passes them through f, and puts the output of f into an
 * output NDArray.
 *
 * @param f A function that takes in an element and returns an element
 *
 * @return the new NDArray after each element is mapped through f
 */
inline fun <T> NDArray<T>.map(f: (T) -> T): NDArray<T> {
    // TODO: Something better than copy here
    val out = this.copy()
    for ((idx, ele) in this.toIterable().withIndex())
        out.setLinear(idx, f(ele))
    return out
}
/**
 * Takes each element in a NDArray, passes them through f, and puts the output of f into an
 * output NDArray. Index given to f is a linear index, depending on the underlying storage
 * major dimension.
 *
 * @param f A function that takes in an element and returns an element. Function also takes
 *      in the linear index of the element's location.
 *
 * @return the new NDArray after each element is mapped through f
 */
inline fun <T> NDArray<T>.mapIndexed(f: (idx: Int, ele: T) -> T): NDArray<T> {
    // TODO: Something better than copy here
    val out = this.copy()
    for ((idx, ele) in this.toIterable().withIndex())
        out.setLinear(idx, f(idx, ele))
    return out
}
/**
 * Takes each element in a NDArray and passes them through f.
 *
 * @param f A function that takes in an element
 *
 */
inline fun <T> NDArray<T>.forEach(f: (ele: T) -> Unit) {
    for (ele in this.toIterable())
        f(ele)
}
/**
 * Takes each element in a NDArray and passes them through f. Index given to f is a linear
 * index, depending on the underlying storage major dimension.
 *
 * @param f A function that takes in an element. Function also takes
 *      in the linear index of the element's location.
 *
 */
inline fun <T> NDArray<T>.forEachIndexed(f: (idx: Int, ele: T) -> Unit) {
    for ((idx, ele) in this.toIterable().withIndex())
        f(idx, ele)
}

// TODO: for both of these, batch compute [linearToNIdx] instead of computing for every ele

/**
 * Takes each element in a NDArray, passes them through f, and puts the output of f into an
 * output NDArray. Index given to f is the full ND index of the element.
 *
 * @param f A function that takes in an element and returns an element. Function also takes
 *      in the ND index of the element's location.
 *
 * @return the new NDArray after each element is mapped through f
 */
inline fun <T> NDArray<T>.mapIndexedN(f: (idx: IntArray, ele: T) -> T): NDArray<T>
        = this.mapIndexed { idx, ele -> f(linearToNIdx(idx), ele) }

/**
 * Takes each element in a NDArray and passes them through f. Index given to f is the full
 * ND index of the element.
 *
 * @param f A function that takes in an element. Function also takes
 *      in the ND index of the element's location.
 *
 */
inline fun <T> NDArray<T>.forEachIndexedN(f: (idx: IntArray, ele: T) -> Unit)
        = this.forEachIndexed { idx, ele -> f(linearToNIdx(idx), ele) }
