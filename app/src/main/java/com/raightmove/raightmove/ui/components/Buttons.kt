package com.raightmove.raightmove.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.raightmove.raightmove.R
import com.raightmove.raightmove.ui.themes.Bronze
import com.raightmove.raightmove.ui.themes.Cream
import com.raightmove.raightmove.viewmodels.ExerciseAnalysisViewModel

@Composable
fun GoogleButtonContent() {
    return Row(
        horizontalArrangement = Arrangement.Absolute.Left,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.google_color_svgrepo_com),
            contentDescription = "Google icon",
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text("Continue with google")
    }
}

@Composable
fun PickExercise(analysisViewModel: ExerciseAnalysisViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = {
                analysisViewModel.setExercise("squat")
                analysisViewModel.setState("video_analysis")
            }, colors = ButtonColors(
                contentColor = Cream,
                containerColor = Bronze,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color.Gray
            )
        ) {
            Text(text = "Squat")
        }
        Button(
            onClick = {
                analysisViewModel.setExercise("lunges")
                analysisViewModel.setState("video_analysis")
            }, colors = ButtonColors(
                contentColor = Cream,
                containerColor = Bronze,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color.Gray
            )
        ) {
            Text(text = "Lunges")
        }
        Button(
            onClick = {
                analysisViewModel.setExercise("plank")
                analysisViewModel.setState("video_analysis")
            }, colors = ButtonColors(
                contentColor = Cream,
                containerColor = Bronze,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color.Gray
            )
        ) {
            Text(text = "Plank")
        }
    }
}

@Composable
fun FinishAnalysis(analysisViewModel: ExerciseAnalysisViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { analysisViewModel.setState("review") }, colors = ButtonColors(
                contentColor = Cream,
                containerColor = Bronze,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color.Gray
            )
        ) {
            Text(text = "Stop and get review")
        }
    }
}