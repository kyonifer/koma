package koma.internal

internal actual fun <R> syncNotNative(lock: Any, block: () -> R): R = synchronized(lock, block)
