package com.arzhang.project.freedomflight.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arzhang.project.freedomflight.data.model.Airport
import com.arzhang.project.freedomflight.data.model.Flight
import com.arzhang.project.freedomflight.data.model.UserFavoriteFlight
import com.arzhang.project.freedomflight.ui.FlightDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightDao {

    @Query(
        "SELECT f.id," +
                " a1.iata_code AS departureCode, a1.name AS departureName, " +
                " a2.iata_code AS destinationCode, a2.name AS destinationName " +
                "FROM favorite f JOIN airport a1 ON f.departure_code = a1.iata_code JOIN " +
                "airport a2 ON f.destination_code = a2.iata_code ORDER BY departure_code ASC"
    )
    fun getFavFights() : Flow<List<FlightDetails>>

    @Query("SELECT * FROM airport WHERE iata_code || name LIKE '%' || :searchQuery || '%'")
    fun searchFlights(searchQuery: String): Flow<List<Airport>>

    @Query("SELECT * FROM airport ORDER BY iata_code")
    fun getAirports(): List<Airport>

    @Insert(entity = Flight::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFlights(flights: List<Flight>)

    @Insert(entity = UserFavoriteFlight::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFav(flights: UserFavoriteFlight)

    @Delete(entity = UserFavoriteFlight::class)
    suspend fun deleteFav(flight: UserFavoriteFlight)

    @Query(
        "SELECT f.id," +
                " a1.iata_code AS departureCode, a1.name AS departureName, " +
                " a2.iata_code AS destinationCode, a2.name AS destinationName " +
                "FROM flights f JOIN airport a1 ON f.departure_code = a1.iata_code JOIN " +
                "airport a2 ON f.destination_code = a2.iata_code WHERE departureCode = :departureCode"
    )
    fun getFlights(departureCode: String) : Flow<List<FlightDetails>>
}