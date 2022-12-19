package com.example.movieproject.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieproject.BuildConfig
import com.example.movieproject.R
import com.example.movieproject.data.api.RetrofitService
import com.example.movieproject.data.models.MovieResponse
import com.example.movieproject.data.models.Singleton
import com.example.movieproject.domain.model.Movie
import com.example.movieproject.domain.model.User
import com.example.movieproject.presentation.adapters.FavouriteAdapter
import com.example.movieproject.presentation.adapters.MoviesAdapter
import com.example.movieproject.presentation.view.CreateListActivity
import com.example.movieproject.presentation.view_model.DetailActivityViewModel
import com.example.movieproject.presentation.view_model.MarkItemViewModel
import com.example.movieproject.presentation.view_model.ViewModelProviderFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Response
import java.lang.reflect.Type
import javax.security.auth.callback.Callback

class LikeMoviesFragment : Fragment() {
    private lateinit var markItemViewModel: MarkItemViewModel
    private lateinit var createTextView: TextView
    private lateinit var user: User
    private lateinit var recyclerView: RecyclerView
    private var movList: List<Movie>? = null
    private var movieAdapter: FavouriteAdapter? = null
    private var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.like_fragment, container, false)
        val viewModelProviderFactory = ViewModelProviderFactory(context!!)
        markItemViewModel = ViewModelProvider(this, viewModelProviderFactory).get(
            MarkItemViewModel::class.java)


        markItemViewModel.liveData.observe(this, Observer {
            loadPosts()
        })
        initviews()
        return rootView
    }

    private fun initviews(){
        recyclerView = (rootView as ViewGroup).findViewById(R.id.recyclerview_favourite)
        movieAdapter = this.activity?.let { FavouriteAdapter(it, movList, markItemViewModel) }
        var linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.layoutManager = GridLayoutManager(context,2)
        recyclerView.adapter = movieAdapter
        movieAdapter?.notifyDataSetChanged()
        checkUser()
    }

    private fun checkUser(){
        val preferences = context?.getSharedPreferences("Username",0)
        if (preferences?.getString("user", null) != null) {
          loadPosts()
        }
        else{
            Toast.makeText(activity, "Please go to profile and login", Toast.LENGTH_SHORT).show()
        }

    }

    private fun loadPosts(){
        val preferences = context?.getSharedPreferences("Username",0)
        val json: String? = preferences?.getString("user", null)
        try {
            val gsonGen = Gson()
            val type: Type = object : TypeToken<User>() {}.type
            user = gsonGen.fromJson<User>(json, type)
        }catch (e:Exception){}
        RetrofitService.getMovieApi().getFavoriteMovies(
            user.accountId,BuildConfig.THE_MOVIE_DB_API_TOKEN,user.sessionId)
            .enqueue(object : retrofit2.Callback<MovieResponse>{
                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    val list = response.body()?.results
                    movieAdapter?.moviesList = list
                    movieAdapter?.notifyDataSetChanged()
                }

            })

    }
}