package com.raightmove.raightmove.viewmodels

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference
import java.util.concurrent.Executors

class CameraViewModel : ViewModel() {
    private var previewViewRef: WeakReference<PreviewView>? = null
    private var cameraProvider: ProcessCameraProvider? = null

    fun setPreviewView(previewView: PreviewView) {
        previewViewRef = WeakReference(previewView)
    }

    fun startCamera(context: Context, analysisViewModel: ExerciseAnalysisViewModel) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            val previewView = previewViewRef?.get()

            previewView?.let { it ->
                preview.setSurfaceProvider(it.surfaceProvider)

                val imageAnalyzer = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(Executors.newSingleThreadExecutor()) { imageProxy ->
                            analysisViewModel.processImageProxy(imageProxy, context)
                        }
                    }

                try {
                    cameraProvider?.unbindAll()
                    cameraProvider?.bindToLifecycle(
                        context as LifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalyzer
                    )
                } catch (exc: Exception) {
                    exc.printStackTrace()
                }
            }
        }, ContextCompat.getMainExecutor(context))
    }

    fun stopCamera() {
        cameraProvider?.unbindAll()
    }
}
