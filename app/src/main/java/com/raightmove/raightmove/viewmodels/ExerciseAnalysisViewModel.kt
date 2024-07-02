package com.raightmove.raightmove.viewmodels

import android.content.Context
import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.core.Delegate
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarker
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExerciseAnalysisViewModel : ViewModel() {
    private var poseLandmarker: PoseLandmarker? = null
    private val _exercise = MutableStateFlow<String?>(null)
    private val _analysisState = MutableStateFlow<String>("pick_exercise")
    private val _landmarks = MutableLiveData<PoseLandmarkerResult>()
    val currentLandmarks: LiveData<PoseLandmarkerResult> get() = _landmarks
    val videoLandmarks = mutableListOf<PoseLandmarkerResult>()
    var analysisState: StateFlow<String> = _analysisState
    var exercise: StateFlow<String?> = _exercise

    fun setExercise(exercise: String) {
        _exercise.value = exercise
    }

    fun setState(state: String) {
        _analysisState.value = state
    }

    fun processImageProxy(imageProxy: ImageProxy, context: Context) {
        viewModelScope.launch(Dispatchers.Default) {
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
            result?.let {
                _landmarks.postValue(it)
            }
            imageProxy.close()
        }
    }
}
