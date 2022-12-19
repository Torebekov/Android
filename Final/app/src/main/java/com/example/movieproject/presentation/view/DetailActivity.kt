package com.example.movieproject.presentation.view

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieproject.BuildConfig
import com.example.movieproject.R
import com.example.movieproject.data.api.RetrofitService
import com.example.movieproject.data.models.InfoList
import com.example.movieproject.data.models.MovieResponse
import com.example.movieproject.domain.model.Movie
import com.example.movieproject.domain.model.Video
import com.example.movieproject.domain.model.VideoResponse
import com.example.movieproject.presentation.adapters.ListDialogAdapter
import com.example.movieproject.presentation.adapters.MoviesAdapter
import com.example.movieproject.presentation.view_model.*
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DetailActivity : AppCompatActivity() {
    private lateinit var addLikeCircleImageView: CircleImageView
    private lateinit var addWatchCircleImageView: CircleImageView
    private lateinit var dialogListRecyclerView: RecyclerView
    private lateinit var recyclerView: RecyclerView
    private lateinit var backButton: ImageView
    private lateinit var youTubePlayerView: YouTubePlayerView
    private lateinit var textView: TextView
    private lateinit var textViewAsId: TextView
    private lateinit var nameofMovie: TextView
    private lateinit var plotSynopsis: TextView
    private lateinit var userRating: TextView
    private lateinit var releaseDate: TextView
    private lateinit var imageView: ImageView
    private var movList: List<Movie>? = null
    private var userList: List<InfoList>? = null
    private var movieAdapter: MoviesAdapter? = null
    private lateinit var listDialogAdapter: ListDialogAdapter
    private lateinit var markItemViewModel: MarkItemViewModel
    private lateinit var detailActivityViewModel: DetailActivityViewModel
    private lateinit var movieListViewModel: MainFragmentViewModel
    private lateinit var accountListViewModel: AccountListViewModel
    private var movie: Movie? = null
    private lateinit var videoTrailer: String
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        dialog = Dialog(this)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.window!!.setBackgroundDrawable(getDrawable(R.drawable.dialog_background))
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
//        dialog.setCancelable(tru)

        val viewModelProviderFactory = ViewModelProviderFactory(this)
        detailActivityViewModel = ViewModelProvider(this, viewModelProviderFactory).get(DetailActivityViewModel::class.java)
        markItemViewModel = ViewModelProvider(this, viewModelProviderFactory).get(MarkItemViewModel::class.java)
        accountListViewModel = ViewModelProvider(this, viewModelProviderFactory).get(AccountListViewModel::class.java)
        movieListViewModel =
                ViewModelProvider(this, viewModelProviderFactory).get(MainFragmentViewModel::class.java)
        detailActivityViewModel.liveData.observe(this, Observer {result ->

            if(result == null){
                videoTrailer = "vxkQlcMYoBA"
            }
            else{
                videoTrailer = result.key
            }
        })
        movieListViewModel.liveData.observe(this, androidx.lifecycle.Observer { result->
            movieAdapter?.moviesList = result
            movieAdapter?.notifyDataSetChanged()
        })
        markItemViewModel.liveData.observe(this, Observer {
            Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
        })

        findView()
        initIntent()
        accountListViewModel.liveData.observe(this, Observer {
            listDialogAdapter.userList = it
            listDialogAdapter.notifyDataSetChanged()
        })

        textView.setOnClickListener {
            if(youTubePlayerView.visibility == View.GONE){
                Toast.makeText(this,"Good",Toast.LENGTH_SHORT).show()
                youTubePlayerView.visibility = View.VISIBLE
                youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                        val videoId = videoTrailer
                        youTubePlayer.cueVideo(videoId, 0f)
                    }
                })
            }
            else{
                youTubePlayerView.visibility = View.GONE
            }

        }
        backButton.setOnClickListener {
            onBackPressed()
        }

        addLikeCircleImageView.setOnClickListener {
            markItemViewModel.markAsFavouriteMovie(movie)
        }

        addWatchCircleImageView.setOnClickListener {
            dialog.show()
        }

    }

    private fun findView(){
        addLikeCircleImageView = findViewById(R.id.btn_favourite)
        addWatchCircleImageView = findViewById(R.id.btn_watch_list)
        recyclerView = findViewById(R.id.recomendation_movie_recycler_view)
        dialogListRecyclerView = dialog.findViewById(R.id.list_to_add_recycleView)
        backButton = findViewById(R.id.back_button)
        textView = findViewById(R.id.trailer_video)
        nameofMovie = findViewById(R.id.title_of_movies)
        plotSynopsis = findViewById(R.id.description)
        userRating = findViewById(R.id.rate_movie_user)
        releaseDate = findViewById(R.id.date_release)
        imageView = findViewById(R.id.movie_pic_detail)
        textViewAsId = findViewById(R.id.id_movie)
        youTubePlayerView = findViewById(R.id.youtube_player_view)
        movieAdapter = this.application?.let { MoviesAdapter(it, movList) }
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = movieAdapter
        movieAdapter?.notifyDataSetChanged()
    }


    private fun initIntent() {
        try {
            movie = intent.extras?.getSerializable("movie") as Movie
            detailActivityViewModel.loadTrailer(movie?.id)
            movieListViewModel.loadRecomendation(movie?.id)
            accountListViewModel.getCreatedLists()
            val thumbnail = movie?.getPosterPath()
            val movieName = movie?.original_title
            val synopsis = movie?.overview
            val rating = movie?.vote_average.toString()
            val sateOfRelease = movie?.release_date

            Glide.with(this)
                .load(thumbnail)
                .into(imageView)

            textViewAsId.text = movie?.id.toString()
            nameofMovie.text = movieName
            plotSynopsis.text = synopsis
            userRating.text = rating
            releaseDate.text = sateOfRelease

            listDialogAdapter = ListDialogAdapter(movie?.id,this, userList, accountListViewModel)
            val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            dialogListRecyclerView.layoutManager = linearLayoutManager
            dialogListRecyclerView.adapter = listDialogAdapter
            listDialogAdapter?.notifyDataSetChanged()
        }
        catch (e: Exception) {
            Toast.makeText(this, "No API Data", Toast.LENGTH_SHORT).show()
        }
    }
}