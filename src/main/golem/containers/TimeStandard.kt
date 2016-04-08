package golem.containers

/**
 * A value indicating the time reference (not an enum class to facilitate interop)
 */
class TimeStandard(var value: Int) {
    companion object {
        @JvmField
        val GPS = TimeStandard(1)
        @JvmField
        val WALL = TimeStandard(2)
        @JvmField
        val UNIX = TimeStandard(3)
        @JvmField
        val OTHER = TimeStandard(4)
    }
}

