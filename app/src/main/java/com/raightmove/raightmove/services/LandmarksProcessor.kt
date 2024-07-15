package com.raightmove.raightmove.services

import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult
import com.raightmove.raightmove.models.Joint

class LandmarksProcessor {
    fun process(videoLandmarks: List<PoseLandmarkerResult>): List<Joint> {
        val outputData = mutableListOf<Joint>()
        videoLandmarks.forEachIndexed { frameIndex, frame ->
            val frameLandmarks = frame.landmarks()
            frameLandmarks[0].forEachIndexed { landmarkIndex, frameLandmarkData ->
                val joint = Joint(
                    frame = frameIndex,
                    id = landmarkIndex,
                    x = frameLandmarkData.x(),
                    y = frameLandmarkData.y(),
                    z = frameLandmarkData.z(),
                    visibility = frameLandmarkData.visibility().orElse(0.0F),
                )
                outputData.add(joint)
            }
        }
        return outputData
    }
}
