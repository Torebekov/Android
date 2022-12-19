package com.example.movieproject.presentation.view_model

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieproject.BuildConfig
import com.example.movieproject.data.api.RetrofitService
import com.example.movieproject.data.models.TVProgram
import com.example.movieproject.data.models.TVProgramResponse
import com.example.movieproject.domain.model.Movie
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class TVProgramViewModel(
    private val context: Context
) : ViewModel(), CoroutineScope {
    private val job = Job()
    val liveData = MutableLiveData<List<TVProgram>>()
    fun loadTVShows(page: Int){
        launch {
            val programs = withContext(Dispatchers.IO){
                try {
                    val response = RetrofitService.getMovieApi()
                        .getPopularTvPrograms(BuildConfig.THE_MOVIE_DB_API_TOKEN,"ru",page)
                    if(response.isSuccessful){
                        val results = response.body()?.results
                        results
                    }
                    else{
                        null
                    }
                }
                catch (e:Exception){
                    null
                }
            }
            liveData.postValue(programs)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
}