package golem.platformsupport

/**
 * Add missing signum function, as math.h doesn't include one. 
 */
fun signum(value: Double) 
        = if (value > 0) 1.0 
          else if (value < 0) -1.0
          else 0.0