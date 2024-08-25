package com.raightmove.raightmove.ui.screens.main

import Destinations.CAMERA_ROUTE
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageProxy
import androidx.camera.view.PreviewView
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult
import com.raightmove.raightmove.models.AnalysisRequestBody
import com.raightmove.raightmove.models.Feedback
import com.raightmove.raightmove.models.Joint
import com.raightmove.raightmove.models.Training
import com.raightmove.raightmove.ui.components.BottomMainNavBar
import com.raightmove.raightmove.ui.components.analysis.ExercisePreview
import com.raightmove.raightmove.ui.components.analysis.GetReview
import com.raightmove.raightmove.ui.components.analysis.PickExercise
import com.raightmove.raightmove.ui.components.analysis.SelectExercisePrompt
import com.raightmove.raightmove.ui.components.analysis.SendLandmarksForReviewButton
import com.raightmove.raightmove.ui.components.analysis.ShowReview
import kotlin.reflect.KFunction2

@Composable
fun CameraScreen(
    navController: NavController,
    exercise: String?,
    state: String,
    landmarksSource: LiveData<PoseLandmarkerResult?>,
    videoLandmarks: MutableList<PoseLandmarkerResult>,
    joints: List<Joint>?,
    feedbacks: List<Feedback>?,
    startCamera: KFunction2<Context, (Context, ImageProxy) -> Unit, Unit>,
    processImage: (Context, ImageProxy) -> Unit,
    stopCamera: () -> Unit,
    setPreviewView: (PreviewView) -> Unit,
    fetchFeedback: suspend (AnalysisRequestBody) -> String,
    setJoints: (List<Joint>) -> Unit,
    setFeedback: (String) -> Unit,
    setState: (String) -> Unit,
    setExercise: (String) -> Unit,
    getUserID: () -> String,
    addTraining: (Context, String, Training) -> Unit
) {
    val context = LocalContext.current

    val cameraPermissionResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                startCamera(context, processImage)
            } else {
                Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        })

    Scaffold(bottomBar = { BottomMainNavBar(CAMERA_ROUTE, navController) }) { padding ->
        SideEffect {
            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                startCamera(context, processImage)
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
                when (state) {
                    "pick_exercise" -> SelectExercisePrompt()
                    "video_analysis" -> ExercisePreview(
                        landmarksSource = landmarksSource,
                        videoLandmarks = videoLandmarks,
                        setPreviewView = setPreviewView
                    )

                    "send_for_review" -> {
                        stopCamera()
                        GetReview(
                            exercise = exercise!!,
                            videoLandmarks = videoLandmarks,
                            fetchFeedback = fetchFeedback,
                            setState = setState,
                            setJoints = setJoints,
                            setFeedback = setFeedback
                        )
                    }

                    "review" -> {
                        ShowReview(
                            exercise = exercise!!,
                            joints = joints,
                            feedbacks = feedbacks,
                            getUserID = getUserID,
                            addTraining = addTraining,
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(padding)
            ) {
                when (state) {
                    "pick_exercise" -> PickExercise(
                        setState = setState, setExercise = setExercise
                    )

                    "video_analysis" -> {
                        SendLandmarksForReviewButton(
                            setState = setState, landmarksSource = landmarksSource
                        )
                    }

                    "review" -> {}
                }
            }
        }
    }
}
