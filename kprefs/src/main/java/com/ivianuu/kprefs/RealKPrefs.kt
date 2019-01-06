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
import android.content.SharedPreferences
import kotlin.reflect.KClass

internal class RealKPrefs(override val sharedPrefs: SharedPreferences) : KPrefs {

    private val listeners = ChangeListeners(sharedPrefs)

    override fun boolean(key: String, defaultValue: Boolean): Pref<Boolean> =
        RealPref(listeners, sharedPrefs, BooleanAdapter, key, defaultValue)

    override fun <T : Enum<T>> enum(key: String, defaultValue: T, clazz: KClass<T>): CustomPref<T> =
        RealPref(listeners, sharedPrefs, EnumAdapter(clazz), key, defaultValue)

    override fun float(key: String, defaultValue: Float): Pref<Float> =
        RealPref(listeners, sharedPrefs, FloatAdapter, key, defaultValue)

    override fun int(key: String, defaultValue: Int): Pref<Int> =
        RealPref(listeners, sharedPrefs, IntAdapter, key, defaultValue)

    override fun long(key: String, defaultValue: Long): Pref<Long> =
        RealPref(listeners, sharedPrefs, LongAdapter, key, defaultValue)

    override fun string(key: String, defaultValue: String): Pref<String> =
        RealPref(listeners, sharedPrefs, StringAdapter, key, defaultValue)

    override fun stringSet(key: String, defaultValue: Set<String>): StringSetPref =
        RealPref(listeners, sharedPrefs, StringSetAdapter, key, defaultValue)

    /**
     * Returns a new [CustomPref]
     */
    override fun <T> custom(key: String, defaultValue: T, adapter: Pref.Adapter<T>): CustomPref<T> =
        RealPref(listeners, sharedPrefs, adapter, key, defaultValue)

    @SuppressLint("ApplySharedPref")
    override fun clear() {
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
    }
}