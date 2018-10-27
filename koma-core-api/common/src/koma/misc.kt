@file:koma.internal.JvmName("Koma")
@file:koma.internal.JvmMultifileClass

package koma

var matFormat: String = "L"

val LONG_NUMBER = "L"
val VERY_LONG_NUMBER = "VL"
val SHORT_NUMBER = "S"
val SCIENTIFIC_NUMBER = "SciNot"
val SCIENTIFIC_LONG_NUMBER = "SciNotLong"
val SCIENTIFIC_VERY_LONG_NUMBER = "SciNotVLong"

val end = -1
val all = 0..end

fun setSeed(seed: Long) = koma.internal.getRng().setSeed(seed)

/**
 * Sets the format for Koma to display numbers in. For example, calling
 *
 * format("long")
 * println(randn(3))
 *
 * will cause println(someMatrix) to show more decimals (precision) than
 *
 * format("short")
 * println(randn(3))
 *
 * format() must be called before a .toString() request to a Koma matrix
 * in order to have any effect, but setting it will persist for all printing
 * of matrices until called again with a different format string. The default
 * setting is "long". Supported options are "long", "short", "verylong".
 */
fun format(formatStr: String) {
    // TODO: use enum
    when (formatStr) {
        "LONG", "long", "Long" -> matFormat = LONG_NUMBER
        "SHORT", "short", "Short" -> matFormat = SHORT_NUMBER
        "VLONG", "VERYLONG", "VeryLong", "VLong", "verylong", "vl" -> matFormat = VERY_LONG_NUMBER
        "scientific", "sci", "SciNot", "scinot" -> matFormat = SCIENTIFIC_NUMBER
        "scilong", "SciLong", "SciNotLong" -> matFormat = SCIENTIFIC_LONG_NUMBER
        "scivlong", "SciVLong", "SciNotVLong" -> matFormat = SCIENTIFIC_VERY_LONG_NUMBER
    }
}