package com.thejosuep.notetasks.data.source.preferences

interface Preferences {

    suspend fun putDarkThemeValue(key: String, value: Boolean)

    suspend fun getDarkThemeValue(key: String): Boolean?
}