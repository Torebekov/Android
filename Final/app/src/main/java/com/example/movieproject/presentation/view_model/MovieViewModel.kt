package com.example.movieproject.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieproject.domain.model.Movie
import com.example.movieproject.domain.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(
    private val repository: MovieRepository
    ): ViewModel(){

    fun fetchRepoList(): LiveData<List<Movie>> {
        return repository.getMoviesTop()
    }
}