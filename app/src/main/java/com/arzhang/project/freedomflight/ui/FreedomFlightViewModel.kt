package com.arzhang.project.freedomflight.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.arzhang.project.freedomflight.FreedomFlightApplication
import com.arzhang.project.freedomflight.data.model.Airport
import com.arzhang.project.freedomflight.data.model.UserFavoriteFlight
import com.arzhang.project.freedomflight.data.repository.FlightRepository
import com.arzhang.project.freedomflight.data.repository.UserEntryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FlightDetails(
    val id: Int,
    val departureCode: String,
    val departureName: String,
    val destinationCode: String,
    val destinationName: String,
)

data class FlightUiState(
    val flightList: Flow<List<FlightDetails>> = emptyFlow(),
    val searchSuggestions: Flow<List<Airport>> = emptyFlow(),
    var query: String = ""
)

class FreedomFlightViewModel(
    private val flightRepository: FlightRepository,
    private val userEntryRepository: UserEntryRepository
) : ViewModel() {
    private val _uiState= MutableStateFlow(FlightUiState())
    val uiState: StateFlow<FlightUiState> = _uiState

    init {
        processAirportFlights(null)
    }

    fun processAirportFlights(departureCode: String?) {
        viewModelScope.launch {
            val userEntry = userEntryRepository.userEntry.first()
           if(departureCode == null) {
               val currentList = if (userEntry.isBlank()) {
                   flightRepository.getFavFights()
               } else {
                   flightRepository.getFlights(userEntry)
               }
                   _uiState.update {
                       it.copy(
                           flightList = currentList,
                           query = userEntry
                       )
                   }
           }else {
               userEntryRepository.saveUserEntry(departureCode)
               val flightList = flightRepository.getFlights(departureCode)
               _uiState.update {
                   it.copy(
                       flightList = flightList,
                       query = departureCode
                   )
               }
           }
        }
    }

    fun getFav() {
        viewModelScope.launch {
            userEntryRepository.saveUserEntry("")
            processAirportFlights(null)
        }
        }

    fun queryChange(query: String) {
        viewModelScope.launch {
           val searchSuggestions = flightRepository.searchAirports(query)
            _uiState.update {
                it.copy(query = query, searchSuggestions = searchSuggestions )
            }
        }
    }

     fun addFav(flightDetails: FlightDetails) {
         viewModelScope.launch {
             val flight = UserFavoriteFlight(flightDetails.id,flightDetails.departureCode,flightDetails.destinationCode)
             flightRepository.insertFav(flight)
         }
    }

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as FreedomFlightApplication
                val container = application.container
                val flightRepo = container.flightRepository
                val userEntryRepo = application.userEntryRepository
                FreedomFlightViewModel(flightRepo, userEntryRepo)
            }
        }
    }
}