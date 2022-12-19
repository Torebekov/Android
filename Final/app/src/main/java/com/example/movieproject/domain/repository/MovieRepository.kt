package com.example.movieproject.domain.repository

import androidx.lifecycle.LiveData
import com.example.movieproject.data.models.TVProgram
import com.example.movieproject.databinding.TvProgramItemBinding
import com.example.movieproject.domain.model.Movie

interface MovieRepository {
    fun getMoviesTop(): LiveData<List<Movie>>
    suspend fun getTvProgramsTop(page: Int): LiveData<List<TVProgram>>
    suspend fun getMovieById(id: Int): LiveData<Movie>
}