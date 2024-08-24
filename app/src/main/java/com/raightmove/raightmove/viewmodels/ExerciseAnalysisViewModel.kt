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
import com.raightmove.raightmove.models.AnalysisRequestBody
import com.raightmove.raightmove.models.Feedback
import com.raightmove.raightmove.models.Joint
import com.raightmove.raightmove.repositories.ExplainerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExerciseAnalysisViewModel(
    private val repository: ExplainerRepository = ExplainerRepository()
) : ViewModel() {
    private var poseLandmarker: PoseLandmarker? = null

    private val _exercise = MutableStateFlow<String?>(null)
    private val _analysisState = MutableStateFlow("pick_exercise")
    private val _landmarks = MutableLiveData<PoseLandmarkerResult?>(null)
    private val _joints = MutableStateFlow<List<Joint>?>(mutableListOf())
    private val _feedback = MutableStateFlow<List<Feedback>?>(mutableListOf())

    var exercise: StateFlow<String?> = _exercise
    var analysisState: StateFlow<String> = _analysisState
    var videoLandmarks = mutableListOf<PoseLandmarkerResult>()
    val currentLandmarks: LiveData<PoseLandmarkerResult?> = _landmarks
    val joints: StateFlow<List<Joint>?> = _joints
    val feedbacks: StateFlow<List<Feedback>?> = _feedback

    fun setExercise(exercise: String) {
        _exercise.value = exercise
    }

    fun setState(state: String) {
        _analysisState.value = state
    }

    fun setJoints(joints: List<Joint>) {
        _joints.value = joints
    }

    fun setFeedback(response: String) {
        val feedback = repository.convertResponseToFeedback(response)
        _feedback.value = feedback
    }

    suspend fun fetchFeedback(analysisData: AnalysisRequestBody): String {
        return repository.fetchFeedback(analysisData)
    }

    fun processImageProxy(context: Context, imageProxy: ImageProxy) {
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
