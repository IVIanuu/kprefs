package com.ivianuu.kprefs.common

import android.content.SharedPreferences
import com.ivianuu.kprefs.KPrefs
import com.ivianuu.kprefs.Pref
import kotlin.reflect.KClass

/**
 * Returns a [Pref] which is represented a an [T] but persisted as an [String]
 */
inline fun <reified T> KPrefs.enumString(
    key: String,
    defaultValue: T
): Pref<T> where T : Enum<T>, T : PrefValueHolder<String> =
    enumString(key, defaultValue, T::class)

/**
 * Returns a [Pref] which is represented a an [T] but persisted as an [String]
 */
fun <T> KPrefs.enumString(
    key: String,
    defaultValue: T,
    type: KClass<T>
): Pref<T> where T : Enum<T>, T : PrefValueHolder<String> =
    pref(key, defaultValue, EnumStringPrefAdapter(type, defaultValue))

private class EnumStringPrefAdapter<T>(
    private val type: KClass<T>,
    private val defaultValue: T
) : Pref.Adapter<T> where T : Enum<T>, T : PrefValueHolder<String> {
    override fun get(key: String, preferences: SharedPreferences) =
        type.valueFor(preferences.getString(key, "")!!, defaultValue)

    override fun set(key: String, value: T, editor: SharedPreferences.Editor) {
        editor.putString(key, value.value)
    }
}