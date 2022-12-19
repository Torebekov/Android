package com.example.movieproject.presentation.view_model

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieproject.BuildConfig
import com.example.movieproject.data.api.RetrofitService
import com.example.movieproject.data.models.Singleton
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ProfileViewModel(private val context: Context): ViewModel(), CoroutineScope {
    val liveData = MutableLiveData<String>()
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun logout(){
        launch {
            val preferences = context?.getSharedPreferences("Username", 0) as SharedPreferences
            preferences.edit().clear().commit()
            val body: JsonObject = JsonObject().apply {
                addProperty("session_id", Singleton.getSession())
            }
            try {
                val response = RetrofitService.getMovieApi().getSession(BuildConfig.THE_MOVIE_DB_API_TOKEN,body)
                if(response.isSuccessful){
                    liveData.postValue("Good")
                }
            }catch (e:Exception){
                liveData.postValue("Problems with network")
            }
        }
    }

}