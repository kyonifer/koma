package golem.containers

/**
 * Represents a time with respect to some [TimeStandard].
 */
data class Time @JvmOverloads constructor(var time: Double, var timeStandard: TimeStandard = TimeStandard.WALL) {

    /**
     * Check two TimeStandards for equivalence before doing any math with
     * the attached time values.
     *
     * @param other: Another Time instance.
     */
    fun checkStandard(other: Time) {
        if (this.timeStandard != other.timeStandard)
            throw Exception("TimeStandard ${this.timeStandard.value} does not match" +
                    " ${other.timeStandard.value}")
    }
}

