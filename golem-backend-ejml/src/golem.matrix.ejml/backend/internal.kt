@file:JvmName("RawEJMLSingletons")

package golem.matrix.ejml.backend

import golem.matrix.ejml.*

internal var factoryInstance: EJMLMatrixFactory = EJMLMatrixFactory()
internal var curSeed = System.currentTimeMillis()
internal var random = java.util.Random(curSeed)

