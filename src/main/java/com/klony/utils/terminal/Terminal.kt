package com.klony.utils.terminal

import java.util.concurrent.TimeUnit

val ansiEscape = "\u001B["

enum class EraseInLineMode {
    FROM_CURSOR,
    TO_CURSOR,
    ENTIRE_LINE;

    val code = this.ordinal.toString()
}

interface SGRValue {
    fun value(): String
}

enum class Styles: SGRValue {
    RESET,
    BOLD,
    FAINT,
    ITALIC,
    UNDERLINE;

    override fun value(): String {
        return this.ordinal.toString()
    }
}

enum class Font: SGRValue {
    PRIMARY,
    ALTERNATIVE_1,
    ALTERNATIVE_2,
    ALTERNATIVE_3,
    ALTERNATIVE_4,
    ALTERNATIVE_5,
    ALTERNATIVE_6,
    ALTERNATIVE_7,
    ALTERNATIVE_8,
    ALTERNATIVE_9;

    override fun value(): String {
        return (this.ordinal + 10).toString()
    }
}

val SGRValue.code get() = this.value().plus("m")

object Terminal {
    data class Dimension(val height: Int, val width: Int)
    private fun Pair<Int, Int>.toDimension(): Dimension {
        return Terminal.Dimension(first, second)
    }
    private val defaultDimension = Dimension(24, 80)

    private fun getDimension(): Dimension? {
        return try {
            val process = ProcessBuilder("stty", "size")
                    .redirectInput(ProcessBuilder.Redirect.INHERIT)
                    .start()
            process.waitFor(1, TimeUnit.SECONDS)
            val reader = process.inputStream.reader().buffered()
            val output = reader.readLine()
            reader.close()
            process.destroy()
            parseOutput(output)
        } catch (t: Throwable) {
            null
        }
    }

    fun getDimensionOrDefault(): Dimension {
        return getDimension() ?: defaultDimension
    }

    private fun parseOutput(output: String?): Dimension? {
        if (output == null) return null

        return output
                .split(" ")
                .mapNotNull { it.toIntOrNull() }
                .takeIf { it.size == 2 }?.zipWithNext()?.first()?.toDimension() ?: return null
    }
}

/**
 * String Extensions
 */

fun String.cursorUp(lines: Int = 1): String = this.plus(ansiEscape).plus(lines).plus("A")

fun String.eraseInLine(mode: EraseInLineMode): String = this.plus(ansiEscape).plus(mode.code).plus("K")

fun String.style(style: Styles) = this.plus(ansiEscape).plus(style.code)

fun String.font(font: Font) = this.plus(ansiEscape).plus(font.code)