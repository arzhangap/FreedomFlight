package com.arzhang.project.freedomflight.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arzhang.project.freedomflight.ui.FlightDetails

@Composable
fun FlightCard(
                flight: FlightDetails,
                onFavoriteClick: (FlightDetails) -> Unit
) {
    Card() {
        Box() {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
            ) {
                Text(flight.departureCode, fontSize = 12.sp)
                Text(text = flight.departureName, fontSize = 8.sp)
                Text("to", style = MaterialTheme.typography.labelSmall, fontSize = 12.sp)
                Text(flight.destinationCode, fontSize = 12.sp)
                Text(text = flight.destinationName, fontSize = 8.sp)
            }
            Icon(imageVector = Icons.Default.Star, contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(horizontal = 4.dp)
                    .clickable { onFavoriteClick(flight) }
            )
        }
    }
}

@Composable
fun FlightCardColumn(flightList: List<FlightDetails>, onFavouriteClick: (FlightDetails) -> Unit) {
    LazyColumn(contentPadding = PaddingValues(10.dp)) {
        items(flightList) { flight ->
            Spacer(modifier = Modifier.padding(4.dp))
            FlightCard(flight = flight, onFavoriteClick = {onFavouriteClick(it)})
        }
    }
}

@Preview(device = "spec:width=411dp,height=891dp")
@Composable
fun FlightCardColumnPreview() {
//    FreedomFlightTheme {
////        val dummyAirport1 =  Airport(1,"IRI","Iran, Karaj, 200", 200)
////        val dummyAirport2 =  Airport(1,"HVN","Heaven", 2000)
////        val dummyFlight = FlightDetails(id = 1,  = dummyAirport1, destinationAirport = dummyAirport2)
////        val dummyList = listOf(dummyFlight, dummyFlight)
//        FlightCardColumn(flightList = dummyList)
//    }
}

//@Preview(device = "spec:width=411dp,height=891dp", showBackground = true)
//@Composable
//fun FlightCardPreview() {
//    FreedomFlightTheme {
//        FlightCard(flightId = 1, departureAirport = Airport(1,"IRI","Iran, Karaj, 200", 200), destinationAirport = Airport(2,"HVN","Heaven", 200))
//    }
//}