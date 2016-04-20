/**
 * This file defines the default backend used for creating matrices at MTJ. This file is
 * conditionally compiled into the build if the defaultMTJ property is set in the build.gradle.
 * Otherwise, another defaults_* may be used (see build.gradle for details).
 */

@file:JvmName("Options")
@file:JvmMultifileClass

package golem

import golem.matrix.*


private var _factory: MatrixFactory<Matrix<Double>>? = null
/**
 *
 * Default factory that all top-level functions use when building new matrices.
 *
 * Replace this factory at runtime with e.g. golem.matrix.ejml.EJMLMatrixFactory() to change what
 * backend the top-level functions use for computation.
 *
 */
var factory: MatrixFactory<Matrix<Double>>
    get() {
        var facInst = _factory
        if (facInst != null) {
            return facInst
        }
        else {
            // At runtime, see which backends are on our classpath (if any)
            var facs = arrayOf("golem.matrix.mtj.MTJMatrixFactory",
                               "golem.matrix.ejml.EJMLMatrixFactory")
            facs.forEach {
                try {
                    var fac = Class.forName(it)
                    fac.constructors.forEach { ctor ->
                        if (ctor.parameterTypes.isEmpty()) {
                            @Suppress("UNCHECKED_CAST")
                            var out = ctor.newInstance() as MatrixFactory<Matrix<Double>>
                            _factory = out
                            return out
                        }
                    }
                } catch(e: ClassNotFoundException) {
                }

            }
            throw RuntimeException("No default backends for golem matrix found. Please set golem.factory manually or" +
                                   " put one on your classpath.")
        }
    }
    set(factory: MatrixFactory<Matrix<Double>>) {
        _factory = factory
    }

/**
 * Whether to validate the dimensions, symmetry, and values of input matrices. false is faster, and should be
 * used for realtime applications. true gives you much more useful errors when your matrices are shaped
 * differently than your code expects. */
var validateMatrices = true
