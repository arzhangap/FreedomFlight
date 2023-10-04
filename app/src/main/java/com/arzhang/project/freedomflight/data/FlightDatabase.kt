package com.arzhang.project.freedomflight.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.arzhang.project.freedomflight.data.model.Airport
import com.arzhang.project.freedomflight.data.model.Flight
import com.arzhang.project.freedomflight.data.model.UserFavoriteFlight

@Database(entities = [Airport::class, Flight::class,UserFavoriteFlight::class], version = 1, exportSchema = false)
abstract class FlightDatabase : RoomDatabase() {

    abstract fun flightDao() : FlightDao

    companion object {
        @Volatile
        private var instance: FlightDatabase? = null

        fun getDatabase(context: Context) : FlightDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, FlightDatabase::class.java, "flight_database")
                    .createFromAsset("flight_search.db")
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                           val cursor = db.query("SELECT * FROM airport")
                            val iataCodes = generateSequence { if (cursor.moveToNext()) cursor else null }
                                .map { it.getString(2) }
                                .toList()
                            val flights = generateFlights(iataCodes)
                            flights.forEach { flight ->
                                db.execSQL("INSERT INTO flights(departure_code,destination_code) VALUES ('${flight.departureCode}','${flight.destinationCode}')")
                            }
//
                        }
                    })
                    .build()
            }
        }
    }

}