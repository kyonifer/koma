package koma.internal

/**
 * Add missing functionality for [MathClass] from the kotlin.js
 * library, as it doesn't include mappings for signum->sign. Note
 * we can't add these directly to MathClass because the name clashes
 * with kotlin-js-library.
 */
internal actual fun signum(num: Double) = kotlin.math.sign(num)
