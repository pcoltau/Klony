package com.klony

import com.klony.filter.CloneCountFilter
import com.klony.formatters.ChecksumMapFormatterPipe
import com.klony.formatters.ChecksumMapFormatterSummary
import com.klony.sorting.Sorting
import com.klony.utils.resolveToRealPath
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.InvalidArgumentException
import com.xenomachina.argparser.default
import java.io.File
import java.util.*

// TODO: Add filename filter (filename regex)
// Example: List only the original files, by setting the "output limit" to 1, and sort by creation date ascending
// Example: List all clones, except the original file, by setting the "output offset" to 1, and sort by creation date ascending
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
                            val filePath = file.toPath().resolveToRealPath()
                            val otherFilePath = otherFile.toPath().resolveToRealPath()
                            if (filePath.startsWith(otherFilePath)) {
                                throw InvalidArgumentException(
                                        "The given directory \"$filePath\" is a subdirectory of \"$otherFilePath\", which is not allowed.")
                            }
                        }
                    }
                }
            }

    val outputFormatter by parser.mapping(
            "--pipe" to ChecksumMapFormatterPipe(),
            help = "Turns on pipeable output format. " +
                    "Without this option, the output will be in a summary format.")
            .default { ChecksumMapFormatterSummary() }

    val sizeLimitLower by parser.storing("--file_size_lower_limit",
            help = "The lower file size limit in bytes. " +
                    "Files with a file size less than or equal to the lower limit will not be included in scan.")
    { toLong() }.default { 0 }

    val sizeLimitHigher by parser.storing("--file_size_greater_limit",
            help = "The greater file size limit in bytes. " +
                    "Files with a file size larger than or equal to the higher limit will not be included in scan")
    { toLong() }.default { Long.MAX_VALUE }

    val reportProgress by parser.flagging("--progress",
            help = "Report on the scanning progress. It is not recommended to use this, if you are piping (see --pipe) the output to another process.")

    val caseSensitive by parser.flagging("--case_sensitive",
            help = "This makes the -e option case-sensitive.")

    val includedExtensions: List<String> by parser.adding("-e", "--include_ext",
            help = "The file extension to limit the search to. The expected form is \".jpg\" or just \"jpg\". " +
                    "This option can be used more than once to include different extensions. " +
                    "Note: The extension check is case-insensitive. Use --case_sensitive to make it case-sensitive.")
    {
        this.extensionCheckDot()
    }.default { Collections.emptyList() }

    val cloneSorting by parser.storing("--clone_sorting",
            help = "Sort order of each individual clone set. " +
                    "Possible values are: " +
                    Sorting.help())
    {
        Sorting.fromString(this)
                ?: throw InvalidArgumentException("Unknown sort order: $this\nThe possible values are: \n" + Sorting.help(System.lineSeparator()))
    }.default { Sorting.default }

    val cloneCountFilter by parser.storing("--clone_count_filter",
            help = "The conditional filter of each individual clone set. " +
                    "Possible values takes the form of: " +
                    "${CloneCountFilter.help()}. " +
                    "Example: '--clone_count_filter ==5' will only include clone sets of exactly 5 items. " +
                    "If not set, there is an implicit (default) value of '${CloneCountFilter.default}' (a clone set with only 1 item isn't really a clone).")
    {
        CloneCountFilter.fromString(this)
                ?: throw InvalidArgumentException("Unable to parse clone_count_filter: $this\nThe possible values takes the form of: \n" + CloneCountFilter.help(System.lineSeparator()))
    }.default { CloneCountFilter.default }

    val cloneCountLimit by parser.storing("--clone_count_limit",
            help = "The limit of number of items included in each individual clone set. " +
                    "Example: '--clone_count_limit 1' will only include the first item (after sorting and filter is applied) in the clone set. ")
    { toLong() }.default { null }

    val cloneCountOffset by parser.storing("--clone_count_offset",
            help = "The index offset of the items included in each individual clone set. " +
                    "Example: '--clone_count_offset 1' will NOT include the first item (after sorting and filter is applied) in the clone set. ")
    { toLong() }.default { 0 }

    private fun String.extensionCheckDot() =
            if (this.first() == '.') this.drop(1) else this

    private fun String.directoryCheckTilde() =
            if (this.startsWith("~")) this.replaceFirst("~", System.getProperty("user.home")) else this
}