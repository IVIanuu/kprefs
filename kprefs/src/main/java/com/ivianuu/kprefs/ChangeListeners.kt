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

internal class ChangeListeners(private val sharedPrefs: SharedPreferences) {

    private val listeners = mutableListOf<(String) -> Unit>()

    private val sharedPrefsChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            listeners.toList().forEach { it(key) }
    }

    private var listenerRegistered = false

    fun addListener(listener: (String) -> Unit) {
        if (listeners.contains(listener)) return

        listeners.add(listener)

        if (!listenerRegistered) {
            sharedPrefs.registerOnSharedPreferenceChangeListener(sharedPrefsChangeListener)
            listenerRegistered = true
        }
    }

    fun removeListener(listener: (String) -> Unit) {
        listeners.remove(listener)
        if (listeners.isEmpty() && listenerRegistered) {
            sharedPrefs.registerOnSharedPreferenceChangeListener(sharedPrefsChangeListener)
            listenerRegistered = false
        }
    }

}