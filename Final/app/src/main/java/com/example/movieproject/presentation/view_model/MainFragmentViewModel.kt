package com.example.movieproject.presentation.view_model

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieproject.BuildConfig
import com.example.movieproject.data.api.RetrofitService
import com.example.movieproject.domain.model.Movie
import com.example.movieproject.data.models.MovieResponse
import com.example.movieproject.data.models.TVProgram
import com.example.movieproject.data.models.TVProgramResponse
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class MainFragmentViewModel(
        private val context: Context
) : ViewModel(), CoroutineScope  {
    private val job = Job()
    val liveData = MutableLiveData<List<Movie>>()

    fun loadMovies(page: Int){
        launch {
            val list = withContext(Dispatchers.IO){
                try {
                    val responseMovie = RetrofitService.getMovieApi()
                        .getPopularMovieListCoroutine(com.example.movieproject.BuildConfig.THE_MOVIE_DB_API_TOKEN
                            ,"ru",
                            page)
                    if(responseMovie.isSuccessful){
                        val result = responseMovie.body()?.results
                        result
                    }
                    else{
                        null
                    }
                }
                catch (e:Exception){
                    null
                }
            }
            liveData.postValue(list)
        }
    }

    fun loadRecomendation(movieId: Int?){
        launch {
            val list = withContext(Dispatchers.IO){
                try {
                    val responseMovie = RetrofitService.getMovieApi()
                            .getRecommendationsofMovie(movieId,
                                    BuildConfig.THE_MOVIE_DB_API_TOKEN)
                    if(responseMovie.isSuccessful){
                        val result = responseMovie.body()?.results
                        result
                    }
                    else{
                        null
                    }
                }
                catch (e:Exception){
                    null
                }
            }
            liveData.postValue(list)
        }
    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
}

