package golem.platformsupport

import golem.*
import golem.matrix.*

fun <T> repr(mat: Matrix<T>): String {
    return buildString {
        mat.eachIndexed { row, col, ele ->
            if (row == 0 && col != 0)
                append("\n")
            append("$ele,")
        }
    }
}
