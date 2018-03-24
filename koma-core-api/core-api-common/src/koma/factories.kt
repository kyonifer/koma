package koma

import koma.matrix.Matrix
import koma.matrix.MatrixFactory


/**
 *
 * Default factory that all top-level functions use when building new matrices.
 * Double precision.
 *
 * Replace this factory at runtime with e.g. koma.matrix.ejml.EJMLMatrixFactory() to change what
 * backend the top-level functions use for computation.
 *
 */
expect var doubleFactory: MatrixFactory<Matrix<Double>>

/**
 *
 * Default factory that all top-level functions use when building new matrices.
 * Single precision.
 *
 * Replace this factory at runtime with another to change what
 * backend the top-level functions use for computation.
 *
 */
expect var floatFactory: MatrixFactory<Matrix<Float>>
/**
 *
 * Default factory that all top-level functions use when building new matrices.
 * Integer matrices.
 *
 * Replace this factory at runtime with another to change what
 * backend the top-level functions use for computation.
 *
 */
expect var intFactory: MatrixFactory<Matrix<Int>>
