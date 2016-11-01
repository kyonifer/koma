package golem.containers

/**
 * A value indicating the time reference (not an enum class to facilitate interop)
 */
class TimeStandard(val value: String) {
    companion object {
        /** Seconds since the GPS epoch, 6 Jan 1980 */
        @JvmField
        val GPS = TimeStandard("GPS")

        /** Value of some local clock; not necessarily synced to any external reference. */
        @JvmField
        val WALL = TimeStandard("WALL")

        /** Seconds since the UNIX epoch, 1 Jan 1970 */
        @JvmField
        val UNIX = TimeStandard("UNIX")

        @JvmField
        val NONE = TimeStandard("NONE")
    }

    /** Return a human-readable representation of this TimeStandard */
    override fun toString() : String = value
}

