package com.example.busapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.busapp.ui.theme.BusAppTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
class BusMapActivity : ComponentActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

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
                            Text("Deslize para ver mas información")
                        }
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(64.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("UPT")
                            Spacer(Modifier.height(20.dp))
                            Button(
                                onClick = {
                                    scope.launch { scaffoldState.bottomSheetState.collapse() }
                                }
                            ) {
                                Text("Close")
                            }
                        }
                    },
                    drawerGesturesEnabled = false,
                    drawerContent = {
                        Column(
                            Modifier.fillMaxWidth().padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Opciones")
                            Spacer(Modifier.height(20.dp))
                            Button(onClick = { scope.launch {
                                startActivity(Intent(baseContext, BusDetailActivity::class.java))
                            } }) {
                                Text("Ver Buses")
                            }
                            Spacer(Modifier.height(20.dp))
                            Button(onClick = { scope.launch {
                                signOut()
                            } }) {
                                Text("SignOut")
                            }
                            Spacer(Modifier.height(20.dp))
                            Button(onClick = { scope.launch { scaffoldState.drawerState.close() } }) {
                                Text("Close")
                            }
                        }
                    },
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopAppBar(
                            title = { Text("Seguimiento Bus UPT") },
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
                ) {
                    MyMap()
                }

            }
        }
    }

    private fun signOut() {
        val gso = GoogleSignInOptions.DEFAULT_SIGN_IN
        GoogleSignIn.getClient(this, gso).signOut().addOnCompleteListener() {
            mAuth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
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
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(myLocationButtonEnabled = true)
    ) {
        Marker(
            state = MarkerState(position = tacna),
            title = "Tacna",
            snippet = "Marker in Tacna"
        )
    }
}
