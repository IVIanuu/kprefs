package com.ivianuu.kprefs

import android.content.SharedPreferences

/**
 * Returns a new [StringPref]
 */
fun KPrefs.string(key: String, defaultValue: String = DEFAULT_STRING): StringPref =
    pref(key, defaultValue, StringAdapter)

typealias StringPref = Pref<String>

private const val DEFAULT_STRING = ""

internal object StringAdapter : Pref.Adapter<String> {
    override fun get(key: String, preferences: SharedPreferences): String =
        preferences.getString(key, DEFAULT_STRING)!!

    override fun set(key: String, value: String, editor: SharedPreferences.Editor) {
        editor.putString(key, value)
    }
}