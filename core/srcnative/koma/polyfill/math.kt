package kotlin.math

//fun abs(value: Double) = fabs(value)

fun floor(num: Double) = platform.posix.floor(num)
fun round(num: Double) = platform.posix.round(num)
fun atan(num: Double) = platform.posix.atan(num)
fun tan(num: Double) = platform.posix.tan(num)
fun sqrt(num: Double) = platform.posix.sqrt(num)
fun sin(num: Double) = platform.posix.sin(num)
fun exp(num: Double) = platform.posix.exp(num)
fun cos(num: Double) = platform.posix.cos(num)
fun ceil(num: Double) = platform.posix.ceil(num)
fun abs(num: Double) = platform.posix.fabs(num)
fun atan2(x: Double, y: Double) = platform.posix.atan2(x, y)
fun asin(num: Double) = platform.posix.asin(num)
fun acos(num: Double) = platform.posix.acos(num)


fun log(num: Double, base: Double) = platform.posix.log(num) / platform.posix.log(base)
fun ln(num: Double) = platform.posix.log(num)
fun Double.pow(other: Double) = platform.posix.pow(this, other)
