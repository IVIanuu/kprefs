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

package com.ivianuu.kprefs.livedata

import androidx.lifecycle.LiveData
import com.ivianuu.kprefs.ChangeListener
import com.ivianuu.kprefs.Pref

/**
 * Returns a [LiveData] which contains the latest value of [this]
 */
fun <T> Pref<T>.liveData(): LiveData<T

private class PreferenceLiveData<T>(private val pref: Pref<T>) : LiveData<T>() {

    private val listener: ChangeListener<T> = { value = it }

    override fun onActive() {
        super.onActive()
        pref.addListener(listener)
    }

    override fun onInactive() {
        super.onInactive()
        pref.removeListener(listener)
    }

}