package com.ivianuu.kprefs

import android.content.SharedPreferences

/**
 * Returns a new [IntPref]
 */
fun KPrefs.int(key: String, defaultValue: Int = DEFAULT_INT): IntPref =
    pref(key, defaultValue, IntAdapter)

typealias IntPref = Pref<Int>

private const val DEFAULT_INT = 0

internal object IntAdapter : Pref.Adapter<Int> {
    override fun get(key: String, preferences: SharedPreferences): Int =
        preferences.getInt(key, DEFAULT_INT)

    override fun set(key: String, value: Int, editor: SharedPreferences.Editor) {
        editor.putInt(key, value)
    }
}