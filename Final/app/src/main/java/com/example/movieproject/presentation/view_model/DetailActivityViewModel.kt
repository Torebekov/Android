package com.example.movieproject.presentation.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieproject.BuildConfig
import com.example.movieproject.data.api.RetrofitService
import com.example.movieproject.domain.model.Movie
import com.example.movieproject.domain.model.Video
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DetailActivityViewModel(
        private val context: Context
) : ViewModel(), CoroutineScope {
    private val job = Job()
    val liveData = MutableLiveData<Video>()


    fun loadTrailer(movieId: Int?){
        launch {
            val list = withContext(Dispatchers.IO){
                try {
                    val responseMovie = RetrofitService.getMovieApi()
                            .getVideos(movieId,
                            BuildConfig.THE_MOVIE_DB_API_TOKEN)
                    if(responseMovie.isSuccessful){
                        val result = responseMovie.body()?.results?.first()
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