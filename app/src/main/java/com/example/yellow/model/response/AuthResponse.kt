package com.example.yellow.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponse(
    val response: Auth
)

@JsonClass(generateAdapter = true)
data class Auth(
    val access_token: String?,
    val token_type: String?,
    val expires_in: Int?,
    val scope: String?,
    val created_at: Int?,
)
