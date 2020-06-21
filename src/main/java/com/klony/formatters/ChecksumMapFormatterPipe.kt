package com.klony.formatters

import com.klony.organiser.OrganisedResult
import com.klony.utils.resolveToRealPath

class ChecksumMapFormatterPipe : ChecksumMapFormatter {
    override fun toString(result: OrganisedResult) = result
            .joinToString(separator = System.lineSeparator()) { files ->
                files.joinToString(separator = System.lineSeparator()) { file ->
                    file.toPath().resolveToRealPath().toString()
                }
            }
}