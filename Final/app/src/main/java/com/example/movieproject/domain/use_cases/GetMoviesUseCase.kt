package com.example.movieproject.domain.use_cases

import com.example.movieproject.data.api.MovieApi
import com.example.movieproject.domain.repository.MovieRepository

class GetMoviesUseCase(private val movieRepository: MovieRepository) {
    suspend fun getMovieList(page: Int) = movieRepository.getMoviesTop()
    suspend fun getTvProgramsTop(page: Int) = movieRepository.getTvProgramsTop(page)

}