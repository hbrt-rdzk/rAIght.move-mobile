package com.raightmove.raightmove.ui.components.analysis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult
import com.raightmove.raightmove.ui.themes.Bronze
import com.raightmove.raightmove.ui.themes.Cream

@Composable
fun SendLandmarksForReviewButton(
    setState: (String) -> Unit, landmarksSource: LiveData<PoseLandmarkerResult?>
) {
    val currentLandmarks by landmarksSource.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { setState("send_for_review") }, colors = ButtonColors(
                contentColor = Cream,
                containerColor = Bronze,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color.Gray
            ), enabled = currentLandmarks != null
        ) {
            Text(text = "Stop and get review")
        }
    }
}