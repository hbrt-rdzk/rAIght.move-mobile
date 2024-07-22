package com.raightmove.raightmove.ui.screens.authentication

import Destinations.AUTHENTICATION_ROUTE
import Destinations.HOME_ROUTE
import Destinations.USER_CREATION_ROUTE
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.raightmove.raightmove.ui.themes.Bronze
import com.raightmove.raightmove.ui.themes.Cream
import com.raightmove.raightmove.viewmodels.AuthenticationViewModel
import com.raightmove.raightmove.viewmodels.UserInfoViewModel

@Composable
fun GoogleLoginScreen(
    navController: NavController? = null,
    authenticationViewModel: AuthenticationViewModel,
    userInfoViewModel: UserInfoViewModel
) {
    val loginUiState = authenticationViewModel.loginUIState
    val userInDb by userInfoViewModel.isUserInDb.collectAsState()

    val isError = loginUiState.loginError != null
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        authenticationViewModel.loginUserByGoogle(context)
    }

    LaunchedEffect(key1 = loginUiState.isSuccessLogin) {
        if (loginUiState.isSuccessLogin) {
            userInfoViewModel.userExistsInDB(authenticationViewModel.userId)
        }
    }

    if (isError) {
        Toast.makeText(context, "Error occurred", Toast.LENGTH_LONG).show()
        navController?.navigate(AUTHENTICATION_ROUTE)
    } else if (loginUiState.isSuccessLogin) {
        if (userInDb == true) {
            navController?.navigate(HOME_ROUTE)
        } else {
            navController?.navigate(USER_CREATION_ROUTE)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Cream, Bronze),
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
