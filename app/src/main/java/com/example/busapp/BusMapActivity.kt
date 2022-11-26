package com.example.busapp

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.busapp.models.LocationDetails
import com.example.busapp.models.User
import com.example.busapp.repositories.LocationSender
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

    private val applicationViewModel: ApplicationViewModel by viewModels()

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        mAuth.currentUser?.let { currentUser ->
            var name: String? = currentUser.displayName
            var email: String? = currentUser.email?.split("@")?.get(0)
            applicationViewModel.setCurrentUser(User(name, email))
        }

        applicationViewModel.getBusesCurrentLocation().addPostEventListener(applicationViewModel.getCurrentUser().email)

        setContent {
            BusAppTheme {
                val scope = rememberCoroutineScope()
                val scaffoldState = rememberScaffoldState()

                Scaffold(
                    drawerGesturesEnabled = false,
                    drawerContent = {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
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
                    }
                ) { contentPadding ->
                    Box(modifier = Modifier.padding(contentPadding)) {
                        val locations by applicationViewModel.getLocationLiveData().observeAsState()
                        val currentLocations by applicationViewModel.getBusesCurrentLocation().currentLocationDetails.observeAsState()
                        val user: User = applicationViewModel.getCurrentUser()
                        MyMap(locations, applicationViewModel.getLocationSender(), currentLocations, user)
                    }
                }
            }

        }
        prepLocationUpdates()
    }
    
    private fun prepLocationUpdates() {
        if(ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
            requestLocationUpdates()
        } else {
            requestSinglePermissionLauncher.launch(ACCESS_FINE_LOCATION)
        }
    }

    private val requestSinglePermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        isGranted ->
        if(isGranted) {
            requestLocationUpdates()
        } else {
            Toast.makeText(this, "GPS Unavailable", Toast.LENGTH_LONG).show()
        }
    }

    private fun requestLocationUpdates() {
        applicationViewModel.startLocationUpdates()
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
fun MyMap(location: LocationDetails?, locationSender: LocationSender, currentLocationBuses: List<LocationDetails>?, user: User) {
    val tacna = LatLng(-18.00, -70.24)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(tacna, 16f)
    }

    Log.d("LOCC", location.toString())
    location?.let {
        LaunchedEffect(cameraPositionState.isMoving) {
//            cameraPositionState.animate(
//                update = CameraUpdateFactory.newCameraPosition(
//                    CameraPosition(LatLng(location.latitude.toDouble(), location.longitude.toDouble()), 16f, 0f, 0f)
//                ),
//                durationMs = 1000
//            )
            locationSender.saveLocation(location, user.email)
        }
    }

    val uiSettings = remember {
        MapUiSettings(myLocationButtonEnabled = true)
    }

    val properties by remember {
        mutableStateOf(MapProperties(isMyLocationEnabled = true))
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = uiSettings,
        properties = properties
    ) {
        currentLocationBuses?.let { listLocations ->
            listLocations.forEach { currentLocationBuses ->
                var position = LatLng(currentLocationBuses.latitude.toDouble(), currentLocationBuses.longitude.toDouble())
                Marker(
                    state = MarkerState(position = position),
                    title = currentLocationBuses.getBusName(),
                    snippet = "Ltd: ${currentLocationBuses.latitude} | Lng: ${currentLocationBuses.longitude}"
                )
            }
        }
    }
}