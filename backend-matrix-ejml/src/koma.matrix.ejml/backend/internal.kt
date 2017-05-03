@file:JvmName("RawEJMLSingletons")

package koma.matrix.ejml.backend

import koma.matrix.ejml.*

internal var factoryInstance: EJMLMatrixFactory = EJMLMatrixFactory()
internal var curSeed = System.currentTimeMillis()
internal var random = java.util.Random(curSeed)

