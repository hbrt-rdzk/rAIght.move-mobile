package com.raightmove.raightmove.ui.components.analysis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raightmove.raightmove.models.Training
import com.raightmove.raightmove.ui.themes.Bronze
import com.raightmove.raightmove.ui.themes.Cream
import com.raightmove.raightmove.viewmodels.AuthenticationViewModel
import com.raightmove.raightmove.viewmodels.ExerciseAnalysisViewModel
import com.raightmove.raightmove.viewmodels.UserInfoViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ShowReview(
    analysisViewModel: ExerciseAnalysisViewModel = viewModel(),
    userInfoViewModel: UserInfoViewModel = viewModel(),
    authenticationViewModel: AuthenticationViewModel = viewModel()
) {
    val feedback = analysisViewModel.feedback.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(feedback.value?.get(0).toString())
        Button(
            onClick = {
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val currentDateTime = current.format(formatter)

                val training = Training(
                    exercise = analysisViewModel.exercise.value!!,
                    feedbacks = analysisViewModel.feedback.value!!,
                    joints = analysisViewModel.joints.value!!,
                    date = currentDateTime
                )
                val userId = authenticationViewModel.userId
                userInfoViewModel.addTraining(context, userId, training)
            }, colors = ButtonColors(
                contentColor = Cream,
                containerColor = Bronze,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color.Gray
            )
        ) {
            Text(text = "Save training")
        }
    }
}
