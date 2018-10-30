package koma.internal

/**
 * On JVM and JS platforms, this is identical to kotlin.synchronized().  On Native platform, this simply executes
 * the block with no synchronization.
 */
internal expect fun <R> syncNotNative(lock: Any, block: () -> R): R
