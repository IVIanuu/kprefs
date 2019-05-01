package com.ivianuu.kprefs

import android.content.SharedPreferences

/**
 * Returns a new [FloatPref]
 */
fun KPrefs.float(key: String, defaultValue: Float = DEFAULT_FLOAT): FloatPref =
    pref(key, defaultValue, FloatAdapter)

typealias FloatPref = Pref<Float>

private const val DEFAULT_FLOAT = 0f

internal object FloatAdapter : Pref.Adapter<Float> {
    override fun get(key: String, preferences: SharedPreferences): Float =
        preferences.getFloat(key, DEFAULT_FLOAT)

    override fun set(key: String, value: Float, editor: SharedPreferences.Editor) {
        editor.putFloat(key, value)
    }
}