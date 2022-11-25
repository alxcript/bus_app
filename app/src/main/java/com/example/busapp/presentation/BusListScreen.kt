package com.example.busapp.presentation

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import com.example.busapp.presentation.BusListItem
import com.example.busapp.presentation.BusListState

@Composable
fun BusListScreen(state: BusListState) {

    Log.d("Busses", state.buses.toString())

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            items = state.buses,
            ){ bus ->
            BusListItem(bus = bus)
        }
    }
}
