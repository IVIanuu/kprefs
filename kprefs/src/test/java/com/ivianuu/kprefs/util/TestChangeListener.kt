package com.ivianuu.kprefs.util

class TestChangeListener : (String) -> Unit {
    val history get() = _history.toList()
    private val _history = mutableListOf<String>()
    val calls get() = _history.size
    override fun invoke(p1: String) {
        _history.add(p1)
    }
}