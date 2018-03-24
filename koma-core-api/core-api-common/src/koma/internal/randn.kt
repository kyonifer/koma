package koma.internal

// We really want a non-internal `expect val rng: KomaRandom` below, but
// kotlin/native breaks currently. This is a workaround.
fun getRng() = rng

expect internal val rng: KomaRandom

interface KomaRandom {
    fun setSeed(seed: Long)
    fun nextGaussian(): Double
    fun nextDouble(): Double
}
