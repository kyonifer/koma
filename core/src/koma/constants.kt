/**
 * This file defines commonly used math constants
 */

@file:JvmName("Koma")
@file:JvmMultifileClass

package koma
import koma.polyfill.annotations.*

/**
 * The value of Pi
 */
val PI = 3.1415926535897932384626433832795028841971693993751058209749445923078164062
/**
 * The value of e, such that e pow ln(x) = x
 */
val E = 2.7182818284590452353602874713527

internal const val NULL_INDICES = "Null indices into a matrix are illegal. The Matrix type " +
                                  "signature allows nullable indices due to a technical limitation."
