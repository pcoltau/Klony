package com.klony.sorting

import com.klony.extensions.attributeView
import com.klony.extensions.attributes
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.attribute.FileTime
import java.sql.Timestamp
import java.time.LocalDate

class SortingTests {

    companion object {
        private val fileMap = mapOf(
            "a/a1" to LocalDate.now().minusDays(6),
            "a/b1" to LocalDate.now().minusDays(7),
            "a/c1" to LocalDate.now().minusDays(1),
            "b/a2" to LocalDate.now().minusDays(2),
            "b/b2" to LocalDate.now().minusDays(5),
            "b/c2" to LocalDate.now().minusDays(8),
            "c/a3" to LocalDate.now().minusDays(3),
            "c/b3" to LocalDate.now().minusDays(4),
            "c/c3" to LocalDate.now().minusDays(0)
        )
        private var fileSet: Set<File> = setOf()

        @BeforeAll
        @JvmStatic
        internal fun beforeAll() {
            val dir = createTempDir()
            val resolvedFileMap = fileMap.mapKeys { dir.resolve(it.key) }
            fileSet = resolvedFileMap.map { it.key }.toSet()

            resolvedFileMap.forEach { (file, date) ->
                file.parentFile.mkdirs()
                assertTrue(file.createNewFile())
                val time = FileTime.fromMillis(Timestamp.valueOf(date.atStartOfDay()).time)
                file.attributeView().setTimes(time, time, time)
            }
        }
    }

    @Test
    fun `Successful sort by CREATION_DATE_DESC`() {
        val sorting = Sorting(Mode.CREATION_DATE, Direction.DESC)
        val sortedFiles = sorting.sortFiles(fileSet)
        assertSortedList(sortedFiles, -1) { it.attributes().creationTime() }
    }

    @Test
    fun `Successful sort by CREATION_DATE_ASC`() {
        val sorting = Sorting(Mode.CREATION_DATE, Direction.ASC)
        val sortedFiles = sorting.sortFiles(fileSet)
        assertSortedList(sortedFiles, 1) { it.attributes().creationTime() }
    }

    @Test
    fun `Successful sort by MODIFIED_DATE_DESC`() {
        val sorting = Sorting(Mode.MODIFIED_DATE, Direction.DESC)
        val sortedFiles = sorting.sortFiles(fileSet)
        assertSortedList(sortedFiles, -1) { it.attributes().lastModifiedTime() }
    }

    @Test
    fun `Successful sort by MODIFIED_DATE_ASC`() {
        val sorting = Sorting(Mode.MODIFIED_DATE, Direction.ASC)
        val sortedFiles = sorting.sortFiles(fileSet)
        assertSortedList(sortedFiles, 1) { it.attributes().lastModifiedTime() }
    }

    @Test
    fun `Successful sort by PATH_DESC`() {
        val sorting = Sorting(Mode.PATH, Direction.DESC)
        val sortedFiles = sorting.sortFiles(fileSet)
        assertSortedList(sortedFiles, -1) { it.toPath().toRealPath() }
    }

    @Test
    fun `Successful sort by PATH_ASC`() {
        val sorting = Sorting(Mode.PATH, Direction.ASC)
        val sortedFiles = sorting.sortFiles(fileSet)
        assertSortedList(sortedFiles, 1) { it.toPath().toRealPath() }
    }

    @Test
    fun `Successful sort by FILE_DESC`() {
        val sorting = Sorting(Mode.FILE, Direction.DESC)
        val sortedFiles = sorting.sortFiles(fileSet)
        assertSortedList(sortedFiles, -1) { it.name }
    }

    @Test
    fun `Successful sort by FILE_ASC`() {
        val sorting = Sorting(Mode.FILE, Direction.ASC)
        val sortedFiles = sorting.sortFiles(fileSet)
        assertSortedList(sortedFiles, 1) { it.name }
    }

    private fun <T: Comparable<T>> assertSortedList(sortedFiles: List<File>, expectedCompare: Int, compareField: (File) -> T) {
        sortedFiles.forEachIndexed { index, file ->
            if (index > 0) {
                val previous = compareField(sortedFiles[index - 1])
                val current = compareField(file)
                val compare = current.compareTo(previous)
                assertEquals(expectedCompare, compare)
            }
        }
    }
}
