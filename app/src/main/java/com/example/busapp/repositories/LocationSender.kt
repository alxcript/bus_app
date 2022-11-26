package com.example.busapp.repositories

import android.util.Log
import com.example.busapp.models.LocationDetails
import com.google.firebase.database.FirebaseDatabase

class LocationSender {

    fun saveLocation(locationDetails: LocationDetails, busIdentifier: String?) {
        val ref = FirebaseDatabase.getInstance().getReference("locationBuses")
        Log.d("SaveLocRef", ref.toString())

        busIdentifier?.let {
            ref.child(busIdentifier)
                .setValue(locationDetails)
                .addOnCompleteListener {
                    Log.d("SaveToDB", "saved in realtime database")
                }
        }


    }

}