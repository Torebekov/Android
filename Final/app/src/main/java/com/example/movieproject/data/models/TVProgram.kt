package com.example.movieproject.data.models

data class TVProgram (
    val backdrop_path: String,
    val first_air_date: String,
    val genre_ids: List<Int>,
    val id: Int,
    val name: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val vote_average: Double,
    val vote_count: Int
){

    fun getPosterPath(): String {
        return "https://image.tmdb.org/t/p/w500" + poster_path
    }
}