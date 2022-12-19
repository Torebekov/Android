package com.example.movieproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movieproject.data.models.TVProgram
import com.example.movieproject.domain.model.Movie
import com.example.movieproject.domain.repository.MovieRepository

class FakeRepository : MovieRepository {
    private val movieItems = mutableListOf<Movie>()
    private val observableMovie = MutableLiveData<Movie>()
    private val observableMovies = MutableLiveData<List<Movie>>(movieItems)
    private val observableTVPrograms = MutableLiveData<List<TVProgram>>()


    override fun getMoviesTop(): LiveData<List<Movie>> {
        return observableMovies
    }

    override suspend fun getTvProgramsTop(page: Int): LiveData<List<TVProgram>> {
        return observableTVPrograms
    }

    override suspend fun getMovieById(id: Int): LiveData<Movie> {
        return observableMovie
    }

    fun insertData(movie: Movie){
        movieItems.add(movie)
        refreshLiveData()
    }

    fun refreshLiveData(){
        observableMovies.postValue(movieItems)
    }
}