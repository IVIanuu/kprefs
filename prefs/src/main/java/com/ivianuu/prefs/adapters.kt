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

package com.ivianuu.prefs

import android.content.SharedPreferences
import kotlin.reflect.KClass

internal object BooleanAdapter : RealPreference.Adapter<Boolean> {
    override fun get(key: String, preferences: SharedPreferences) = preferences.getBoolean(key, false)
    override fun set(key: String, value: Boolean, editor: SharedPreferences.Editor) {
        editor.putBoolean(key, value)
    }
}

internal class ConverterAdapter<T>(private val converter: Preference.Converter<T>) :
    RealPreference.Adapter<T> {
    override fun get(key: String, preferences: SharedPreferences) =
        converter.deserialize(preferences.getString(key, "")!!)

    override fun set(key: String, value: T, editor: SharedPreferences.Editor) {
        editor.putString(key, converter.serialize(value))
    }
}

internal class EnumAdapter<T : Enum<T>>(private val enumClass: KClass<T>) : RealPreference.Adapter<T> {
    override fun get(key: String, preferences: SharedPreferences): T =
        java.lang.Enum.valueOf(enumClass.java, preferences.getString(key, "")!!)

    override fun set(key: String, value: T, editor: SharedPreferences.Editor) {
        editor.putString(key, value.name)
    }
}

internal object FloatAdapter : RealPreference.Adapter<Float> {
    override fun get(key: String, preferences: SharedPreferences) =
        preferences.getFloat(key, 0f)
    override fun set(key: String, value: Float, editor: SharedPreferences.Editor) {
        editor.putFloat(key, value)
    }
}

internal object IntAdapter : RealPreference.Adapter<Int> {
    override fun get(key: String, preferences: SharedPreferences) = preferences.getInt(key, 0)
    override fun set(key: String, value: Int, editor: SharedPreferences.Editor) {
        editor.putInt(key, value)
    }
}

internal object LongAdapter : RealPreference.Adapter<Long> {
    override fun get(key: String, preferences: SharedPreferences) = preferences.getLong(key, 0L)
    override fun set(key: String, value: Long, editor: SharedPreferences.Editor) {
        editor.putLong(key, value)
    }
}

internal object StringAdapter : RealPreference.Adapter<String> {
    override fun get(key: String, preferences: SharedPreferences): String = preferences.getString(key, "")!!
    override fun set(key: String, value: String, editor: SharedPreferences.Editor) {
        editor.putString(key, value)
    }
}

internal object StringSetAdapter : RealPreference.Adapter<Set<String>> {
    override fun get(key: String, preferences: SharedPreferences): Set<String> = preferences.getStringSet(key, emptySet())!!
    override fun set(key: String, value: Set<String>, editor: SharedPreferences.Editor) {
        editor.putStringSet(key, value)
    }
}