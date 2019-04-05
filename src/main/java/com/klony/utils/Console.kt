package com.klony.utils

import com.klony.formatters.ChecksumMapFormatter
import com.klony.organiser.OrganisedResult
import com.klony.utils.terminal.EraseInLineMode
import com.klony.utils.terminal.cursorUp
import com.klony.utils.terminal.eraseInLine

object Console {
    // Used to ensure that there is something to clear (something produced by this program)
    private var shouldClearConsoleLine = false

    fun erasePreviousLine() = if (shouldClearConsoleLine) System.out.print("".cursorUp().eraseInLine(EraseInLineMode.ENTIRE_LINE)) else Unit

    fun printLine(message: String) {
        println(message)
        shouldClearConsoleLine = true
    }

    fun printResult(result: OrganisedResult, formatter: ChecksumMapFormatter) {
        printLine(formatter.toString(result))
    }
}