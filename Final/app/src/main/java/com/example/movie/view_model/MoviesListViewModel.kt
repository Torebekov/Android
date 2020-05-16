package com.example.movie.view_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.BuildConfig
import com.example.movie.model.api.MovieApi
import com.example.movie.model.api.RetrofitService
import com.example.movie.model.favourite.*
import com.example.movie.model.movie.Movie
import com.example.movie.model.movie.MovieDao
import com.example.movie.model.movie.MovieDatabase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class MoviesListViewModel(
    private val context: Context

) : ViewModel(), CoroutineScope {
    private val job = Job()
    private val movieDao: MovieDao
    private val favDao: FavDao
    private val likeDao: LikeDao
    var isLiked: Boolean = false
    private var sessionId: String

    val liveData = MutableLiveData<State>()


    init {
        val pref =
            context.getSharedPreferences("tkn", Context.MODE_PRIVATE)
        sessionId = pref.getString("sessionID", "empty").toString()
        movieDao = MovieDatabase.getDatabase(context).movieDao()
        favDao = MovieDatabase.getDatabase(context).favDao()
        likeDao = MovieDatabase.getDatabase(context).likeDao()
        getMovies()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun getMovies(page: Int = 1) {
        launch {
            if (page == 1) {
                liveData.value =
                    State.ShowLoading
            }
            val list = withContext(Dispatchers.IO) {
                try {
                    syncFav()
                    val api: MovieApi? = RetrofitService.getClient()?.create(MovieApi::class.java)
                    val response =
                        api?.getPopularMoviesListCoroutine(BuildConfig.THE_MOVIE_DB_API_TOKEN, page)
                    if (response?.isSuccessful!!) {
                        val result = response.body()
                        val list = response.body()?.results ?: emptyList()
                        val totalPage = response.body()?.totalPages ?: 0
                        if (!result?.results.isNullOrEmpty()) {
                            movieDao.insertAll(result?.results?.subList(1, 19))
                            Log.d("ddd", result?.results?.subList(1, 2).toString())
                        }


                        Pair(totalPage, list)
                    } else {
                        Pair(
                            1, movieDao.getAll()
                        )

                    }
                } catch (e: Exception) {
                    Log.e("Moviedatabase", e.toString())
                    Pair(
                        1, movieDao.getAll()
                    )
                }
            }

            liveData.postValue(
                State.Result(
                    totalPage = list.first,
                    list = list.second
                )
            )
            liveData.value = State.HideLoading
        }
    }

    fun syncFav() {
        val moviesToUpdate = likeDao.getMovieStatuses()
        if (!moviesToUpdate.isNullOrEmpty()) {
            for (movie in moviesToUpdate) {
                val movieToSync = FavMovieInfo(
                    mediaId = movie.id,
                    mediaType = "movie",
                    favorite = movie.favorite
                )
                markAsFav(movieToSync)
            }
        }
        likeDao.deleteAll()
    }

    fun getFavMovies(sessionId: String?) {
        launch {
            liveData.value = State.ShowLoading
            val list = withContext(Dispatchers.IO) {
                try {
                    syncFav()

                    val api: MovieApi? = RetrofitService.getClient()?.create(MovieApi::class.java)
                    val response = api?.getFavListCoroutine(sessionId)
                    if (response?.isSuccessful!!) {
                        val result = response.body()
                        if (!result?.results.isNullOrEmpty()) {
                            favDao.insertFav(result?.results)
                            for (movie in result?.results!!) {
                                movie.isLiked = true
                            }
                        }
                        result?.results
                    } else {
                        favDao.getFav()
                    }
                } catch (e: Exception) {
                    Log.e("Moviedatabase", e.toString())
                    favDao.getFav()
                }
            }
            liveData.value = State.HideLoading
            @Suppress("UNCHECKED_CAST")
            liveData.value = State.FavResult(list)
        }
    }

    fun getMovieDetailCoroutine(id: Int) {
        launch {
            val movie = withContext(Dispatchers.IO) {
                try {
                    val api: MovieApi? = RetrofitService.getClient()?.create(MovieApi::class.java)
                    val response =
                        api?.getMovieDetailCoroutine(id, BuildConfig.THE_MOVIE_DB_API_TOKEN)
                    if (response!!.isSuccessful) {
                        val result = response.body()
                        result
                    } else {
                        movieDao.getMovie(id)
                    }
                } catch (e: java.lang.Exception) {
                    movieDao.getMovie(id)
                }
            }
            liveData.value = State.MovieDetails(movie)
        }
    }

    fun addToFavourites(item: Movie) {
        lateinit var selectedMovie: FavMovieInfo

        if (!item.isLiked) {
            item.isLiked = true
            selectedMovie =
                FavMovieInfo(mediaType = "movie", favorite = item.isLiked, mediaId = item.id)
        } else {
            item.isLiked = false
            selectedMovie =
                FavMovieInfo(mediaType = "movie", favorite = item.isLiked, mediaId = item.id)
        }
        markAsFav(selectedMovie)
    }

    fun markAsFav(selectedMovie: FavMovieInfo) {
        launch {
            try {
                val response = RetrofitService.getApi()
                    ?.addFavList(selectedMovie, sessionId)
                if (response?.isSuccessful!!) {
                }
            } catch (e: Exception) {
                withContext(Dispatchers.IO) {
                    movieDao.updateMovieIsCLicked(
                        selectedMovie.favorite,
                        selectedMovie.mediaId
                    )
                    Log.d("likee", selectedMovie.favorite.toString())
                    favDao.insertFavOne(movieDao.getMovieAsFav(selectedMovie.mediaId))
                    Log.d("likee", movieDao.getMovie(selectedMovie.mediaId).originalTitle!!)

                    val movieStatus =
                        MovieStatus(
                            selectedMovie.mediaId,
                            selectedMovie.favorite
                        )
                    likeDao.insertMovieStatus(movieStatus)
                }
            }
        }
    }

    fun getState(movieId: Int) {
        launch {
            try {
                val api: MovieApi? = RetrofitService.getClient()?.create(MovieApi::class.java)
                val response =
                    api?.getMovieState(movieId, BuildConfig.THE_MOVIE_DB_API_TOKEN, sessionId)
                if (response?.isSuccessful!!) {
                    val movieStatus = response.body()
                    if (movieStatus != null) {
                        movieStatus.favorite
                        liveData.value = State.liked(movieStatus.favorite)
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    sealed class State {
        object ShowLoading : State()
        object HideLoading : State()
        data class Result(val totalPage: Int, val list: List<Movie>) : State()
        data class FavResult(val list: List<FavMovies>?) : State()
        data class MovieDetails(val movie: Movie?) : State()
        data class liked(val movieLiked: Boolean) : State()
    }
}
