package com.example.busapp.models

import com.google.firebase.firestore.DocumentId
import java.io.Serializable

data class Bus(
    @DocumentId
    val id: String = "",
    val placa: String = "",
    val chofer: String = "",
    val capacidad: String = "",
    val busImageUrl: String = "",
    val ruta: String = "",
    val salidas: Departures = Departures()
) : Serializable
