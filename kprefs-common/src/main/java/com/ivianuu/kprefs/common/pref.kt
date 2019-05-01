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