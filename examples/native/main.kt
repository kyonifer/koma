import koma.*
import koma.matrix.common.DoubleMatrixBase
import koma.matrix.Matrix
import koma.matrix.cblas.CBlasMatrix
import kotlinx.cinterop.*

fun main(args: Array<String>) {

    val m1 = zeros(3,3)
    val m2 = ones(3,3)
    val m3 = (m1+1)*3+(m2+4)*4
    println(m1)
    println(m2)
    println(m3)
    println(eye(3)+6)

}
