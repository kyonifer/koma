package golem.ndarray

import golem.matrix.*

// TODO: broadcasting, iteration by selected dims, views, reshape
/**
 * A general N-dimensional container for arbitrary types. If you are looking
 * for a ND container restricted to numerical types, please look at [NumericalNDArray].
 * If you are looking for a 2D container supporting linear algebra, please look at 
 * [Matrix].
 */
interface NDArray<T> {
    operator fun get(vararg indices: Int): T
    operator fun get(vararg indices: IntRange): NDArray<T>
    operator fun set(vararg indices: Int, value: T)
    operator fun set(vararg indices: Int, value: NDArray<T>)

    fun getLinear(index: Int): T
    fun setLinear(index: Int, value: T)
    
    fun shape(): List<Int>
    fun copy(): NDArray<T>

    fun getBaseArray(): Any

    fun toIterable(): Iterable<T> {
        return object: Iterable<T> {
            override fun iterator(): Iterator<T> = object: Iterator<T> {
                private var cursor = 0
                private val size = this@NDArray.shape().reduce{a,b->a*b}
                override fun next(): T {
                    cursor += 1
                    // TODO: Either make 1D access work like Matrix or fix this
                    // to not use the largest dimension.
                    return this@NDArray.getLinear(cursor - 1)
                }
                override fun hasNext() = cursor < size
            }
        }
    }
    
    /**
     * Takes each element in a NDArray, passes them through f, and puts the output of f into an
     * output NDArray.
     *
     * @param f A function that takes in an element and returns an element
     *
     * @return the new NDArray after each element is mapped through f
     */
    fun map(f: (T) -> T): NDArray<T> {
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
    fun mapIndexed(f: (idx: Int, ele: T) -> T): NDArray<T> {
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
    fun forEach(f: (ele: T) -> Unit) {
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
    fun forEachIndexed(f: (idx: Int, ele: T) -> Unit) {
        for ((idx, ele) in this.toIterable().withIndex())
            f(idx, ele)
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
    fun mapIndexedN(f: (idx: IntArray, ele: T) -> T): NDArray<T>
    /**
     * Takes each element in a NDArray and passes them through f. Index given to f is the full 
     * ND index of the element.
     *
     * @param f A function that takes in an element. Function also takes
     *      in the ND index of the element's location.
     *
     */
    fun forEachIndexedN(f: (idx: IntArray, ele: T) -> Unit)

}



