package com.example.movieproject.data.models

import androidx.room.Entity
import com.google.gson.annotations.SerializedName


data class Tmdb(
    @SerializedName("avatar_path")
    val avatar_path: String
)