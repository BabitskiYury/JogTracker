package com.example.yellow.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class JogsResponse(
    val response: Jogs
)

@JsonClass(generateAdapter = true)
data class Jogs(
    val jogs: List<Jog>
)

@JsonClass(generateAdapter = true)
data class Jog(
    val id: Int,
    val user_id: String,
    val distance: Float,
    val time: Int,
    val date: Long
)
