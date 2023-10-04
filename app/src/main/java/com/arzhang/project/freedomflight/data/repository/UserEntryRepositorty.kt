package com.arzhang.project.freedomflight.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserEntryRepository(
    private val dataStore: DataStore<Preferences>
) {

    private companion object {
        val USER_SEARCH_ENTRY = stringPreferencesKey("user_search_entry")
    }

    suspend fun saveUserEntry(userEntry: String) {
        dataStore.edit {preferences->
            preferences[USER_SEARCH_ENTRY] = userEntry
        }
    }


    val userEntry: Flow<String> = dataStore.data
        .catch {
            if(it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else { throw it }
        }
        .map {preferences->
        preferences[USER_SEARCH_ENTRY] ?: ""
    }
}