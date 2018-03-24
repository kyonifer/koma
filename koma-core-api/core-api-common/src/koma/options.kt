/**
 * This file defines the default backend used for creating matrices at MTJ. This file is
 * conditionally compiled into the build if the defaultMTJ property is set in the build.gradle.
 * Otherwise, another defaults_* may be used (see build.gradle for details).
 */

@file:KomaJvmName("Options")
@file:KomaJvmMultifileClass

package koma

import koma.matrix.*
import koma.matrix.default.DefaultDoubleMatrixFactory
import koma.matrix.default.DefaultFloatMatrixFactory
import koma.matrix.default.DefaultIntMatrixFactory
import kotlin.reflect.KProperty
import koma.internal.KomaJvmName
import koma.internal.KomaJvmMultifileClass

@Deprecated("Use doubleFactory instead", replaceWith=ReplaceWith("doubleFactory"))
var factory: MatrixFactory<Matrix<Double>>
    get() = doubleFactory
    set(value: MatrixFactory<Matrix<Double>>) { doubleFactory = value }

/**
 *
 * Default factory that all top-level functions use when building new matrices.
 * Double precision.
 *
 * Replace this factory at runtime with e.g. koma.matrix.ejml.EJMLMatrixFactory() to change what
 * backend the top-level functions use for computation.
 *
 */
var doubleFactory: MatrixFactory<Matrix<Double>> by MatFacProperty(::getPlatformDoubleFactories,
                                                             default = DefaultDoubleMatrixFactory())

/**
 *
 * Default factory that all top-level functions use when building new matrices.
 * Single precision.
 *
 * Replace this factory at runtime with another to change what
 * backend the top-level functions use for computation.
 *
 */
var floatFactory: MatrixFactory<Matrix<Float>> by MatFacProperty(::getPlatformFloatFactories,
                                                                 default = DefaultFloatMatrixFactory())
/**
 *
 * Default factory that all top-level functions use when building new matrices.
 * Integer matrices.
 *
 * Replace this factory at runtime with another to change what
 * backend the top-level functions use for computation.
 *
 */
var intFactory: MatrixFactory<Matrix<Int>> by MatFacProperty(::getPlatformIntFactories,
                                                             default = DefaultIntMatrixFactory())

/**
 *  At runtime, see which double backends are available on our classpath (if any).
 *
 *  @return A list of factory instances for backends that were found.
 */
fun getPlatformDoubleFactories(): List<MatrixFactory<Matrix<Double>>> {
    return koma.internal.getFactories()
}

/**
 *  At runtime, see which float backends are available on our classpath (if any).
 *
 *  @return A list of factory instances for backends that were found.
 */
fun getPlatformFloatFactories(): List<MatrixFactory<Matrix<Float>>> = listOf()

/**
 *  At runtime, see which int backends are available on our classpath (if any).
 *
 *  @return A list of factory instances for backends that were found.
 */
fun getPlatformIntFactories(): List<MatrixFactory<Matrix<Int>>> = listOf()

/**
 * Whether to validate the dimensions, symmetry, and values of input matrices. false is faster, and should be
 * used for realtime applications. true gives you much more useful errors when your matrices are shaped
 * differently than your code expects.
 */
var validateMatrices = true


/**
 * A property which queries the platform-specific discovery function [available]
 * for a backend, and if none are found there it selects [default] instead. If this
 * property is ever set by the user then the user's choice overrides all others.
 */
class MatFacProperty<T>(val available: ()->List<MatrixFactory<Matrix<T>>>,
                        val default: MatrixFactory<Matrix<T>>) {
    private var defaultFactory: MatrixFactory<Matrix<T>>? = null

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
            else {
                defaultFactory = default
                return default
            }
        }

    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: MatrixFactory<Matrix<T>>?) {
        defaultFactory = value
    }
}
