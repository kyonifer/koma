/**
 * This file contains top-level common mathematical functions that operate on
 * Matrices. These definitions follow numpy as close as possible, and allow
 * one to do things like cos(randn(5,5))
 */

package golem

import golem.matrix.ejml.Mat

// Scalar funcs
fun abs(arr: Mat) = arr.map{java.lang.Math.abs(it)}
fun ceil(arr: Mat) = arr.map{java.lang.Math.ceil(it)}
fun cos(arr: Mat) = arr.map{java.lang.Math.cos(it)}
fun exp(arr: Mat) = arr.map{java.lang.Math.exp(it)}
fun log(arr: Mat) = arr.map{java.lang.Math.log(it)}
fun pow(arr: Mat, num: Double) = arr.map{java.lang.Math.pow(it, num)}
fun sign(arr: Mat) = arr.map{java.lang.Math.signum(it)}
fun sin(arr: Mat) = arr.map{java.lang.Math.sin(it)}
fun sqrt(arr: Mat) = arr.map{java.lang.Math.sqrt(it)}
fun tan(arr: Mat) = arr.map{java.lang.Math.tan(it)}
fun round(arr: Mat) = arr.map{java.lang.Math.round(it).toDouble()}
fun floor(arr: Mat) = arr.map{java.lang.Math.floor(it)}

// Matrix funcs
fun diag(arr: Mat) = arr.storage.extractDiag()
fun cumsum(arr: Mat) = arr.storage.elementSum()
fun max(arr: Mat) = arr.max()
fun mean(arr: Mat) = arr.mean()
fun min(arr: Mat) = arr.min()
fun argMax(arr: Mat) = arr.argMax()
fun argMean(arr: Mat) = arr.argMean()
fun argMin(arr: Mat) = arr.argMin()
fun norm(arr: Mat) = arr.norm()


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
