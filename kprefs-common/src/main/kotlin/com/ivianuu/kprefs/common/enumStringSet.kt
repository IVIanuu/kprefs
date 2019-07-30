package com.ivianuu.kprefs.common

import android.content.SharedPreferences
import com.ivianuu.kprefs.KPrefs
import com.ivianuu.kprefs.Pref
import kotlin.reflect.KClass

/**
 * Returns a [Pref] which is represented a an [T] but persisted as an [Set] of [String]s
 */
inline fun <reified T> KPrefs.enumStringSet(
    key: String,
    defaultValue: Set<T>
): Pref<Set<T>> where T : Enum<T>, T : PrefValueHolder<String> =
    enumStringSet(key, defaultValue, T::class)

/**
 * Returns a [Pref] which is represented a an [T] but persisted as an [Set] of [String]s
 */
fun <T> KPrefs.enumStringSet(
    key: String,
    defaultValue: Set<T>,
    type: KClass<T>
): Pref<Set<T>> where T : Enum<T>, T : PrefValueHolder<String> =
    pref(key, defaultValue, EnumStringSetPrefAdapter(type))

private class EnumStringSetPrefAdapter<T>(
    private val type: KClass<T>
) : Pref.Adapter<Set<T>> where T : Enum<T>, T : PrefValueHolder<String> {
    override fun get(key: String, preferences: SharedPreferences) =
        preferences.getStringSet(key, emptySet())!!
            .mapNotNull { type.valueForOrNull(it) }
            .toSet()

    override fun set(key: String, value: Set<T>, editor: SharedPreferences.Editor) {
        editor.putStringSet(key, value.map { it.value }.toSet())
    }
}