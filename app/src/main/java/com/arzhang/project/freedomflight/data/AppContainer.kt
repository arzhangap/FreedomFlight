package com.arzhang.project.freedomflight.data

import android.content.Context
import com.arzhang.project.freedomflight.data.repository.FlightRepository
import com.arzhang.project.freedomflight.data.repository.OfflineFlightRepository

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val flightRepository: FlightRepository
}


class AppDataContainer(private val context: Context) : AppContainer {

    override val flightRepository: FlightRepository by lazy {
        OfflineFlightRepository(
            FlightDatabase.getDatabase(context).flightDao()
        )
    }

}
