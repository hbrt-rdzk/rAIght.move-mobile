package com.raightmove.raightmove.ui.components.analysis

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.raightmove.raightmove.models.AnalysisRequestBody
import com.raightmove.raightmove.services.LandmarksProcessor
import com.raightmove.raightmove.ui.components.ProgressIndicator
import com.raightmove.raightmove.viewmodels.ExerciseAnalysisViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun GetReview(analysisViewModel: ExerciseAnalysisViewModel) {
    val feedback by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    val exercise = analysisViewModel.exercise.collectAsState()

    val processor = LandmarksProcessor()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        LaunchedEffect(Unit) {
            val processedJoints = processor.process(analysisViewModel.videoLandmarks)
            val analysisRequestBody =
                AnalysisRequestBody(exercise = exercise.value.orEmpty(), joints = processedJoints)
            val response = analysisViewModel.fetchFeedback(analysisRequestBody)

            analysisViewModel.setJoints(processedJoints)
            analysisViewModel.setFeedback(response)
            analysisViewModel.setState("review")
            isLoading = false
        }
        if (isLoading) {
            ProgressIndicator()
        } else {
            Text(text = feedback)
        }
    }
}
