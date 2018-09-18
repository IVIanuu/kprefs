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

package com.ivianuu.prefs.lifecycle

import androidx.lifecycle.Lifecycle
import com.ivianuu.prefs.PreferencesPlugins

private var _defaultRemoveEvent = Lifecycle.Event.ON_DESTROY
    set(value) {
        value.checkValid()
        field = value
    }

/**
 * The default remove event when addListener with a lifecycle owner is used
 */
var PreferencesPlugins.defaultRemoveEvent: Lifecycle.Event
    get() = _defaultRemoveEvent
    set(value) { _defaultRemoveEvent = value }