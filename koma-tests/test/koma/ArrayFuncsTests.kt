package koma

import koma.extensions.get
import koma.internal.getRng
import koma.ndarray.NDArray
import org.junit.Test

class ArrayFuncsTests {
    
    @Test
    fun testStandardFunctions() {
        fun testFloat(arrayFn: (NDArray<Float>) -> NDArray<Float>, elemFn: (Float) -> Float, minVal: Float=0f, maxVal: Float=1f) {
            val x = NDArray(10, filler = { minVal + (maxVal-minVal)*getRng().nextDouble().toFloat() })
            val y = arrayFn(x)
            for (i in 0..9)
                assert(y[i] == elemFn(x[i]))
        }
        fun testDouble(arrayFn: (NDArray<Double>) -> NDArray<Double>, elemFn: (Double) -> Double, minVal: Double=0.0, maxVal: Double=1.0) {
            val x = NDArray(10, filler = { minVal + (maxVal-minVal)*getRng().nextDouble() })
            val y = arrayFn(x)
            for (i in 0..9)
                assert(y[i] == elemFn(x[i]))
        }
        testFloat({ abs(it) }, { kotlin.math.abs(it) }, -3f, 3f)
        testDouble({ abs(it) }, { kotlin.math.abs(it) }, -3.0, 3.0)
        testFloat({ acos(it) }, { kotlin.math.acos(it) })
        testDouble({ acos(it) }, { kotlin.math.acos(it) })
        testFloat({ acosh(it) }, { kotlin.math.acosh(it) }, 1f, 3f)
        testDouble({ acosh(it) }, { kotlin.math.acosh(it) }, 1.0, 3.0)
        testFloat({ asin(it) }, { kotlin.math.asin(it) })
        testDouble({ asin(it) }, { kotlin.math.asin(it) })
        testFloat({ asinh(it) }, { kotlin.math.asinh(it) })
        testDouble({ asinh(it) }, { kotlin.math.asinh(it) })
        testFloat({ atan(it) }, { kotlin.math.atan(it) })
        testDouble({ atan(it) }, { kotlin.math.atan(it) })
        testFloat({ atanh(it) }, { kotlin.math.atanh(it) })
        testDouble({ atanh(it) }, { kotlin.math.atanh(it) })
        testFloat({ ceil(it) }, { kotlin.math.ceil(it) }, -3f, 3f)
        testDouble({ ceil(it) }, { kotlin.math.ceil(it) }, -3.0, 3.0)
        testFloat({ cos(it) }, { kotlin.math.cos(it) })
        testDouble({ cos(it) }, { kotlin.math.cos(it) })
        testFloat({ cosh(it) }, { kotlin.math.cosh(it) })
        testDouble({ cosh(it) }, { kotlin.math.cosh(it) })
        testFloat({ exp(it) }, { kotlin.math.exp(it) })
        testDouble({ exp(it) }, { kotlin.math.exp(it) })
        testFloat({ floor(it) }, { kotlin.math.floor(it) }, -3f, 3f)
        testDouble({ floor(it) }, { kotlin.math.floor(it) }, -3.0, 3.0)
        testFloat({ ln(it) }, { kotlin.math.ln(it) })
        testDouble({ ln(it) }, { kotlin.math.ln(it) })
        testFloat({ round(it) }, { kotlin.math.round(it) }, -3f, 3f)
        testDouble({ round(it) }, { kotlin.math.round(it) }, -3.0, 3.0)
        testFloat({ sign(it) }, { kotlin.math.sign(it) })
        testDouble({ sign(it) }, { kotlin.math.sign(it) })
        testFloat({ sin(it) }, { kotlin.math.sin(it) })
        testDouble({ sin(it) }, { kotlin.math.sin(it) })
        testFloat({ sinh(it) }, { kotlin.math.sinh(it) })
        testDouble({ sinh(it) }, { kotlin.math.sinh(it) })
        testFloat({ sqrt(it) }, { kotlin.math.sqrt(it) })
        testDouble({ sqrt(it) }, { kotlin.math.sqrt(it) })
        testFloat({ tan(it) }, { kotlin.math.tan(it) })
        testDouble({ tan(it) }, { kotlin.math.tan(it) })
        testFloat({ tanh(it) }, { kotlin.math.tanh(it) })
        testDouble({ tanh(it) }, { kotlin.math.tanh(it) })
    }
}