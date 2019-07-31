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
 * Returns a new [StringPref]
 */
fun KPrefs.string(key: String, defaultValue: String = DEFAULT_STRING): StringPref =
    pref(key, defaultValue, StringAdapter)

typealias StringPref = Pref<String>

private const val DEFAULT_STRING = ""

private object StringAdapter : Pref.Adapter<String> {
    override fun get(key: String, preferences: SharedPreferences): String =
        preferences.getString(key, DEFAULT_STRING)!!

    override fun set(key: String, value: String, editor: SharedPreferences.Editor) {
        editor.putString(key, value)
    }
}