package com.example.busapp.presentation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.busapp.R
import com.example.busapp.models.Bus
import com.example.busapp.models.Departures
import com.example.busapp.utils.baselineHeight


@Composable
fun ProfileScreen(bus: Bus) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints(modifier = Modifier.weight(1f)) {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                ) {
                    ProfileHeader(
                        scrollState,
                        bus,
                        this@BoxWithConstraints.maxHeight
                    )
                    ProfileContent(bus, this@BoxWithConstraints.maxHeight)
                }
            }
        }
    }
}

@Composable
private fun ProfileHeader(
    scrollState: ScrollState,
    bus: Bus,
    containerHeight: Dp
) {
    val offset = (scrollState.value / 2)
    val offsetDp = with(LocalDensity.current) { offset.toDp() }

    AsyncImage(
        model = bus.busImageUrl,
        modifier = Modifier
            .heightIn(max = containerHeight / 2)
            .fillMaxWidth()
            .padding(top = offsetDp),
        //painter = painterResource(id = bus.puppyImageId),
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}


@Composable
private fun ProfileContent(bus: Bus, containerHeight: Dp) {
    Column {
        Spacer(modifier = Modifier.height(8.dp))

        Name(bus)

        ProfileProperty(stringResource(R.string.driver), bus.chofer)

        ProfileProperty(stringResource(R.string.licensePlate), bus.placa)

        ProfileProperty(stringResource(R.string.capacity), bus.capacidad)

        ProfileProperty(stringResource(R.string.route), bus.ruta)

        ProfilePropertyDeparture(stringResource(R.string.departures), bus.salidas)

        Spacer(Modifier.height((containerHeight - 320.dp).coerceAtLeast(0.dp)))
    }
}

@Composable
fun ProfilePropertyDeparture(label: String, departures: Departures) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Divider()
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = label,
                modifier = Modifier.baselineHeight(24.dp),
                style = MaterialTheme.typography.caption,
            )
        }
        Column() {
            Text(
                text = "Primera: ${departures.primera}",
                modifier = Modifier.baselineHeight(24.dp),
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "Segunda: ${departures.segunda}",
                modifier = Modifier.baselineHeight(24.dp),
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "Tercera: ${departures.tercera}",
                modifier = Modifier.baselineHeight(24.dp),
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "Cuarta: ${departures.cuarta}",
                modifier = Modifier.baselineHeight(24.dp),
                style = MaterialTheme.typography.body1
            )
        }
    }
}


@Composable
private fun Name(
    bus: Bus
) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Name(
            bus = bus,
            modifier = Modifier.baselineHeight(32.dp)
        )
    }
}


@Composable
private fun Name(bus: Bus, modifier: Modifier = Modifier) {
    Text(
        text = bus.id,
        modifier = modifier,
        style = MaterialTheme.typography.h5,
        fontWeight = FontWeight.Bold
    )
}


@Composable
fun ProfileProperty(label: String, value: String, isLink: Boolean = false) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Divider()
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = label,
                modifier = Modifier.baselineHeight(24.dp),
                style = MaterialTheme.typography.caption,
            )
        }
        val style = if (isLink) {
            MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.primary)
        } else {
            MaterialTheme.typography.body1
        }
        Text(
            text = value,
            modifier = Modifier.baselineHeight(24.dp),
            style = style
        )
    }
}