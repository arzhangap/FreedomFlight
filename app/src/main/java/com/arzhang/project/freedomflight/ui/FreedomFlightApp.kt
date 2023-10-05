package com.arzhang.project.freedomflight.ui


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arzhang.project.freedomflight.ui.components.FlightCardColumn
import com.arzhang.project.freedomflight.ui.components.SearchCard
import com.arzhang.project.freedomflight.ui.components.SearchSuggestions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FreedomFlightApp(
    viewModel: FreedomFlightViewModel = viewModel(factory = FreedomFlightViewModel.factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val favFlights by uiState.flightList.collectAsState(emptyList())
    val searchSuggestion by uiState.searchSuggestions.collectAsState(emptyList())

    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding)) {
            var open by remember { mutableStateOf(false) }
            val focusManager = LocalFocusManager.current

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            viewModel.getFav()
                            focusManager.clearFocus()
                            open = false
                        }
                )
                Spacer(Modifier.padding(5.dp))
                SearchCard(
                    uiState.query,
                    onValueChange = {
                        open = it.isNotBlank()
                        viewModel.queryChange(it)
                    },
                )
            }

                        AnimatedVisibility(visible = open) {
                            Column(
                                Modifier.padding(horizontal = 30.dp)) {
                                Text(
                                    "List of Airports:",
                                    style = MaterialTheme.typography.labelMedium
                                )
                                    Card(
                                        modifier = Modifier.fillMaxWidth().heightIn(max = 350.dp)
                                    ) {
                                        SearchSuggestions(
                                            searchResult = searchSuggestion,
                                            onAirportClicked = {
                                                viewModel.processAirportFlights(it)
                                                open = false
                                                focusManager.clearFocus()
                                            }
                                        )
                                    }
                                }
                            }
                        AnimatedVisibility(!open) {
                        if (favFlights.isNotEmpty()) {
                                val context = LocalContext.current
                                FlightCardColumn(
                                    flightList = favFlights,
                                    onFavouriteClick = { viewModel.addFav(it, context = context) },
                                    isFav = uiState.isFav
                                )
                        } else {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text("Favourite Flights is Empty.")
                                Text("Search For Flights")
                            }
                        }
                    }
                }
            }
        }

