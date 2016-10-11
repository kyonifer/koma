package golem

import golem.containers.Time
import golem.containers.TimeStandard
import org.junit.Test
import org.junit.Assert.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith

class ContainersTests {
    val t1 = Time(0.0, TimeStandard.GPS)
    val t2 = Time(1.0, TimeStandard.GPS)
    val t3 = Time(1.0, TimeStandard.WALL)
    val t4 = Time(1.0, TimeStandard.OTHER)

    /**
     * Nothing to assert- if no error thrown all is well.
     */
    @Test
    fun testEqualStandard() {
        t1.checkStandard(t2)
    }

    @Test
    fun testNotEqualStandard() {
        assertFailsWith(Exception::class,
                "TimeStandard GPS does not match WALL"){
            t1.checkStandard(t3)
        }
    }
    @Test
    fun testIgnoreOther() {
        t1.checkStandard(t4)
        t4.checkStandard(t1)
    }

    @Test
    fun testToString() {
        assertEquals("GPS:0.0", t1.toString())
        assertEquals("GPS:1.0", t2.toString())
        assertEquals("WALL:1.0", t3.toString())
        assertEquals("OTHER:1.0", t4.toString())
    }
}
