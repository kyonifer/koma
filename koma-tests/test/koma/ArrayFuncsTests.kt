package koma

import koma.extensions.*
import koma.internal.getRng
import koma.ndarray.NDArray
import org.junit.Test

class ArrayFuncsTests {
    
    @Test
    fun testAllClose() {
        assert(allclose(ndArrayOf(1, 2, 3), ndArrayOf(1.0, 2.0, 3.0)))
        assert(!allclose(ndArrayOf(1, 2, 3, 4), ndArrayOf(1.0, 2.0, 3.0)))
        assert(!allclose(ndArrayOf(1, 2, 3), ndArrayOf(1.0, 2.0, 3.0, 4.0)))
        assert(allclose(ndArrayOf(1, 2, 3), ndArrayOf(1.0, 2.0, 3.0+1e-6)))
        assert(!allclose(ndArrayOf(1, 2, 3), ndArrayOf(1.0, 2.0, 3.0+1e-4)))
        assert(allclose(ndArrayOf(1.0e6, 2.0e6, 3.0e6), ndArrayOf(1.0e6, 2.0e6, 3.0e6)))
        assert(allclose(ndArrayOf(1.0e6, 2.0e6, 3.0e6), ndArrayOf(1.0e6, 2.0e6+1.0, 3.0e6)))
        assert(!allclose(ndArrayOf(1.0e6, 2.0e6, 3.0e6), ndArrayOf(1.0e6, 2.0e6+1.0, 3.0e6), rtol=0.0))
        assert(allclose(ndArrayOf(1.0e6, 2.0e6, 3.0e6), ndArrayOf(1.0e6, 2.0e6+1.0, 3.0e6), rtol=0.0, atol=1.1))
    }
    
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
    
    @Test
    fun testSum() {
        val bytes = NDArray.createLinear(100, filler = { (it+1).toByte() })
        assert(sum(bytes) == 5050L)
        val shorts = NDArray.createLinear(100, filler = { (it+1).toShort() })
        assert(sum(shorts) == 5050L)
        val ints = NDArray.createLinear(100, filler = { it+1 })
        assert(sum(ints) == 5050L)
        val longs = NDArray.createLinear(100, filler = { (it+1).toLong() })
        assert(sum(longs) == 5050L)
        val floats = NDArray(1001, filler = { PI.toFloat() })
        floats[500] = (10000*PI).toFloat()
        val expectedFloatSum = 11000*PI.toFloat()
        assert(kotlin.math.abs(sum(floats)-expectedFloatSum)/expectedFloatSum < 1e-7)
        val doubles = NDArray(1001, filler = { PI })
        doubles[500] = 10000*PI
        val expectedDoubleSum = 11000*PI
        assert(kotlin.math.abs(sum(doubles)-expectedDoubleSum)/expectedDoubleSum < 1e-15)
    }
    
    @Test
    fun testMean() {
        val bytes = NDArray.createLinear(100, filler = { (it+1).toByte() })
        assert(mean(bytes) == 50.50)
        val shorts = NDArray.createLinear(100, filler = { (it+1).toShort() })
        assert(mean(shorts) == 50.50)
        val ints = NDArray.createLinear(100, filler = { it+1 })
        assert(mean(ints) == 50.50)
        val longs = NDArray.createLinear(100, filler = { (it+1).toLong() })
        assert(mean(longs) == 50.50)
        val floats = NDArray.createLinear(100, filler = { (it+1).toFloat() })
        assert(mean(floats) == 50.50)
        val doubles = NDArray.createLinear(100, filler = { (it+1).toDouble() })
        assert(mean(doubles) == 50.50)
    }

    @Test
    fun testArgMin() {
        val bytes = NDArray(5, 5, filler = { (getRng().nextGaussian()*100).toByte() })
        val minByte = argMin(bytes)
        for (i in bytes.toIterable())
            assert(i >= bytes.getByte(minByte))
        val shorts = NDArray(5, 5, filler = { (getRng().nextGaussian()*100).toShort() })
        val minShort = argMin(shorts)
        for (i in shorts.toIterable())
            assert(i >= shorts.getShort(minShort))
        val ints = NDArray(5, 5, filler = { (getRng().nextGaussian()*100).toInt() })
        val minInt = argMin(ints)
        for (i in ints.toIterable())
            assert(i >= ints.getInt(minInt))
        val longs = NDArray(5, 5, filler = { (getRng().nextGaussian()*100).toLong() })
        val minLong = argMin(longs)
        for (i in longs.toIterable())
            assert(i >= longs.getLong(minLong))
        val floats = NDArray(5, 5, filler = { (getRng().nextGaussian()*100).toFloat() })
        val minFloat = argMin(floats)
        for (i in floats.toIterable())
            assert(i >= floats.getFloat(minFloat))
        val doubles = NDArray(5, 5, filler = { getRng().nextGaussian()*100 })
        val minDouble = argMin(doubles)
        for (i in doubles.toIterable())
            assert(i >= doubles.getDouble(minDouble))
        val strings = ndArrayOf("abc", "ab", "def", "ghi", shape=intArrayOf(2, 2))
        assert(argMin(strings) == 1)
    }

    @Test
    fun testArgMax() {
        val bytes = NDArray(5, 5, filler = { (getRng().nextGaussian()*100).toByte() })
        val maxByte = argMax(bytes)
        for (i in bytes.toIterable())
            assert(i <= bytes.getByte(maxByte))
        val shorts = NDArray(5, 5, filler = { (getRng().nextGaussian()*100).toShort() })
        val maxShort = argMax(shorts)
        for (i in shorts.toIterable())
            assert(i <= shorts.getShort(maxShort))
        val ints = NDArray(5, 5, filler = { (getRng().nextGaussian()*100).toInt() })
        val maxInt = argMax(ints)
        for (i in ints.toIterable())
            assert(i <= ints.getInt(maxInt))
        val longs = NDArray(5, 5, filler = { (getRng().nextGaussian()*100).toLong() })
        val maxLong = argMax(longs)
        for (i in longs.toIterable())
            assert(i <= longs.getLong(maxLong))
        val floats = NDArray(5, 5, filler = { (getRng().nextGaussian()*100).toFloat() })
        val maxFloat = argMax(floats)
        for (i in floats.toIterable())
            assert(i <= floats.getFloat(maxFloat))
        val doubles = NDArray(5, 5, filler = { getRng().nextGaussian()*100 })
        val maxDouble = argMax(doubles)
        for (i in doubles.toIterable())
            assert(i <= doubles.getDouble(maxDouble))
        val strings = ndArrayOf("abc", "ab", "def", "ghi", shape=intArrayOf(2, 2))
        assert(argMax(strings) == 3)
    }

    @Test
    fun testMin() {
        val bytes = NDArray(5, 5, filler = { (getRng().nextGaussian()*100).toByte() })
        val minByte = min(bytes)
        for (i in bytes.toIterable())
            assert(i >= minByte)
        val shorts = NDArray(5, 5, filler = { (getRng().nextGaussian()*100).toShort() })
        val minShort = min(shorts)
        for (i in shorts.toIterable())
            assert(i >= minShort)
        val ints = NDArray(5, 5, filler = { (getRng().nextGaussian()*100).toInt() })
        val minInt = min(ints)
        for (i in ints.toIterable())
            assert(i >= minInt)
        val longs = NDArray(5, 5, filler = { (getRng().nextGaussian()*100).toLong() })
        val minLong = min(longs)
        for (i in longs.toIterable())
            assert(i >= minLong)
        val floats = NDArray(5, 5, filler = { (getRng().nextGaussian()*100).toFloat() })
        val minFloat = min(floats)
        for (i in floats.toIterable())
            assert(i >= minFloat)
        val doubles = NDArray(5, 5, filler = { getRng().nextGaussian()*100 })
        val minDouble = min(doubles)
        for (i in doubles.toIterable())
            assert(i >= minDouble)
        val strings = ndArrayOf("abc", "ab", "def", "ghi", shape=intArrayOf(2, 2))
        assert(min(strings) == "ab")
    }

    @Test
    fun testMax() {
        val bytes = NDArray(5, 5, filler = { (getRng().nextGaussian()*100).toByte() })
        val maxByte = max(bytes)
        for (i in bytes.toIterable())
            assert(i <= maxByte)
        val shorts = NDArray(5, 5, filler = { (getRng().nextGaussian()*100).toShort() })
        val maxShort = max(shorts)
        for (i in shorts.toIterable())
            assert(i <= maxShort)
        val ints = NDArray(5, 5, filler = { (getRng().nextGaussian()*100).toInt() })
        val maxInt = max(ints)
        for (i in ints.toIterable())
            assert(i <= maxInt)
        val longs = NDArray(5, 5, filler = { (getRng().nextGaussian()*100).toLong() })
        val maxLong = max(longs)
        for (i in longs.toIterable())
            assert(i <= maxLong)
        val floats = NDArray(5, 5, filler = { (getRng().nextGaussian()*100).toFloat() })
        val maxFloat = max(floats)
        for (i in floats.toIterable())
            assert(i <= maxFloat)
        val doubles = NDArray(5, 5, filler = { getRng().nextGaussian()*100 })
        val maxDouble = max(doubles)
        for (i in doubles.toIterable())
            assert(i <= maxDouble)
        val strings = ndArrayOf("abc", "ab", "def", "ghi", shape=intArrayOf(2, 2))
        assert(max(strings) == "ghi")
    }

    @Test
    fun testSumAxis() {
        val doubles = NDArray(5, 5, filler = { getRng().nextGaussian()*100 })
        val a1 = sum(doubles, 0, true)
        val a2 = sum(doubles, 1, true)
        val a3 = sum(doubles, 0, false)
        val a4 = sum(doubles, 1, false)
        assert(a1.shape().toIntArray() contentEquals intArrayOf(1, 5))
        assert(a2.shape().toIntArray() contentEquals intArrayOf(5, 1))
        assert(a3.shape().toIntArray() contentEquals intArrayOf(5))
        assert(a4.shape().toIntArray() contentEquals intArrayOf(5))
        for (i in 0..4) {
            assert(a1[0,i] == a3[i])
            assert(a2[i,0] == a4[i])
            val sum1 = doubles[0,i]+doubles[1,i]+doubles[2,i]+doubles[3,i]+doubles[4,i]
            val sum2 = doubles[i,0]+doubles[i,1]+doubles[i,2]+doubles[i,3]+doubles[i,4]
            assert(kotlin.math.abs((a3[i]-sum1)/sum1) < 1e-14)
            assert(kotlin.math.abs((a4[i]-sum2)/sum2) < 1e-14)
        }
    }

    @Test
    fun testMeanAxis() {
        val doubles = NDArray(5, 5, filler = { getRng().nextGaussian()*100 })
        val a1 = mean(doubles, 0, true)
        val a2 = mean(doubles, 1, true)
        val a3 = mean(doubles, 0, false)
        val a4 = mean(doubles, 1, false)
        assert(a1.shape().toIntArray() contentEquals intArrayOf(1, 5))
        assert(a2.shape().toIntArray() contentEquals intArrayOf(5, 1))
        assert(a3.shape().toIntArray() contentEquals intArrayOf(5))
        assert(a4.shape().toIntArray() contentEquals intArrayOf(5))
        for (i in 0..4) {
            assert(a1[0,i] == a3[i])
            assert(a2[i,0] == a4[i])
            val mean1 = (doubles[0,i]+doubles[1,i]+doubles[2,i]+doubles[3,i]+doubles[4,i])/5
            val mean2 = (doubles[i,0]+doubles[i,1]+doubles[i,2]+doubles[i,3]+doubles[i,4])/5
            assert(kotlin.math.abs((a3[i]-mean1)/mean1) < 1e-14)
            assert(kotlin.math.abs((a4[i]-mean2)/mean2) < 1e-14)
        }
    }

    @Test
    fun testArgMinAxis() {
        val doubles = NDArray(5, 5, filler = { getRng().nextGaussian()*100 })
        val a1 = argMin(doubles, 0, true)
        val a2 = argMin(doubles, 1, true)
        val a3 = argMin(doubles, 0, false)
        val a4 = argMin(doubles, 1, false)
        assert(a1.shape().toIntArray() contentEquals intArrayOf(1, 5))
        assert(a2.shape().toIntArray() contentEquals intArrayOf(5, 1))
        assert(a3.shape().toIntArray() contentEquals intArrayOf(5))
        assert(a4.shape().toIntArray() contentEquals intArrayOf(5))
        for (i in 0..4) {
            assert(a1[0,i] == a3[i])
            assert(a2[i,0] == a4[i])
            for (j in 0..4) {
                assert(doubles[i,j] >= doubles[a3[j],j])
                assert(doubles[i,j] >= doubles[i,a4[i]])
            }
        }
    }

    @Test
    fun testArgMaxAxis() {
        val doubles = NDArray(5, 5, filler = { getRng().nextGaussian()*100 })
        val a1 = argMax(doubles, 0, true)
        val a2 = argMax(doubles, 1, true)
        val a3 = argMax(doubles, 0, false)
        val a4 = argMax(doubles, 1, false)
        assert(a1.shape().toIntArray() contentEquals intArrayOf(1, 5))
        assert(a2.shape().toIntArray() contentEquals intArrayOf(5, 1))
        assert(a3.shape().toIntArray() contentEquals intArrayOf(5))
        assert(a4.shape().toIntArray() contentEquals intArrayOf(5))
        for (i in 0..4) {
            assert(a1[0,i] == a3[i])
            assert(a2[i,0] == a4[i])
            for (j in 0..4) {
                assert(doubles[i,j] <= doubles[a3[j],j])
                assert(doubles[i,j] <= doubles[i,a4[i]])
            }
        }
    }

    @Test
    fun testMinAxis() {
        val doubles = NDArray(5, 5, filler = { getRng().nextGaussian()*100 })
        val a1 = min(doubles, 0, true)
        val a2 = min(doubles, 1, true)
        val a3 = min(doubles, 0, false)
        val a4 = min(doubles, 1, false)
        assert(a1.shape().toIntArray() contentEquals intArrayOf(1, 5))
        assert(a2.shape().toIntArray() contentEquals intArrayOf(5, 1))
        assert(a3.shape().toIntArray() contentEquals intArrayOf(5))
        assert(a4.shape().toIntArray() contentEquals intArrayOf(5))
        for (i in 0..4) {
            assert(a1[0,i] == a3[i])
            assert(a2[i,0] == a4[i])
            for (j in 0..4) {
                assert(doubles[i,j] >= a3[j])
                assert(doubles[i,j] >= a4[i])
            }
        }
    }

    @Test
    fun testMaxAxis() {
        val doubles = NDArray(5, 5, filler = { getRng().nextGaussian()*100 })
        val a1 = max(doubles, 0, true)
        val a2 = max(doubles, 1, true)
        val a3 = max(doubles, 0, false)
        val a4 = max(doubles, 1, false)
        assert(a1.shape().toIntArray() contentEquals intArrayOf(1, 5))
        assert(a2.shape().toIntArray() contentEquals intArrayOf(5, 1))
        assert(a3.shape().toIntArray() contentEquals intArrayOf(5))
        assert(a4.shape().toIntArray() contentEquals intArrayOf(5))
        for (i in 0..4) {
            assert(a1[0,i] == a3[i])
            assert(a2[i,0] == a4[i])
            for (j in 0..4) {
                assert(doubles[i,j] <= a3[j])
                assert(doubles[i,j] <= a4[i])
            }
        }
    }
    
    @Test
    fun testAxisReductionShapes() {
        val array = ndArrayOf(1, 2, 3, 4, 5, 6, 7, 8, shape= intArrayOf(2, 2, 2))
        assert(min(array, 0, true).shape().toIntArray() contentEquals intArrayOf(1, 2, 2))
        assert(min(array, 1, true).shape().toIntArray() contentEquals intArrayOf(2, 1, 2))
        assert(min(array, 2, true).shape().toIntArray() contentEquals intArrayOf(2, 2, 1))
        assert(min(array, 0, false).shape().toIntArray() contentEquals intArrayOf(2, 2))
        assert(min(array, 1, false).shape().toIntArray() contentEquals intArrayOf(2, 2))
        assert(min(array, 2, false).shape().toIntArray() contentEquals intArrayOf(2, 2))
        val array2 = ndArrayOf(1, 2, 3, 4, 5)
        assert(min(array2, 0, true).shape().toIntArray() contentEquals intArrayOf(1))
        assert(min(array2, 0, false).shape().toIntArray() contentEquals intArrayOf(1))
    }
}