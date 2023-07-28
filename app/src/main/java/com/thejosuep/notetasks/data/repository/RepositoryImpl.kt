package com.thejosuep.notetasks.data.repository

import com.thejosuep.notetasks.data.source.preferences.Preferences
import com.thejosuep.notetasks.domain.Repository
import javax.inject.Inject

// Repository's implementation
class RepositoryImpl @Inject constructor(
    private val preferences: Preferences
): Repository {
    override suspend fun putDarkThemeValue(key: String, value: Boolean) {
        preferences.putDarkThemeValue(key, value)
    }

    override suspend fun getDarkThemeValue(key: String): Boolean? {
        return preferences.getDarkThemeValue(key)
    }

}