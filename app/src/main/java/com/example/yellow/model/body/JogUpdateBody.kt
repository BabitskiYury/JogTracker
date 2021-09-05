package com.example.yellow.model.body

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class JogUpdateBody(
    val date: String,
    val time: Int,
    val distance: Float,
    val jog_id: Int,
    val user_id: String
)
