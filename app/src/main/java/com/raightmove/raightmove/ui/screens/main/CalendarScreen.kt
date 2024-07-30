package com.raightmove.raightmove.ui.screens.main

import Destinations.CAMERA_ROUTE
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.raightmove.raightmove.ui.components.BottomMainNavBar
import com.raightmove.raightmove.ui.components.ProgressIndicator
import com.raightmove.raightmove.ui.themes.Bronze
import com.raightmove.raightmove.ui.themes.Cream
import com.raightmove.raightmove.viewmodels.AuthenticationViewModel
import com.raightmove.raightmove.viewmodels.UserInfoViewModel

@Composable
fun CalendarScreen(
    navController: NavController,
    userInfoViewModel: UserInfoViewModel = viewModel(),
    authenticationViewModel: AuthenticationViewModel = viewModel()
) {
    val trainings = userInfoViewModel.userTrainings.collectAsState()

    LaunchedEffect(Unit) {
        val userId = authenticationViewModel.userId
        userInfoViewModel.fetchTrainings(userId)
    }

    Scaffold(bottomBar = { BottomMainNavBar(CAMERA_ROUTE, navController) }) { padding ->
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
            when {
                trainings.value != null -> {
                    Text(text = "123")
                }

                else -> {
                    Spacer(modifier = Modifier.padding(padding))
                    ProgressIndicator()
                }
            }
        }

    }
}