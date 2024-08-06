package com.raightmove.raightmove.ui.screens.main

import Destinations.AUTHENTICATION_ROUTE
import Destinations.HOME_ROUTE
import Destinations.PROFILE_ROUTE
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.raightmove.raightmove.ui.components.BottomMainNavBar
import com.raightmove.raightmove.ui.themes.Bronze
import com.raightmove.raightmove.ui.themes.Cream
import com.raightmove.raightmove.viewmodels.AuthenticationViewModel

@Composable
fun ProfileScreen(navController: NavController, authenticationViewModel: AuthenticationViewModel) {

    Scaffold(bottomBar = { BottomMainNavBar(PROFILE_ROUTE, navController) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    authenticationViewModel.logOut()
                    val startDestination =
                        if (authenticationViewModel.hasUser) HOME_ROUTE else AUTHENTICATION_ROUTE
                    navController.navigate(startDestination)
                }, colors = ButtonColors(
                    contentColor = Cream,
                    containerColor = Bronze,
                    disabledContentColor = Color.Black,
                    disabledContainerColor = Color.Gray
                )
            ) {
                Text(text = "Sign out")
            }
        }
    }
}