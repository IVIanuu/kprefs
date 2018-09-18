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

package com.ivianuu.kprefs.moshi

import com.ivianuu.kprefs.KPrefsPlugins
import com.ivianuu.kprefs.KSharedPreferences
import com.ivianuu.kprefs.Preference
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlin.reflect.KClass

/**
 * Returns a [CustomPreference] of type [T] which will be deserialized/serialized by [moshi]
 */
inline fun <reified T : Any> KSharedPreferences.customMoshi(
    key: String,
    defaultValue: T,
    moshi: Moshi = KPrefsPlugins.defaultMoshi
) = customMoshi(key, defaultValue, T::class, moshi)

/**
 * Returns a [CustomPreference] of type [T] which will be deserialized/serialized by [moshi]
 */
fun <T : Any> KSharedPreferences.customMoshi(
    key: String,
    defaultValue: T,
    clazz: KClass<T>,
    moshi: Moshi = KPrefsPlugins.defaultMoshi
) = custom(key, defaultValue, MoshiConverter(moshi.adapter(clazz.java)))

private class MoshiConverter<T>(private val adapter: JsonAdapter<T>) : Preference.Converter<T> {

    override fun deserialize(serialized: String) = adapter.fromJson(serialized)!!

    override fun serialize(value: T): String = adapter.toJson(value)
}