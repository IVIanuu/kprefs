/*
 * Copyright 2018 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.kprefs

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import kotlin.reflect.KClass

/**
 * KPrefs
 */
class KPrefs private constructor(private val sharedPrefs: SharedPreferences) {

    private val listeners = ChangeListeners(sharedPrefs)

    /**
     * Returns a new [BooleanPref]
     */
    fun boolean(key: String, defaultValue: Boolean = DEFAULT_BOOLEAN): BooleanPref =
        RealPref(listeners, sharedPrefs, BooleanAdapter, key, defaultValue)

    /**
     * Returns a new [EnumPref]
     */
    inline fun <reified T : Enum<T>> enum(key: String, defaultValue: T) =
            enum(key, defaultValue, T::class)

    /**
     * Returns a new [EnumPref]
     */
    fun <T : Enum<T>> enum(key: String, defaultValue: T, clazz: KClass<T>): EnumPref<T> =
        RealPref(listeners, sharedPrefs, EnumAdapter(clazz), key, defaultValue)

    /**
     * Returns a new [FloatPref]
     */
    fun float(key: String, defaultValue: Float = DEFAULT_FLOAT): FloatPref =
        RealPref(listeners, sharedPrefs, FloatAdapter, key, defaultValue)

    /**
     * Returns a new [IntPref]
     */
    fun int(key: String, defaultValue: Int = DEFAULT_INT): IntPref =
        RealPref(listeners, sharedPrefs, IntAdapter, key, defaultValue)

    /**
     * Returns a new [LongPref]
     */
    fun long(key: String, defaultValue: Long = DEFAULT_LONG): LongPref =
        RealPref(listeners, sharedPrefs, LongAdapter, key, defaultValue)

    /**
     * Returns a new [StringPref]
     */
    fun string(key: String, defaultValue: String = DEFAULT_STRING): StringPref =
        RealPref(listeners, sharedPrefs, StringAdapter, key, defaultValue)

    /**
     * Returns a new [StringSetPref]
     */
    fun stringSet(key: String, defaultValue: Set<String> = DEFAULT_STRING_SET): StringSetPref =
        RealPref(listeners, sharedPrefs, StringSetAdapter, key, defaultValue)

    /**
     * Returns a new [CustomPref]
     */
    fun <T> custom(key: String, defaultValue: T, adapter: Pref.Adapter<T>): CustomPref<T> =
        RealPref(listeners, sharedPrefs, adapter, key, defaultValue)

    @SuppressLint("ApplySharedPref")
    fun clear() {
        sharedPrefs.edit().clear().apply {
            if (KPrefsPlugins.useCommit) {
                commit()
            } else {
                apply()
            }
        }
    }

    companion object {
        internal const val DEFAULT_BOOLEAN = false
        internal const val DEFAULT_FLOAT = 0f
        internal const val DEFAULT_INT = 0
        internal const val DEFAULT_LONG = 0L
        internal const val DEFAULT_STRING = ""
        internal val DEFAULT_STRING_SET = emptySet<String>()

        /**
         * Returns a new [KPrefs] instance which uses [sharedPrefs] internally
         */
        operator fun invoke(sharedPrefs: SharedPreferences) =
            KPrefs(sharedPrefs)

        /**
         * Returns a new [KPrefs] instance which uses the default [SharedPreferences]
         */
        operator fun invoke(context: Context) =
                invoke(context.getSharedPreferences(
                    context.packageName + "_preferences", Context.MODE_PRIVATE))

    }
}