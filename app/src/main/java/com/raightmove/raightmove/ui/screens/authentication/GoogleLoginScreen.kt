package com.raightmove.raightmove.ui.screens.authentication

import Destinations.AUTHENTICATION_ROUTE
import Destinations.USER_CREATION_ROUTE
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        navController?.navigate(AUTHENTICATION_ROUTE)
    } else if (loginUiState.isSuccessLogin) {
        navController?.navigate(USER_CREATION_ROUTE)
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            authenticationViewModel.loginUserByGoogle(context)
            CircularProgressIndicator()
        }
    }
}

