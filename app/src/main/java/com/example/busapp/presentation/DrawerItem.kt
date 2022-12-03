package com.example.busapp.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector

enum class DrawerItem(
    val icon: ImageVector,
    val text: String
) {
    BUSES(Icons.Default.Menu, "Buses"),
    SIGN_OUT(Icons.Default.Info, "Sign Out"),
    CLOSE(Icons.Default.Close, "Cerrar")
}