package com.example.movieproject.presentation.view_model

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieproject.BuildConfig
import com.example.movieproject.data.api.RetrofitService
import com.example.movieproject.data.models.InfoList
import com.example.movieproject.data.models.ListDetailResponse
import com.example.movieproject.domain.model.User
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

class ListDetailViewModel(private val context: Context
) : ViewModel(), CoroutineScope {
    val preferences = context.getSharedPreferences("Username",0)
    val json: String? = preferences.getString("user", null)
    private val job = Job()
    val liveData = MutableLiveData<ListDetailResponse>()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun getDetailList(listId: Int?){
        launch {
            try {
                val response = RetrofitService.getMovieApi().listDetail(listId,BuildConfig.THE_MOVIE_DB_API_TOKEN)
                if(response.isSuccessful){
                    liveData.postValue(response.body())
                }
                else{
                    Toast.makeText(context,"There network error or you don't have List", Toast.LENGTH_SHORT).show()
                }
            }catch (e:Exception){
                Toast.makeText(context, "Error is :$e",Toast.LENGTH_SHORT).show()
            }
        }
    }

}