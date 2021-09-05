package com.example.yellow.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class JogUpdateResponse(
    val response: UpdatedItem
)

@JsonClass(generateAdapter = true)
data class UpdatedItem(
    val id: Int,
    val user_id: Int,
    val distance: Float,
    val time: Int,
    val date: String
)
