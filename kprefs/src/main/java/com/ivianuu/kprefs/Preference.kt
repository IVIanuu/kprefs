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

/**
 * Preference
 */
interface Preference<T> {

    /**
     * The key of this preference
     */
    val key: String

    /**
     * The default value of this preference
     */
    val defaultValue: T

    /**
     * The current value of this preference
     */
    var value: T

    /**
     * Whether or not a value for this preference is set or not
     */
    val isSet: Boolean

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
     * Converts [T]'s to [String]'s and vice versa
     */
    interface Converter<T> {

        /**
         * Takes [serialized] and returns a [T]
         */
        fun deserialize(serialized: String): T

        /**
         * Takes a [T] and returns a [String]
         */
        fun serialize(value: T): String

    }
}