package com.ivianuu.kprefs.common

import android.content.SharedPreferences
import com.ivianuu.kprefs.CustomPref
import com.ivianuu.kprefs.KPrefs
import com.ivianuu.kprefs.Pref
import kotlin.reflect.KClass

interface PrefValueHolder<T> {
    val value: T
}

fun <T, V> KClass<T>.valueFor(
    value: V,
    defaultValue: T
): T where T : Enum<T>, T : PrefValueHolder<V> =
    java.enumConstants.firstOrNull { it.value == value } ?: defaultValue

fun <T, V> KClass<T>.valueForOrNull(value: V): T? where T : Enum<T>, T : PrefValueHolder<V> =
    java.enumConstants.firstOrNull { it.value == value }

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

inline fun <reified T> KPrefs.enumBoolean(
    key: String,
    defaultValue: T
): CustomPref<T> where T : Enum<T>, T : PrefValueHolder<Boolean> =
    enumBoolean(key, defaultValue, T::class)

fun <T> KPrefs.enumBoolean(
    key: String,
    defaultValue: T,
    type: KClass<T>
): CustomPref<T> where T : Enum<T>, T : PrefValueHolder<Boolean> =
    custom(key, defaultValue, EnumBooleanPrefAdapter(type, defaultValue))

private class EnumIntPrefAdapter<T>(
    private val type: KClass<T>,
    private val defaultValue: T
) : Pref.Adapter<T> where T : Enum<T>, T : PrefValueHolder<Int> {
    override fun get(key: String, preferences: SharedPreferences): T =
        type.valueFor(preferences.getInt(key, 0), defaultValue)

    override fun set(key: String, value: T, editor: SharedPreferences.Editor) {
        editor.putInt(key, value.value)
    }
}

inline fun <reified T> KPrefs.enumInt(
    key: String,
    defaultValue: T
): CustomPref<T> where T : Enum<T>, T : PrefValueHolder<Int> =
    enumInt(key, defaultValue, T::class)

fun <T> KPrefs.enumInt(
    key: String,
    defaultValue: T,
    type: KClass<T>
): CustomPref<T> where T : Enum<T>, T : PrefValueHolder<Int> =
    custom(key, defaultValue, EnumIntPrefAdapter(type, defaultValue))

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

inline fun <reified T> KPrefs.enumFloat(
    key: String,
    defaultValue: T
): CustomPref<T> where T : Enum<T>, T : PrefValueHolder<Float> =
    enumFloat(key, defaultValue, T::class)

fun <T> KPrefs.enumFloat(
    key: String,
    defaultValue: T,
    type: KClass<T>
): CustomPref<T> where T : Enum<T>, T : PrefValueHolder<Float> =
    custom(key, defaultValue, EnumFloatPrefAdapter(type, defaultValue))

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

inline fun <reified T> KPrefs.enumLong(
    key: String,
    defaultValue: T
): CustomPref<T> where T : Enum<T>, T : PrefValueHolder<Long> =
    enumLong(key, defaultValue, T::class)

fun <T> KPrefs.enumLong(
    key: String,
    defaultValue: T,
    type: KClass<T>
): CustomPref<T> where T : Enum<T>, T : PrefValueHolder<Long> =
    custom(key, defaultValue, EnumLongPrefAdapter(type, defaultValue))

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

inline fun <reified T> KPrefs.enumString(
    key: String,
    defaultValue: T
): CustomPref<T> where T : Enum<T>, T : PrefValueHolder<String> =
    enumString(key, defaultValue, T::class)

fun <T> KPrefs.enumString(
    key: String,
    defaultValue: T,
    type: KClass<T>
): CustomPref<T> where T : Enum<T>, T : PrefValueHolder<String> =
    custom(key, defaultValue, EnumStringPrefAdapter(type, defaultValue))

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

inline fun <reified T> KPrefs.enumStringSet(
    key: String,
    defaultValue: Set<T>
): CustomPref<Set<T>> where T : Enum<T>, T : PrefValueHolder<String> =
    enumStringSet(key, defaultValue, T::class)

fun <T> KPrefs.enumStringSet(
    key: String,
    defaultValue: Set<T>,
    type: KClass<T>
): CustomPref<Set<T>> where T : Enum<T>, T : PrefValueHolder<String> =
    custom(key, defaultValue, EnumStringSetPrefAdapter(type))