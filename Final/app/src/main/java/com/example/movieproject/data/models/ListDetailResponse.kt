package com.example.movieproject.data.models

import com.example.movieproject.domain.model.Movie

data class ListDetailResponse(
    val created_by: String,
    val description: String,
    val favorite_count: Int,
    val id: String,
    val iso_639_1: String,
    val item_count: Int,
    val items: List<Movie>,
    val name: String,
    val poster_path: Any
)