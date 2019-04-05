package com.klony.filter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CloneCountFilterParsingTests {

    @Test
    fun `Successful test parsing equal 1 from string`() {
        val filter = CloneCountFilter.fromString("==1")
        assertNotNull(filter)
        assertEquals(1, filter?.value)
        assertEquals(CloneCountOperator.EQUAL, filter?.operator)
    }

    @Test
    fun `Successful test parsing equal 100 from string`() {
        val filter = CloneCountFilter.fromString("==100")
        assertNotNull(filter)
        assertEquals(100, filter?.value)
        assertEquals(CloneCountOperator.EQUAL, filter?.operator)
    }

    @Test
    fun `Successful test parsing not equal 1 from string`() {
        val filter = CloneCountFilter.fromString("!=1")
        assertNotNull(filter)
        assertEquals(1, filter?.value)
        assertEquals(CloneCountOperator.NOT_EQUAL, filter?.operator)
    }

    @Test
    fun `Successful test parsing greater than 1 from string`() {
        val filter = CloneCountFilter.fromString(">1")
        assertNotNull(filter)
        assertEquals(1, filter?.value)
        assertEquals(CloneCountOperator.GREATER_THAN, filter?.operator)
    }

    @Test
    fun `Successful test parsing lesser than 1 from string`() {
        val filter = CloneCountFilter.fromString("<1")
        assertNotNull(filter)
        assertEquals(1, filter?.value)
        assertEquals(CloneCountOperator.LESS_THAN, filter?.operator)
    }

    @Test
    fun `Successful test parsing lesser than or equal 1 from string`() {
        val filter = CloneCountFilter.fromString("<=1")
        assertNotNull(filter)
        assertEquals(1, filter?.value)
        assertEquals(CloneCountOperator.LESS_THAN_OR_EQUAL, filter?.operator)
    }

    @Test
    fun `Successful test parsing greater than or equal 1 from string`() {
        val filter = CloneCountFilter.fromString(">=1")
        assertNotNull(filter)
        assertEquals(1, filter?.value)
        assertEquals(CloneCountOperator.GREATER_THAN_OR_EQUAL, filter?.operator)
    }

    @Test
    fun `Failure to parse string with a single equal sign`() {
        val filter = CloneCountFilter.fromString("=1")
        assertNull(filter)
    }

    @Test
    fun `Failure to parse string with a negative value`() {
        val filter = CloneCountFilter.fromString("==-1")
        assertNull(filter)
    }

    @Test
    fun `Failure to parse string with a floating point value`() {
        val filter = CloneCountFilter.fromString("==1.0")
        assertNull(filter)
    }

    @Test
    fun `Failure test parsing from string with no operator`() {
        val filter = CloneCountFilter.fromString("10")
        assertNull(filter)
    }

    @Test
    fun `Failure to parse string with only an operator`() {
        val filter = CloneCountFilter.fromString("==")
        assertNull(filter)
    }

    @Test
    fun `Failure to parse an empty string`() {
        val filter = CloneCountFilter.fromString("")
        assertNull(filter)
    }
}
