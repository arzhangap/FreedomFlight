package com.arzhang.project.freedomflight.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arzhang.project.freedomflight.data.model.Airport

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCard(
    inputValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(value = inputValue,
        onValueChange = {onValueChange(it)},
        colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.background) ,
        shape = RoundedCornerShape(50.dp),
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(56.dp))
    }

@Composable
fun SearchSuggestions(searchResult: List<Airport>, onAirportClicked: (String) -> Unit) {
    Column(modifier = Modifier.padding(2.dp)) {
        searchResult.forEach {
                Text(it.iataCode, modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { onAirportClicked(it.iataCode) })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchCardPreview() {
//    SearchCard(inputValue = "", onValueChange = {  }, onAirportClicked = {}, searchSuggestion = emptyList())
}