package com.example.movieproject.presentation.view_model

import android.content.ClipDescription
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieproject.BuildConfig
import com.example.movieproject.data.api.RetrofitService
import com.example.movieproject.data.models.InfoList
import com.example.movieproject.domain.model.User
import com.example.movieproject.domain.model.Video
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import kotlin.coroutines.CoroutineContext

class AccountListViewModel (private val context: Context
) : ViewModel(), CoroutineScope {
    val preferences = context.getSharedPreferences("Username",0)
    val json: String? = preferences.getString("user", null)
    private val job = Job()
    val liveData = MutableLiveData<List<InfoList>>()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun createOwnList(name: String, description: String){
        launch {
            try {
                val gsonGen = Gson()
                val type: Type = object : TypeToken<User>() {}.type
                val user = gsonGen.fromJson<User>(json, type)

                val body = JsonObject().apply {
                    addProperty("name", name)
                    addProperty("description", description)
                    addProperty("language", "en")
                }
                val response = RetrofitService.getMovieApi().createList(BuildConfig.THE_MOVIE_DB_API_TOKEN,user.sessionId,body)
                if(response.isSuccessful){
                    Toast.makeText(context,"Your list is created",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(context,"Your list is not created",Toast.LENGTH_SHORT).show()
                }

            }catch (e:Exception){}
        }
    }

    fun getCreatedLists(){
        launch {
            try {
                val gsonGen = Gson()
                val type: Type = object : TypeToken<User>() {}.type
                val user = gsonGen.fromJson<User>(json, type)
                val response = RetrofitService.getMovieApi().getCreatedLists(user.accountId,BuildConfig.THE_MOVIE_DB_API_TOKEN,user.sessionId)
                if(response.isSuccessful){
                    val list = response.body()?.results
                    liveData.postValue(list)
                }
                else{
                    Toast.makeText(context,"You don't have list",Toast.LENGTH_SHORT).show()
                }
            }catch (e:Exception){}
        }
    }

    fun addMovieToList(listId: Int?, movieId: Int?){
        launch {
            try {
                val gsonGen = Gson()
                val type: Type = object : TypeToken<User>() {}.type
                val user = gsonGen.fromJson<User>(json, type)
                val body = JsonObject().apply {
                    addProperty("media_id", movieId)
                }
                val response = RetrofitService.getMovieApi().addMovie(
                        listId,
                        BuildConfig.THE_MOVIE_DB_API_TOKEN,
                        user.sessionId,body)
                if(response.isSuccessful){
                    Toast.makeText(context, "Your list changed",Toast.LENGTH_SHORT).show()
                }
            }catch (e:Exception){
                Toast.makeText(context, "There is error $e",Toast.LENGTH_SHORT).show()
            }
        }
    }
}