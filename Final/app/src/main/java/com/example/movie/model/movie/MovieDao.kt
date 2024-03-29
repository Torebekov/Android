package com.example.movie.model.movie

import androidx.room.*
import com.example.movie.model.favourite.FavMovies
import com.example.movie.model.movie.Movie


@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Movie>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFav(list: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Query("DELETE FROM movie_table")
    fun deleteAll()

    @Query("Select * from movie_table")
    fun getAll(): List<Movie>


    @Query("Select * from movie_table where id like :detail_id")
    fun getMovie(detail_id: Int): Movie

    @Query("Select * from movie_table where id like :detail_id")
    fun getMovieAsFav(detail_id: Int): FavMovies

    @Query("UPDATE movie_table SET isLiked = :isClicked WHERE id like :id")
    fun updateMovieIsCLicked(isClicked: Boolean, id: Int)

    @Update
    fun setLike(movie: Movie)

}