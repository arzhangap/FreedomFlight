package com.arzhang.project.freedomflight

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.arzhang.project.freedomflight.data.AppContainer
import com.arzhang.project.freedomflight.data.AppDataContainer
import com.arzhang.project.freedomflight.data.repository.UserEntryRepository

private const val USER_ENTRY_NAME = "user_entry"
private val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = USER_ENTRY_NAME)

class FreedomFlightApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer
    lateinit var userEntryRepository: UserEntryRepository

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        userEntryRepository = UserEntryRepository(dataStore)
    }
}
