@file:JvmName("Golem")
@file:JvmMultifileClass

package golem

private var matFormat: String = "L"

/**
 * Sets the format for Golem to display numbers in. For example, calling
 *
 * format("long")
 * println(randn(3))
 *
 * will cause println(someMatrix) to show more decimals (precision) than
 *
 * format("short")
 * println(randn(3))
 *
 * format() must be called before a .toString() request to a Golem matrix
 * in order to have any effect, but setting it will persist for all printing
 * of matrices until called again with a different format string. The default
 * setting is "long". Supported options are "long", "short", "verylong".
 */
fun format(formatStr: String)
{
    // TODO: add scientific notation
    when (formatStr) {
        "LONG","long","Long" -> matFormat = "L"
        "SHORT","short","Short" -> matFormat = "S"
        "VLONG","VERYLONG", "VeryLong","VLong", "verylong", "vl" -> matFormat = "VL"
    }

}