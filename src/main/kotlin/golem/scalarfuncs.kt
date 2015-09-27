package golem

// Scalar funcs

fun abs(arr: Double) = java.lang.Math.abs(arr)
fun ceil(num: Double) = Math.ceil(num)
fun cos(arr: Double) = java.lang.Math.cos(arr)
fun exp(arr: Double) = java.lang.Math.exp(arr)
fun log(arr: Double) = java.lang.Math.log(arr)
fun pow(num:Double, exp: Double) = Math.pow(num, exp)
fun sign(arr: Double) = java.lang.Math.signum(arr)
fun sin(arr: Double) = java.lang.Math.sin(arr)
fun sqrt(arr: Double) = java.lang.Math.sqrt(arr)
fun tan(arr: Double) = java.lang.Math.tan(arr)
fun round(arr: Double) = java.lang.Math.round(arr)
fun floor(arr: Double) = java.lang.Math.floor(arr)
fun max(num1: Double, num2: Double) = if (num1 > num2) num1 else num2
fun min(num1: Double, num2: Double) = if (num2 > num1) num1 else num2

fun min(num1: Int, num2: Int) = if (num2 > num1) num1 else num2
fun logb(base: Int, num: Double) = Math.log(num) / Math.log(base.toDouble())
