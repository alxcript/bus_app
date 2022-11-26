package com.example.busapp.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.busapp.models.LocationDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class BusesCurrentLocation {

    var currentLocationDetails: MutableLiveData<List<LocationDetails>> = MutableLiveData<List<LocationDetails>>()

    fun addPostEventListener(email: String?) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val currentLocationList = mutableListOf<LocationDetails>();
                for(children in dataSnapshot.children) {
                    val busLocationDetails = children.getValue<LocationDetails>()
                    busLocationDetails?.let {
                        val busLocationDetailsComplete = LocationDetails(busLocationDetails.longitude, busLocationDetails.latitude, children.key.toString() )
                        currentLocationList.add(busLocationDetailsComplete)
                    }
                    Log.d("dsLocationDetailsFromRTDB", children.toString())
                }
                currentLocationDetails.value = currentLocationList.toList()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("BUSES_CURRENT_LOCATION", "loadPost:onCancelled", databaseError.toException())
            }
        }
        val busLocationReference = FirebaseDatabase.getInstance().getReference("locationBuses/")
        busLocationReference.addValueEventListener(postListener)
    }
}