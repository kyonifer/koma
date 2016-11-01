package golem.containers

/**
 * A value indicating the time reference (not an enum class to facilitate interop)
 */

enum class TimeStandard {
    GPS, WALL, UNIX, NONE;
    /** Return a human-readable representation of this TimeStandard */
    override fun toString() : String = when(this) {
        GPS -> "GPS"
        WALL -> "WALL"
        UNIX -> "UNIX"
        NONE -> "NONE"
    }

}

