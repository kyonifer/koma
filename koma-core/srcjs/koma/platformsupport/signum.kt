package koma.platformsupport

/**
 * Add missing functionality for [MathClass] from the kotlin.js
 * library, as it doesn't include mappings for signum->sign. Note
 * we can't add these directly to MathClass because the name clashes
 * with kotlin-js-library.
 */
fun signum(value: Double): Double {
    val m: dynamic = kotlin.js.Math
    return m.sign(value)
}