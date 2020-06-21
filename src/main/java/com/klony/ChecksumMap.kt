package com.klony

import com.klony.utils.Checksum
import net.boeckling.crc.CRC64
import java.io.File

/**
 * This class is used to store the checksums for files.
 * The parent class BackedMutableMap has a map, which stores the Checksums for each set of files (with the same checksum).
 * The visitedFiles contains the set of all added files.
 */
class ChecksumMap : BackedMutableMap<Checksum, Set<File>>() {
    private val visitedFiles = mutableSetOf<File>()

    fun add(file: File) {
        if (visitedFiles.add(file)) {
            val checksum = calculateChecksum(file)
            addToChecksumMap(checksum, file)
        }
    }

    private fun addToChecksumMap(checksum: Checksum, file: File) {
        val existingSet = this[checksum]
        if (existingSet != null) {
            (existingSet as MutableSet).add(file)
        } else {
            backedMap[checksum] = mutableSetOf(file)
        }
    }

    private fun calculateChecksum(file: File): Long {
        val checksum = CRC64()
        file.forEachBlock { buffer, _ ->
            checksum.update(buffer, buffer.size)
        }
        return checksum.value
    }
}