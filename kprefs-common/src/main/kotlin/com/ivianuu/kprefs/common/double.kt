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

/**
 * Returns a new [DoublePref]
 */
fun KPrefs.double(key: String, defaultValue: Double = DEFAULT_DOUBLE): DoublePref =
    pref(key, defaultValue, DoubleAdapter)

typealias DoublePref = Pref<Double>

private const val DEFAULT_DOUBLE = 0.0

private object DoubleAdapter : Pref.Adapter<Double> {
    override fun get(key: String, preferences: SharedPreferences): Double =
        preferences.getString(key, "")!!.toDouble()

    override fun set(key: String, value: Double, editor: SharedPreferences.Editor) {
        editor.putString(key, value.toString())
    }
}