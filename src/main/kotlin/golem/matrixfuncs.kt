/**
 * This file contains top-level common mathematical functions that operate on
 * Matrices. These definitions follow numpy as close as possible, and allow
 * one to do things like cos(randn(5,5))
 */

package golem

import golem.matrix.Matrix

// Elementwise funcs
fun abs(arr: Matrix<Double>) = arr.map{java.lang.Math.abs(it)}
fun ceil(arr: Matrix<Double>) = arr.map{java.lang.Math.ceil(it)}
fun cos(arr: Matrix<Double>) = arr.map{java.lang.Math.cos(it)}
fun exp(arr: Matrix<Double>) = arr.map{java.lang.Math.exp(it)}
fun log(arr: Matrix<Double>) = arr.map{java.lang.Math.log(it)}
fun epow(arr: Matrix<Double>, num: Double) = arr.epow(num)
fun pow(arr: Matrix<Double>, num:Int) = arr.pow(num)
fun sign(arr: Matrix<Double>) = arr.map{java.lang.Math.signum(it)}
fun sin(arr: Matrix<Double>) = arr.map{java.lang.Math.sin(it)}
fun sqrt(arr: Matrix<Double>) = arr.map{java.lang.Math.sqrt(it)}
fun tan(arr: Matrix<Double>) = arr.map{java.lang.Math.tan(it)}
fun round(arr: Matrix<Double>) = arr.map{java.lang.Math.round(it).toDouble()}
fun floor(arr: Matrix<Double>) = arr.map{java.lang.Math.floor(it)}
fun logb(base: Int, arr: Matrix<Double>) = arr.map{java.lang.Math.log(it) / Math.log(base.toDouble())}

// Matrix funcs
fun diag(arr: Matrix<Double>) = arr.diag()
fun cumsum(arr: Matrix<Double>) = arr.cumsum()
fun max(arr: Matrix<Double>) = arr.max()
fun mean(arr: Matrix<Double>) = arr.mean()
fun min(arr: Matrix<Double>) = arr.min()
fun argMax(arr: Matrix<Double>) = arr.argMax()
fun argMean(arr: Matrix<Double>) = arr.argMean()
fun argMin(arr: Matrix<Double>) = arr.argMin()
fun norm(arr: Matrix<Double>) = arr.norm()

// Adv funcs
fun expm(A: Matrix<Double>) = A.expm()

// TODO:
//fun cumsum(arr: Matrix, dimension: Int = 0) = arr.cumsum(true)
//fun fft(arr: Matrix)
//fun hstack(vararg arrs: Matrix) = .hstack(*arrs)
//fun vstack(vararg arrs: Matrix) = .vstack(*arrs)
//fun neg(arr: Matrix) = arr.
//fun oneminus(arr: Matrix) = exec(transforms.OneMinus(arr))
//fun acos(arr: Matrix) = arr.acos(Calculation.Ret.NEW)
//fun asin(arr: Matrix) =
//fun atan(arr: Matrix) =
