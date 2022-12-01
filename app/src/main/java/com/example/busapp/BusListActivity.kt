package com.example.busapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.busapp.presentation.BusListScreen
import com.example.busapp.presentation.BusListViewModel
import com.example.busapp.ui.theme.BusAppTheme

class BusListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val busListViewModel: BusListViewModel by viewModels()

        setContent {
            BusAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val state = busListViewModel.state.value
                    Log.d("OnBusDetailActivity2", state.buses.toString())
                    BusListScreen(state, this)
                }
            }
        }
    }
}
