package com.example.busapp.models

data class LocationDetails(
    val longitude: String = "",
    val latitude: String = ""
) {

    private var busName: String = ""

    constructor(longitude: String, latitude: String, busName: String) : this(longitude, latitude) {
        this.busName = busName
    }

    fun getBusName(): String {
        return this.busName
    }
}