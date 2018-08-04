package com.klony.utils

import com.klony.ChecksumMap
import com.klony.ChecksumMapFormatter

object Console {
    // Used to ensure that there is something to clear (something produced by this program)
    private var shouldClearConsoleLine = false

    fun erasePreviousLine() = if (shouldClearConsoleLine) System.out.print("".cursorUp().eraseInLine(EraseInLineMode.ENTIRE_LINE)) else Unit

    fun printLine(message: String) {
        println(message)
        shouldClearConsoleLine = true
    }

    fun printResult(checksumMap: ChecksumMap, formatter: ChecksumMapFormatter) {
        printLine(formatter.toString(checksumMap))
    }
}