package com.example.movie.model.movie

import com.example.movie.model.movie.Movie
import com.google.gson.annotations.SerializedName


data class MovieResponse(
    @SerializedName("results")
    val results: List<Movie>,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)