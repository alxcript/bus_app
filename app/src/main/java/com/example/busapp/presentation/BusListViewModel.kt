package com.example.busapp.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.busapp.repositories.BusRepository
import com.example.busapp.repositories.Result
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BusListViewModel: ViewModel() {
    private val _state: MutableState<BusListState> = mutableStateOf(BusListState())
    val state: State<BusListState> = _state

    init {
        getBusList()
    }

    private fun getBusList() {
        val busRepository = BusRepository()
        busRepository.getBusList().onEach { result ->
            when(result) {
                is Result.Error -> {
                    _state.value = BusListState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = BusListState(isLoading = true)
                }
                is Result.Success -> {
                    Log.d("OnGetBusListViewModel", result.data.toString())
                    _state.value = BusListState(buses = result.data ?: emptyList())
                    Log.d("OnGetBusListViewModel2", _state.value.buses.size.toString())
                }
            }
        }.launchIn(viewModelScope)
    }
}
