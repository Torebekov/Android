package com.example.movie.model.favourite

import com.google.gson.annotations.SerializedName


data class FavMovieResponse(
    @SerializedName("results")
    val results: List<FavMovies>
)