package com.raightmove.raightmove.ui.components.analysis

import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult
import com.raightmove.raightmove.viewmodels.CameraViewModel
import com.raightmove.raightmove.viewmodels.ExerciseAnalysisViewModel

@Composable
fun DrawLandmarks(
    landmarks: PoseLandmarkerResult?,
    analysisViewModel: ExerciseAnalysisViewModel
) {
    landmarks?.let {
        if (it.landmarks().isNotEmpty()) {
            val landmarksData = it.landmarks()[0]
            analysisViewModel.videoLandmarks.add(it)
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

@Composable
fun ExercisePreview(
    cameraViewModel: CameraViewModel,
    analysisViewModel: ExerciseAnalysisViewModel,
) {
    val landmarks = analysisViewModel.currentLandmarks.observeAsState()
    AndroidView(factory = { context ->
        PreviewView(context).apply {
            id = PreviewView.generateViewId()
            cameraViewModel.setPreviewView(this)
        }
    }, modifier = Modifier.fillMaxSize())

    DrawLandmarks(landmarks.value, analysisViewModel)
}