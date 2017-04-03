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
 * Double precision.
 *
 * Replace this factory at runtime with e.g. golem.matrix.ejml.EJMLMatrixFactory() to change what
 * backend the top-level functions use for computation.
 *
 */
var factory: MatrixFactory<Matrix<Double>> by makePropertyProxy(::getAvailableFactories)
/**
 *
 * Default factory that all top-level functions use when building new matrices.
 * Single precision.
 *
 * Replace this factory at runtime with e.g. golem.matrix.ejml.EJMLMatrixFactory() to change what
 * backend the top-level functions use for computation.
 *
 */
var floatFactory: MatrixFactory<Matrix<Float>> by makePropertyProxy(::getAvailableFloatFactories)
/**
 *
 * Default factory that all top-level functions use when building new matrices.
 * Integer matrices.
 *
 * Replace this factory at runtime with e.g. golem.matrix.ejml.EJMLMatrixFactory() to change what
 * backend the top-level functions use for computation.
 *
 */
var intFactory: MatrixFactory<Matrix<Int>> by makePropertyProxy(::getAvailableIntFactories)

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


private interface MatFacProperty<T> {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): MatrixFactory<Matrix<T>>
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: MatrixFactory<Matrix<T>>?)
}


private inline fun <reified T> makePropertyProxy(crossinline available: ()->List<MatrixFactory<Matrix<T>>>) = object : MatFacProperty<T> {
    private var defaultFactory: MatrixFactory<Matrix<T>>? = null

    override
    operator fun getValue(thisRef: Any?, property: KProperty<*>): MatrixFactory<Matrix<T>> {
        val facInst = defaultFactory
        if (facInst != null) {
            return facInst
        }
        else {
            // No-one has set the factory property manually.
            // Lets look through the list of known backends
            // on this platform and set the property if one exists.
            val facs = available()
            if(facs.isNotEmpty()) {
                val newFac = facs[0]
                defaultFactory = newFac
                return newFac
            }
            else
                throw RuntimeException("No default backends for golem matrix found. Please set golem.factory manually or" +
                                       " put one on your classpath.")
        }

    }

    override
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: MatrixFactory<Matrix<T>>?) {
        defaultFactory = value
    }
}
