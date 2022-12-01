package com.example.busapp.presentation

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*

//import androidx.compose.foundation.layout.CrossAxisAlignment.EndCrossAxisAlignment.align
//import androidx.compose.foundation.layout.CrossAxisAlignment.StartCrossAxisAlignment.align
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.busapp.presentation.BusListItem
import com.example.busapp.presentation.BusListState
//import java.lang.reflect.Modifier

@Composable
fun BusListScreen(state: BusListState, context: Context) {

    Log.d("Busses", state.buses.toString())

    Box(modifier = Modifier.fillMaxSize(),) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(
                items = state.buses,
            ){ bus ->
                BusListItem(bus = bus, context)
            }
        }

        if(state.error.isNotBlank()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center),
                text = state.error,
                color = Color.Red,
                textAlign = TextAlign.Center
            )
        }

        if(state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }



}
