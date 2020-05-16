package com.example.movie.model.api

import com.example.movie.model.favourite.*
import com.example.movie.model.authorization.LoginData
import com.example.movie.model.authorization.RequestToken
import com.example.movie.model.movie.Movie
import com.example.movie.model.movie.MovieResponse
import com.example.movie.model.favourite.MovieStatus
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface MovieApi {

    @POST("authentication/token/validate_with_login?api_key=2f0d69a585b1ec8a833e56046239144b")
    suspend fun login2(@Body loginData: LoginData): Response<RequestToken>

    @POST("authentication/session/new?api_key=2f0d69a585b1ec8a833e56046239144b")
    fun getSession(@Body sessionId: SessionId): Call<RequestSession>

    @GET("authentication/token/new")
    suspend fun getRequestToken2(@Query("api_key") apiKey: String): Response<RequestToken>

    @Headers("Content-Type:application/json; charset=UTF-8")
    @POST("account/9178480/favorite?api_key=2f0d69a585b1ec8a833e56046239144b")
    suspend fun addFavList(
        @Body movie: FavMovieInfo,
        @Query("session_id") session: String?
    ): Response<FavResponse>

    @GET("movie/{movie_id}/account_states")
    suspend fun getMovieState(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String?,
        @Query("session_id") session: String?
    ): Response<MovieStatus>

    @GET("movie/popular")
    suspend fun getPopularMoviesListCoroutine(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetailCoroutine(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ): Response<Movie>

    @GET("account/9178480/favorite/movies?api_key=2f0d69a585b1ec8a833e56046239144b")
    suspend fun getFavListCoroutine(@Query("session_id") session: String?): Response<FavMovieResponse>

}