package com.klony.filter

data class CloneCountFilter(val value: Int, val operator: CloneCountOperator) {
    fun testAgainst(cloneCount: Int): Boolean {
        return operator.compare(cloneCount, value)
    }

    companion object {
        val default = CloneCountFilter(1, CloneCountOperator.GREATER_THAN)

        fun help(separator: String = ", "): String {
            return enumValues<CloneCountOperator>().joinToString(separator = separator) { "${it.stringOperator}X" }
        }

        fun fromString(string: String): CloneCountFilter? {
            val cloneCountOperator = try { CloneCountOperator.valueOfOrNull(string.substring(0, 2)) ?: CloneCountOperator.valueOfOrNull(string.substring(0, 1)) } catch (_: Exception) { null }
            if (cloneCountOperator != null) {
                val value = string.substring(cloneCountOperator.stringOperator.length).toIntOrNull()
                if (value != null && value >= 0) {
                    return CloneCountFilter(value, cloneCountOperator)
                }
            }
            return null
        }
    }

    override fun toString(): String {
        return "${operator.stringOperator}$value"
    }
}