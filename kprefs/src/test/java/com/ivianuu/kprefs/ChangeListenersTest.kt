package com.ivianuu.kprefs

import android.content.SharedPreferences
import com.ivianuu.kprefs.util.TestChangeListener
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.assertEquals
import org.junit.Test

class ChangeListenersTest {

    private val sharedPrefsChangeListeners =
        mutableListOf<SharedPreferences.OnSharedPreferenceChangeListener>()

    val sharedPrefs = mock<SharedPreferences> {
        on { registerOnSharedPreferenceChangeListener(any()) } doAnswer {
            sharedPrefsChangeListeners.add(it.getArgument(0))
            Unit
        }
        on { unregisterOnSharedPreferenceChangeListener(any()) } doAnswer {
            sharedPrefsChangeListeners.remove(it.getArgument(0))
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
    fun testAddSameListenerTwice() {
        val testChangeListener = TestChangeListener()

        changeListeners.addListener(testChangeListener)
        changeListeners.addListener(testChangeListener)

        sharedPrefsChangeListeners.forEach { it.onSharedPreferenceChanged(sharedPrefs, "key") }

        assertEquals(1, testChangeListener.calls)
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