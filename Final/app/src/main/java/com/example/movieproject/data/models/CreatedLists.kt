package com.example.movieproject.data.models

data class CreatedLists(
    val page: Int,
    val results: List<InfoList>,
    val total_pages: Int,
    val total_results: Int
)