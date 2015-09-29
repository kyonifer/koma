package golem

import org.junit.Test

class matrixfuncs_tests {
    @Test
    fun testExpm()
    {
        assert(abs(cumsum(expm(eye(3)) - E*eye(3))) < 1e-9)

        val expected = mat[20.08553692318771,   0,                 0 end
                0,                   20.08553692318771, 0 end
                0,                   0,                 20.08553692318771]
        assert(abs(cumsum(expm(eye(3)*3) - expected)) < 1e-9)
    }
}