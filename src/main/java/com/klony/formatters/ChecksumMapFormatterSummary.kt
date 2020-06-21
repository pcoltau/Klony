package com.klony.formatters

import com.klony.organiser.OrganisedResult
import com.klony.utils.resolveToRealPath
import com.klony.utils.terminal.Terminal
import com.klony.utils.toStringShortened

class ChecksumMapFormatterSummary : ChecksumMapFormatter {
    override fun toString(result: OrganisedResult): String {
        val terminalDimension = Terminal.getDimensionOrDefault()
        val fullLine = "-".repeat(terminalDimension.width)
        val setSeparator = "${System.lineSeparator()}$fullLine${System.lineSeparator()}"
        val prefix = "The following files are clones (each clone set is divided):"
        return result
                .joinToString(prefix = prefix, separator = setSeparator) { files ->
                    files.joinToString(separator = System.lineSeparator()) { file ->
                        file.toPath().resolveToRealPath().toStringShortened(terminalDimension.width)
                    }
                }
    }
}