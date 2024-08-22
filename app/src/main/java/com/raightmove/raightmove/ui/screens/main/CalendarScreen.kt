package com.raightmove.raightmove.ui.screens.main

import Destinations.CALENDAR_ROUTE
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.raightmove.raightmove.models.Training
import com.raightmove.raightmove.ui.components.BottomMainNavBar
import com.raightmove.raightmove.ui.components.PreviousTrainingButton
import com.raightmove.raightmove.ui.components.ProgressIndicator

@Composable
fun CalendarScreen(
    navController: NavController,
    trainings: List<Training>?,
    getUserID: () -> String,
    fetchTraining: suspend (String) -> Unit
) {

    LaunchedEffect(Unit) {
        val userId = getUserID()
        fetchTraining(userId)
    }

    Scaffold(bottomBar = { BottomMainNavBar(CALENDAR_ROUTE, navController) }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 8.dp)
                    .background(
                        color = Color.White
                    )
                    .padding(6.dp),
            ) {
                Text(
                    text = "Previous trainings",
                    modifier = Modifier.padding(24.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.Black
                )
            }

            when {
                trainings != null -> {
                    LazyColumn {
                        items(trainings) { training ->
                            PreviousTrainingButton(
                                training.exercise,
                                training.date,
                                training.feedbacks.size
                            ) {}
                        }
                    }
                }

                else -> {
                    Spacer(modifier = Modifier.padding(padding))
                    ProgressIndicator()
                }
            }
        }
    }
}