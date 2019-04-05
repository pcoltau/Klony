package com.klony.extensions

import com.klony.utils.resolveToRealPath
import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributeView
import java.nio.file.attribute.BasicFileAttributes

fun File.attributes(): BasicFileAttributes {
    return Files.readAttributes(this.toPath().resolveToRealPath(), BasicFileAttributes::class.java)
}

fun File.attributeView(): BasicFileAttributeView {
    return Files.getFileAttributeView(this.toPath().resolveToRealPath(), BasicFileAttributeView::class.java)
}
