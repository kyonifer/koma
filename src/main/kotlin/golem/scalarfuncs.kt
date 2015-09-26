package golem

// Scalar funcs
fun logb(base: Int, num: Double) = Math.log(num) / Math.log(base.toDouble())
fun max(num1: Double, num2: Double) = if (num1 > num2) num1 else num2
fun min(num1: Double, num2: Double) = if (num2 > num1) num1 else num2
fun min(num1: Int, num2: Int) = if (num2 > num1) num1 else num2
fun ceil(num: Double) = Math.ceil(num)
fun pow(num:Double, exp: Double) = Math.pow(num, exp)
