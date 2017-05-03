@file:JvmName("Koma")
@file:JvmMultifileClass

package koma
import koma.polyfill.annotations.*

/**
 * An implementation of an unsigned byte. Still a WIP.
 */
class UInt8 {
    constructor(init: Short) {
        value = init.toByte()

    }

    constructor(init: Byte) {
        value = init
    }

    var value: Byte

    operator fun plus(other: UInt8): UInt8 {
        return UInt8((this.value + other.value).toByte())

    }

    operator fun minus(other: UInt8): UInt8 {
        return UInt8((this.value - other.value).toByte())
    }

    fun divide(other: UInt8): UInt8 {
        return UInt8((value.toInt() / other.value.toInt()).toByte())
    }

    operator fun times(other: UInt8): UInt8 {
        return UInt8((value * other.value).toByte())
    }

    fun read(): Int = value.toInt() and 0xFF
}
