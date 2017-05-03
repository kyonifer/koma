@file:JvmName("RawMTJSingleton")

package koma.matrix.mtj.backend

import koma.matrix.mtj.*


internal var factoryInstance: MTJMatrixFactory = MTJMatrixFactory()
internal var curSeed = System.currentTimeMillis()
internal var random = java.util.Random(curSeed)
