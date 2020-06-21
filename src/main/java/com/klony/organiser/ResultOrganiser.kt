package com.klony.organiser

import com.klony.Args
import com.klony.ChecksumMap

object ResultOrganizer {

    fun organise(checksumMap: ChecksumMap, args: Args): OrganisedResult = checksumMap.values
            .filter { args.cloneCountFilter.testAgainst(it.count()) }
            .map { files ->
                args.cloneSorting.sortFiles(files)
                        .filterIndexed { index, _ ->
                            val limit = args.cloneCountLimit ?: files.size.toLong()
                            val offset = args.cloneCountOffset
                            index in offset until limit
                        }
            }
}