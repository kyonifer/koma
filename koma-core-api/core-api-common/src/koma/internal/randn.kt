package koma.internal

expect internal val rng: KomaRandom

interface KomaRandom {
    fun setSeed(seed: Long)
    fun nextGaussian(): Double
    fun nextDouble(): Double
}
