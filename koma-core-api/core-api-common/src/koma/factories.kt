package koma

import koma.internal.getDoubleFactory
import koma.internal.getFloatFactory
import koma.internal.getIntFactory
import koma.matrix.Matrix
import koma.matrix.MatrixFactory

// TODO: Ideally these properties are expect/actual with implementations. However, there's currently
// a generation issue with kotlin/native that breaks this approach, so as a workaround we'll define
// getXFactory methods in koma.internal as expect actual and proxy them here. These properties have
// to be lazily evaluated to avoid a race on startup in js, so we use private nullable fields and
// initialize on first use

/**
 *
 * Default factory that all top-level functions use when building new matrices.
 * Double precision.
 *
 * Replace this factory at runtime with e.g. koma.matrix.ejml.EJMLMatrixFactory() to change what
 * backend the top-level functions use for computation.
 *
 */
var doubleFactory: MatrixFactory<Matrix<Double>>
    get() = _doubleFactory ?: getDoubleFactory().also { _doubleFactory = it }
    set(value) { _doubleFactory = value}
private var _doubleFactory: MatrixFactory<Matrix<Double>>? = null

/**
 *
 * Default factory that all top-level functions use when building new matrices.
 * Single precision.
 *
 * Replace this factory at runtime with another to change what
 * backend the top-level functions use for computation.
 *
 */
var floatFactory: MatrixFactory<Matrix<Float>>
    get() = _floatFactory ?: getFloatFactory().also { _floatFactory = it }
    set(value) { _floatFactory = value}
private var _floatFactory: MatrixFactory<Matrix<Float>>? = null

/**
 *
 * Default factory that all top-level functions use when building new matrices.
 * Integer matrices.
 *
 * Replace this factory at runtime with another to change what
 * backend the top-level functions use for computation.
 *
 */
var intFactory: MatrixFactory<Matrix<Int>>
    get() = _intFactory ?: getIntFactory().also { _intFactory = it }
    set(value) { _intFactory = value}
private var _intFactory: MatrixFactory<Matrix<Int>>? = null
