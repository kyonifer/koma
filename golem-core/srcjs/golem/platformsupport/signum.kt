package golem.platformsupport

/**
 * Add missing functionality for [MathClass] from the kotlin.js
 * library, as it doesn't include mappings for signum->sign. Note
 * we can't add these directly to MathClass because the name clashes
 * with kotlin-js-library.
 */
@native
class ExtraMathClass() {
    // Our 2 functions
    fun signum(value: Double): Double {
        val m: dynamic = Math
        return m.sign(value)
    }
}

@native
val ExtraMath: ExtraMathClass = ExtraMathClass()

fun signum(value: Double) = ExtraMath.signum(value)