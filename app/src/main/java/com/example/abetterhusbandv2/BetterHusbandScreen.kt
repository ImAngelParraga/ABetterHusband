package com.example.abetterhusbandv2

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class BetterHusbandScreen(val icon: ImageVector) {
    Main(icon = Icons.Filled.Home),
    CreateHusbandTask(Icons.Filled.Create);

    companion object {
        fun fromRoute(route: String?): BetterHusbandScreen =
            when (route?.substringBefore("/")) {
                Main.name -> Main
                CreateHusbandTask.name -> CreateHusbandTask
                null -> Main
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}