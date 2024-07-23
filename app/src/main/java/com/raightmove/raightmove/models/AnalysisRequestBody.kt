package com.raightmove.raightmove.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class AnalysisRequestBody(
    @Json(name = "joints_data") val joints: List<Joint>,
    val exercise: String
)
