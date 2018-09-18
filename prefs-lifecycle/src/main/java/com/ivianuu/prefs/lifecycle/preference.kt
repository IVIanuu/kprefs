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

import androidx.lifecycle.GenericLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.ivianuu.prefs.ChangeListener
import com.ivianuu.prefs.Preference

/**
 * Adds the [listener] and automatically calls [Preference.removeListener] on [removeEvent]
 */
fun <T> Preference<T>.addListener(
    owner: LifecycleOwner,
    removeEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    listener: ChangeListener<T>
): ChangeListener<T> {
    removeEvent.checkValid()

    owner.lifecycle.addObserver(object : GenericLifecycleObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == removeEvent) {
                owner.lifecycle.removeObserver(this)
                removeListener(listener)
            }
        }
    })

    addListener(listener)

    return listener
}

/**
 * Returns a [LiveData] which contains the latest value of [this]
 */
val <T> Preference<T>.liveData: LiveData<T>
    get() = PreferenceLiveData(this)

private class PreferenceLiveData<T>(private val preference: Preference<T>) : LiveData<T>() {

    private val listener: (T) -> Unit = { value = it }

    override fun onActive() {
        super.onActive()
        preference.addListener(listener)
    }

    override fun onInactive() {
        super.onInactive()
        preference.removeListener(listener)
    }

}