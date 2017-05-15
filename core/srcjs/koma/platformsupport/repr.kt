package koma.platformsupport

import koma.*
import koma.matrix.*

fun <T> repr(mat: Matrix<T>): String {
    return buildString {
        mat.eachIndexed { row, col, ele ->
            if (row == 0 && col != 0)
                append("\n")
            append("$ele,")
        }
    }
}
