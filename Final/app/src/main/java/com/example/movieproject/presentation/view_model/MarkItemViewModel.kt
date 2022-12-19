package com.example.movieproject.presentation.view_model

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieproject.BuildConfig
import com.example.movieproject.data.api.RetrofitService
import com.example.movieproject.data.models.Singleton
import com.example.movieproject.domain.model.Movie
import com.example.movieproject.domain.model.User
import com.example.movieproject.domain.model.Video
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.reflect.Type
import kotlin.coroutines.CoroutineContext

class MarkItemViewModel (
    private val context: Context
) : ViewModel(), CoroutineScope {
    val liveData = MutableLiveData<String>()
    val preferences = context.getSharedPreferences("Username",0)
    val json: String? = preferences.getString("user", null)
    private val job = Job()
    private var favourite = false

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun markAsFavouriteMovie(movie: Movie?){
        launch {
            try {
                val gsonGen = Gson()
                val type: Type = object : TypeToken<User>() {}.type
                val user = gsonGen.fromJson<User>(json, type)

                val body = JsonObject().apply {
                    addProperty("media_type", "movie")
                    addProperty("media_id", movie?.id)
                    addProperty("favorite", true)
                }
                val response = RetrofitService.getMovieApi().markAsFavourite(user.accountId,BuildConfig.THE_MOVIE_DB_API_TOKEN,user.sessionId,body)
                if(response.isSuccessful){
                    liveData.postValue("Changes applied")
                }
            }catch (e:Exception){
                liveData.postValue("Problem With network")
            }
        }
    }

    fun unmarkAsFavourite(movie: Movie?){
        launch {
            try {
                val gsonGen = Gson()
                val type: Type = object : TypeToken<User>() {}.type
                val user = gsonGen.fromJson<User>(json, type)

                val body = JsonObject().apply {
                    addProperty("media_type", "movie")
                    addProperty("media_id", movie?.id)
                    addProperty("favorite", false)
                }
                val response = RetrofitService.getMovieApi().markAsFavourite(user.accountId,BuildConfig.THE_MOVIE_DB_API_TOKEN,user.sessionId,body)
                if(response.isSuccessful){
                    liveData.postValue("Changes applied")
                }
            }catch (e:Exception){
                liveData.postValue("Problem With network")
            }
        }
    }

    fun getFavourites(){
        launch {
            try {
                val gsonGen = Gson()
                val type: Type = object : TypeToken<User>() {}.type
                val user = gsonGen.fromJson<User>(json, type)

                val response = RetrofitService.getMovieApi().getFavorites(user.accountId,BuildConfig.THE_MOVIE_DB_API_TOKEN,user.sessionId)
                if(response.isSuccessful){
                    liveData.postValue("Changes applied")
                }
            }catch (e:Exception){
                liveData.postValue("Problem With network")
            }
        }

    }

    fun deleteFromList(listId: Int?,movieId: Int?){
        launch {
            try {
                val gsonGen = Gson()
                val type: Type = object : TypeToken<User>() {}.type
                val user = gsonGen.fromJson<User>(json, type)

                val body = JsonObject().apply {
                    addProperty("media_id", movieId)
                }
                val response = RetrofitService.getMovieApi().deleteFromList(listId,BuildConfig.THE_MOVIE_DB_API_TOKEN,user.sessionId,body)
                if (response.isSuccessful){
                    Toast.makeText(context,"You are successfully deleted", Toast.LENGTH_SHORT).show()
                    liveData.postValue("ListUpgrade")
                }
                else{
                    Toast.makeText(context,"There network error or you don't have List", Toast.LENGTH_SHORT).show()
                }
            }
            catch (e:Exception){
                Toast.makeText(context, "Error is :$e", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun clearAllPostsFromList(listId: Int?){
        launch {
            try {
                val gsonGen = Gson()
                val type: Type = object : TypeToken<User>() {}.type
                val user = gsonGen.fromJson<User>(json, type)
                val response = RetrofitService.getMovieApi().clearAllPostsFromList(listId,BuildConfig.THE_MOVIE_DB_API_TOKEN,user.sessionId,true)
                if (response.isSuccessful){
                    Toast.makeText(context,"You are successfully deleted", Toast.LENGTH_SHORT).show()
                    liveData.postValue("ListClear")
                }
                else{
                    Toast.makeText(context,"There network error or you don't have List", Toast.LENGTH_SHORT).show()
                }
            }
            catch (e:Exception){
                Toast.makeText(context, "Error is :$e", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun deleteList(listId: Int?){
        launch {
            try {
                val gsonGen = Gson()
                val type: Type = object : TypeToken<User>() {}.type
                val user = gsonGen.fromJson<User>(json, type)

                val response = RetrofitService.getMovieApi().deleteList(listId.toString(),BuildConfig.THE_MOVIE_DB_API_TOKEN,user.sessionId)
                if (response.isSuccessful){
                    Toast.makeText(context,"You are successfully deleted", Toast.LENGTH_SHORT).show()
                    liveData.postValue("ListDelete")
                }
                else{
                    Toast.makeText(context,"There network error or you don't have List", Toast.LENGTH_SHORT).show()
                }
            }
            catch (e:Exception){
                Toast.makeText(context, "Error is :$e", Toast.LENGTH_SHORT).show()
            }
        }
    }
}