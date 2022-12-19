package com.example.movieproject.presentation.view_model

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.se.omapi.Session
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieproject.BuildConfig
import com.example.movieproject.data.api.RetrofitService
import com.example.movieproject.data.models.Singleton
import com.example.movieproject.data.models.UserAvatar
import com.example.movieproject.domain.model.Movie
import com.example.movieproject.domain.model.User
import com.example.movieproject.presentation.view.MainActivity
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginViewModel(private val context: Context) : ViewModel(), CoroutineScope {
    val liveData = MutableLiveData<String>()
    private lateinit var requestToken: String
    private lateinit var preferences: SharedPreferences
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun getToken(userLogin: String, userPassword: String){
        launch {
            try {
                val response = RetrofitService.getMovieApi()
                    .getRequestToken(BuildConfig.THE_MOVIE_DB_API_TOKEN)
                if (response.isSuccessful) {
                    requestToken = response.body()?.request_token.toString()
                    val body = JsonObject().apply {
                        addProperty("username", userLogin)
                        addProperty("password", userPassword)
                        addProperty("request_token", requestToken)
                    }
                    falidateRequestToken(body)
                } else {
                    val badRes = "bad request"
                    liveData.postValue(badRes)
                }
            } catch (e: Exception) {
                val badRes = "bad request"
                liveData.postValue(badRes)
            }
        }
    }

    fun falidateRequestToken(body: JsonObject){
        launch {
            try {
                val response = RetrofitService.getMovieApi()
                    .validateToken(BuildConfig.THE_MOVIE_DB_API_TOKEN, body)
                if(response.isSuccessful){
                    val bodySession = JsonObject().apply {
                        addProperty("request_token", response.body()?.request_token.toString())
                    }
                    getSession(bodySession)
                }
                else{
                    val badRes = "bad token"
                    liveData.postValue(badRes)
                }
            }
            catch (e:Exception){
                val badRes = "bad token"
                liveData.postValue(badRes)
            }
        }
    }

    fun getSession(body: JsonObject){
        launch {
            try {
                val response = RetrofitService.getMovieApi()
                    .getSession(BuildConfig.THE_MOVIE_DB_API_TOKEN,body)
                if(response.isSuccessful){
                    val sessionId = response.body()?.session_id
                    getAccount(sessionId)
                }
            }
            catch (e:Exception){
                val badRes = "bad session"
                liveData.postValue(badRes)
            }
        }
    }

    fun getAccount(sessionId: String?){
        launch {
            try {
                val response = RetrofitService.getMovieApi()
                    .getAccount(BuildConfig.THE_MOVIE_DB_API_TOKEN,sessionId)
                if(response.isSuccessful){
                    liveData.postValue("Everything is OK")
                    val userAvatar =
                        Gson().fromJson(response.body(), UserAvatar::class.java)
                    val idAcc = userAvatar.id
                    val newUser = User(userAvatar.name, userAvatar.username, sessionId, idAcc,userAvatar.avatar.tmdb.avatar_path)
                    val json = Gson().toJson(newUser)
                    preferences = context.getSharedPreferences("Username",0)
                    preferences.edit().putString("user",json).commit()
                    var mySingleton =
                        Singleton.create(
                            newUser.username,
                            newUser.sessionId,
                            newUser.accountId
                        )
                    val intent = Intent(context,MainActivity::class.java)
                    intent.putExtra("userName", newUser.username)
                    intent.putExtra("avatar_path",userAvatar?.avatar?.tmdb?.avatar_path)
                    intent.putExtra("name",userAvatar?.name)
                    context.startActivity(intent)
                }
                else{
                    liveData.postValue("No validated account")
                }
            }catch (e:Exception){
                val barR = e.toString()
                liveData.postValue(barR)
            }
        }
    }

}