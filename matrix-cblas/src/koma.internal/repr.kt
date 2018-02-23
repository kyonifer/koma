package koma.internal

import koma.*
import koma.extensions.*
import koma.matrix.*

internal actual fun <T> repr(mat: Matrix<T>): String {
    // Replace when buildString {} is implemented on native
    val out = StringBuilder()
    out.append("mat[")
    mat.forEachIndexed { row, col, ele ->
        if (col == 0 && row != 0)
            out.append("\n    ")
        out.append("$ele,")
    }
    out.append("]")
    return out.toString()
}
