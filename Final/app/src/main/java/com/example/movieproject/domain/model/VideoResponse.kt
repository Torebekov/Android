package com.example.movieproject.domain.model

import com.google.gson.annotations.SerializedName

data class VideoResponse(
        @SerializedName("results")
        val results: List<Video>
) {
}