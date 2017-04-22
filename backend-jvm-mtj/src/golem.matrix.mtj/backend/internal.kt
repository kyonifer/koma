@file:JvmName("RawMTJSingleton")

package golem.matrix.mtj.backend

import golem.matrix.mtj.*


internal var factoryInstance: MTJMatrixFactory = MTJMatrixFactory()
internal var curSeed = System.currentTimeMillis()
internal var random = java.util.Random(curSeed)
