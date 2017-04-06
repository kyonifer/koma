package golem.ndarray


/**
 * An [NDArray] that holds a numerical type, such that
 * math operations are available.
 */
interface NumericalNDArray<T>: NDArray<T> {
    operator fun div(other: Int): NDArray<T>
    operator fun div(other: T): NDArray<T>
    operator fun times(other: NDArray<T>): NDArray<T>
    operator fun times(other: T): NDArray<T>
    operator fun unaryMinus(): NDArray<T>
    operator fun minus(other: T): NDArray<T>
    operator fun minus(other: NDArray<T>): NDArray<T>
    operator fun plus(other: T): NDArray<T>
    operator fun plus(other: NDArray<T>): NDArray<T>
    infix fun pow(exponent: Int): NDArray<T>

}