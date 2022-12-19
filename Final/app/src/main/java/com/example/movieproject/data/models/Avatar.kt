package com.example.movieproject.data.models

import androidx.room.Entity
import com.google.gson.annotations.SerializedName


data class Avatar(
    @SerializedName("tmdb")
    val tmdb: Tmdb
)