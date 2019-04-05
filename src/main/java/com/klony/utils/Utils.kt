package com.klony.utils

import java.nio.file.LinkOption
import java.nio.file.Path

// TODO: Consider moving these to extensions or separate files

typealias Checksum = Long

fun String.extensionCheckCaseInsensitive(isCaseSensitive: Boolean) =
        if (!isCaseSensitive) this.toLowerCase() else this

// From: https://stackoverflow.com/questions/3758606/how-to-convert-byte-size-into-human-readable-format-in-java
fun humanReadableByteCount(bytes: Long, si: Boolean): String {
    val unit = if (si) 1000 else 1024
    if (bytes < unit) return "$bytes B"
    val exp = (Math.log(bytes.toDouble()) / Math.log(unit.toDouble())).toInt()
    val pre = (if (si) "kMGTPE" else "KMGTPE")[exp - 1] + if (si) "" else "i"
    return String.format("%.1f %sB", bytes / Math.pow(unit.toDouble(), exp.toDouble()), pre)
}

fun Path.resolveToRealPath(followSymbolicLink: Boolean = false): Path {
    return if (followSymbolicLink) this.toRealPath() else this.toRealPath(LinkOption.NOFOLLOW_LINKS)
}

// From: https://www.rgagnon.com/javadetails/java-0661.html
fun Path.toStringShortened(limit: Int): String {
    val path = this.toString()
    val threeDots = "..."

    if (path.length <= limit) {
        return path
    }

    val shortPathArray = CharArray(limit)
    val pathArray = path.toCharArray()
    val ellipseArray = threeDots.toCharArray()

    val pathIndex = pathArray.size - 1
    val shortPathIndex = limit - 1


    // fill the array from the end
    var i = 0
    while (i < limit) {
        if (pathArray[pathIndex - i] != '/' && pathArray[pathIndex - i] != '\\') {
            shortPathArray[shortPathIndex - i] = pathArray[pathIndex - i]
        } else {
            break
        }
        i++
    }
    // check how much space is left
    val free = limit - i

    if (free < threeDots.length) {
        // fill the beginning with ellipse
        for (j in ellipseArray.indices) {
            shortPathArray[j] = ellipseArray[j]
        }
    } else {
        // fill the beginning with path and leave room for the ellipse
        var j = 0
        while (j + ellipseArray.size < free) {
            shortPathArray[j] = pathArray[j]
            j++
        }
        // ... add the ellipse
        var k = 0
        while (j + k < free) {
            shortPathArray[j + k] = ellipseArray[k]
            k++
        }
    }
    return String(shortPathArray)
}