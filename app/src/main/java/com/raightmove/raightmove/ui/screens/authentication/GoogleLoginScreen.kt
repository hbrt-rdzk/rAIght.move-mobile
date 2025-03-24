package com.raightmove.raightmove.ui.screens.authentication

import Destinations.AUTHENTICATION_ROUTE
import Destinations.HOME_ROUTE
import Destinations.USER_CREATION_ROUTE
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.raightmove.raightmove.ui.components.ProgressIndicator
import com.raightmove.raightmove.ui.themes.Bronze
import com.raightmove.raightmove.ui.themes.Cream
import com.raightmove.raightmove.viewmodels.UserInfoUiState
import kotlinx.coroutines.launch

@Composable
fun GoogleLoginScreen(
    navController: NavController? = null,
    userInfo: UserInfoUiState?,
    loginError: String?,
    userCreationError: String?,
    isSuccessLogin: Boolean,
    onEnter: (Context) -> Unit,
    getUserId: () -> String,
    fetchUserInfo: suspend (String) -> Unit
) {
    val context = LocalContext.current

    var checkedUser by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        onEnter(context)
    }

    LaunchedEffect(key1 = isSuccessLogin) {
        if (isSuccessLogin) {
            launch {
                val userId = getUserId()
                fetchUserInfo(userId)
                checkedUser = true
            }
        }
    }

    when {
        loginError != null || userCreationError != null -> {
            Toast.makeText(context, "Error occurred", Toast.LENGTH_LONG).show()
            navController?.navigate(AUTHENTICATION_ROUTE)
        }

        isSuccessLogin && checkedUser -> {
            when (userInfo) {
                null -> navController?.navigate(USER_CREATION_ROUTE)
                else -> navController?.navigate(HOME_ROUTE)
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
                ProgressIndicator()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGoogleLoginScreenPreview() {
    GoogleLoginScreen(
        userInfo = null,
        loginError = null,
        userCreationError = null,
        isSuccessLogin = false,
        onEnter = {},
        getUserId = { "" },
        fetchUserInfo = {}
    )
}

