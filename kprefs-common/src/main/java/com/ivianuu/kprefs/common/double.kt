package com.ivianuu.kprefs.common

import android.content.SharedPreferences
import com.ivianuu.kprefs.KPrefs
import com.ivianuu.kprefs.Pref

/**
 * Returns a new [DoublePref]
 */
fun KPrefs.double(key: String, defaultValue: Double = DEFAULT_DOUBLE): DoublePref =
    pref(key, defaultValue, DoubleAdapter)

typealias DoublePref = Pref<Double>

private const val DEFAULT_DOUBLE = 0.0

internal object DoubleAdapter : Pref.Adapter<Double> {
    override fun get(key: String, preferences: SharedPreferences): Double =
        preferences.getString(key, "")!!.toDouble()

    override fun set(key: String, value: Double, editor: SharedPreferences.Editor) {
        editor.putString(key, value.toString())
    }
}