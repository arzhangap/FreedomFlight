package com.arzhang.project.freedomflight.data

import com.arzhang.project.freedomflight.data.model.Flight

fun generateFlights(airports: List<String>) : List<Flight> {

    val flights = mutableListOf<Flight>()
    for(airport in airports) {
        val otherAirports = airports.toMutableList().apply {
            remove(airport)
            shuffle()
        }
        val destinations = otherAirports.take(4)

        for(destination in destinations) {
            flights.add(Flight(id = 0,departureCode = airport , destinationCode = destination))
        }
    }
    return flights
}