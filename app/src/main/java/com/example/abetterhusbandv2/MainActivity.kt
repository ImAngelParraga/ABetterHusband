package com.example.abetterhusbandv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.abetterhusbandv2.ui.theme.ABetterHusbandV2Theme
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.abetterhusbandv2.ui.main.MainScreen
import com.example.abetterhusbandv2.ui.newHusbandTask.CreateHusbandTaskScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.abetterhusbandv2.ui.login.LoginScreen
import com.example.abetterhusbandv2.ui.login.LoginViewModel
import com.example.abetterhusbandv2.ui.main.MainViewModel
import com.example.abetterhusbandv2.ui.newHusbandTask.CreateHusbandTaskViewModel
import com.example.abetterhusbandv2.ui.splash.SplashScreen
import com.example.abetterhusbandv2.ui.splash.SplashViewModel

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
        startDestination = SPLASH_SCREEN,
        modifier = modifier
    ) {
        composable(MAIN_SCREEN) {
            val viewModel = hiltViewModel<MainViewModel>()
            MainScreen(
                mainViewModel = viewModel,
                newHusbandTask = {
                    navController.navigate("$CREATE_HUSBAND_TASK_SCREEN/${viewModel.user.value.userId}")
                })
        }

        composable(
            "$CREATE_HUSBAND_TASK_SCREEN/{listId}",
            arguments = listOf(navArgument("listId") { type = NavType.StringType })
        ) { backStackEntry ->
            val viewModel = hiltViewModel<CreateHusbandTaskViewModel>()
            CreateHusbandTaskScreen(
                createHusbandTaskViewModel = viewModel,
                onClickedSendButton = {
                    navController.navigate(MAIN_SCREEN)
                },
                listId = backStackEntry.arguments?.getString("listId")!!
            )
        }

        composable(SPLASH_SCREEN) {
            val viewModel = hiltViewModel<SplashViewModel>()
            SplashScreen(
                splashViewModel = viewModel
            ) { navController.navigate(MAIN_SCREEN) }
        }

        composable(LOGIN_SCREEN) {
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(viewModel) {
                navController.navigate(MAIN_SCREEN)
            }
        }
    }
}