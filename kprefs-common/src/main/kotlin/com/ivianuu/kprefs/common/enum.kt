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

package com.ivianuu.kprefs.common

import android.content.SharedPreferences
import com.ivianuu.kprefs.KPrefs
import com.ivianuu.kprefs.Pref
import kotlin.reflect.KClass

/**
 * Returns a new [EnumPref]
 */
fun <T : Enum<T>> KPrefs.enum(key: String, defaultValue: T, clazz: KClass<T>): EnumPref<T> =
    pref(key, defaultValue, EnumAdapter(clazz))

/**
 * Returns a new [EnumPref]
 */
inline fun <reified T : Enum<T>> KPrefs.enum(key: String, defaultValue: T): EnumPref<T> =
    enum(key, defaultValue, T::class)

typealias EnumPref<T> = Pref<T>

internal class EnumAdapter<T : Enum<T>>(private val enumClass: KClass<T>) : Pref.Adapter<T> {
    override fun get(key: String, preferences: SharedPreferences): T =
        java.lang.Enum.valueOf(enumClass.java, preferences.getString(key, "")!!)

    override fun set(key: String, value: T, editor: SharedPreferences.Editor) {
        editor.putString(key, value.name)
    }
}