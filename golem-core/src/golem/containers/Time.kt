package golem.containers
import golem.platformsupport.annotations.*

/**
 * Represents a time with respect to some [TimeStandard].
 */
data class Time @JvmOverloads constructor(var time: Double, var timeStandard: TimeStandard = TimeStandard.NONE) {

    /**
     * Check two TimeStandards for equivalence before doing any math with
     * the attached time values.
     *
     * @param other: Another Time instance.
     */
    fun checkStandard(other: Time) {
        if (this.timeStandard != TimeStandard.NONE &&
            other.timeStandard != TimeStandard.NONE &&
            this.timeStandard != other.timeStandard)
            throw Exception("TimeStandard ${this.timeStandard} does not match" +
                    " ${other.timeStandard}")
    }

    /** Return a human-readable representation of this Time */
    override fun toString() : String = String.format("%s:%.3f", timeStandard.toString(), time)
}

