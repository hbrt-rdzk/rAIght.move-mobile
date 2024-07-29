package com.raightmove.raightmove.models

import com.squareup.moshi.Json

data class Feedback(
    val exercise: String,
    val repetition: Int,
    @Json(name = "repetition_start_frame") val repetitionStartFrame: Int,
    @Json(name = "repetition_finish_frame") val repetitionFinishFrame: Int,
    @Json(name = "mistake_name") val mistakeName: String,
    @Json(name = "fix_info") val fixInfo: String,
    @Json(name = "angle_name") val angleName: String,
    val threshold: Float
)