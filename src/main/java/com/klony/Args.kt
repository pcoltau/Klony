package com.klony

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.InvalidArgumentException
import com.xenomachina.argparser.default
import java.io.File
import java.util.*

// TODO: Add exclusive regex
// TODO: Add support for symbolic links (should not be included per default) - that also includes symlinks as root directory

class Args(parser: ArgParser) {
    val directoryRoots by parser.positionalList("DIRECTORIES",
            help = "The list of directories where the scan will start. " +
                    "One directory must not be within another given directory. " +
                    "Note: There is only support for simple tilde expansion, e.g. ~/ -> /user/myuser/")
        { File(directoryCheckTilde()).canonicalFile }
            .addValidator {
                value.forEach { file ->
                    if (!file.exists()) {
                        throw InvalidArgumentException(
                                "The given directory does not exist: $file")
                    }
                    if (!file.isDirectory) {
                        throw InvalidArgumentException(
                                "The given directory is not a directory: $file")
                    }
                }
            }
            .addValidator {
                value.forEach { file ->
                    value.forEach { otherFile ->
                        if (file != otherFile) {
                            val filePath = file.path
                            val otherFilePath = otherFile.path
                            if (filePath.startsWith(otherFilePath)) {
                                throw InvalidArgumentException(
                                        "The given directory \"$filePath\" is a subdirectory of \"$otherFilePath\", which is not allowed.")
                            }
                        }
                    }
                }
            }

    val outputFormatter by parser.storing("-o", "--output_format",
            help = "The output format. " +
                    "Possible values are: \nSUMMARY (default)\nPIPEABLE")
    {
        when (this.toUpperCase()) {
            "SUMMARY" -> ChecksumMapFormatterSummary()
            "PIPEABLE" -> ChecksumMapFormatterSummary() // TODO: Implement
            else -> throw InvalidArgumentException(
                "Unknown output format: $this")
        }
    }.default { ChecksumMapFormatterSummary() }

    val limitLower by parser.storing("-l", "--limit_lower",
            help = "The lower file size limit in bytes. " +
                    "Files with a file size less than or equal to the lower limit will not be included in scan.")
        { toLong() }.default { 0 }

    val limitHigher by parser.storing("-g", "--limit_higher",
            help = "The higher file size limit in bytes. " +
                    "Files with a file size larger than or equal to the higher limit will not be included in scan")
        { toLong() }.default { Long.MAX_VALUE }

    val caseInsensitive by parser.flagging("-c", "--case_insensitive",
            help = "This makes the -e option case-insensitive.")

    val includedExtensions by parser.adding("-e", "--include_ext",
            help = "The file extension to limit the search to. The expected form is \".jpg\" or just \"jpg\". " +
                    "This option can be used more than once to include different extensions. " +
                    "Note: The extension check is case-sensitive. Use -c to make it case-insensitive.")
        {
            extensionCheckDot()
            // TODO: Re-add when the bug has been fixed in ArgParser
            //.extensionCheckCaseInsensitive()
        }.default { Collections.emptyList() }

    private fun String.extensionCheckDot() =
            if (this.first() == '.') this.drop(1) else this

    private fun String.directoryCheckTilde() =
            if (this.startsWith("~")) this.replaceFirst("~", System.getProperty("user.home")) else this
}