package koma.internal

/**
 * Add missing signum function, as math.h doesn't include one. 
 */
internal actual fun signum(num: Double)
        = if (num > 0) 1.0
          else if (num < 0) -1.0
          else 0.0