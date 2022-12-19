package com.example.movieproject.data.api

import android.view.textclassifier.TextLanguage
import com.example.movieproject.data.models.*
import com.example.movieproject.domain.model.Video
import com.example.movieproject.domain.model.VideoResponse
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object RetrofitService {
    private const val urlMovie = "https://api.themoviedb.org/3/"

    fun getMovieApi(): MovieApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(urlMovie)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(MovieApi::class.java)
    }
}

interface MovieApi {
    @GET("movie/popular")
    fun getPopularMovieList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MovieResponse>

    @GET("tv/popular")
    suspend fun getPopularTvPrograms(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Int
    ): Response<TVProgramResponse>

    @GET("movie/{movie_id}/videos")
    suspend fun getVideos(
            @Path("movie_id") movieId: Int?,
            @Query("api_key") apiKey: String
    ):  Response<VideoResponse>

    @GET("movie/popular")
    suspend fun getPopularMovieListCoroutine(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MovieResponse>

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendationsofMovie(
        @Path("movie_id") movieId: Int?,
        @Query("api_key") apiKey: String
    ) : Response<MovieResponse>

    @GET("authentication/token/new")
    suspend fun getRequestToken(
        @Query("api_key") apiKey: String
    ) : Response<RequestToken>

    @POST("authentication/token/validate_with_login")
    suspend fun validateToken(
        @Query("api_key") apiKey: String,
        @Body body: JsonObject
    ): Response<RequestToken>

    @POST("authentication/session/new")
    suspend fun getSession(
        @Query("api_key") apiKey: String,
        @Body body: JsonObject
    ): Response<Session>

    @GET("account")
    suspend fun getAccount(
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String?
    ) : Response<JsonObject>

    @DELETE("authentication/session")
    fun deleteSession(
        @Query("api_key") apiKey: String,
        @Body body: JsonObject
    ): Response<JsonObject>

    @GET("movie/{movie_id}/account_states")
    suspend fun checkStatus(
        @Path("movie_id") movieId: Int?,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String?
    ): Response<MovieStatus>

    @POST("account/{account_id}/favorite")
    suspend fun markAsFavourite(
        @Path("account_id") accountId: Int?,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String?,
        @Body body: JsonObject
    ): Response<JsonObject>

    @POST("account/{account_id}/favorite")
    fun markAsFavouriteMovie(
        @Path("account_id") accountId: Int?,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String?,
        @Body body: JsonObject
    ): Call<JsonObject>

    @GET("account/{account_id}/favorite/movies")
    fun getFavoriteMovies(
        @Path("account_id") accountId: Int?,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String?
    ): Call<MovieResponse>

    @GET("account/{account_id}/favorite/movies")
    suspend fun getFavorites(
        @Path("account_id") accountId: Int?,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String?
    ): Response<MovieResponse>

    @POST("list")
    suspend fun createList (
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String?,
        @Body body: JsonObject
    ): Response<JsonObject>

    @GET("account/{account_id}/lists")
    suspend fun getCreatedLists(
        @Path("account_id") accountId: Int?,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String?
    ): Response<CreatedLists>

    @POST("list/{list_id}/add_item")
    suspend fun addMovie(
        @Path("list_id") list_id: Int?,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String?,
        @Body body: JsonObject
    ): Response<JsonObject>

    @GET("list/{list_id}")
    suspend fun listDetail(
        @Path("list_id") list_id: Int?,
        @Query("api_key") apiKey: String
    ) : Response<ListDetailResponse>

    @POST("list/{list_id}/remove_item")
    suspend fun deleteFromList(
        @Path("list_id") list_id: Int?,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String?,
        @Body body: JsonObject
    ) : Response<JsonObject>

    @POST("list/{list_id}/clear")
    suspend fun clearAllPostsFromList(
        @Path("list_id") list_id: Int?,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String?,
        @Query("confirm") confirm: Boolean?
    ) : Response<JsonObject>

    @DELETE("list/{list_id}")
    suspend fun deleteList(
        @Path("list_id") list_id: String?,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String?
    ) : Response<JsonObject>

}