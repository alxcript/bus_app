package com.example.busapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun BusListItem(bus: Bus) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ){

        Row {
            Column(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
            ) {
                BusImage(bus)
                Text(text = "${bus.id}", style = typography.h6)
                Text(text = bus.placa, style = typography.h6)
                Text(text = bus.chofer, style = typography.h6)
                Button(onClick = { /*TODO*/ }, shape = CutCornerShape(10), modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Ver Más", style = typography.caption)
                }
            }
        }
    }
}

@Composable
private fun BusImage(bus: Bus){
    AsyncImage(model = bus.mascotaImageUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
    )
}

