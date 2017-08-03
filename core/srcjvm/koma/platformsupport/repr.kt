package koma.platformsupport

import koma.*
import koma.matrix.*
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.text.DecimalFormat

fun <T> repr(mat: Matrix<T>): String {
    val fmtString = when (matFormat) {
        SHORT_NUMBER                -> "0.00##"
        LONG_NUMBER                 -> "0.00############"
        VERY_LONG_NUMBER            -> "0.00#############################"
        SCIENTIFIC_NUMBER           -> "0.00#####E0#"
        SCIENTIFIC_LONG_NUMBER      -> "0.00############E0#"
        SCIENTIFIC_VERY_LONG_NUMBER -> "0.00############################E0#"
        else                        -> "0.00############"
    }

    val formatter = DecimalFormat(fmtString)

    val bstream = ByteArrayOutputStream()
    val pstream = PrintStream(bstream)

    mat.run {
        val lens = IntArray(numCols())
        forEachIndexed { _, col, element ->
            val formatted = formatter.format(element)
            if (lens[col] < formatted.length) lens[col] = formatted.length
        }

        var indent = "mat["
        forEachIndexed { row, col, element ->
            var formatted = formatter.format(element)
            if (col == 0) {
                if (row > 0)
                    pstream.append("end\n")
                pstream.append(indent)
                indent = "    "
            }
            if (formatted[0] != '-')
                formatted = " " + formatted
            pstream.append(formatted)
            if (col != lens.size - 1)
                pstream.append(",")
            (-1..(lens[col] - formatted.length)).forEach { pstream.append(" ") }
        }
        pstream.append("]")
    }
    return bstream.toString()
}
