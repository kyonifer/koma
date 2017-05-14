package koma.platformsupport

import koma.*
import koma.matrix.*

fun <T, U: Matrix<T, U>> repr(mat: Matrix<T, U>): String {
    // Replace when buildString {} is implemented on native
    val out = StringBuilder()
    out.append("mat[")
    mat.eachIndexed { row, col, ele ->
        if (col == 0 && row != 0)
            out.append("\n    ")
        out.append("$ele,")
    }
    out.append("]")
    return out.toString()
}
