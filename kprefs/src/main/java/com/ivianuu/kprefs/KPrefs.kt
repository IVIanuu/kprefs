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

/**
 * KPrefs
 */
interface KPrefs {

    /**
     * Returns a new [Pref]
     */
    fun <T> pref(key: String, defaultValue: T, adapter: Pref.Adapter<T>): Pref<T>

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