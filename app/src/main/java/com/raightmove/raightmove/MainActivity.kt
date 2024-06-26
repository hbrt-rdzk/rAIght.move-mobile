package com.raightmove.raightmove

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raightmove.raightmove.ui.screens.CameraScreen
import com.raightmove.raightmove.ui.screens.LoginScreen
import com.raightmove.raightmove.ui.screens.RegisterScreen
import com.raightmove.raightmove.ui.screens.StartScreen
import com.raightmove.raightmove.ui.themes.RAIghtmoveTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RAIghtmoveTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "start_screen") {
        composable("start_screen") {
            StartScreen(navController)
        }
        composable("login_screen") {
            LoginScreen(navController)
        }
        composable("register_screen") {
            RegisterScreen(navController)
        }
        composable("camera_screen") {
            CameraScreen()
        }
    }
}
