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
import com.ivianuu.kprefs.util.TestChangeListener
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Test

class ChangeListenersTest {

    private val sharedPrefsChangeListeners =
        mutableListOf<SharedPreferences.OnSharedPreferenceChangeListener>()

    val sharedPrefs = mock<SharedPreferences> {
        on { registerOnSharedPreferenceChangeListener(any()) } doAnswer {
            sharedPrefsChangeListeners += it.getArgument<SharedPreferences.OnSharedPreferenceChangeListener>(
                0
            )
            Unit
        }
        on { unregisterOnSharedPreferenceChangeListener(any()) } doAnswer {
            sharedPrefsChangeListeners -= it.getArgument<SharedPreferences.OnSharedPreferenceChangeListener>(
                0
            )
            Unit
        }
    }

    private val changeListeners = ChangeListeners(sharedPrefs)

    @Test
    fun testInternalListenerRegistration() {
        val listener1 = TestChangeListener()
        val listener2 = TestChangeListener()

        changeListeners.addListener(listener1)
        changeListeners.removeListener(listener1)

        changeListeners.addListener(listener1)
        changeListeners.addListener(listener2)
        changeListeners.removeListener(listener2)
        changeListeners.removeListener(listener1)

        verify(sharedPrefs, times(2)).registerOnSharedPreferenceChangeListener(any())
        verify(sharedPrefs, times(2)).unregisterOnSharedPreferenceChangeListener(any())
    }

    @Test
    fun testNotifyingListeners() {
        val testChangeListener = TestChangeListener()

        changeListeners.addListener(testChangeListener)
        assertEquals(0, testChangeListener.calls)

        sharedPrefsChangeListeners.forEach { it.onSharedPreferenceChanged(sharedPrefs, "key1") }
        assertEquals(testChangeListener.history[0], "key1")
        assertEquals(1, testChangeListener.calls)

        sharedPrefsChangeListeners.forEach { it.onSharedPreferenceChanged(sharedPrefs, "key2") }
        assertEquals(testChangeListener.history[1], "key2")
        assertEquals(2, testChangeListener.calls)

        sharedPrefsChangeListeners.forEach { it.onSharedPreferenceChanged(sharedPrefs, "key3") }
        assertEquals(testChangeListener.history[2], "key3")
        assertEquals(3, testChangeListener.calls)
    }

    @Test
    fun testRemoveListener() {
        val testChangeListener = TestChangeListener()

        changeListeners.addListener(testChangeListener)
        changeListeners.removeListener(testChangeListener)

        sharedPrefsChangeListeners.forEach { it.onSharedPreferenceChanged(sharedPrefs, "key") }

        assertEquals(0, testChangeListener.calls)
    }
}