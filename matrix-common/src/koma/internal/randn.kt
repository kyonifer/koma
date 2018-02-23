package koma.internal

internal expect var curSeed: Long

internal expect val rng: Random

internal interface Random {
    fun nextGaussian(): Double
    fun nextDouble(): Double
}
