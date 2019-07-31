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
 * Returns a new [LongPref]
 */
fun KPrefs.long(key: String, defaultValue: Long = DEFAULT_LONG): LongPref =
    pref(key, defaultValue, LongAdapter)

typealias LongPref = Pref<Long>

private const val DEFAULT_LONG = 0L

private object LongAdapter : Pref.Adapter<Long> {
    override fun get(key: String, preferences: SharedPreferences): Long =
        preferences.getLong(key, DEFAULT_LONG)

    override fun set(key: String, value: Long, editor: SharedPreferences.Editor) {
        editor.putLong(key, value)
    }
}