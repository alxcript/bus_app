package com.example.busapp

import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.busapp.ui.theme.BusAppTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BusAppTheme {
                val scope = rememberCoroutineScope()
                val scaffoldState = rememberBottomSheetScaffoldState()

                BottomSheetScaffold(
                    sheetContent = {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(128.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Swipe up to expand sheet")
                        }
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(64.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Sheet content")
                            Spacer(Modifier.height(20.dp))
                            Button(
                                onClick = {
                                    scope.launch { scaffoldState.bottomSheetState.collapse() }
                                }
                            ) {
                                Text("Click to collapse sheet")
                            }
                        }
                    },
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopAppBar(
                            title = { Text("Bottom sheet scaffold") },
                            navigationIcon = {
                                IconButton(onClick = { scope.launch { scaffoldState.drawerState.open() } }) {
                                    Icon(Icons.Default.Menu, contentDescription = "Localized description")
                                }
                            }
                        )
                    },
                    floatingActionButton = {
                        var clickCount by remember { mutableStateOf(0) }
                        FloatingActionButton(
                            onClick = {
                                // show snackbar as a suspend function
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar("Snackbar #${++clickCount}")
                                }
                            }
                        ) {
                            Icon(Icons.Default.Favorite, contentDescription = "Localized description")
                        }
                    },
                    floatingActionButtonPosition = FabPosition.End,
                    sheetPeekHeight = 128.dp
                ) { innerPadding ->
                    MyMap()
                }

            }
        }
    }
}

@Composable
fun MyMap() {
    val tacna = LatLng(-18.00, -70.24)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(tacna, 12f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = tacna),
            title = "Tacna",
            snippet = "Marker in Tacna"
        )
    }
}
