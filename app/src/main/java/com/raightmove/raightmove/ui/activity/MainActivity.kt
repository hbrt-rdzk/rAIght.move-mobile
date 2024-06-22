package com.raightmove.raightmove.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raightmove.raightmove.ui.theme.RAIghtmoveTheme
import com.raightmove.raightmove.viewmodel.CameraViewModel

const val CAMERA_PERMISSION_REQUEST_CODE = 100

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAppPermission()
        enableEdgeToEdge()
        setContent {
            RAIghtmoveTheme {
                MainScreen()
            }
        }
    }

    private fun checkAppPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        } else {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun MainScreen(viewModel: CameraViewModel = viewModel()) {
    val context = LocalContext.current

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
        AndroidView(factory = { ctx ->
            val previewView = PreviewView(ctx).apply {
                id = PreviewView.generateViewId()
            }
            viewModel.setPreviewView(previewView)
            previewView
        }, modifier = Modifier.fillMaxSize())
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RAIghtmoveTheme {
        MainScreen()
    }
}
