package koma.platformsupport

import kotlinx.cinterop.*
import Math.log
import Math.sqrt

var seed: Long? = null
    get() = field
    set(value) {
        field = value
        value?.let {
            rng.setSeed(it.toInt())
        }
    }


object rng {
    
    init {
        C.srand(C.time(null).toInt())
    }
    fun setSeed(seed: Int) {
        C.srand(seed)
    }
    private var spare: Double? = null
    // TODO: Implement ziggurat
    fun nextGaussian(): Double {
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
            val num1 = C.rand().toDouble()/C.RAND_MAX * 2 - 1
            val num2 = C.rand().toDouble()/C.RAND_MAX * 2 - 1
            val cand = num1*num1 + num2*num2
            if (cand < 1.0 && cand != 0.0) {
                val scale = sqrt((-2 * log(cand))/cand)
                spare = scale*num1
                return scale*num2
            }
        }
    }
    fun nextDouble(): Double {
        return C.rand().toDouble()/C.RAND_MAX
    }
}
