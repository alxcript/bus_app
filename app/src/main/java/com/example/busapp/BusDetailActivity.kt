package com.example.busapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.busapp.models.Bus
import com.example.busapp.presentation.ProfileScreen
import com.example.busapp.ui.theme.BusAppTheme

class BusDetailActivity : ComponentActivity() {

    private val bus: Bus by lazy {
        intent?.getSerializableExtra(BUS_ID) as Bus
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ProfileScreen(bus = bus)
                }
            }
        }
    }

    companion object {
        private const val BUS_ID = "bus_id"
        fun newIntent(context: Context, bus: Bus) =
            Intent(context, BusDetailActivity::class.java).apply {
                putExtra(BUS_ID, bus)
            }
    }

}
