/**
 * This file implements random number generation using a PCG-XSH-RR generator.  Some elements of this file are based
 * on the pcg-c-basic library (https://github.com/imneme/pcg-c-basic), which is also distributed under the Apache 2.0
 * license.  More specifically, the file these elements are modelled after contains the following notice:
 *
 * ------------------------------------------------------------------------------------
 * PCG Random Number Generation for C.
 *
 * Copyright 2014 Melissa O'Neill <oneill@pcg-random.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * For additional information about the PCG random number generation scheme,
 * including its license and other licensing options, visit
 *
 *       http://www.pcg-random.org
 * ------------------------------------------------------------------------------------
 */

package koma.internal

import kotlin.math.ln
import kotlin.math.sqrt

private val rng = KomaRandom(0, 0x4B4F4D41)

/**
 * Get the default random number generator.
 */
fun getRng() = rng

/**
 * This class implements random number generation using the PCG-XSH-RR algorithm.  It has excellent statistical
 * properties and a period of 2^64.
 */
class KomaRandom {
    private val inc1: Long
    private val inc2: Long
    private var state1: Long
    private var state2: Long
    private var gaussianValue = 0.0
    private var gaussianIsValid = false

    private val MULTIPLIER = 6364136223846793005L
    private val FLOAT_SCALE = 1.0f/(1 shl 24)
    private val DOUBLE_SCALE = 1.0/(1L shl 53)

    /**
     * Create a new random number generator.
     * 
     * @param seed      the seed value to use.  This determines the initial position of the generator within
     *                  the random sequence.  It can be changed later by calling [setSeed].
     * @param streamID  every stream ID produces a different sequence.  This cannot be negative.
     */
    constructor(seed: Long, streamID: Long) {
        // The highest bit of the stream ID is ignored.  To avoid a risk of someone thinking two streams are distinct
        // when they really aren't, throw an exception if it's set.
        
        if (streamID < 0)
            throw IllegalArgumentException("streamID cannot be negative")
        
        // We actually create two different streams.  When generating 64 bit values, we use the second stream to
        // generate the upper 32 bits.  Create a second stream ID by inverting some of the bits.

        inc1 = (streamID shl 1) or 1
        inc2 = inc1 xor 0xAAAAAAAA
        state1 = 0
        state2 = 0
        setSeed(seed)
    }

    /**
     * Reset the position of the generator within its random sequence based on a seed value.
     */
    fun setSeed(seed: Long) {
        syncNotNative(this) {
            state1 = 0
            state2 = 0
            nextLongUnsafe()
            state1 += seed
            state2 += seed
            nextLongUnsafe()
            gaussianIsValid = false
        }
    }

    /**
     * Get a uniformly distributed Int between Int.MIN_VALUE and Int.MAX_VALUE inclusive.
     */
    fun nextInt(): Int {
        return syncNotNative(this) {
            nextIntUnsafe()
        }
    }

    /**
     * Get a uniformly distributed Int between 0 (inclusive) and [bound] (exclusive).
     */
    fun nextInt(bound: Int): Int {
        return syncNotNative(this) {
            nextIntUnsafe(bound)
        }
    }

    /**
     * Get a uniformly distributed Long between Long.MIN_VALUE and Long.MAX_VALUE inclusive.
     */
    fun nextLong(): Long {
        return syncNotNative(this) {
            nextLongUnsafe()
        }
    }

    /**
     * Get a uniformly distributed Long between 0 (inclusive) and [bound] (exclusive).
     */
    fun nextLong(bound: Long): Long {
        return syncNotNative(this) {
            nextLongUnsafe(bound)
        }
    }

    /**
     * Get a uniformly distributed Float between 0 (inclusive) and 1 (exclusive).
     */
    fun nextFloat(): Float {
        return syncNotNative(this) {
            nextFloatUnsafe()
        }
    }

    /**
     * Get a uniformly distributed Double between 0 (inclusive) and 1 (exclusive).
     */
    fun nextDouble(): Double {
        return syncNotNative(this) {
            nextDoubleUnsafe()
        }
    }

    /**
     * Get a normally distributed Double with min 0 and standard deviation 1.
     */
    fun nextGaussian(): Double {
        return syncNotNative(this) {
            nextGaussianUnsafe()
        }
    }

    /**
     * Get a uniformly distributed Int between Int.MIN_VALUE and Int.MAX_VALUE inclusive.
     * This method is not thread safe.  Only call it if you are holding the lock on this object!
     */
    internal fun nextIntUnsafe(): Int {
        val oldState = state1
        state1 = oldState * MULTIPLIER + inc1
        val xorShifted = (((oldState ushr 18) xor oldState) ushr 27).toInt()
        val rot = (oldState ushr 59).toInt()
        return xorShifted ushr rot or (xorShifted shl (-rot and 31))
    }

    /**
     * Get a uniformly distributed Int between 0 (inclusive) and [bound] (exclusive).
     * This method is not thread safe.  Only call it if you are holding the lock on this object!
     */
    internal fun nextIntUnsafe(bound: Int): Int {
        val threshold = -bound % bound
        while (true) {
            val r = nextIntUnsafe()
            if (r >= threshold)
                return r % bound;
        }
    }

    /**
     * Get a uniformly distributed Long between Long.MIN_VALUE and Long.MAX_VALUE inclusive.
     * This method is not thread safe.  Only call it if you are holding the lock on this object!
     */
    internal fun nextLongUnsafe(): Long {
        // Compute the lower half of the result.

        val oldState1 = state1
        state1 = oldState1 * MULTIPLIER + inc1
        val xorShifted1 = (((oldState1 ushr 18) xor oldState1) ushr 27).toInt()
        val rot1 = (oldState1 ushr 59).toInt()
        val result1 = xorShifted1 ushr rot1 or (xorShifted1 shl (-rot1 and 31))

        // Now use the second stream to compute the upper half.

        val oldState2 = state2
        state2 = oldState2 * MULTIPLIER + inc2
        val xorShifted2 = (((oldState2 ushr 18) xor oldState2) ushr 27).toInt()
        val rot2 = (oldState2 ushr 59).toInt()
        val result2 = xorShifted2 ushr rot2 or (xorShifted2 shl (-rot2 and 31))
        return (result2.toLong() shl 32) + result1
    }

    /**
     * Get a uniformly distributed Long between 0 (inclusive) and [bound] (exclusive).
     * This method is not thread safe.  Only call it if you are holding the lock on this object!
     */
    internal fun nextLongUnsafe(bound: Long): Long {
        val threshold = -bound % bound
        while (true) {
            val r = nextLongUnsafe()
            if (r >= threshold)
                return r % bound;
        }
    }

    /**
     * Get a uniformly distributed Float between 0 (inclusive) and 1 (exclusive).
     * This method is not thread safe.  Only call it if you are holding the lock on this object!
     */
    internal fun nextFloatUnsafe(): Float {
        return nextIntUnsafe().ushr(8) * FLOAT_SCALE
    }

    /**
     * Get a uniformly distributed Double between 0 (inclusive) and 1 (exclusive).
     * This method is not thread safe.  Only call it if you are holding the lock on this object!
     */
    internal fun nextDoubleUnsafe(): Double {
        return nextLongUnsafe().ushr(11) * DOUBLE_SCALE
    }

    /**
     * Get a normally distributed Double with min 0 and standard deviation 1.
     * This method is not thread safe.  Only call it if you are holding the lock on this object!
     */
    internal fun nextGaussianUnsafe(): Double {
        if (gaussianIsValid) {
            gaussianIsValid = false
            return gaussianValue
        }
        
        // Compute two new values using the Box-Muller transform.
        
        var x: Double
        var y: Double
        var r2: Double
        do {
            x = 2*nextDoubleUnsafe() - 1.0
            y = 2*nextDoubleUnsafe() - 1.0
            r2 = x*x + y*y
        } while (r2 >= 1.0 || r2 == 0.0)
        val multiplier = sqrt((-2.0*ln(r2))/r2);
        gaussianValue = y*multiplier;
        gaussianIsValid = true;
        return x*multiplier
    }
}
