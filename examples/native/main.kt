import koma.*
import koma.matrix.common.DoubleMatrixBase
import koma.matrix.Matrix
import koma.matrix.cblas.CBlasMatrix
import kotlinx.cinterop.*

fun main(args: Array<String>) {

    val m1 = CBlasMatrix(nativeHeap.allocArray<DoubleVar>(25), 1,1)
    m1[0] = 5.0
    println("Before:" + m1[0])

    val m2 = m1.times(m1)
    println("After:" + m2[0])
    

}
