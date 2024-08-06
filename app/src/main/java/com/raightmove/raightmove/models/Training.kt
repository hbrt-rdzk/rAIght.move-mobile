package com.raightmove.raightmove.models

data class Training(
    val exercise: String = "",
    val date: String = "",
    val joints: List<Joint> = emptyList(),
    val feedbacks: List<Feedback> = emptyList()
)
