package com.klony

import com.klony.utils.humanReadableByteCount
import java.nio.file.LinkOption

interface ChecksumMapFormatter {
    fun toString(checksumMap: ChecksumMap): String
}

class ChecksumMapFormatterSummary: ChecksumMapFormatter {
    override fun toString(checksumMap: ChecksumMap): String {
        val builder = StringBuilder()
        builder.appendln("The following files are clones:")
        builder.appendln("(Note: Each set of [...] contains a set of clones)")
        var cleanupSize: Long = 0
        checksumMap
                .filterValues { it.count() > 1 }
                .values.forEach {
            builder.append("[")
            var i = 0
            val c = it.size
            it.forEach {
                builder.append(it.toPath().toRealPath(LinkOption.NOFOLLOW_LINKS))
                if (i < c - 1) {
                    cleanupSize += it.length()
                    builder.append(", ")
                }
                i++
            }
            builder.appendln("]")
        }
        val humanReadableSize = humanReadableByteCount(cleanupSize, true)
        builder.appendln("You can save $humanReadableSize by cleaning up.")
        return builder.toString()
    }
}