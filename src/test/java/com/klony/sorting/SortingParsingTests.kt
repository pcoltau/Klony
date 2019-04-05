package com.klony.sorting

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SortingParsingTests {

    @Test
    fun `Successful test parsing CREATION_DATE_DESC from string`() {
        val sorting = Sorting.fromString("CREATION_DATE_DESC")
        assertNotNull(sorting)
        assertEquals(Mode.CREATION_DATE, sorting?.mode)
        assertEquals(Direction.DESC, sorting?.direction)
    }

    @Test
    fun `Successful test parsing CREATION_DATE_ASC from string`() {
        val sorting = Sorting.fromString("CREATION_DATE_ASC")
        assertNotNull(sorting)
        assertEquals(Mode.CREATION_DATE, sorting?.mode)
        assertEquals(Direction.ASC, sorting?.direction)
    }

    @Test
    fun `Successful test parsing MODIFIED_DATE_DESC from string`() {
        val sorting = Sorting.fromString("MODIFIED_DATE_DESC")
        assertNotNull(sorting)
        assertEquals(Mode.MODIFIED_DATE, sorting?.mode)
        assertEquals(Direction.DESC, sorting?.direction)
    }

    @Test
    fun `Successful test parsing MODIFIED_DATE_ASC from string`() {
        val sorting = Sorting.fromString("MODIFIED_DATE_ASC")
        assertNotNull(sorting)
        assertEquals(Mode.MODIFIED_DATE, sorting?.mode)
        assertEquals(Direction.ASC, sorting?.direction)
    }

    @Test
    fun `Successful test parsing PATH_DESC from string`() {
        val sorting = Sorting.fromString("PATH_DESC")
        assertNotNull(sorting)
        assertEquals(Mode.PATH, sorting?.mode)
        assertEquals(Direction.DESC, sorting?.direction)
    }

    @Test
    fun `Successful test parsing PATH_ASC from string`() {
        val sorting = Sorting.fromString("PATH_ASC")
        assertNotNull(sorting)
        assertEquals(Mode.PATH, sorting?.mode)
        assertEquals(Direction.ASC, sorting?.direction)
    }

    @Test
    fun `Successful test parsing FILE_DESC from string`() {
        val sorting = Sorting.fromString("FILE_DESC")
        assertNotNull(sorting)
        assertEquals(Mode.FILE, sorting?.mode)
        assertEquals(Direction.DESC, sorting?.direction)
    }

    @Test
    fun `Successful test parsing FILE_ASC from string`() {
        val sorting = Sorting.fromString("FILE_ASC")
        assertNotNull(sorting)
        assertEquals(Mode.FILE, sorting?.mode)
        assertEquals(Direction.ASC, sorting?.direction)
    }

    @Test
    fun `Failure to parse string with no direction`() {
        val sorting = Sorting.fromString("CREATION_DATE")
        assertNull(sorting)
    }

    @Test
    fun `Failure to parse string with ending underscore`() {
        val sorting = Sorting.fromString("CREATION_DATE_")
        assertNull(sorting)
    }

    @Test
    fun `Failure to parse string with ending unknown direction`() {
        val sorting = Sorting.fromString("CREATION_DATE_ABC")
        assertNull(sorting)
    }

    @Test
    fun `Failure to parse string with ending no mode`() {
        val sorting = Sorting.fromString("ASC")
        assertNull(sorting)
    }

    @Test
    fun `Failure to parse string with ending prefixed underscore`() {
        val sorting = Sorting.fromString("_ASC")
        assertNull(sorting)
    }

    @Test
    fun `Failure to parse string with ending unknown mode`() {
        val sorting = Sorting.fromString("ABC_ASC")
        assertNull(sorting)
    }

    @Test
    fun `Failure to parse string with ending unknown string`() {
        val sorting = Sorting.fromString("ABC")
        assertNull(sorting)
    }

    @Test
    fun `Failure to parse string with empty string`() {
        val sorting = Sorting.fromString("")
        assertNull(sorting)
    }
}
