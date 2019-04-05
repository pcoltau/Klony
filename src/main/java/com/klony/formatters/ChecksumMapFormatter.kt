package com.klony.formatters

import com.klony.organiser.OrganisedResult

interface ChecksumMapFormatter {
    fun toString(result: OrganisedResult): String
}