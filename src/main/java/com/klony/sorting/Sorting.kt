package com.klony.sorting

import com.klony.utils.resolveToRealPath
import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes

data class Sorting(val mode: Mode, val direction: Direction) {
    companion object {
        val default = Sorting(Mode.CREATION_DATE, Direction.ASC)

        fun help(separator: String = ", "): String {
            return enumValues<Mode>().joinToString(separator = separator) { mode ->
                enumValues<Direction>().joinToString(separator = separator) { direction ->
                    mode.name + "_" + direction.name + if (default.mode == mode && default.direction == direction) " (default)" else ""
                }
            }
        }

        fun fromString(string: String): Sorting? {
            return try {
                val mode = enumValueOf<Mode>(string.substringBeforeLast("_"))
                val direction = enumValueOf<Direction>(string.substringAfterLast("_"))
                Sorting(mode, direction)
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }

    fun sortFiles(files: Set<File>): List<File> {
        return when (this.mode) {
            Mode.CREATION_DATE -> files.sortedWithDirectionBy(this.direction) { file -> Files.readAttributes(file.toPath().resolveToRealPath(), BasicFileAttributes::class.java).creationTime() }
            Mode.MODIFIED_DATE -> files.sortedWithDirectionBy(this.direction) { file -> Files.readAttributes(file.toPath().resolveToRealPath(), BasicFileAttributes::class.java).lastModifiedTime() }
            Mode.PATH -> files.sortedWithDirectionBy(this.direction) { file -> file.toPath().toRealPath() }
            Mode.FILE -> files.sortedWithDirectionBy(this.direction) { file -> file.name }
        }
    }

    private inline fun <T, R : Comparable<R>> Iterable<T>.sortedWithDirectionBy(direction: Direction, crossinline selector: (T) -> R?): List<T> {
        return when (direction) {
            Direction.ASC -> this.sortedBy(selector)
            Direction.DESC -> this.sortedByDescending(selector)
        }
    }
}