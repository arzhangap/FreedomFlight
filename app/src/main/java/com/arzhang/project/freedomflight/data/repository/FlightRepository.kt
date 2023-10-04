package com.arzhang.project.freedomflight.data.repository

import com.arzhang.project.freedomflight.data.FlightDao
import com.arzhang.project.freedomflight.data.model.Airport
import com.arzhang.project.freedomflight.data.model.Flight
import com.arzhang.project.freedomflight.data.model.UserFavoriteFlight
import com.arzhang.project.freedomflight.ui.FlightDetails
import kotlinx.coroutines.flow.Flow

interface FlightRepository {
    fun getFlights(departureCode: String) : Flow<List<FlightDetails>>
    fun searchAirports(searchQuery: String): Flow<List<Airport>>
    fun getAirports(): List<Airport>
    suspend fun insertFlights(flights: List<Flight>)
    suspend fun insertFav(flight: UserFavoriteFlight)
    fun getFavFights() : Flow<List<FlightDetails>>
}

class OfflineFlightRepository(
  private val flightDao: FlightDao
) : FlightRepository {
    override fun getFlights(departureCode: String): Flow<List<FlightDetails>> = flightDao.getFlights(departureCode)

    override fun searchAirports(searchQuery: String):Flow<List<Airport>> = flightDao.searchFlights(searchQuery)

    override fun getAirports(): List<Airport> = flightDao.getAirports()

    override suspend fun insertFlights(flights: List<Flight>) = flightDao.insertFlights(flights)

    override suspend fun insertFav(flight: UserFavoriteFlight) = flightDao.insertFav(flight)

    override fun getFavFights(): Flow<List<FlightDetails>> = flightDao.getFavFights()


}