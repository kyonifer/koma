package koma.internal

import koma.extensions.*
import koma.matrix.*

internal actual fun <T> repr(mat: Matrix<T>): String {
    return buildString {
        mat.forEachIndexed { row, col, ele ->
            if (row == 0 && col != 0)
                append("\n")
            append("$ele,")
        }
    }
}
