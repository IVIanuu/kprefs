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

/**
 * Actual implementation of a [Pref]
 */
internal class RealPref<T>(
    private val listeners: ChangeListeners,
    private val sharedPrefs: SharedPreferences,
    private val adapter: Pref.Adapter<T>,
    override val key: String,
    override val defaultValue: T
) : Pref<T> {

    override val isSet: Boolean get() = sharedPrefs.contains(key)

    private val changeListener: (String) -> Unit = { key ->
        if (this.key == key) {
            val value = get()
            changeListeners.toList().forEach { it(value) }
        }
    }

    private val changeListeners = mutableListOf<ChangeListener<T>>()
    private var listeningForChanges = false

    override fun get(): T = if (isSet) {
        adapter.get(key, sharedPrefs)
    } else {
        defaultValue
    }

    @SuppressLint("ApplySharedPref")
    override fun set(value: T) {
        sharedPrefs.edit().apply {
            adapter.set(key, value, this)
            if (KPrefsPlugins.useCommit) {
                commit()
            } else {
                apply()
            }
        }
    }

    @SuppressLint("ApplySharedPref")
    override fun delete() {
        sharedPrefs.edit().apply {
            remove(key)
            if (KPrefsPlugins.useCommit) {
                commit()
            } else {
                apply()
            }
        }
    }

    override fun addListener(listener: ChangeListener<T>) {
        if (changeListeners.contains(listener)) return

        changeListeners.add(listener)

        // dispatch the current value
        listener(get())

        if (!listeningForChanges) {
            listeners.addListener(changeListener)
            listeningForChanges = true
        }
    }

    override fun removeListener(listener: ChangeListener<T>) {
        changeListeners.remove(listener)
        if (changeListeners.isEmpty() && listeningForChanges) {
            listeners.removeListener(changeListener)
            listeningForChanges = false
        }
    }

}