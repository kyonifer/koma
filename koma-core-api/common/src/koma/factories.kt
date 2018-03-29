package koma

import koma.matrix.Matrix
import koma.matrix.MatrixFactory

@Deprecated("Use koma.matrix property instead", ReplaceWith("koma.matrix.doubleFactory"))
var doubleFactory: MatrixFactory<Matrix<Double>>
    get() = koma.matrix.doubleFactory
    set(value) { koma.matrix.doubleFactory = value }

@Deprecated("Use koma.matrix property instead", ReplaceWith("koma.matrix.floatFactory"))
var floatFactory: MatrixFactory<Matrix<Float>>
    get() = koma.matrix.floatFactory
    set(value) { koma.matrix.floatFactory = value }

@Deprecated("Use koma.matrix property instead", ReplaceWith("koma.matrix.intFactory"))
var intFactory: MatrixFactory<Matrix<Int>>
    get() = koma.matrix.intFactory
    set(value) { koma.matrix.intFactory = value }
