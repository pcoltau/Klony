package com.klony.utils

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

/**
 * String Extensions
 */

fun String.cursorUp(lines: Int = 1): String = this.plus(ansiEscape).plus(lines).plus("A")

fun String.eraseInLine(mode: EraseInLineMode): String = this.plus(ansiEscape).plus(mode.code).plus("K")

fun String.style(style: Styles) = this.plus(ansiEscape).plus(style.code)

fun String.font(font: Font) = this.plus(ansiEscape).plus(font.code)