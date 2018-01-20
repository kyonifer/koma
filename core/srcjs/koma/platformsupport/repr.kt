package koma.platformsupport

import koma.*
import koma.extensions.*
import koma.matrix.*

fun <T> repr(mat: Matrix<T>): String {
    return buildString {
        mat.forEachIndexed { row, col, ele ->
            if (row == 0 && col != 0)
                append("\n")
            append("$ele,")
        }
    }
}
