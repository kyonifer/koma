import koma.*
import koma.matrix.default.*

fun main(args: Array<String>) {
    
    factory = DefaultDoubleMatrixFactory()
    
    println("randn(5,6): \n${randn(5,6).repr()}")

    var a = mat[1,2,3 end
                4,5,6]
    
    var b = mat[2, 0 end
                0, 1 end
               -1, 5]
    
    println("a: \n${a.repr()}")
    println("b: \n${b.repr()}")
    println("a*b: \n${(a*b).repr()}")
    
}
