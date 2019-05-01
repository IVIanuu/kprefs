package com.ivianuu.kprefs.common

import android.content.SharedPreferences
import com.ivianuu.kprefs.KPrefs
import com.ivianuu.kprefs.Pref
import kotlin.reflect.KClass

/**
 * Returns a [Pref] which is represented a an [T] but persisted as an [Float]
 */
inline fun <reified T> KPrefs.enumFloat(
    key: String,
    defaultValue: T
): Pref<T> where T : Enum<T>, T : PrefValueHolder<Float> =
    enumFloat(key, defaultValue, T::class)

/**
 * Returns a [Pref] which is represented a an [T] but persisted as an [Float]
 */
fun <T> KPrefs.enumFloat(
    key: String,
    defaultValue: T,
    type: KClass<T>
): Pref<T> where T : Enum<T>, T : PrefValueHolder<Float> =
    pref(key, defaultValue, EnumFloatPrefAdapter(type, defaultValue))


private class EnumFloatPrefAdapter<T>(
    private val type: KClass<T>,
    private val defaultValue: T
) : Pref.Adapter<T> where T : Enum<T>, T : PrefValueHolder<Float> {
    override fun get(key: String, preferences: SharedPreferences) =
        type.valueFor(preferences.getFloat(key, 0f), defaultValue)

    override fun set(key: String, value: T, editor: SharedPreferences.Editor) {
        editor.putFloat(key, value.value)
    }
}