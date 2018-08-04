package com.klony

import net.boeckling.crc.CRC64
import java.io.File

typealias Checksum = Long
typealias FileSize = Long

/**
 * This class is used to store the checksums for files.
 * The parent class BackedMutableMap has a map, which stores the Checksums for each set of files (with the same checksum).
 * The fileMap contains the checksum and file size for each registered file. The file size is stored 
 */
class ChecksumMap: BackedMutableMap<Checksum, Set<File>>() {
    private val fileMap = mutableMapOf<File, Pair<Checksum, FileSize>>()

    fun add(file: File) {
        if (!fileMap.containsKey(file)) {
            val checksum = calculateChecksum(file)
            addToChecksumMap(checksum, file)
            fileMap[file] = Pair(checksum, file.length())
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