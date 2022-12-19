package com.example.movieproject.data.models

import com.example.movieproject.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results")
    val results: List<Movie>
)