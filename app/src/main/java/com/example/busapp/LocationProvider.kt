package com.example.busapp

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

@SuppressLint("MissingPermission")
class LocationProvider(private val activity: ComponentActivity) {
    //1
    private val client
            by lazy { LocationServices.getFusedLocationProviderClient(activity) }

    //2
    private val locations = mutableListOf<LatLng>()

    //3
    val liveLocation = MutableLiveData<LatLng>()

    //4
    fun getUserLocation() {
        client.lastLocation.addOnSuccessListener { location ->
            val latLng = LatLng(location.latitude, location.longitude)
            locations.add(latLng)
            liveLocation.value = latLng
        }
    }
}