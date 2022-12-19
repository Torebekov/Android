package com.example.movieproject.data.models

data class RequestToken(
    val expires_at: String,
    val request_token: String,
    val success: Boolean
)