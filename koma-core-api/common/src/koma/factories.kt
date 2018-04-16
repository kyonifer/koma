@file:koma.internal.JvmName("Koma")
@file:koma.internal.JvmMultifileClass

package koma

import koma.matrix.Matrix
import koma.matrix.MatrixFactory


@Deprecated("Use koma.matrix.Matrix.doubleFactory property instead", ReplaceWith("koma.matrix.Matrix.doubleFactory"))
var doubleFactory: MatrixFactory<Matrix<Double>>
    get() = koma.matrix.doubleFactory
    set(value) { koma.matrix.doubleFactory = value }

@Deprecated("Use koma.matrix.Matrix.floatFactory property instead", ReplaceWith("koma.matrix.Matrix.floatFactory"))
var floatFactory: MatrixFactory<Matrix<Float>>
    get() = koma.matrix.floatFactory
    set(value) { koma.matrix.floatFactory = value }

@Deprecated("Use koma.matrix.Matrix.intFactory property instead", ReplaceWith("koma.matrix.Matrix.intFactory"))
var intFactory: MatrixFactory<Matrix<Int>>
    get() = koma.matrix.intFactory
    set(value) { koma.matrix.intFactory = value }


@Deprecated("Use koma.matrix.Matrix.doubleFactory property instead", ReplaceWith("koma.matrix.Matrix.doubleFactory"))
var factory: MatrixFactory<Matrix<Double>>
    get() = doubleFactory
    set(value) { doubleFactory = value }

