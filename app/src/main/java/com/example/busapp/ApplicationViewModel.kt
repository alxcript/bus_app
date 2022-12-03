package com.example.busapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.busapp.models.User
import com.example.busapp.repositories.BusesCurrentLocation
import com.example.busapp.repositories.LocationSender

class ApplicationViewModel(application: Application) : AndroidViewModel(application) {

    private val locationLiveData =  LocationLiveData(application)
    private val locationSender =  LocationSender()
    private val busesCurrentLocation = BusesCurrentLocation()

    private lateinit var currentUser: User

    fun getLocationLiveData() = locationLiveData
    fun getLocationSender() = locationSender
    fun getBusesCurrentLocation() = busesCurrentLocation

    fun startLocationUpdates() {
        locationLiveData.startLocationUpdates()
    }

    fun setCurrentUser(user: User) {
        this.currentUser = user
        if(registeredBusEmails.contains(user.email)) {
            this.currentUser.type = "BUS"
        } else {
            this.currentUser.type = "STUDENT"
        }
    }

    fun getCurrentUser(): User {
        return this.currentUser
    }

    private var registeredBusEmails: List<String> = listOf("jl2019063326","bus1", "bus2")

}