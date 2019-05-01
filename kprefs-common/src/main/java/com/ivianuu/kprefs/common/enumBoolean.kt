package com.ivianuu.kprefs.common

import android.content.SharedPreferences
import com.ivianuu.kprefs.KPrefs
import com.ivianuu.kprefs.Pref
import kotlin.reflect.KClass

/**
 * Returns a [Pref] which is represented a an [T] but persisted as an [Boolean]
 */
inline fun <reified T> KPrefs.enumBoolean(
    key: String,
    defaultValue: T
): Pref<T> where T : Enum<T>, T : PrefValueHolder<Boolean> =
    enumBoolean(key, defaultValue, T::class)

/**
 * Returns a [Pref] which is represented a an [T] but persisted as an [Boolean]
 */
fun <T> KPrefs.enumBoolean(
    key: String,
    defaultValue: T,
    type: KClass<T>
): Pref<T> where T : Enum<T>, T : PrefValueHolder<Boolean> =
    pref(key, defaultValue, EnumBooleanPrefAdapter(type, defaultValue))

private class EnumBooleanPrefAdapter<T>(
    private val type: KClass<T>,
    private val defaultValue: T
) : Pref.Adapter<T> where T : Enum<T>, T : PrefValueHolder<Boolean> {
    override fun get(key: String, preferences: SharedPreferences): T =
        type.valueFor(preferences.getBoolean(key, false), defaultValue)

    override fun set(key: String, value: T, editor: SharedPreferences.Editor) {
        editor.putBoolean(key, value.value)
    }
}
