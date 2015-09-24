/**
 * This file contains top-level common mathematical functions that operate on
 * Matrices. These definitions follow numpy as close as possible, and allow
 * one to do things like cos(randn(5,5))
 */

package golem

import org.ujmp.core.calculation.Calculation
import org.ujmp.core.Matrix

fun abs(arr: Matrix) = arr.abs(Calculation.Ret.NEW)
fun ceil(arr: Matrix) = arr.ceil(Calculation.Ret.NEW)
fun cos(arr: Matrix) = arr.cos(Calculation.Ret.NEW)
fun exp(arr: Matrix) = arr.exp(Calculation.Ret.NEW)
fun floor(arr: Matrix) = arr.floor(Calculation.Ret.NEW)
fun log(arr: Matrix) = arr.log(Calculation.Ret.NEW)
fun pow(arr: Matrix, num: Double) = arr.power(Calculation.Ret.NEW, num)
fun round(arr: Matrix) = arr.round(Calculation.Ret.NEW)
fun sign(arr: Matrix) = arr.sign(Calculation.Ret.NEW)
fun sin(arr: Matrix) = arr.sin(Calculation.Ret.NEW)
fun sqrt(arr: Matrix) = arr.sqrt(Calculation.Ret.NEW)
fun tan(arr: Matrix) = arr.tan(Calculation.Ret.NEW)
fun argMax(arr: Matrix, index: Int) = arr.indexOfMax(Calculation.Ret.NEW, index)
fun cumsum(arr: Matrix) = arr.cumsum(true)
fun diag(arr: Matrix) = arr.diag(Calculation.Ret.NEW)
fun max(arr: Matrix, dimension: Int = 0) = arr.max(Calculation.Ret.NEW, dimension)
fun mean(arr: Matrix, dimension: Int = 0) = arr.mean(Calculation.Ret.NEW, dimension, true)
fun min(arr: Matrix, dimension: Int = 0) = arr.min(Calculation.Ret.NEW, dimension)

fun norm1(arr: Matrix) = arr.norm1()
fun norm2(arr: Matrix) = arr.norm2()
fun norm(arr: Matrix) = norm1(arr)  // Default is L2 (Euclidean) norm

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
