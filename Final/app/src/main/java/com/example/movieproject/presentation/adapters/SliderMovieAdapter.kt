package com.example.movieproject.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.movieproject.R
import com.example.movieproject.domain.model.Movie

class SliderMovieAdapter(var context: Context, var moviesList: List<Movie>?, var viewPager2: ViewPager2)
    : RecyclerView.Adapter<SliderMovieAdapter.SlideMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideMovieViewHolder {
        return SlideMovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.slide_item,parent,false))
    }

    override fun getItemCount(): Int = moviesList?.size ?: 0

    override fun onBindViewHolder(holder: SlideMovieViewHolder, position: Int) {
        holder.bind(moviesList?.get(position))
    }

    inner class SlideMovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie?){
            val slideTitle = view.findViewById<TextView>(R.id.txt_slide_title)
            val slideImage = view.findViewById<ImageView>(R.id.slide_poster)
            slideTitle.text = movie?.title
            Glide.with(context).load(movie?.getPosterPath()).into(slideImage)
        }

    }




}