package com.example.abetterhusbandv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.abetterhusbandv2.ui.theme.ABetterHusbandV2Theme
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.abetterhusbandv2.ui.main.MainScreen
import com.example.abetterhusbandv2.ui.newHusbandTask.CreateHusbandTaskScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.abetterhusbandv2.ui.main.MainViewModel
import com.example.abetterhusbandv2.ui.newHusbandTask.CreateHusbandTaskViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BetterHusbandApp()
        }
    }
}

@Composable
fun BetterHusbandApp() {
    ABetterHusbandV2Theme {
        val navController: NavHostController = rememberNavController()
        //val backstackEntry = navController.currentBackStackEntryAsState()
        //val currentScreen = BetterHusbandScreen.fromRoute(backstackEntry.value?.destination?.route)

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            BetterHusbandNavHost(navController = navController)
        }
    }
}

@Composable
fun BetterHusbandNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = BetterHusbandScreen.Main.name,
        modifier = modifier
    ) {
        composable(BetterHusbandScreen.Main.name) {
            val viewModel = hiltViewModel<MainViewModel>()
            MainScreen(
                mainViewModel = viewModel,
                newHusbandTask = {
                    navController.navigate(BetterHusbandScreen.CreateHusbandTask.name)
                })
        }

        composable(BetterHusbandScreen.CreateHusbandTask.name) {
            val viewModel = hiltViewModel<CreateHusbandTaskViewModel>()
            CreateHusbandTaskScreen(
                createHusbandTaskViewModel = viewModel,
                onClickedSendButton = {
                    navController.navigate(
                        BetterHusbandScreen.Main.name
                    )
                })
        }
    }
}