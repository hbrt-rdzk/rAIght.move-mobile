package com.raightmove.raightmove.models

data class Training(
    val exercise: String,
    val date: String,
    val joints: List<Joint>,
    val feedbacks: List<Feedback>
)
