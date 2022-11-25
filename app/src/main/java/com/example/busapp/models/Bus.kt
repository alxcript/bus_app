package com.example.busapp.models

import com.google.firebase.firestore.DocumentId

data class Bus(
    @DocumentId
    val id: String = "",
    val placa: String = "",
    val chofer: String = "",
    val capacidad: String = "",
    val mascotaImageUrl: String = ""
)
