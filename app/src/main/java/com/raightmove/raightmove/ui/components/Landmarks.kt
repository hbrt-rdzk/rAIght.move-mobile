package com.raightmove.raightmove.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult

@Composable
fun DrawLandmarks(landmarks: State<PoseLandmarkerResult?>) {
    landmarks.value?.let {
        if (it.landmarks().isNotEmpty()) {
            val landmarksData = it.landmarks()[0]
            Canvas(modifier = Modifier.fillMaxSize()) {
                landmarksData.forEach { landmark ->
                    val visibility = landmark.visibility().orElse(0.0F)
                    if (visibility >= 0.5) {
                        drawCircle(
                            color = Color.Red,
                            radius = 20f,
                            center = Offset(
                                y = landmark.x() * size.width,
                                x = (1 - landmark.y()) * size.width
                            )
                        )
                    }
                }
            }
        }
    }
}