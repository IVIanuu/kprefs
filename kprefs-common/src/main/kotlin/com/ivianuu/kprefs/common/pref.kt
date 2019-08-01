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

import com.ivianuu.kprefs.Pref
import kotlin.reflect.KProperty

operator fun <T> Pref<T>.setValue(
    thisRef: Any,
    property: KProperty<*>,
    value: T
) {
    set(value)
}

operator fun <T> Pref<T>.getValue(
    thisRef: Any,
    property: KProperty<*>
): T = get()