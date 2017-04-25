package golem.polyfill

fun IntArray(size: Int, init: (Int) -> Int): kotlin.IntArray {
    val out = IntArray(size)
    for(i in 0.until(size))
        out[i] = init(i)
    return out
}
