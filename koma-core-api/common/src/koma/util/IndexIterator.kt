package koma.util

/**
 * An Iterator that counts indices of a given shape in row-major order, simultaneously in
 * both array and linear form. Useful for stepping through N-dimensional data.
 */
data class IndexIterator(var nd: IntArray, var linear: Int = 0): Iterator<IndexIterator> {
    private var needsAdvance = false
    private val shape = nd
    private val lastIndex = nd.size - 1
    override fun hasNext(): Boolean {
        if (needsAdvance) {
            ++linear
            for (idx in lastIndex downTo 0)
                if (++nd[idx] >= shape[idx] && idx > 0)
                    nd[idx] = 0
                else
                    break
            needsAdvance = false
        }
        return nd.size > 0 && nd[0] < shape[0]
    }
    override fun next() = apply {
        if (!hasNext())
            throw NoSuchElementException("Iterator exhausted")
        needsAdvance = true
    }
    init { nd = IntArray(nd.size) { 0 } }

    companion object {
        operator fun invoke(shapeFactory: ()->IntArray): Iterable<IndexIterator> = 
            object : Iterable<IndexIterator> {
                override fun iterator() = IndexIterator(shapeFactory())
            }
    }
}
