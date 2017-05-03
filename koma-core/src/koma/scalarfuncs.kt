@file:JvmName("Koma")
@file:JvmMultifileClass

package koma

import koma.platformsupport.*
import koma.polyfill.annotations.*

// Scalar funcs

/**
 * A convenience function performing the abs operation via the [Math] equivalent call.
 */
fun abs(arr: Double) = Math.abs(arr)

/**
 * A convenience function performing the ceil via the [Math] equivalent call.
 */
fun ceil(num: Double) = Math.ceil(num).toInt()

/**
 * A convenience function performing the cos operation via the [Math] equivalent call.
 */
fun cos(arr: Double) = Math.cos(arr)

/**
 * A convenience function performing the exp operation via the [Math] equivalent call.
 */
fun exp(arr: Double) = Math.exp(arr)

/**
 * A convenience function performing the log operation via the [Math] equivalent call.
 */
fun log(arr: Double) = Math.log(arr)

/**
 * A convenience function performing the pow operation via the [Math] equivalent call.
 */
fun pow(num: Double, exp: Double) = Math.pow(num, exp)

/**
 * A convenience function performing the sign operation via the [Math] equivalent call.
 */
fun sign(arr: Double) = signum(arr)

/**
 * A convenience function performing the sin operation via the [Math] equivalent call.
 */
fun sin(arr: Double) = Math.sin(arr)

/**
 * A convenience function performing the sqrt operation via the [Math] equivalent call.
 */
fun sqrt(arr: Double) = Math.sqrt(arr)

/**
 * A convenience function performing the tan operation via the [Math] equivalent call.
 */
fun tan(arr: Double) = Math.tan(arr)

/**
 * A convenience function performing the round operation via the [Math] equivalent call.
 */
fun round(arr: Double) = Math.round(arr)

/**
 * A convenience function performing the floor operation via the [Math] equivalent call.
 */
fun floor(arr: Double) = Math.floor(arr).toInt()

/**
 * A convenience function performing the acos operation via the [Math] equivalent call.
 */
fun acos(arr: Double) = Math.acos(arr)

/**
 * A convenience function performing the asin operation via the [Math] equivalent call.
 */
fun asin(arr: Double) = Math.asin(arr)

/**
 * A convenience function performing the atan operation via the [Math] equivalent call.
 */
fun atan(arr: Double) = Math.atan(arr)

/**
 * A convenience function performing the atan2 operation via the [Math] equivalent call.
 */
fun atan2(x: Double, y: Double) = Math.atan2(x, y)

/**
 * A convenience function performing the acos operation via the [Math] equivalent call.
 */
fun acos(arr: Int) = Math.acos(arr.toDouble())

/**
 * A convenience function performing the asin operation via the [Math] equivalent call.
 */
fun asin(arr: Int) = Math.asin(arr.toDouble())

/**
 * A convenience function performing the atan operation via the [Math] equivalent call.
 */
fun atan(arr: Int) = Math.atan(arr.toDouble())

/**
 * A convenience function performing the atan2 operation via the [Math] equivalent call.
 */
fun atan2(x: Int, y: Int) = Math.atan2(x.toDouble(), y.toDouble())

/**
 * A convenience function performing the max operation via the [Math] equivalent call.
 */
fun max(num1: Double, num2: Double) = if (num1 > num2) num1 else num2

/**
 * A convenience function performing the min operation via the [Math] equivalent call.
 */
fun min(num1: Double, num2: Double) = if (num2 > num1) num1 else num2

/**
 * A convenience function performing the logb operation via the [Math] equivalent call.
 */
fun logb(base: Double, num: Double) = Math.log(num) / Math.log(base)

// Int versions
/**
 * A convenience function performing the abs via the [Math] equivalent call.
 */
fun abs(arr: Int) = Math.abs(arr.toDouble())

/**
 * A convenience function performing the ceil operation via the [Math] equivalent call.
 */
fun ceil(num: Int) = Math.ceil(num.toDouble())

/**
 * A convenience function performing the cos operation via the [Math] equivalent call.
 */
fun cos(arr: Int) = Math.cos(arr.toDouble())

/**
 * A convenience function performing the exp operation via the [Math] equivalent call.
 */
fun exp(arr: Int) = Math.exp(arr.toDouble())

/**
 * A convenience function performing the log operation via the [Math] equivalent call.
 */
fun log(arr: Int) = Math.log(arr.toDouble())

/**
 * A convenience function performing the sign operation via the [Math] equivalent call.
 */
fun sign(arr: Int) = signum(arr.toDouble())

/**
 * A convenience function performing the sin operation via the [Math] equivalent call.
 */
fun sin(arr: Int) = Math.sin(arr.toDouble())

/**
 * A convenience function performing the sqrt operation via the [Math] equivalent call.
 */
fun sqrt(arr: Int) = Math.sqrt(arr.toDouble())

/**
 * A convenience function performing the tan operation via the [Math] equivalent call.
 */
fun tan(arr: Int) = Math.tan(arr.toDouble())

/**
 * A convenience function performing the round operation via the [Math] equivalent call.
 */
fun round(arr: Int) = Math.round(arr.toDouble())

/**
 * A convenience function performing the floor operation via the [Math] equivalent call.
 */
fun floor(arr: Int) = Math.floor(arr.toDouble())

/**
 * A convenience function performing the max operation via the [Math] equivalent call.
 */
fun max(num1: Int, num2: Int) = if (num1 > num2) num1 else num2

/**
 * A convenience function performing the max operation via the [Math] equivalent call.
 */
fun max(num1: Double, num2: Int) = if (num1 > num2) num1 else num2.toDouble()

/**
 * A convenience function performing the max operation via the [Math] equivalent call.
 */
fun max(num1: Int, num2: Double) = if (num1 > num2) num1.toDouble() else num2

/**
 * A convenience function performing the min operation via the [Math] equivalent call.
 */
fun min(num1: Int, num2: Int) = if (num2 > num1) num1 else num2

/**
 * A convenience function performing the min operation via the [Math] equivalent call.
 */
fun min(num1: Int, num2: Double) = if (num2 > num1) num1.toDouble() else num2

/**
 * A convenience function performing the min operation via the [Math] equivalent call.
 */
fun min(num1: Double, num2: Int) = if (num2 > num1) num1 else num2.toDouble()

/**
 * A convenience function performing the logb operation via the [Math] equivalent call.
 */
fun logb(base: Int, num: Int) = Math.log(num.toDouble()) / Math.log(base.toDouble())

/**
 * A convenience function performing the logb operation via the [Math] equivalent call.
 */
fun logb(base: Int, num: Double) = Math.log(num) / Math.log(base.toDouble())

/**
 * A convenience function performing the logb operation via the [Math] equivalent call.
 */
fun logb(base: Double, num: Int) = Math.log(num.toDouble()) / Math.log(base)

/**
 * A convenience function performing the pow operation via the [Math] equivalent call.
 */
fun pow(num: Double, exp: Int) = Math.pow(num, exp.toDouble())

/**
 * A convenience function performing the pow operation via the [Math] equivalent call.
 */
fun pow(num: Int, exp: Int) = Math.pow(num.toDouble(), exp.toDouble())

/**
 * A convenience function performing the pow operation via the [Math] equivalent call.
 */
fun pow(num: Int, exp: Double) = Math.pow(num.toDouble(), exp)

// Extension functions for basic type for infix operators

/**
 * A convenience function performing the pow operation via the [Math] equivalent call.
 */
@JvmName("powIntInt")
infix fun Int.pow(exp: Int) = Math.pow(this.toDouble(), exp.toDouble())

/**
 * A convenience function performing the pow operation via the [Math] equivalent call.
 */
@JvmName("powDoubleInt")
infix fun Double.pow(exp: Int) = Math.pow(this, exp.toDouble())

/**
 * A convenience function performing the pow operation via the [Math] equivalent call.
 */
@JvmName("powIntDouble")
infix fun Int.pow(exp: Double) = Math.pow(this.toDouble(), exp)

/**
 * A convenience function performing the pow operation via the [Math] equivalent call.
 */
@JvmName("powDoubleDouble")
infix fun Double.pow(exp: Double) = Math.pow(this, exp)
