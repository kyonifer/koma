package golem

internal var matFormat: String = "L"

fun format(formatStr: String)
{
    when (formatStr) {
        "LONG","long","Long" -> matFormat = "L"
        "SHORT","short","Short" -> matFormat = "S"
        "VLONG","VERYLONG", "VeryLong","VLong", "verylong", "vl" -> matFormat = "VL"
    }

}