package com.example.movie.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val postId: Int,
    @SerializedName("email") val userId: String,
    @SerializedName("token") val title: String
)


