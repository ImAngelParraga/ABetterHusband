package com.example.abetterhusbandv2.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.abetterhusbandv2.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private val TAG = "UserPreferencesRepo"

    private object PreferencesKeys {
        val IS_WIFE = booleanPreferencesKey("is_wife")
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            mapUserPreferences(preferences)
        }

    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        val isWife = preferences[PreferencesKeys.IS_WIFE] ?: false
        return UserPreferences(isWife)
    }

    suspend fun updateIsWife(isWife: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_WIFE] = isWife
        }
    }
}