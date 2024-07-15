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
import com.google.firebase.FirebaseApp
import com.raightmove.raightmove.ui.screens.AuthenticationScreen
import com.raightmove.raightmove.ui.screens.CameraScreen
import com.raightmove.raightmove.ui.screens.EmailLoginScreen
import com.raightmove.raightmove.ui.screens.GoogleLoginScreen
import com.raightmove.raightmove.ui.screens.RegisterScreen
import com.raightmove.raightmove.ui.themes.RAIghtmoveTheme
import com.raightmove.raightmove.viewmodels.AuthenticationViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
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
    val authenticationViewModel = AuthenticationViewModel()
//    val startDestination = if (authenticationViewModel.hasUser) "camera_screen" else "authentication_screen"
    val startDestination = "authentication_screen"

    NavHost(navController, startDestination = startDestination) {
        composable("authentication_screen") {
            AuthenticationScreen(navController)
        }
        composable("email_login_screen") {
            EmailLoginScreen(navController, authenticationViewModel)
        }
        composable("register_screen") {
            RegisterScreen(navController, authenticationViewModel)
        }
        composable("google_login_screen") {
            GoogleLoginScreen(navController, authenticationViewModel)
        }
        composable("camera_screen") {
            CameraScreen()
        }
    }
}
