package com.thejosuep.notetasks.data.source.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private const val PREFERENCES_NAME = "preferences_name"

private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

// Preferences' implementation
class PreferencesImp @Inject constructor(
    private val context: Context
): Preferences {
    override suspend fun putDarkThemeValue(key: String, value: Boolean) {
        // Sets the preference key by the given key
        val preferenceKey = booleanPreferencesKey(key)

        // Updates the data value
        context.dataStore.edit { pref ->
            pref[preferenceKey] = value
        }
    }

    override suspend fun getDarkThemeValue(key: String): Boolean? {
        return try {
            // Sets the preference key by the given key
            val preferenceKey = booleanPreferencesKey(key)

            // Gets the preference in the first value
            val preference = context.dataStore.data.first()

            preference[preferenceKey] //Returned value
        } catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

}