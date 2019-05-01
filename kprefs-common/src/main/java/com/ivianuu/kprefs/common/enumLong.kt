package com.ivianuu.kprefs.common

import android.content.SharedPreferences
import com.ivianuu.kprefs.KPrefs
import com.ivianuu.kprefs.Pref
import kotlin.reflect.KClass

/**
 * Returns a [Pref] which is represented a an [T] but persisted as an [Long]
 */
inline fun <reified T> KPrefs.enumLong(
    key: String,
    defaultValue: T
): Pref<T> where T : Enum<T>, T : PrefValueHolder<Long> =
    enumLong(key, defaultValue, T::class)

/**
 * Returns a [Pref] which is represented a an [T] but persisted as an [Long]
 */
fun <T> KPrefs.enumLong(
    key: String,
    defaultValue: T,
    type: KClass<T>
): Pref<T> where T : Enum<T>, T : PrefValueHolder<Long> =
    pref(key, defaultValue, EnumLongPrefAdapter(type, defaultValue))

private class EnumLongPrefAdapter<T>(
    private val type: KClass<T>,
    private val defaultValue: T
) : Pref.Adapter<T> where T : Enum<T>, T : PrefValueHolder<Long> {
    override fun get(key: String, preferences: SharedPreferences): T =
        type.valueFor(preferences.getLong(key, 0L), defaultValue)

    override fun set(key: String, value: T, editor: SharedPreferences.Editor) {
        editor.putLong(key, value.value)
    }
}
