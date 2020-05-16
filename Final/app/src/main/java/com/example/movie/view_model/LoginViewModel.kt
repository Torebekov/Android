package com.example.movie.view_model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.BuildConfig
import com.example.movie.model.favourite.RequestSession
import com.example.movie.model.favourite.SessionId
import com.example.movie.model.api.MovieApi
import com.example.movie.model.api.RetrofitService
import com.example.movie.model.authorization.LoginData
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext


class LoginViewModel(
    private val context: Context
) : ViewModel(), CoroutineScope {
    private val job = Job()

    val liveData = MutableLiveData<State>()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun getTkn() {
        liveData.value = State.ShowLoading
        launch {
            val api: MovieApi? = RetrofitService.getClient()?.create(MovieApi::class.java)
            val response = api?.getRequestToken2(BuildConfig.THE_MOVIE_DB_API_TOKEN)
            if (response?.body()?.success == true) {
                val requestedToken = response.body()?.requestToken
                if (requestedToken != null) {
                    liveData.value = State.StartLogin(requestedToken)
                }
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun login2(email: String, password: String, requestedToken: String) {
        launch {
            val response =
                RetrofitService.getApi()?.login2(LoginData(email, password, requestedToken))
            if (response?.body()?.success == true) {
                val requestedToken1 = response.body()?.requestToken
                if (requestedToken1 != null) {
                    liveData.value =
                        State.ShowActivity
                    liveData.value = State.SaveUser
                    liveData.value = State.GetSession(requestedToken1)
                    liveData.value = State.HideLoading
                }
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getSessionId(token: String?) {
        Log.d("pusk", token!!)
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                return
            }
            val api: MovieApi? = RetrofitService.getClient()?.create(MovieApi::class.java)
            api?.getSession(SessionId(token))
                ?.enqueue(object : Callback<RequestSession> {
                    override fun onFailure(call: Call<RequestSession>, t: Throwable) {
                        Log.d("pusk", "failure occured")
                    }

                    override fun onResponse(
                        call: Call<RequestSession>,
                        response: Response<RequestSession>
                    ) {
                        if (response.body()?.success == true) {

                            Log.d("pusk", response.body()?.session_id!!)
                            liveData.value = State.SaveSession(response.body()?.session_id)
                        } else
                            Log.d("pusk", response.body().toString())
                    }
                })
        } catch (e: Exception) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    sealed class State {
        object ShowLoading : State()
        object HideLoading : State()
        class StartLogin(val token: String) : State()
        object SaveUser : State()
        class GetSession(val token: String) : State()
        class SaveSession(val sessionId: String?) : State()
        object ShowActivity : State()
    }
}