package com.ivianuu.kprefs

import android.content.SharedPreferences

/**
 * Returns a new [StringSetPref]
 */
fun KPrefs.stringSet(key: String, defaultValue: Set<String> = DEFAULT_STRING_SET): StringSetPref =
    pref(key, defaultValue, StringSetAdapter)

typealias StringSetPref = Pref<Set<String>>

private val DEFAULT_STRING_SET = emptySet<String>()

private object StringSetAdapter : Pref.Adapter<Set<String>> {
    override fun get(key: String, preferences: SharedPreferences): Set<String> =
        preferences.getStringSet(key, DEFAULT_STRING_SET)!!

    override fun set(key: String, value: Set<String>, editor: SharedPreferences.Editor) {
        editor.putStringSet(key, value)
    }
}