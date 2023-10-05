package com.arzhang.project.freedomflight.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arzhang.project.freedomflight.ui.FlightDetails

@Composable
fun FlightCard(
            flight: FlightDetails,
            onFavoriteClick: (FlightDetails) -> Unit,
            isFav: Boolean
) {
    Card {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text("DEPART", style = MaterialTheme.typography.labelLarge)
                AirportRow(flight.departureCode,flight.departureName)
                Spacer(modifier = Modifier.padding(5.dp))
                Text("ARRIVE", style = MaterialTheme.typography.labelLarge)
                AirportRow(flight.destinationCode,flight.destinationName)
            }
                Icon(imageVector = Icons.Default.Star , contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(horizontal = 8.dp)
                        .size(35.dp)
                        .clickable { onFavoriteClick(flight) },
                    tint = if(isFav) Color(255, 229, 0, 255) else Color.Gray
                )
        }
    }
}
@Composable
fun AirportRow(code:String, name:String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(code, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.padding(5.dp))
        Text(text = name, style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
fun FlightCardColumn(flightList: List<FlightDetails>, onFavouriteClick: (FlightDetails) -> Unit, isFav: Boolean =false) {
    LazyColumn(contentPadding = PaddingValues(10.dp)) {
        items(flightList) { flight ->
            Spacer(modifier = Modifier.padding(4.dp))
            FlightCard(flight = flight, onFavoriteClick = {onFavouriteClick(it)}, isFav = isFav)
        }
    }
}

@Preview
@Composable
fun CardPreview() {
    val flight= FlightDetails(0,"IRI","IRAN","FRR", "FREEDOM")
    FlightCard(
        flight, onFavoriteClick = {}, isFav = false
    )
}