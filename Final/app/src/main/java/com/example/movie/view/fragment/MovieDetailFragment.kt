package com.example.movie.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movie.view.utils.DetailScrollView
import com.example.movie.R
import com.example.movie.model.favourite.FavMovieInfo
import com.example.movie.model.movie.Movie
import com.example.movie.view_model.MoviesListViewModel
import com.example.movie.view_model.ViewModelProviderFactory
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*


class MovieDetailFragment : Fragment() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private var movieTitle: TextView? = null
    private var movieJanre: TextView? = null
    private var movieDate: TextView? = null
    private var movieYear: TextView? = null
    private var movieDescription: TextView? = null
    var movieId: Int = 0
    private lateinit var poster: ImageView
    private var likeBtn: ImageView? = null
    private var isLiked: Boolean? = null
    private var backBtn: ImageButton? = null
    var sessionId: String? = null
    private var movie: Movie? = null
    private var scrollView: DetailScrollView? = null
    private lateinit var movieListViewModel: MoviesListViewModel


    private val dateFormat = SimpleDateFormat("MMMM d, YYYY H:m", Locale.ENGLISH)
    private val dateYearFormat = SimpleDateFormat("YYYY", Locale.ENGLISH)
    private val initialFormat = SimpleDateFormat("YY-MM-DD", Locale.ENGLISH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = Firebase.analytics

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = LayoutInflater.from(container?.context)
            .inflate(R.layout.fragment_movie_detail, container, false)

        val toolbar = v.findViewById(R.id.toolbar) as Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setTitle("");

        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            activity?.onBackPressed()
        })

        scrollView = v.findViewById(R.id.detail_scroll_view)
        val gesture = GestureDetector(v.context,
            object : SimpleOnGestureListener() {
                override fun onDown(e: MotionEvent): Boolean {
                    return true
                }

                override fun onFling(
                    e1: MotionEvent, e2: MotionEvent, velocityX: Float,
                    velocityY: Float
                ): Boolean {
                    val SWIPE_MIN_DISTANCE = 200
                    val SWIPE_THRESHOLD_VELOCITY = 200
                    try {
                        Log.e("EmpDetailFragmnt", "ongesture")
                        if (e2.x - e1.x > SWIPE_MIN_DISTANCE
                            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY
                        ) {
                            Log.e("EmpDetailFragmnt", "Left to right")
                            (activity as AppCompatActivity?)?.onBackPressed()
                        }
                    } catch (e: Exception) {
                    }
                    return super.onFling(e1, e2, velocityX, velocityY)
                }
            })
        scrollView?.setDetector(gesture)

        bindViews(v)
        getDetails(movieId)
        val pref =
            requireActivity().getSharedPreferences("tkn", Context.MODE_PRIVATE)
        sessionId = pref.getString("sessionID", "empty")

        return v

    }

    fun getDetails(movieId: Int) {
        val viewModelProviderFactory = ViewModelProviderFactory(requireActivity())
        movieListViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(MoviesListViewModel::class.java)
        movieListViewModel.getMovieDetailCoroutine(movieId)
        movieListViewModel.getState(movieId)

        movieListViewModel.liveData.observe(
            requireActivity(),
            androidx.lifecycle.Observer { result ->
                when (result) {
                    is MoviesListViewModel.State.liked -> {
                        isLiked = result.movieLiked
                        if (isLiked == true) {
                            likeBtn?.setImageResource(R.drawable.ic_favorite_black_24dp)

                        } else{
                            likeBtn?.setImageResource(R.drawable.ic_favorite_border_black_24dp)

                        }
                    }
                    is MoviesListViewModel.State.MovieDetails -> {

                        if (result.movie?.releaseDate != null) {
                            val dateTime = initialFormat.parse(result.movie.releaseDate)
                            movieDate?.text = dateFormat.format(dateTime!!)
                            movieYear?.text = dateYearFormat.format(dateTime)
                        } else {
                            movieDate?.text = dateFormat.format(Date())
                            movieYear?.text = dateYearFormat.format(Date())
                        }
                        var bundle = Bundle()
                        bundle.putString("movie_detail", result.movie?.originalTitle)
                        firebaseAnalytics.logEvent("clicked",bundle)
                        movieTitle?.text = result.movie?.originalTitle
                        movieDescription?.text = result.movie?.overview
                        movieJanre?.text = result.movie?.genres?.first()?.name
                        Glide.with(this@MovieDetailFragment)
                            .load(result.movie?.getPosterPath())
                            .into(this@MovieDetailFragment.poster)

                        likeBtn?.setOnClickListener(View.OnClickListener {
                            if (isLiked == false) {
                                isLiked = true
                                var bundle = Bundle()
                                bundle.putString("like", result.movie?.originalTitle)
                                firebaseAnalytics.logEvent("clicked",bundle)
                                likeBtn?.setImageResource(R.drawable.ic_favorite_black_24dp)
                                Toast.makeText(
                                    activity,
                                    "Film added to favList",
                                    Toast.LENGTH_LONG
                                ).show()
                                movieListViewModel.markAsFav(
                                    FavMovieInfo(
                                        true,
                                        movieId,
                                        "movie"
                                    )
                                )
                                likeBtn?.refreshDrawableState()
                            } else {
                                isLiked = false

                                likeBtn?.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                                movieListViewModel.markAsFav(
                                    FavMovieInfo(
                                        false,
                                        movieId,
                                        "movie"
                                    )
                                )
                                likeBtn?.refreshDrawableState()

                            }
                        })
                    }
                }

            })
    }

    private fun bindViews(v: View) {
        movieTitle = v.findViewById(R.id.m_movie_title)
        movieJanre = v.findViewById(R.id.m_movie_genre)
        movieDate = v.findViewById(R.id.m_movie_date_detail)
        movieDescription = v.findViewById(R.id.m_movie_overview)
        poster = v.findViewById(R.id.m_avatar_detail)
        likeBtn = v.findViewById(R.id.fav_btn)
        movieYear = v.findViewById(R.id.m_movie_release_date)
//        backBtn = v.findViewById(R.id.back_btn)

    }
    companion object {
        fun newInstance(movie: Movie?): MovieDetailFragment? {
            val fragment = MovieDetailFragment()
            fragment.movie = movie
            return fragment
        }
    }

}