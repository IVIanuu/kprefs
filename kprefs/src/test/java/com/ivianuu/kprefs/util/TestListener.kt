package com.ivianuu.kprefs.util

/**
 * @author Manuel Wrage (IVIanuu)
 */
class TestListener<T> : (T) -> Unit {
    val history get() = _history.toList()
    private val _history = mutableListOf<T>()
    val calls get() = _history.size
    override fun invoke(p1: T) {
        _history.add(p1)
    }
}