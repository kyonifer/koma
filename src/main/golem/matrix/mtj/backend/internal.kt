@file:JvmName("RawMTJSingleton")

package golem.matrix.mtj.backend

import golem.matrix.mtj.MTJMatrixFactory


internal var factoryInstance: MTJMatrixFactory = MTJMatrixFactory()
internal var random = java.util.Random()
