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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.raightmove.raightmove.ui.themes.Bronze
import com.raightmove.raightmove.ui.themes.Cream
import com.raightmove.raightmove.viewmodels.AuthenticationViewModel
import com.raightmove.raightmove.viewmodels.UserInfoViewModel
import kotlinx.coroutines.launch

@Composable
fun GoogleLoginScreen(
    navController: NavController? = null,
    authenticationViewModel: AuthenticationViewModel,
    userInfoViewModel: UserInfoViewModel
) {
    val loginUiState = authenticationViewModel.loginUIState
    val userInfoUiState = userInfoViewModel.userInfoUiState

    val isLoginError = loginUiState.loginError != null
    val isUserCreationError = userInfoUiState.error != null

    val isSuccessLogin = loginUiState.isSuccessLogin
    val context = LocalContext.current

    var userInDb by remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(Unit) {
        authenticationViewModel.loginUserByGoogle(context)
    }

    LaunchedEffect(key1 = isSuccessLogin) {
        if (isSuccessLogin) {
            launch {
                val userId = authenticationViewModel.userId
                userInDb = userInfoViewModel.userExistsInDB(userId)
            }
        }
    }

    when {
        isLoginError || isUserCreationError -> {
            Toast.makeText(context, "Error occurred", Toast.LENGTH_LONG).show()
            navController?.navigate(AUTHENTICATION_ROUTE)
        }

        loginUiState.isSuccessLogin && (userInDb != null) -> {
            when (userInDb) {
                true -> navController?.navigate(HOME_ROUTE)
                else -> navController?.navigate(USER_CREATION_ROUTE)
            }
        }

        else -> {
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
}
