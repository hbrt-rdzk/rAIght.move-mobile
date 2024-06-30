package com.raightmove.raightmove.viewmodels

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.core.Delegate
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarker
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult
import java.lang.ref.WeakReference
import java.util.concurrent.Executors

class CameraViewModel : ViewModel() {
    private var previewViewRef: WeakReference<PreviewView>? = null
    private var poseLandmarker: PoseLandmarker? = null
    private val _landmarks = MutableLiveData<PoseLandmarkerResult>()
    val landmarks: LiveData<PoseLandmarkerResult> get() = _landmarks

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

            previewView?.let { it ->
                preview.setSurfaceProvider(it.surfaceProvider)

                val imageAnalyzer = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(Executors.newSingleThreadExecutor()) { imageProxy ->
                            processImageProxy(imageProxy, context)
                        }
                    }

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        context as LifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalyzer
                    )
                } catch (exc: Exception) {
                    exc.printStackTrace() // Handle exception
                }
            }
        }, ContextCompat.getMainExecutor(context))
    }

    private fun processImageProxy(imageProxy: ImageProxy, context: Context) {
        val bitmapImage = imageProxy.toBitmap()

        if (poseLandmarker == null) {
            val baseOptions = BaseOptions.builder()
                .setDelegate(Delegate.CPU)
                .setModelAssetPath("pose_landmarker_lite.task")
                .build()

            val options = PoseLandmarker.PoseLandmarkerOptions.builder()
                .setBaseOptions(baseOptions)
                .build()
            poseLandmarker = PoseLandmarker.createFromOptions(context, options)
        }

        val mpImage = BitmapImageBuilder(bitmapImage).build()
        val result = poseLandmarker?.detect(mpImage)
        println(result?.landmarks())
        result?.let {
            _landmarks.postValue(it)
        }
        imageProxy.close()
    }
}
