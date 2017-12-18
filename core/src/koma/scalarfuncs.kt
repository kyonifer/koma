@file:JvmName("Koma")
@file:JvmMultifileClass

package koma

import koma.platformsupport.*
import koma.polyfill.annotations.*
import kotlin.math.pow as kotlinPow
// Scalar funcs

/**
 * A convenience function performing the abs operation via the [kotlin.math] equivalent call.
 */
fun abs(arr: Double) = kotlin.math.abs(arr)

/**
 * A convenience function performing the ceil via the [kotlin.math] equivalent call.
 */
fun ceil(num: Double) = kotlin.math.ceil(num).toInt()

/**
 * A convenience function performing the cos operation via the [kotlin.math] equivalent call.
 */
fun cos(arr: Double) = kotlin.math.cos(arr)

/**
 * A convenience function performing the exp operation via the [kotlin.math] equivalent call.
 */
fun exp(arr: Double) = kotlin.math.exp(arr)

/**
 * A convenience function performing the sign operation via the [kotlin.math] equivalent call.
 */
fun sign(arr: Double) = signum(arr)

/**
 * A convenience function performing the sin operation via the [kotlin.math] equivalent call.
 */
fun sin(arr: Double) = kotlin.math.sin(arr)

/**
 * A convenience function performing the sqrt operation via the [kotlin.math] equivalent call.
 */
fun sqrt(arr: Double) = kotlin.math.sqrt(arr)

/**
 * A convenience function performing the tan operation via the [kotlin.math] equivalent call.
 */
fun tan(arr: Double) = kotlin.math.tan(arr)

/**
 * A convenience function performing the round operation via the [kotlin.math] equivalent call.
 */
fun round(arr: Double) = kotlin.math.round(arr)

/**
 * A convenience function performing the floor operation via the [kotlin.math] equivalent call.
 */
fun floor(arr: Double) = kotlin.math.floor(arr).toInt()

/**
 * A convenience function performing the acos operation via the [kotlin.math] equivalent call.
 */
fun acos(arr: Double) = kotlin.math.acos(arr)

/**
 * A convenience function performing the asin operation via the [kotlin.math] equivalent call.
 */
fun asin(arr: Double) = kotlin.math.asin(arr)

/**
 * A convenience function performing the atan operation via the [kotlin.math] equivalent call.
 */
fun atan(arr: Double) = kotlin.math.atan(arr)

/**
 * A convenience function performing the atan2 operation via the [kotlin.math] equivalent call.
 */
fun atan2(x: Double, y: Double) = kotlin.math.atan2(x, y)

/**
 * A convenience function performing the acos operation via the [kotlin.math] equivalent call.
 */
fun acos(arr: Int) = kotlin.math.acos(arr.toDouble())

/**
 * A convenience function performing the asin operation via the [kotlin.math] equivalent call.
 */
fun asin(arr: Int) = kotlin.math.asin(arr.toDouble())

/**
 * A convenience function performing the atan operation via the [kotlin.math] equivalent call.
 */
fun atan(arr: Int) = kotlin.math.atan(arr.toDouble())

/**
 * A convenience function performing the atan2 operation via the [kotlin.math] equivalent call.
 */
fun atan2(x: Int, y: Int) = kotlin.math.atan2(x.toDouble(), y.toDouble())

/**
 * A convenience function performing the max operation via the [kotlin.math] equivalent call.
 */
fun max(num1: Double, num2: Double) = if (num1 > num2) num1 else num2

/**
 * A convenience function performing the min operation via the [kotlin.math] equivalent call.
 */
fun min(num1: Double, num2: Double) = if (num2 > num1) num1 else num2

// Int versions
/**
 * A convenience function performing the abs via the [kotlin.math] equivalent call.
 */
fun abs(arr: Int) = kotlin.math.abs(arr.toDouble())

/**
 * A convenience function performing the ceil operation via the [kotlin.math] equivalent call.
 */
fun ceil(num: Int) = kotlin.math.ceil(num.toDouble())

/**
 * A convenience function performing the cos operation via the [kotlin.math] equivalent call.
 */
fun cos(arr: Int) = kotlin.math.cos(arr.toDouble())

/**
 * A convenience function performing the exp operation via the [kotlin.math] equivalent call.
 */
fun exp(arr: Int) = kotlin.math.exp(arr.toDouble())



/**
 * A convenience function performing the sign operation via the [kotlin.math] equivalent call.
 */
fun sign(arr: Int) = signum(arr.toDouble())

/**
 * A convenience function performing the sin operation via the [kotlin.math] equivalent call.
 */
fun sin(arr: Int) = kotlin.math.sin(arr.toDouble())

/**
 * A convenience function performing the sqrt operation via the [kotlin.math] equivalent call.
 */
fun sqrt(arr: Int) = kotlin.math.sqrt(arr.toDouble())

/**
 * A convenience function performing the tan operation via the [kotlin.math] equivalent call.
 */
fun tan(arr: Int) = kotlin.math.tan(arr.toDouble())

/**
 * A convenience function performing the round operation via the [kotlin.math] equivalent call.
 */
fun round(arr: Int) = kotlin.math.round(arr.toDouble())

/**
 * A convenience function performing the floor operation via the [kotlin.math] equivalent call.
 */
fun floor(arr: Int) = kotlin.math.floor(arr.toDouble())

/**
 * A convenience function performing the max operation via the [kotlin.math] equivalent call.
 */
fun max(num1: Int, num2: Int) = if (num1 > num2) num1 else num2

/**
 * A convenience function performing the max operation via the [kotlin.math] equivalent call.
 */
fun max(num1: Double, num2: Int) = if (num1 > num2) num1 else num2.toDouble()

/**
 * A convenience function performing the max operation via the [kotlin.math] equivalent call.
 */
fun max(num1: Int, num2: Double) = if (num1 > num2) num1.toDouble() else num2

/**
 * A convenience function performing the min operation via the [kotlin.math] equivalent call.
 */
fun min(num1: Int, num2: Int) = if (num2 > num1) num1 else num2

/**
 * A convenience function performing the min operation via the [kotlin.math] equivalent call.
 */
fun min(num1: Int, num2: Double) = if (num2 > num1) num1.toDouble() else num2

/**
 * A convenience function performing the min operation via the [kotlin.math] equivalent call.
 */
fun min(num1: Double, num2: Int) = if (num2 > num1) num1 else num2.toDouble()


/**
 * A convenience function performing the log operation via the [kotlin.math] equivalent call.
 */
fun ln(arr: Double) = kotlin.math.ln(arr)

@Deprecated("Please use ln() instead", ReplaceWith("ln(arr)"))
fun log(arr: Double) = ln(arr)

/**
 * A convenience function performing the log operation via the [kotlin.math] equivalent call.
 */
fun ln(arr: Int) = ln(arr.toDouble())

@Deprecated("Use ln()", ReplaceWith("ln(arr)"))
fun log(arr: Int) = ln(arr)

/**
 * A convenience function performing the logb operation via the [kotlin.math] equivalent call.
 */
fun logb(base: Double, num: Double) = kotlin.math.log(num, base)

/**
 * A convenience function performing the logb operation via the [kotlin.math] equivalent call.
 */
fun logb(base: Int, num: Int) = logb(base.toDouble(), num.toDouble())

/**
 * A convenience function performing the logb operation via the [kotlin.math] equivalent call.
 */
fun logb(base: Int, num: Double) = logb(base.toDouble(), num)

/**
 * A convenience function performing the logb operation via the [kotlin.math] equivalent call.
 */
fun logb(base: Double, num: Int) = logb(base, num.toDouble())

/**
 * A convenience function performing the pow operation via the [kotlin.math] equivalent call.
 */
fun pow(num: Double, exp: Double) = num.kotlinPow(exp) // Picks up kotlin.math extension

/**
 * A convenience function performing the pow operation via the [kotlin.math] equivalent call.
 */
fun pow(num: Int, exp: Int) = koma.pow(num.toDouble(), exp.toDouble())

/**
 * A convenience function performing the pow operation via the [kotlin.math] equivalent call.
 */
fun pow(num: Double, exp: Int) = koma.pow(num, exp.toDouble())

/**
 * A convenience function performing the pow operation via the [kotlin.math] equivalent call.
 */
fun pow(num: Int, exp: Double) = koma.pow(num.toDouble(), exp)

/**
 * A convenience ext function performing the pow operation via the [kotlin.math] equivalent call.
 */
@JvmName("powDoubleDouble")
infix fun Double.pow(exp: Double) = koma.pow(this, exp)

/**
 * A convenience ext function performing the pow operation via the [kotlin.math] equivalent call.
 */
@JvmName("powIntInt")
infix fun Int.pow(exp: Int) = koma.pow(this.toDouble(), exp.toDouble())

/**
 * A convenience ext function performing the pow operation via the [kotlin.math] equivalent call.
 */
@JvmName("powDoubleInt")
infix fun Double.pow(exp: Int) = koma.pow(this, exp.toDouble())

/**
 * A convenience ext function performing the pow operation via the [kotlin.math] equivalent call.
 */
@JvmName("powIntDouble")
infix fun Int.pow(exp: Double) = koma.pow(this.toDouble(), exp)

