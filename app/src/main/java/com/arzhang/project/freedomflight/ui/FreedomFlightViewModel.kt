package com.arzhang.project.freedomflight.ui

import android.content.Context
import android.widget.Toast
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
    val destinationName: String
)

data class FlightUiState(
    val flightList: Flow<List<FlightDetails>> = emptyFlow(),
    val searchSuggestions: Flow<List<Airport>> = emptyFlow(),
    var query: String = "",
    val isFav: Boolean = false
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
               val fav:Boolean
               val currentList = if (userEntry.isBlank()) {
                   fav = true
                   flightRepository.getFavFights()
               } else {
                   fav = false
                   flightRepository.getFlights(userEntry)
               }
                   _uiState.update {
                       it.copy(
                           flightList = currentList,
                           query = userEntry,
                           isFav = fav
                       )
                   }
           }else {
               userEntryRepository.saveUserEntry(departureCode)
               val flightList = flightRepository.getFlights(departureCode)
               _uiState.update {
                   it.copy(
                       flightList = flightList,
                       query = departureCode,
                       isFav = false
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

     fun addFav(flightDetails: FlightDetails,context: Context) {
         viewModelScope.launch {
                 val flight = UserFavoriteFlight(
                     flightDetails.id,
                     flightDetails.departureCode,
                     flightDetails.destinationCode
                 )
             if(!_uiState.value.isFav) {
                 flightRepository.insertFav(flight)
                 Toast.makeText(context,"Successfully Added.",Toast.LENGTH_SHORT).show()
             } else {
                 flightRepository.deleteFav(flight)
                 Toast.makeText(context,"Successfully Deleted From Favorites.",Toast.LENGTH_SHORT).show()
             }
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