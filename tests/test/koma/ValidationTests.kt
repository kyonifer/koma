package koma

import koma.matrix.*
import koma.util.validation.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

//@formatter: off
class ValidationTests {
    // Visual tests are tests which fail on purpose so you can examine the output.  In this file, the visual tests show
    // you the exception messages so you can check that they're human-readable and formatted nicely.
    val visualTestsOn = false

    @Rule
    @JvmField val exception = ExpectedException.none()

    @Test
    fun test_simpleBoundsChcek() {
        val foo = mat[1, 2, 3]
        exception.expect(IndexOutOfBoundsException::class.java)
        foo.validate { 2 x 2 }
    }

    @Test
    fun test_letterBoundsCheck() {
        val foo = mat[1, 2, 3]
        val bar = mat[1, 2, 3, 4]
        exception.expect(IndexOutOfBoundsException::class.java)
        validate {
            foo { 1 x 'N' }
            bar { 1 x 'N' }
        }
    }

    @Test
    fun test_nonIdiomaticFormChainable() {
        val foo = mat[ 1, 2 end
                       3, 4 ]
        val bar = mat[ 1, 2 end
                       2, 1 ]
        testMatrix(bar).dim(2, 2).symmetric().min(0.0).max(5.0).validate()
        exception.expect(IllegalArgumentException::class.java)
        testMatrix(foo).dim(2, 2).symmetric().min(0.0).max(5.0).validate()
    }

    @Test
    fun test_boundsCheckOverloadsExist() {
        mat[1, 2, 3].validate {  1  x  3  }
        mat[1, 2, 3].validate {  1  x 'M' }
        mat[1, 2, 3].validate { 'N' x  3  }
        mat[1, 2, 3].validate { 'N' x 'M' }
    }

    @Test
    fun test_boundsFiguresOutHowToOrientData() {
        val a = mat[-1,  -2, -3]
        val b = mat[ 1,  2,  3 end
                     4,  5,  6 end
                     7,  8,  9 end
                    10, 11, 12]
        val c = mat[-4, -5, -6, -7]
        validate {
            a {  1  x 'N'; transposable }
            b { 'N' x 'M'; transposable }
            c { 'M' x  1 ; transposable }
        }
    }

    @Test
    fun test_min() {
        exception.expect(IllegalArgumentException::class.java)
        mat[1, 2, 3].validate { min = 4.0 }
    }

    @Test
    fun test_max() {
        exception.expect(IllegalArgumentException::class.java)
        mat[1, 2, 3].validate { max = 0.0 }
    }

    @Test
    fun test_symmetric() {
        val sym = mat[ 1, 2, 3 end
                       2, 4, 5 end
                       3, 5, 6]
        val asym = mat[1, 2, 3 end
                       4, 5, 6 end
                       7, 8, 9]
        sym.validate { symmetric }
        exception.expect(IllegalArgumentException::class.java)
        asym.validate { symmetric }
    }

    @Test
    fun test_symmetricWithImpossibleDimensions() {
        val asym = mat[1, 2, 3]
        exception.expect(IndexOutOfBoundsException::class.java)
        asym.validate { symmetric }
    }

    @Test
    fun test_symmetricAndBoundsInSameExpr() {
        val sym = mat[1, 2 end 2, 1]
        sym.validate { symmetric; 2 x 2}
    }


    @Test
    fun test_namesInExceptionsDimInline() {
        exception.expectMessage("foo")
        mat[1, 2, 3].validate("foo") { 2 x 2 }
    }

    @Test
    fun test_namesInExceptionsDimMaxBlock() {
        exception.expectMessage("bar")
        validate {
            mat[1, 2, 3]("bar") { 2 x 2 }
        }
    }

    @Test
    fun test_nameInExceptionsDimTestMatrix() {
        exception.expectMessage("baz")
        testMatrix(mat[1, 2, 3], "baz").dim(2, 2).validate()
    }

    @Test
    fun test_namesInExceptionsMaxInline() {
        exception.expectMessage("foo")
        mat[1, 2, 3].validate("foo") { max = 2.0 }
    }

    @Test
    fun test_namesInExceptionsMaxBlock() {
        exception.expectMessage("bar")
        validate {
            mat[1, 2, 3]("bar") { max = 2.0 }
        }
    }

    @Test
    fun test_nameInExceptionsMaxTestMatrix() {
        exception.expectMessage("baz")
        testMatrix(mat[1, 2, 3], "baz").max(2.0).validate()
    }

    @Test
    fun test_namesInExceptionsMinInline() {
        exception.expectMessage("foo")
        mat[1, 2, 3].validate("foo") { min = 2.0 }
    }

    @Test
    fun test_namesInExceptionsMinBlock() {
        exception.expectMessage("bar")
        validate {
            mat[1, 2, 3]("bar") { min = 2.0 }
        }
    }

    @Test
    fun test_nameInExceptionsMinTestMatrix() {
        exception.expectMessage("baz")
        testMatrix(mat[1, 2, 3], "baz").min(2.0).validate()
    }

    @Test
    fun test_namesInExceptionsSymmetricInline() {
        exception.expectMessage("foo")
        mat[1, 2, 3].validate("foo") { symmetric }
    }

    @Test
    fun test_namesInExceptionsSymmetricMaxBlock() {
        exception.expectMessage("bar")
        validate {
            mat[1, 2, 3]("bar") { symmetric }
        }
    }

    @Test
    fun test_nameInExceptionsSymmetricTestMatrix() {
        exception.expectMessage("baz")
        testMatrix(mat[1, 2, 3], "baz").symmetric.validate()
    }



    @Test
    fun test_visual_maxExceptionMessage() {
        if (!visualTestsOn) return
        mat[15,7].validate("CaridinaCantonensis") { max = 5.0 }
    }

    @Test
    fun test_visual_minExceptionMessage() {
        if (!visualTestsOn) return
        mat[15,7].validate("NeocaridinaZhanghjiajiensis") { min = 10.0 }
    }

    @Test
    fun test_visual_dimensionsExceptionMessage() {
        if (!visualTestsOn) return
        validate {
            mat[1, 2, 3]("first")        {  1  x  2  }
            mat[2, 3 end 4, 5]("second") { 'M' x 15  }
            mat[1]("third")              { 'N' x 'M' }
            mat[1 end 2]("This is a really long name, isn't it?") { 'M' x 'N' }
        }
    }

    @Test
    fun test_visual_symmetricExceptionMessage() {
        if (!visualTestsOn) return
        mat[1, 2 end 3, 4].validate("PenaeusMonodon") { symmetric }
    }

    @Test
    fun test_visual_symmetricImpossibleBoundsExceptionMessage() {
        if (!visualTestsOn) return
        mat[1, 2].validate("MacrobrachiumCarcinus") { symmetric }
    }

    fun doSomething(foo: Matrix<Double>, bar: Matrix<Double>, baz: Matrix<Double>) {
        validate {
            foo("foo") { 1 x 'N'; max = 2*PI; min = -2*PI }
            bar("bar") { 'N' x 'N'; symmetric }
            baz("baz") { 'N' x 1; }
        }
    }

    @Test
    fun test_visual_exampleInHipchat() {
        if (!visualTestsOn) return
        doSomething(mat[1, 2], mat[1, 2 end 2, 1], mat[1, 2, 3 end 4, 5, 6])
    }
}



