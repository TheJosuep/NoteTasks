package com.thejosuep.notetasks.domain

interface Repository {

    suspend fun putDarkTheme(key: String, value: Boolean)

    suspend fun getDarkTheme(key: String): Boolean?
}