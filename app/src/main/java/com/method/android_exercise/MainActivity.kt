package com.method.android_exercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.method.android_exercise.ui.NavigationItem
import com.method.android_exercise.ui.screens.RandomNumberScreen
import com.method.android_exercise.ui.screens.WeatherScreen
import com.method.android_exercise.ui.theme.AndroidexerciseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidexerciseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavigationItem.RandomNumberScreen.route
    ) {
        composable(NavigationItem.RandomNumberScreen.route) {
            RandomNumberScreen()
        }
        composable(NavigationItem.WeatherScreen.route) {
            WeatherScreen()
        }
    }
}
