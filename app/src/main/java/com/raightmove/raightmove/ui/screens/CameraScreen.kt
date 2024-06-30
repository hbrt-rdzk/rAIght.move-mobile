package com.raightmove.raightmove.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raightmove.raightmove.ui.components.DrawLandmarks
import com.raightmove.raightmove.viewmodels.CameraViewModel

@Composable
fun CameraScreen(viewModel: CameraViewModel = viewModel()) {
    val context = LocalContext.current
    var exercise by remember { mutableStateOf<String?>(null) }
    val landmarks = viewModel.landmarks.observeAsState()

    val cameraPermissionResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                viewModel.startCamera(context)
            } else {
                Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    )

    SideEffect {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.startCamera(context)
        } else {
            cameraPermissionResult.launch(Manifest.permission.CAMERA)
        }
    }

    Box(modifier = Modifier.fillMaxHeight(0.5f)) {
        if (exercise == null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { exercise = "squat" }) { Text(text = "Squat") }
                Button(onClick = { exercise = "lunges" }) { Text(text = "Lunges") }
                Button(onClick = { exercise = "plank" }) { Text(text = "Plank") }
            }
        } else {
            AndroidView(factory = { ctx ->
                val previewView = PreviewView(ctx).apply {
                    id = PreviewView.generateViewId()
                }
                viewModel.setPreviewView(previewView)
                previewView
            }, modifier = Modifier.fillMaxSize())
            landmarks.value?.let {
                if (it.worldLandmarks().isNotEmpty()) {
                    DrawLandmarks(landmarks = it.landmarks()[0])
                }
            }
        }
    }
}

