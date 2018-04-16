@file:koma.internal.JvmName("Koma")
@file:koma.internal.JvmMultifileClass

package koma

import koma.matrix.Matrix
import koma.matrix.MatrixFactory


/**
 * Whether to validate the dimensions, symmetry, and values of input matrices. false is faster, and should be
 * used for realtime applications. true gives you much more useful errors when your matrices are shaped
 * differently than your code expects.
 */
var validateMatrices = true
