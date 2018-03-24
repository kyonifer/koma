package koma

import koma.internal.getDoubleFactory
import koma.internal.getFloatFactory
import koma.internal.getIntFactory
import koma.matrix.Matrix
import koma.matrix.MatrixFactory

// TODO: Ideally these properties are expect/actual with implementations. However, there's currently
// a generation issue with kotlin/native that breaks this approach, so as a workaround we'll define
// getXFactory methods in koma.internal as expect actual and proxy them here

/**
 *
 * Default factory that all top-level functions use when building new matrices.
 * Double precision.
 *
 * Replace this factory at runtime with e.g. koma.matrix.ejml.EJMLMatrixFactory() to change what
 * backend the top-level functions use for computation.
 *
 */
var doubleFactory: MatrixFactory<Matrix<Double>> = getDoubleFactory()

/**
 *
 * Default factory that all top-level functions use when building new matrices.
 * Single precision.
 *
 * Replace this factory at runtime with another to change what
 * backend the top-level functions use for computation.
 *
 */
var floatFactory: MatrixFactory<Matrix<Float>> = getFloatFactory()
/**
 *
 * Default factory that all top-level functions use when building new matrices.
 * Integer matrices.
 *
 * Replace this factory at runtime with another to change what
 * backend the top-level functions use for computation.
 *
 */
var intFactory: MatrixFactory<Matrix<Int>> = getIntFactory()
