import koma.*
import koma.extensions.*

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

    println("Matrix mult:")
    val a1 = mat[1, 2 end
                 3, 4]
    val a2 = mat[4, 5 end
                 3, 2]
    println(a1*a2)

    println("Cholesky decomp:")
    val c = (mat[4,1 end 1,6]).chol()
    println(c)
    println(c*c.T)

    println("LU decomp:")
    val (p, l, u) = mat[1, 2 end 2,1].LU()
    println(p)
    println(l)
    println(u)

    println("Inverse:")
    println(eye(3)*4)
    val inv = (eye(3)*4).inv()
    println(inv)
    println(eye(3)*4*inv)

    println("1-Norm:")
    println(mat[1,2 end 3,4 end 5,6].normIndP1())

    println("F-Norm:")
    println(mat[1,2 end 3,4 end 5,6].normF())

    println("expm:")
    println(expm(mat[1,2,3 end 4,5,6 end 7,8,9]))

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
