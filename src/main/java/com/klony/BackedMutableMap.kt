package com.klony

/**
 * This class is an implementation of the Map interface with a protected mutable map that can be changed by implementing classes, but not from outside the class
 */
abstract class BackedMutableMap<K: Any, V: Any>: Map<K, V> {
    protected val backedMap = mutableMapOf<K, V>()

    override val entries get() = backedMap.entries
    override val keys get() = backedMap.keys
    override val size get() = backedMap.size
    override val values get() = backedMap.values
    override fun containsKey(key: K) = backedMap.containsKey(key)
    override fun containsValue(value: V) = backedMap.containsValue(value)
    override fun get(key: K) = backedMap[key]
    override fun isEmpty() = backedMap.isEmpty()
}
