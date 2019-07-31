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
 * Returns a new [FloatPref]
 */
fun KPrefs.float(key: String, defaultValue: Float = DEFAULT_FLOAT): FloatPref =
    pref(key, defaultValue, FloatAdapter)

typealias FloatPref = Pref<Float>

private const val DEFAULT_FLOAT = 0f

private object FloatAdapter : Pref.Adapter<Float> {
    override fun get(key: String, preferences: SharedPreferences): Float =
        preferences.getFloat(key, DEFAULT_FLOAT)

    override fun set(key: String, value: Float, editor: SharedPreferences.Editor) {
        editor.putFloat(key, value)
    }
}