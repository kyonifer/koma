package golem.platformsupport

import golem.*
import golem.matrix.*

fun <T> repr(mat: Matrix<T>): String {
    // Replace when buildString {} is implemented on native
    val out = StringBuilder()
    mat.eachIndexed { row, col, ele ->
        if (row == 0 && col != 0)
            out.append("\n")
        out.append("$ele,")
    }
    return out.toString()
}
