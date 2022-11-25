package com.example.busapp.presentation

import com.example.busapp.models.Bus

data class BusListState(
    val isLoading: Boolean = false,
    val buses: List<Bus> = emptyList(),
    val error: String = ""
)