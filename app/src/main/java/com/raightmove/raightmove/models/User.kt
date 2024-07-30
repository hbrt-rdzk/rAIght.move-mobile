package com.raightmove.raightmove.models

data class User(
    val nick: String = "",
    val age: Int = 0,
    val gender: String = "",
    val height: Int = 0,
    val trainings: List<String> = emptyList()
)
