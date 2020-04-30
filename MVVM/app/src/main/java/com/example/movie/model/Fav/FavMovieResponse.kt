package com.example.movie.model.Fav

import com.example.movie.model.FavMovies
import com.google.gson.annotations.SerializedName

data class FavMovieResponse(
    @SerializedName("results")
    val results: List<FavMovies>
)