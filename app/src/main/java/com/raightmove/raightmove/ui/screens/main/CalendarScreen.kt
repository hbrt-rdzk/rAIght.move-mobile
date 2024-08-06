package com.raightmove.raightmove.ui.screens.main

import Destinations.CALENDAR_ROUTE
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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

    Scaffold(bottomBar = { BottomMainNavBar(CALENDAR_ROUTE, navController) }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when {
                trainings.value != null -> {
                    LazyColumn(
                    ) {
                        trainings.value!!.forEach { training ->
                            items(training.hashCode()) {
                                Button(
                                    onClick = {},
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    colors = ButtonColors(
                                        contentColor = Cream,
                                        containerColor = Bronze,
                                        disabledContentColor = Color.Black,
                                        disabledContainerColor = Color.Gray
                                    )
                                ) {
                                    Text(training.date)
                                }
                            }
                        }
                    }
                    Text(text = trainings.value.toString())
                }

                else -> {
                    Spacer(modifier = Modifier.padding(padding))
                    ProgressIndicator()
                }
            }
        }

    }
}