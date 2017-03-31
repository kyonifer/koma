/**
 * This file defines the default backend used for creating matrices at MTJ. This file is
 * conditionally compiled into the build if the defaultMTJ property is set in the build.gradle.
 * Otherwise, another defaults_* may be used (see build.gradle for details).
 */

@file:JvmName("Options")
@file:JvmMultifileClass

package golem

import golem.matrix.*
import kotlin.reflect.KProperty


/**
 *
 * Default factory that all top-level functions use when building new matrices.
 *
 * Replace this factory at runtime with e.g. golem.matrix.ejml.EJMLMatrixFactory() to change what
 * backend the top-level functions use for computation.
 *
 */
var factory: MatrixFactory<Matrix<Double>> by MatFacProperty()
var floatFactory: MatrixFactory<Matrix<Float>> by MatFacProperty()
var intFactory: MatrixFactory<Matrix<Int>> by MatFacProperty()

/**
 *  At runtime, see which double backends are available on our classpath (if any).
 *
 *  @return A list of factory instances for backends that were found.
 */
fun getAvailableFactories(): List<MatrixFactory<Matrix<Double>>> {
    return golem.platformsupport.getFactories()
}

/**
 *  At runtime, see which float backends are available on our classpath (if any).
 *
 *  @return A list of factory instances for backends that were found.
 */
fun getAvailableFloatFactories(): List<MatrixFactory<Matrix<Float>>> = listOf()

/**
 *  At runtime, see which int backends are available on our classpath (if any).
 *
 *  @return A list of factory instances for backends that were found.
 */
fun getAvailableIntFactories(): List<MatrixFactory<Matrix<Int>>> = listOf()

/**
 * Whether to validate the dimensions, symmetry, and values of input matrices. false is faster, and should be
 * used for realtime applications. true gives you much more useful errors when your matrices are shaped
 * differently than your code expects.
 */
var validateMatrices = true


private class MatFacProperty<T> {
    private var _factory: MatrixFactory<Matrix<Double>>? = null

    operator inline fun <reified T> getValue(thisRef: Any?, property: KProperty<*>): MatrixFactory<Matrix<T>> {
        when (T::class.java) {
            java.lang.Double::class.java -> {
                val facInst = _factory
                if (facInst != null) {
                    @Suppress("UNCHECKED_CAST")
                    return facInst as MatrixFactory<Matrix<T>>
                }
                else {
                    val facs = getAvailableFactories()
                    if(facs.isNotEmpty()) {
                        _factory = facs[0]
                        @Suppress("UNCHECKED_CAST")
                        return facs[0] as MatrixFactory<Matrix<T>>
                    }
                    else
                        throw RuntimeException("No default backends for golem matrix found. Please set golem.factory manually or" +
                                               " put one on your classpath.")
                }
            }
            else -> {TODO()}
        }
    }
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: MatrixFactory<Matrix<T>>?) {
        _factory = factory
    }
}