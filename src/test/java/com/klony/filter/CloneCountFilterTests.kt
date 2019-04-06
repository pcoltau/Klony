package com.klony.filter

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

class CloneCountFilterTests {

    @Test
    fun `Successful test that testAgainst returns true when testing that 1 is equal to 1`() {
        val filter = CloneCountFilter.fromString("==1")
        assertNotNull(filter)
        assertTrue(filter.testAgainst(1))
    }

    @Test
    fun `Successful test that testAgainst returns false when testing that 0 is equal to 1`() {
        val filter = CloneCountFilter.fromString("==1")
        assertNotNull(filter)
        assertFalse(filter.testAgainst(0))
    }

    @Test
    fun `Successful test that testAgainst returns false when testing that -1 is equal to 1`() {
        val filter = CloneCountFilter.fromString("==1")
        assertNotNull(filter)
        assertFalse(filter.testAgainst(-1))
    }

    @Test
    fun `Successful test that testAgainst returns false when testing that 1 is not equal to 1`() {
        val filter = CloneCountFilter.fromString("!=1")
        assertNotNull(filter)
        assertFalse(filter.testAgainst(1))
    }

    @Test
    fun `Successful test that testAgainst returns truewhen testing that 0 is not equal to 1`() {
        val filter = CloneCountFilter.fromString("!=1")
        assertNotNull(filter)
        assertTrue(filter.testAgainst(0))
    }

    @Test
    fun `Successful test that testAgainst returns false when testing that -1 is not equal to 1`() {
        val filter = CloneCountFilter.fromString("!=1")
        assertNotNull(filter)
        assertTrue(filter.testAgainst(-1))
    }

    @Test
    fun `Successful test that testAgainst returns true when testing that 1 is greater than to 0`() {
        val filter = CloneCountFilter.fromString(">0")
        assertNotNull(filter)
        assertTrue(filter.testAgainst(1))
    }

    @Test
    fun `Successful test that testAgainst returns false when testing that 0 is greater than to 0`() {
        val filter = CloneCountFilter.fromString(">0")
        assertNotNull(filter)
        assertFalse(filter.testAgainst(0))
    }

    @Test
    fun `Successful test that testAgainst returns false when testing that -1 is greater than to 0`() {
        val filter = CloneCountFilter.fromString(">0")
        assertNotNull(filter)
        assertFalse(filter.testAgainst(-1))
    }

    @Test
    fun `Successful test that testAgainst returns false when testing that 1 is lesser than to 0`() {
        val filter = CloneCountFilter.fromString("<0")
        assertNotNull(filter)
        assertFalse(filter.testAgainst(1))
    }

    @Test
    fun `Successful test that testAgainst returns false when testing that 0 is lesser than to 0`() {
        val filter = CloneCountFilter.fromString("<0")
        assertNotNull(filter)
        assertFalse(filter.testAgainst(0))
    }

    @Test
    fun `Successful test that testAgainst returns true when testing that -1 is lesser than to 0`() {
        val filter = CloneCountFilter.fromString("<0")
        assertNotNull(filter)
        assertTrue(filter.testAgainst(-1))
    }

    @Test
    fun `Successful test that testAgainst returns true when testing that 1 is greater than or equal to 0`() {
        val filter = CloneCountFilter.fromString(">=0")
        assertNotNull(filter)
        assertTrue(filter.testAgainst(1))
    }

    @Test
    fun `Successful test that testAgainst returns true when testing that 0 is greater than or equal to 0`() {
        val filter = CloneCountFilter.fromString(">=0")
        assertNotNull(filter)
        assertTrue(filter.testAgainst(0))
    }

    @Test
    fun `Successful test that testAgainst returns false when testing that -1 is greater than or equal to 0`() {
        val filter = CloneCountFilter.fromString(">=0")
        assertNotNull(filter)
        assertFalse(filter.testAgainst(-1))
    }

    @Test
    fun `Successful test that testAgainst returns false when testing that 1 is lesser than or equal to 0`() {
        val filter = CloneCountFilter.fromString("<=0")
        assertNotNull(filter)
        assertFalse(filter.testAgainst(1))
    }

    @Test
    fun `Successful test that testAgainst returns true when testing that 0 is lesser than or equal to 0`() {
        val filter = CloneCountFilter.fromString("<=0")
        assertNotNull(filter)
        assertTrue(filter.testAgainst(0))
    }

    @Test
    fun `Successful test that testAgainst returns true when testing that -1 is lesser than or equal to 0`() {
        val filter = CloneCountFilter.fromString("<=0")
        assertNotNull(filter)
        assertTrue(filter.testAgainst(-1))
    }
}
