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
 * KSharedPreferences
 */
class KSharedPreferences private constructor(private val sharedPreferences: SharedPreferences) {

    private val listeners = ChangeListeners(sharedPreferences)

    /**
     * Returns a new [BooleanPreference]
     */
    fun boolean(key: String, defaultValue: Boolean = DEFAULT_BOOLEAN): BooleanPreference =
        RealPreference(listeners, sharedPreferences, BooleanAdapter, key, defaultValue)

    /**
     * Returns a new [EnumPreference]
     */
    inline fun <reified T : Enum<T>> enum(key: String, defaultValue: T) =
            enum(key, defaultValue, T::class)

    /**
     * Returns a new [EnumPreference]
     */
    fun <T : Enum<T>> enum(key: String, defaultValue: T, clazz: KClass<T>): EnumPreference<T> =
            RealPreference(listeners, sharedPreferences, EnumAdapter(clazz), key, defaultValue)

    /**
     * Returns a new [FloatPreference]
     */
    fun float(key: String, defaultValue: Float = DEFAULT_FLOAT): FloatPreference =
        RealPreference(listeners, sharedPreferences, FloatAdapter, key, defaultValue)

    /**
     * Returns a new [IntPreference]
     */
    fun int(key: String, defaultValue: Int = DEFAULT_INT): IntPreference =
        RealPreference(listeners, sharedPreferences, IntAdapter, key, defaultValue)

    /**
     * Returns a new [LongPreference]
     */
    fun long(key: String, defaultValue: Long = DEFAULT_LONG): LongPreference =
        RealPreference(listeners, sharedPreferences, LongAdapter, key, defaultValue)

    /**
     * Returns a new [StringPreference]
     */
    fun string(key: String, defaultValue: String = DEFAULT_STRING): StringPreference =
        RealPreference(listeners, sharedPreferences, StringAdapter, key, defaultValue)

    /**
     * Returns a new [StringSetPreference]
     */
    fun stringSet(key: String, defaultValue: Set<String> = DEFAULT_STRING_SET): StringSetPreference =
        RealPreference(listeners, sharedPreferences, StringSetAdapter, key, defaultValue)

    /**
     * Returns a new [CustomPreference]
     */
    fun <T> custom(key: String, defaultValue: T, converter: Preference.Converter<T>): CustomPreference<T> =
        RealPreference(listeners, sharedPreferences, ConverterAdapter(converter), key, defaultValue)

    @SuppressLint("ApplySharedPref")
    fun clear() {
        sharedPreferences.edit().clear().apply {
            if (KPrefsPlugins.useCommit) {
                commit()
            } else {
                apply()
            }
        }
    }

    companion object {
        private const val DEFAULT_BOOLEAN = false
        private const val DEFAULT_FLOAT = 0f
        private const val DEFAULT_INT = 0
        private const val DEFAULT_LONG = 0L
        private const val DEFAULT_STRING = ""
        private val DEFAULT_STRING_SET = emptySet<String>()

        /**
         * Returns a new [KSharedPreferences] instance which uses [sharedPreferences] internally
         */
        operator fun invoke(sharedPreferences: SharedPreferences) =
            KSharedPreferences(sharedPreferences)

        /**
         * Returns a new [KSharedPreferences] instance which uses the default [SharedPreferences]
         */
        operator fun invoke(context: Context) =
                invoke(context.getSharedPreferences(
                    context.packageName + "_preferences", Context.MODE_PRIVATE))

    }
}