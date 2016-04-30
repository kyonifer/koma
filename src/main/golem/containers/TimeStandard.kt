package golem.containers

/**
 * A value indicating the time reference (not an enum class to facilitate interop)
 */
class TimeStandard(val value: String) {
    companion object {
        @JvmField
        val GPS = TimeStandard("GPS")
        @JvmField
        val WALL = TimeStandard("WALL")
        @JvmField
        val UNIX = TimeStandard("UNIX")
        @JvmField
        val OTHER = TimeStandard("OTHER")
    }
}

