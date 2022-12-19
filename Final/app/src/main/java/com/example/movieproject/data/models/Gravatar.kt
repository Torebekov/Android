package com.example.movieproject.data.models

import androidx.room.Entity
import com.google.gson.annotations.SerializedName


data class Gravatar(
    @SerializedName("hash")
    val hash: String
)