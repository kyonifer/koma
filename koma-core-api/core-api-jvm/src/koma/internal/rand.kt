package koma.internal

import java.util.*

actual internal val rng = object: KomaRandom {
    private val jvmRand = Random()
    override fun setSeed(seed: Long) = jvmRand.setSeed(seed)
    override fun nextGaussian() = jvmRand.nextGaussian()
    override fun nextDouble() = jvmRand.nextDouble()
}