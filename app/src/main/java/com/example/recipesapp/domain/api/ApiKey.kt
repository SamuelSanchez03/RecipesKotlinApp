package com.example.recipesapp.domain.api

import kotlin.reflect.KProperty

data class ApiKey(private val key: String) {
	operator fun getValue(thisRef: Any?, property: KProperty<*>): String = key
}