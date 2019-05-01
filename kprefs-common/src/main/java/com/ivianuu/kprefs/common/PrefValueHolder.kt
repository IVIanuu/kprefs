package com.ivianuu.kprefs.common

import kotlin.reflect.KClass

interface PrefValueHolder<T> {
    val value: T
}

fun <T, V> KClass<T>.valueFor(
    value: V,
    defaultValue: T
): T where T : Enum<T>, T : PrefValueHolder<V> =
    java.enumConstants.firstOrNull { it.value == value } ?: defaultValue

fun <T, V> KClass<T>.valueForOrNull(value: V): T? where T : Enum<T>, T : PrefValueHolder<V> =
    java.enumConstants.firstOrNull { it.value == value }