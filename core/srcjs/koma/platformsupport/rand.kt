package koma.platformsupport

import kotlin.js.Math

// TODO: merge this with kotlin-native implementation
// and abstract out common algorithm

var seed: Long? = null
    set(value) {
        throw Exception("Seed is not supported on js")
    }


object rng {
    private var spare: Double? = null
    // TODO: Implement ziggurat
    fun nextGaussian(): Double {
        val checkSpare = spare
        if (checkSpare != null) {
            spare=null
            return checkSpare
        }
        while(true) {
            val num1 = Math.random().toDouble() * 2 - 1
            val num2 = Math.random().toDouble() * 2 - 1
            val cand = num1*num1 + num2*num2
            if (cand < 1.0 && cand != 0.0) {
                val scale = Math.sqrt((-2 * Math.log(cand))/cand)
                spare = scale*num1
                return scale*num2
            }
        }
    }
    fun nextDouble(): Double {
        return kotlin.js.Math.random().toDouble()
    }
}
