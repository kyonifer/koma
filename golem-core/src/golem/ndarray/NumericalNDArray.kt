package golem.ndarray


/**
 * An [NDArray] that holds a numerical type, such that
 * math operations are available.
 */
interface NumericalNDArray<T>: NDArray<T> {
    operator fun div(other: Int): NumericalNDArray<T>
    operator fun div(other: T): NumericalNDArray<T>
    operator fun times(other: NDArray<T>): NumericalNDArray<T>
    operator fun times(other: T): NumericalNDArray<T>
    operator fun unaryMinus(): NumericalNDArray<T>
    operator fun minus(other: T): NumericalNDArray<T>
    operator fun minus(other: NDArray<T>): NumericalNDArray<T>
    operator fun plus(other: T): NumericalNDArray<T>
    operator fun plus(other: NDArray<T>): NumericalNDArray<T>
    infix fun pow(exponent: Int): NumericalNDArray<T>


}