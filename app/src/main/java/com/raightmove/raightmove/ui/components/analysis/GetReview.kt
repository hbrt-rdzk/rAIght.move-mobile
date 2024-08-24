package com.raightmove.raightmove.ui.components.analysis

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult
import com.raightmove.raightmove.models.AnalysisRequestBody
import com.raightmove.raightmove.models.Joint
import com.raightmove.raightmove.services.LandmarksProcessor
import com.raightmove.raightmove.ui.components.ProgressIndicator

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun GetReview(
    exercise: String,
    videoLandmarks: MutableList<PoseLandmarkerResult>,
    fetchFeedback: suspend (AnalysisRequestBody) -> String,
    setJoints: (List<Joint>) -> Unit,
    setFeedback: (String) -> Unit,
    setState: (String) -> Unit

) {
    val feedback by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    val processor = LandmarksProcessor()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        LaunchedEffect(Unit) {
            val processedJoints = processor.process(videoLandmarks)
            val analysisRequestBody =
                AnalysisRequestBody(exercise = exercise, joints = processedJoints)
            val response = fetchFeedback(analysisRequestBody)

            setJoints(processedJoints)
            setFeedback(response)
            setState("review")
            isLoading = false
        }
        if (isLoading) {
            ProgressIndicator()
        } else {
            Text(text = feedback)
        }
    }
}
