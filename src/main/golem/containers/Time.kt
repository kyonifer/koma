package golem.containers

/**
 * Represents a time with respect to some [TimeStandard].
 */
data class Time(var time: Double, var timeStandard: TimeStandard = TimeStandard.WALL)

