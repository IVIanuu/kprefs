package com.ivianuu.kprefs

import android.content.SharedPreferences

/**
 * Returns a new [BooleanPref]
 */
fun KPrefs.boolean(key: String, defaultValue: Boolean = DEFAULT_BOOLEAN): BooleanPref =
    pref(key, defaultValue, BooleanAdapter)

typealias BooleanPref = Pref<Boolean>

private const val DEFAULT_BOOLEAN = false

internal object BooleanAdapter : Pref.Adapter<Boolean> {
    override fun get(key: String, preferences: SharedPreferences): Boolean =
        preferences.getBoolean(key, DEFAULT_BOOLEAN)

    override fun set(key: String, value: Boolean, editor: SharedPreferences.Editor) {
        editor.putBoolean(key, value)
    }
}