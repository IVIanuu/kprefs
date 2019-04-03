package com.ivianuu.kprefs

import android.content.SharedPreferences
import com.ivianuu.kprefs.util.TestListener
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RealPrefTest {

    private val sharedPrefsEditor = mock<SharedPreferences.Editor>()
    private val sharedPrefs = mock<SharedPreferences> {
        on { edit() } doReturn sharedPrefsEditor
    }

    private val addedChangeListeners = mutableListOf<(String) -> Unit>()
    private val changeListeners = mock<ChangeListeners> {
        on { addListener(any()) } doAnswer {
            addedChangeListeners.add(it.getArgument(0))
            Unit
        }
        on { removeListener(any()) } doAnswer {
            addedChangeListeners.remove(it.getArgument(0))
            Unit
        }
    }

    private val pref = RealPref(
        changeListeners,
        sharedPrefs,
        StringAdapter,
        PREF_KEY,
        PREF_DEFAULT_VALUE
    )

    @Test
    fun testIsSet() {
        whenever(sharedPrefs.contains(PREF_KEY))
            .doReturn(true)
        assertTrue(pref.isSet)

        whenever(sharedPrefs.contains(PREF_KEY))
            .doReturn(false)
        assertFalse(pref.isSet)
    }

    @Test
    fun testDefaultValue() {
        whenever(sharedPrefs.contains(PREF_KEY))
            .doReturn(false)
        assertEquals(PREF_DEFAULT_VALUE, pref.get())
    }

    @Test
    fun testGet() {
        whenever(sharedPrefs.contains(PREF_KEY))
            .doReturn(true)

        val expectedValue = "value"

        whenever(sharedPrefs.getString(eq(PREF_KEY), any()))
            .doReturn(expectedValue)

        assertEquals("value", pref.get())
    }

    @Test
    fun testSet() {
        whenever(sharedPrefsEditor.putString(PREF_KEY, "value"))
            .thenReturn(sharedPrefsEditor)

        pref.set("value")

        verify(sharedPrefsEditor.putString(PREF_KEY, "value")).apply()
    }

    @Test
    fun testDelete() {
        whenever(sharedPrefsEditor.remove(PREF_KEY))
            .thenReturn(sharedPrefsEditor)

        pref.delete()

        verify(sharedPrefsEditor.remove(PREF_KEY)).apply()
    }

    @Test
    fun testSetUseCommit() {
        KPrefsPlugins.useCommit = true

        whenever(sharedPrefsEditor.putString(PREF_KEY, "value"))
            .thenReturn(sharedPrefsEditor)

        pref.set("value")

        verify(sharedPrefsEditor.putString(PREF_KEY, "value")).commit()

        KPrefsPlugins.useCommit = false
    }

    @Test
    fun testDeleteUseCommit() {
        whenever(sharedPrefsEditor.remove(PREF_KEY))
            .thenReturn(sharedPrefsEditor)

        pref.delete()

        verify(sharedPrefsEditor.remove(PREF_KEY)).apply()
    }

    @Test
    fun testChangeListenerRegistration() {
        val listener1 = TestListener<String>()
        val listener2 = TestListener<String>()

        pref.addListener(listener1)
        pref.removeListener(listener1)

        pref.addListener(listener1)
        pref.addListener(listener2)
        pref.removeListener(listener2)
        pref.removeListener(listener1)

        verify(changeListeners, times(2)).addListener(any())
        verify(changeListeners, times(2)).removeListener(any())
    }

    @Test
    fun testNotifyingListeners() {
        whenever(sharedPrefs.contains(PREF_KEY))
            .doReturn(false)

        val testListener = TestListener<String>()

        // initial value
        pref.addListener(testListener)
        assertEquals(1, testListener.calls)
        assertEquals(PREF_DEFAULT_VALUE, testListener.history[0])

        // same key
        whenever(sharedPrefs.contains(PREF_KEY))
            .doReturn(true)
        whenever(sharedPrefs.getString(eq(PREF_KEY), any()))
            .doReturn("value")

        addedChangeListeners.forEach { it(PREF_KEY) }
        assertEquals(2, testListener.calls)
        assertEquals("value", testListener.history[1])

        // other key
        addedChangeListeners.forEach { it("other_key") }
        assertEquals(2, testListener.calls)
    }

    @Test
    fun testRemoveListener() {
        val testListener = TestListener<String>()

        pref.addListener(testListener)
        pref.removeListener(testListener)

        addedChangeListeners.forEach { it(PREF_KEY) }

        assertEquals(1, testListener.calls)
    }

    private companion object {
        private const val PREF_KEY = "key"
        private const val PREF_DEFAULT_VALUE = "default"
    }
}