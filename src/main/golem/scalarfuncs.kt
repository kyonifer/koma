@file:JvmName("Golem")
@file:JvmMultifileClass

package golem

// Scalar funcs

/**
 * A convenience function performing the abs operation via the [java.lang.Math] equivalent call.
 */
fun abs(arr: Double) = java.lang.Math.abs(arr)
/**
 * A convenience function performing the ceil via the [java.lang.Math] equivalent call.
 */
fun ceil(num: Double) = Math.ceil(num)
/**
 * A convenience function performing the cos operation via the [java.lang.Math] equivalent call.
 */
fun cos(arr: Double) = java.lang.Math.cos(arr)
/**
 * A convenience function performing the exp operation via the [java.lang.Math] equivalent call.
 */
fun exp(arr: Double) = java.lang.Math.exp(arr)
/**
 * A convenience function performing the log operation via the [java.lang.Math] equivalent call.
 */
fun log(arr: Double) = java.lang.Math.log(arr)
/**
 * A convenience function performing the pow operation via the [java.lang.Math] equivalent call.
 */
fun pow(num: Double, exp: Double) = Math.pow(num, exp)
/**
 * A convenience function performing the sign operation via the [java.lang.Math] equivalent call.
 */
fun sign(arr: Double) = java.lang.Math.signum(arr)
/**
 * A convenience function performing the sin operation via the [java.lang.Math] equivalent call.
 */
fun sin(arr: Double) = java.lang.Math.sin(arr)
/**
 * A convenience function performing the sqrt operation via the [java.lang.Math] equivalent call.
 */
fun sqrt(arr: Double) = java.lang.Math.sqrt(arr)
/**
 * A convenience function performing the tan operation via the [java.lang.Math] equivalent call.
 */
fun tan(arr: Double) = java.lang.Math.tan(arr)
/**
 * A convenience function performing the round operation via the [java.lang.Math] equivalent call.
 */
fun round(arr: Double) = java.lang.Math.round(arr)
/**
 * A convenience function performing the floor operation via the [java.lang.Math] equivalent call.
 */
fun floor(arr: Double) = java.lang.Math.floor(arr)
/**
 * A convenience function performing the acos operation via the [java.lang.Math] equivalent call.
 */
fun acos(arr: Double) = java.lang.Math.acos(arr)
/**
 * A convenience function performing the asin operation via the [java.lang.Math] equivalent call.
 */
fun asin(arr: Double) = java.lang.Math.asin(arr)
/**
 * A convenience function performing the atan operation via the [java.lang.Math] equivalent call.
 */
fun atan(arr: Double) = java.lang.Math.atan(arr)
/**
 * A convenience function performing the atan2 operation via the [java.lang.Math] equivalent call.
 */
fun atan2(x: Double, y:Double) = java.lang.Math.atan2(x,y)
/**
 * A convenience function performing the acos operation via the [java.lang.Math] equivalent call.
 */
fun acos(arr: Int) = java.lang.Math.acos(arr.toDouble())
/**
 * A convenience function performing the asin operation via the [java.lang.Math] equivalent call.
 */
fun asin(arr: Int) = java.lang.Math.asin(arr.toDouble())
/**
 * A convenience function performing the atan operation via the [java.lang.Math] equivalent call.
 */
fun atan(arr: Int) = java.lang.Math.atan(arr.toDouble())
/**
 * A convenience function performing the atan2 operation via the [java.lang.Math] equivalent call.
 */
fun atan2(x: Int, y:Int) = java.lang.Math.atan2(x.toDouble(),y.toDouble())
/**
 * A convenience function performing the max operation via the [java.lang.Math] equivalent call.
 */
fun max(num1: Double, num2: Double) = if (num1 > num2) num1 else num2
/**
 * A convenience function performing the min operation via the [java.lang.Math] equivalent call.
 */
fun min(num1: Double, num2: Double) = if (num2 > num1) num1 else num2
/**
 * A convenience function performing the logb operation via the [java.lang.Math] equivalent call.
 */
fun logb(base: Double, num: Double) = Math.log(num) / Math.log(base)

// Int versions
/**
 * A convenience function performing the abs via the [java.lang.Math] equivalent call.
 */
fun abs(arr: Int) = java.lang.Math.abs(arr.toDouble())
/**
 * A convenience function performing the ceil operation via the [java.lang.Math] equivalent call.
 */
fun ceil(num: Int) = Math.ceil(num.toDouble())
/**
 * A convenience function performing the cos operation via the [java.lang.Math] equivalent call.
 */
fun cos(arr: Int) = java.lang.Math.cos(arr.toDouble())
/**
 * A convenience function performing the exp operation via the [java.lang.Math] equivalent call.
 */
fun exp(arr: Int) = java.lang.Math.exp(arr.toDouble())
/**
 * A convenience function performing the log operation via the [java.lang.Math] equivalent call.
 */
fun log(arr: Int) = java.lang.Math.log(arr.toDouble())
/**
 * A convenience function performing the sign operation via the [java.lang.Math] equivalent call.
 */
fun sign(arr: Int) = java.lang.Math.signum(arr.toDouble())
/**
 * A convenience function performing the sin operation via the [java.lang.Math] equivalent call.
 */
fun sin(arr: Int) = java.lang.Math.sin(arr.toDouble())
/**
 * A convenience function performing the sqrt operation via the [java.lang.Math] equivalent call.
 */
fun sqrt(arr: Int) = java.lang.Math.sqrt(arr.toDouble())
/**
 * A convenience function performing the tan operation via the [java.lang.Math] equivalent call.
 */
fun tan(arr: Int) = java.lang.Math.tan(arr.toDouble())
/**
 * A convenience function performing the round operation via the [java.lang.Math] equivalent call.
 */
fun round(arr: Int) = java.lang.Math.round(arr.toDouble())
/**
 * A convenience function performing the floor operation via the [java.lang.Math] equivalent call.
 */
fun floor(arr: Int) = java.lang.Math.floor(arr.toDouble())
/**
 * A convenience function performing the max operation via the [java.lang.Math] equivalent call.
 */
fun max(num1: Int, num2: Int) = if (num1 > num2) num1 else num2
/**
 * A convenience function performing the max operation via the [java.lang.Math] equivalent call.
 */
fun max(num1: Double, num2: Int) = if (num1 > num2) num1 else num2.toDouble()
/**
 * A convenience function performing the max operation via the [java.lang.Math] equivalent call.
 */
fun max(num1: Int, num2: Double) = if (num1 > num2) num1.toDouble() else num2
/**
 * A convenience function performing the min operation via the [java.lang.Math] equivalent call.
 */
fun min(num1: Int, num2: Int) = if (num2 > num1) num1 else num2
/**
 * A convenience function performing the min operation via the [java.lang.Math] equivalent call.
 */
fun min(num1: Int, num2: Double) = if (num2 > num1) num1.toDouble() else num2
/**
 * A convenience function performing the min operation via the [java.lang.Math] equivalent call.
 */
fun min(num1: Double, num2: Int) = if (num2 > num1) num1 else num2.toDouble()
/**
 * A convenience function performing the logb operation via the [java.lang.Math] equivalent call.
 */
fun logb(base: Int, num: Int) = Math.log(num.toDouble()) / Math.log(base.toDouble())
/**
 * A convenience function performing the logb operation via the [java.lang.Math] equivalent call.
 */
fun logb(base: Int, num: Double) = Math.log(num) / Math.log(base.toDouble())
/**
 * A convenience function performing the logb operation via the [java.lang.Math] equivalent call.
 */
fun logb(base: Double, num: Int) = Math.log(num.toDouble()) / Math.log(base)
/**
 * A convenience function performing the pow operation via the [java.lang.Math] equivalent call.
 */
fun pow(num: Double, exp: Int) = Math.pow(num, exp.toDouble())
/**
 * A convenience function performing the pow operation via the [java.lang.Math] equivalent call.
 */
fun pow(num: Int, exp: Int) = Math.pow(num.toDouble(), exp.toDouble())
/**
 * A convenience function performing the pow operation via the [java.lang.Math] equivalent call.
 */
fun pow(num: Int, exp: Double) = Math.pow(num.toDouble(), exp)

// Extension functions for basic type for infix operators

/**
 * A convenience function performing the pow operation via the [java.lang.Math] equivalent call.
 */
@JvmName("powIntInt")
infix fun Int.pow(exp: Int) = Math.pow(this.toDouble(), exp.toDouble())
/**
 * A convenience function performing the pow operation via the [java.lang.Math] equivalent call.
 */
@JvmName("powDoubleInt")
infix fun Double.pow(exp: Int) = Math.pow(this, exp.toDouble())
/**
 * A convenience function performing the pow operation via the [java.lang.Math] equivalent call.
 */
@JvmName("powIntDouble")
infix fun Int.pow(exp: Double) = Math.pow(this.toDouble(), exp)
/**
 * A convenience function performing the pow operation via the [java.lang.Math] equivalent call.
 */
@JvmName("powDoubleDouble")
infix fun Double.pow(exp: Double) = Math.pow(this, exp)
