package com.example.busapp



import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

class PermissionsManager(
    activity: ComponentActivity,
    private val locationProvider: LocationProvider) {


    //1
    private val locationPermissionProvider = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            locationProvider.getUserLocation()
        }
    }

    //2
    fun requestUserLocation() {
        locationPermissionProvider.launch(ACCESS_FINE_LOCATION)
    }

}