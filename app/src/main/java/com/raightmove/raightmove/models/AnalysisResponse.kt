package com.raightmove.raightmove.models

data class AnalysisResponse(
    val exercise: String,
    val repetition: Int,
    val repetitionStartFrame: Int,
    val repetitionFinishFrame: Int,
    val mistakeName: String,
    val fixInfo: String,
    val angleName: String,
    val threshold: Float
)