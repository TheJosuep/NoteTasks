package com.thejosuep.notetasks.domain.model

interface Repository {

    suspend fun putDarkThemeValue(key: String, value: Boolean)

    suspend fun getDarkThemeValue(key: String): Boolean?
}