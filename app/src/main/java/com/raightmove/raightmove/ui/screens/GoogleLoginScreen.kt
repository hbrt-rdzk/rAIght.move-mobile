package com.raightmove.raightmove.ui.screens

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.raightmove.raightmove.viewmodels.AuthenticationViewModel


@Composable
fun GoogleLoginScreen(
    navController: NavController? = null,
    authenticationViewModel: AuthenticationViewModel
) {
    val loginUiState = authenticationViewModel.loginUIState
    val isError = loginUiState.loginError != null
    val context = LocalContext.current

    if (isError) {
        navController?.navigate("authentication_screen")
    } else if (loginUiState.isSuccessLogin) {
        navController?.navigate("camera_screen")
    } else {
        authenticationViewModel.loginUserByGoogle(context)
        CircularProgressIndicator()
    }
}

