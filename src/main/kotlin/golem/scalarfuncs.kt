@file:JvmName("Golem")
@file:JvmMultifileClass

package golem

// Scalar funcs

fun abs(arr: Double) = java.lang.Math.abs(arr)
fun ceil(num: Double) = Math.ceil(num)
fun cos(arr: Double) = java.lang.Math.cos(arr)
fun exp(arr: Double) = java.lang.Math.exp(arr)
fun log(arr: Double) = java.lang.Math.log(arr)
fun pow(num: Double, exp: Double) = Math.pow(num, exp)
fun sign(arr: Double) = java.lang.Math.signum(arr)
fun sin(arr: Double) = java.lang.Math.sin(arr)
fun sqrt(arr: Double) = java.lang.Math.sqrt(arr)
fun tan(arr: Double) = java.lang.Math.tan(arr)
fun round(arr: Double) = java.lang.Math.round(arr)
fun floor(arr: Double) = java.lang.Math.floor(arr)

fun max(num1: Double, num2: Double) = if (num1 > num2) num1 else num2
fun min(num1: Double, num2: Double) = if (num2 > num1) num1 else num2
fun logb(base: Double, num: Double) = Math.log(num) / Math.log(base)


// Int versions

fun abs(arr: Int) = java.lang.Math.abs(arr.toDouble())
fun ceil(num: Int) = Math.ceil(num.toDouble())
fun cos(arr: Int) = java.lang.Math.cos(arr.toDouble())
fun exp(arr: Int) = java.lang.Math.exp(arr.toDouble())
fun log(arr: Int) = java.lang.Math.log(arr.toDouble())
//fun pow(num: Int, exp: Int) = Math.pow(num.toDouble(), exp.toDouble())
fun pow(num: Double, exp: Int) = Math.pow(num, exp.toDouble())
fun pow(num: Int, exp: Double) = Math.pow(num.toDouble(), exp)
fun sign(arr: Int) = java.lang.Math.signum(arr.toDouble())
fun sin(arr: Int) = java.lang.Math.sin(arr.toDouble())
fun sqrt(arr: Int) = java.lang.Math.sqrt(arr.toDouble())
fun tan(arr: Int) = java.lang.Math.tan(arr.toDouble())
fun round(arr: Int) = java.lang.Math.round(arr.toDouble())
fun floor(arr: Int) = java.lang.Math.floor(arr.toDouble())
fun max(num1: Int, num2: Int) = if (num1 > num2) num1 else num2
fun max(num1: Double, num2: Int) = if (num1 > num2) num1 else num2.toDouble()
fun max(num1: Int, num2: Double) = if (num1 > num2) num1.toDouble() else num2
fun min(num1: Int, num2: Int) = if (num2 > num1) num1 else num2
fun min(num1: Int, num2: Double) = if (num2 > num1) num1.toDouble() else num2
fun min(num1: Double, num2: Int) = if (num2 > num1) num1 else num2.toDouble()
fun logb(base: Int, num: Int) = Math.log(num.toDouble()) / Math.log(base.toDouble())
fun logb(base: Int, num: Double) = Math.log(num) / Math.log(base.toDouble())
fun logb(base: Double, num: Int) = Math.log(num.toDouble()) / Math.log(base)

// Extension functions for basic type for infix operators

@JvmName("powIntInt")
fun Int.pow(exp: Int) = Math.pow(this.toDouble(), exp.toDouble())
@JvmName("powDoubleInt")
fun Double.pow(exp: Int) = Math.pow(this, exp.toDouble())
@JvmName("powIntDouble")
fun Int.pow(exp: Double) = Math.pow(this.toDouble(), exp)
@JvmName("powDoubleDouble")
fun Double.pow(exp: Double) = Math.pow(this, exp)
