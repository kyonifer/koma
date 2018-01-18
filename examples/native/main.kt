import koma.*
import koma.matrix.common.DoubleMatrixBase
import koma.matrix.Matrix
import koma.matrix.cblas.CBlasMatrix
import kotlinx.cinterop.*

fun main(args: Array<String>) {

    val m1 = zeros(3,3)+2
    val m2 = ones(3,3)+3
    val m3 = m1*2+m2/2
    println(m1)
    println(m2)
    m3[0,1] = 12345.0
    println(m3)
    println(eye(3)+6)
    println(randn(2,2))

    println(mat[1,2])
    println(mat[3,4].T)
    println(mat[3 end 4])
    println(zeros(2,1))
    println(mat[1,2]*mat[3,4].T)


}
