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

import android.content.SharedPreferences

/**
 * Returns a new [BooleanPref]
 */
fun KPrefs.boolean(key: String, defaultValue: Boolean = DEFAULT_BOOLEAN): BooleanPref =
    pref(key, defaultValue, BooleanAdapter)

typealias BooleanPref = Pref<Boolean>

private const val DEFAULT_BOOLEAN = false

private object BooleanAdapter : Pref.Adapter<Boolean> {
    override fun get(key: String, preferences: SharedPreferences): Boolean =
        preferences.getBoolean(key, DEFAULT_BOOLEAN)

    override fun set(key: String, value: Boolean, editor: SharedPreferences.Editor) {
        editor.putBoolean(key, value)
    }
}