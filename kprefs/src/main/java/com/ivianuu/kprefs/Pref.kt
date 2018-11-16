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
 * Pref
 */
interface Pref<T> {

    /**
     * The key of this preference
     */
    val key: String

    /**
     * The default value of this preference
     */
    val defaultValue: T

    /**
     * Whether or not a value for this preference is set or not
     */
    val isSet: Boolean

    /**
     * Returns the current value
     */
    fun get(): T

    /**
     * Sets the value of this [Pref] to [value]
     */
    fun set(value: T)

    /**
     * Deletes the value for this preferences
     */
    fun delete()

    /**
     * Adds the [listener] which will be notified on changes
     * Returns the same [listener] for convenience
     */
    fun addListener(listener: ChangeListener<T>): ChangeListener<T>

    /**
     * Removes the [listener]
     */
    fun removeListener(listener: ChangeListener<T>)

    /**
     * Reads and writes values of [T] to [SharedPreferences]
     */
    interface Adapter<T> {

        /**
         * Reads the value
         */
        fun get(key: String, preferences: SharedPreferences): T

        /**
         * Writes the value
         */
        fun set(key: String, value: T, editor: SharedPreferences.Editor)

    }
}