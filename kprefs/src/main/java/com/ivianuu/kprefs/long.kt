package com.ivianuu.kprefs

import android.content.SharedPreferences

/**
 * Returns a new [LongPref]
 */
fun KPrefs.long(key: String, defaultValue: Long = DEFAULT_LONG): LongPref =
    pref(key, defaultValue, LongAdapter)

typealias LongPref = Pref<Long>

private const val DEFAULT_LONG = 0L

private object LongAdapter : Pref.Adapter<Long> {
    override fun get(key: String, preferences: SharedPreferences): Long =
        preferences.getLong(key, DEFAULT_LONG)

    override fun set(key: String, value: Long, editor: SharedPreferences.Editor) {
        editor.putLong(key, value)
    }
}