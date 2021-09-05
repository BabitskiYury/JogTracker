package com.example.yellow.model.body

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthBody(
    val uuid: String
)
