package golem.ndarray

import golem.matrix.*
import java.io.ObjectOutputStream
import java.io.Serializable
import java.io.StreamCorruptedException

// TODO: broadcasting, iteration by selected dims, views, reshape
/**
 * A general N-dimensional container for arbitrary types. If you are looking
 * for a ND container restricted to numerical types, please look at [NumericalNDArray].
 * If you are looking for a 2D container supporting linear algebra, please look at 
 * [Matrix].
 */
interface NDArray<T> : Iterable<T>, Serializable {
    operator fun get(vararg indices: Int): T
    operator fun get(vararg indices: IntRange): NDArray<T>
    operator fun set(vararg indices: Int, value: T)
    operator fun set(vararg indices: IntRange, value: NDArray<T>)

    fun shape(): List<Int>
    fun copy(): NDArray<T>

    fun getBaseArray(): Any

    override fun iterator(): Iterator<T> = object: Iterator<T> { 
        private var cursor = 0
        override fun next(): T {
        cursor += 1
        return this@NDArray[cursor - 1]
        }
        override fun hasNext() = cursor < this@NDArray.shape().reduce{a,b->a*b}
    }
    
    fun serializeObject(out: ObjectOutputStream): Unit {
        out.writeObject(this.shape())
        this.forEach { out.writeObject(it) }
    }

    fun deserializeObjectNoData() {
        throw StreamCorruptedException("No Data for Matrix In Stream")
    }

    /**
     * Takes each element in a NDArray, passes them through f, and puts the output of f into an
     * output NDArray.
     *
     * @param f A function that takes in an element and returns an element
     *
     * @return the new NDArray after each element is mapped through f
     */
    fun mapArr(f: (T) -> T): NDArray<T> {
        // TODO: Something better than copy here
        val out = this.copy()
        for ((idx, ele) in this.withIndex())
            out[idx] = f(ele)
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
    fun mapArrIndexed(f: (idx: Int, ele: T) -> T): NDArray<T> {
        // TODO: Something better than copy here
        val out = this.copy()
        for ((idx, ele) in this.withIndex())
            out[idx] = f(idx, ele)
        return out
    }
    /**
     * Takes each element in a NDArray, passes them through f, and puts the output of f into an
     * output NDArray. Index given to f is the full ND index of the element.
     *
     * @param f A function that takes in an element and returns an element. Function also takes
     *      in the ND index of the element's location.
     *
     * @return the new NDArray after each element is mapped through f
     */
    fun mapArrIndexedN(f: (idx: IntArray, ele: T) -> T): NDArray<T> = TODO()
}


