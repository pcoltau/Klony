package com.klony.filter

import com.pcoltau.guard.guard

data class CloneCountFilter(val value: Int, val operator: CloneCountOperator) {
    fun testAgainst(cloneCount: Int) = operator.compare(cloneCount, value)

    companion object {
        val default = CloneCountFilter(1, CloneCountOperator.GREATER_THAN)

        fun help(separator: String = ", ") =
                enumValues<CloneCountOperator>().joinToString(separator = separator) { "${it.stringOperator}X" }

        fun fromString(string: String): CloneCountFilter? {
            val cloneCountOperator = try {
                CloneCountOperator.valueOfOrNull(string.substring(0, 2))
                        ?: CloneCountOperator.valueOfOrNull(string.substring(0, 1))
            } catch (_: Exception) {
                null
            }
            val operator = guard(cloneCountOperator).otherwise {
                return null
            }

            val value = guard(string.substring(operator.stringOperator.length).toIntOrNull()).where { it >= 0 }.otherwise {
                return null
            }

            return CloneCountFilter(value, operator)
        }
    }

    override fun toString() = "${operator.stringOperator}$value"
}