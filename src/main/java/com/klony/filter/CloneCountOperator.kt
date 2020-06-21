package com.klony.filter

enum class CloneCountOperator(val stringOperator: String, val comparator: (Int) -> Boolean) {
    EQUAL("==", { it == 0 }),
    NOT_EQUAL("!=", { it != 0 }),
    GREATER_THAN(">", { it > 0 }),
    LESS_THAN("<", { it < 0 }),
    GREATER_THAN_OR_EQUAL(">=", { it >= 0 }),
    LESS_THAN_OR_EQUAL("<=", { it <= 0 });

    fun <T: Comparable<T>> compare(lhs: T, rhs: T) = comparator(lhs.compareTo(rhs))

    companion object {
        private val operatorMap = values().associateBy(CloneCountOperator::stringOperator)

        fun valueOfOrNull(string: String) = operatorMap[string]
    }

}