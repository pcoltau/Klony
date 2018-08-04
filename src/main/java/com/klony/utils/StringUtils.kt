package com.klony.utils

fun String.extensionCheckCaseInsensitive(isCaseInsensitive: Boolean) =
        if (isCaseInsensitive) this.toLowerCase() else this

// From: https://stackoverflow.com/questions/3758606/how-to-convert-byte-size-into-human-readable-format-in-java
fun humanReadableByteCount(bytes: Long, si: Boolean): String {
    val unit = if (si) 1000 else 1024
    if (bytes < unit) return bytes.toString() + " B"
    val exp = (Math.log(bytes.toDouble()) / Math.log(unit.toDouble())).toInt()
    val pre = (if (si) "kMGTPE" else "KMGTPE")[exp - 1] + if (si) "" else "i"
    return String.format("%.1f %sB", bytes / Math.pow(unit.toDouble(), exp.toDouble()), pre)
}
