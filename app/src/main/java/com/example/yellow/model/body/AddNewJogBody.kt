package com.example.yellow.model.body

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddNewJogBody(
    val date: String,
    val time: Int,
    val distance: Float
)
