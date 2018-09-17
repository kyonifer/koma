package koma.internal

import kotlinx.cinterop.*
import platform.posix.*

actual internal val rng = object: KomaRandom {

    init {
        srand(time(null).toUInt())
    }
    override fun setSeed(seed: Long) {
        // TODO: Cast might be bad news for randomness
        srand(seed.toUInt())
    }
    private var spare: Double? = null
    // TODO: Implement ziggurat
    override fun nextGaussian(): Double {
        val checkSpare = spare
        if (checkSpare != null) {
            spare=null
            return checkSpare
        }
        /* TODO: Replace above with below when not broken on native
        spare?.let {
            spare = null
            return it
        }
        */
        while(true) {
            val num1 = rand().toDouble()/RAND_MAX * 2 - 1
            val num2 = rand().toDouble()/RAND_MAX * 2 - 1
            val cand = num1*num1 + num2*num2
            if (cand < 1.0 && cand != 0.0) {
                val scale = sqrt((-2 * log(cand))/cand)
                spare = scale*num1
                return scale*num2
            }
        }
    }
    override fun nextDouble(): Double {
        return rand().toDouble()/RAND_MAX
    }
}

