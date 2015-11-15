/**
 * This file defines the default backend. Change the property below to use another
 * linear algebra library.
 */

@file:JvmName("Golem")
@file:JvmMultifileClass

package golem

import golem.matrix.Matrix
import golem.matrix.MatrixFactory

/**
 *
 * Default factory to build matrices from.
 *
 * Replace this factory at runtime with e.g. golem.matrix.ejml.EJMLMatrixFactory() to change what
 * backend the top-level functions use for computation.
 */
var factory: MatrixFactory<Matrix<Double>> = golem.matrix.mtj.MTJMatrixFactory()
