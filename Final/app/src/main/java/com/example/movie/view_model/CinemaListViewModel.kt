package com.example.movie.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.movie.model.cinema.Cinema
import com.example.movie.model.cinema.CinemaDao
import com.example.movie.model.cinema.CinemaDatabase

class CinemaListViewModel(context: Context) : ViewModel() {
    private val cinemaDao: CinemaDao = CinemaDatabase.getDatabase(context).cinemaDao()

    fun getCinemaList(): List<Cinema> {
        return cinemaDao.getAllCinema()
    }

    fun addCinemaListToDatabase(list: List<Cinema>){
        cinemaDao.insertAllCinema(list)
    }
}