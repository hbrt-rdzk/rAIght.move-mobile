package com.raightmove.raightmove.ui.screens.main

import Destinations.CAMERA_ROUTE
import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.raightmove.raightmove.ui.components.BottomMainNavBar
import com.raightmove.raightmove.ui.components.analysis.ExercisePreview
import com.raightmove.raightmove.ui.components.analysis.GetReview
import com.raightmove.raightmove.ui.components.analysis.PickExercise
import com.raightmove.raightmove.ui.components.analysis.SelectExercisePrompt
import com.raightmove.raightmove.ui.components.analysis.SendLandmarksForReviewButton
import com.raightmove.raightmove.ui.components.analysis.ShowReview
import com.raightmove.raightmove.viewmodels.CameraViewModel
import com.raightmove.raightmove.viewmodels.ExerciseAnalysisViewModel

@Composable
fun CameraScreen(
    navController: NavController,
    analysisViewModel: ExerciseAnalysisViewModel = viewModel(),
    cameraViewModel: CameraViewModel = viewModel()
) {
    val context = LocalContext.current
    val state = analysisViewModel.analysisState.collectAsState()

    val cameraPermissionResult =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(),
            onResult = { granted ->
                if (granted) {
                    cameraViewModel.startCamera(context, analysisViewModel)
                } else {
                    Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
                }
            })

    Scaffold(bottomBar = { BottomMainNavBar(CAMERA_ROUTE, navController) }) { padding ->
        SideEffect {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                cameraViewModel.startCamera(context, analysisViewModel)
            } else {
                cameraPermissionResult.launch(Manifest.permission.CAMERA)
            }
        }
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
                text = "Posture fixer",
                modifier = Modifier.padding(24.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black
            )
        }
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(padding)
            ) {
                when (state.value) {
                    "pick_exercise" -> SelectExercisePrompt()
                    "video_analysis" -> ExercisePreview(cameraViewModel, analysisViewModel)
                    "send_for_review" -> {
                        cameraViewModel.stopCamera()
                        GetReview(analysisViewModel)
                    }

                    "review" -> {
                        ShowReview(analysisViewModel)
                    }
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(padding)
            ) {
                when (state.value) {
                    "pick_exercise" -> PickExercise(analysisViewModel)
                    "video_analysis" -> {
                        SendLandmarksForReviewButton(analysisViewModel)
                    }

                    "review" -> {}
                }
            }
        }
    }
}



