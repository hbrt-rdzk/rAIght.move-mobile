package com.raightmove.raightmove.viewmodel

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

class CameraViewModel : ViewModel() {
    private var previewViewRef: WeakReference<PreviewView>? = null

    fun setPreviewView(previewView: PreviewView) {
        previewViewRef = WeakReference(previewView)
    }

    fun startCamera(context: Context) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            val previewView = previewViewRef?.get()

            previewView?.let {
                preview.setSurfaceProvider(it.surfaceProvider)

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        context as LifecycleOwner,
                        cameraSelector,
                        preview
                    )
                } catch (exc: Exception) {
                    exc.printStackTrace() // Handle exception
                }
            }
        }, ContextCompat.getMainExecutor(context))
    }
}
