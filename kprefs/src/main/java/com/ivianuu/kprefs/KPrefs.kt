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

import android.content.Context
import android.content.SharedPreferences
import kotlin.reflect.KClass

/**
 * KPrefs
 */
interface KPrefs {

    /**
     * The underlying shared preferences instance
     */
    val sharedPrefs: SharedPreferences

    /**
     * Returns a new [BooleanPref]
     */
    fun boolean(key: String, defaultValue: Boolean = DEFAULT_BOOLEAN): BooleanPref

    /**
     * Returns a new [EnumPref]
     */
    fun <T : Enum<T>> enum(key: String, defaultValue: T, clazz: KClass<T>): EnumPref<T>

    /**
     * Returns a new [FloatPref]
     */
    fun float(key: String, defaultValue: Float = DEFAULT_FLOAT): FloatPref

    /**
     * Returns a new [IntPref]
     */
    fun int(key: String, defaultValue: Int = DEFAULT_INT): IntPref

    /**
     * Returns a new [LongPref]
     */
    fun long(key: String, defaultValue: Long = DEFAULT_LONG): LongPref

    /**
     * Returns a new [StringPref]
     */
    fun string(key: String, defaultValue: String = DEFAULT_STRING): StringPref

    /**
     * Returns a new [StringSetPref]
     */
    fun stringSet(key: String, defaultValue: Set<String> = DEFAULT_STRING_SET): StringSetPref

    /**
     * Returns a new [CustomPref]
     */
    fun <T> custom(key: String, defaultValue: T, adapter: Pref.Adapter<T>): CustomPref<T>

    /**
     * Clears all values
     */
    fun clear()
}

/**
 * Returns a new [KPrefs] instance which uses [sharedPrefs] internally
 */
fun KPrefs(sharedPrefs: SharedPreferences): KPrefs = RealKPrefs(sharedPrefs)

/**
 * Returns a new [KPrefs] instance which uses the [SharedPreferences] with the [name] and [mode]
 */
fun KPrefs(
    context: Context,
    name: String = context.packageName + "_preferences",
    mode: Int = Context.MODE_PRIVATE
): KPrefs = KPrefs(context.getSharedPreferences(name, mode))

/**
 * Returns a new [EnumPref]
 */
inline fun <reified T : Enum<T>> KPrefs.enum(key: String, defaultValue: T): CustomPref<T> =
    enum(key, defaultValue, T::class)
