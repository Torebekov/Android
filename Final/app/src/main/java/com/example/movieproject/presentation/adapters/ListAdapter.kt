package com.example.movieproject.presentation.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieproject.R
import com.example.movieproject.domain.model.Movie
import com.example.movieproject.presentation.view.DetailActivity
import com.example.movieproject.presentation.view_model.ListDetailViewModel
import com.example.movieproject.presentation.view_model.MarkItemViewModel

class ListAdapter(
    var context: Context,
    var moviesList: List<Movie>? = null,
    var markItemViewModel: MarkItemViewModel,
    var listId: Int?
): RecyclerView.Adapter<ListAdapter.ListAdapterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListAdapter.ListAdapterViewHolder {
        val  view = LayoutInflater.from(parent.context).inflate(R.layout.favourite_item, parent, false)
        return ListAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListAdapter.ListAdapterViewHolder, position: Int) {
        holder.bind(moviesList?.get(position))
    }

    override fun getItemCount(): Int = moviesList?.size ?: 0

    inner class ListAdapterViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bind(movie: Movie?){
            val delete  = view.findViewById<TextView>(R.id.delete_fav)
            val title = view.findViewById<TextView>(R.id.item_movie_title)
            val thumbnail = view.findViewById<ImageView>(R.id.movie_poster)
            title.text = movie?.title
            Glide.with(context)
                .load(movie?.getPosterPath())
                .into(thumbnail)

            view.setOnClickListener {
                if(context.packageName == "DetailActivity"){
                    Toast.makeText(context,"For getting detail info go to the catotygory", Toast.LENGTH_SHORT).show()
                }
                else{
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra("movie_id", movie?.id)
                    intent.putExtra("original_title", movie?.original_title)
                    intent.putExtra("movie", movie)
                    intent.putExtra("poster_path", movie?.getPosterPath())
                    intent.putExtra("overview", movie?.overview)
                    intent.putExtra("vote_average", (movie?.vote_average).toString())
                    intent.putExtra("release_date", movie?.release_date)
                    view.context.startActivity(intent)
                }

            }

            delete.setOnClickListener {
                deleteMoviewFrom(movie)
            }
        }
    }

    fun deleteMoviewFrom(movie: Movie?,){
        markItemViewModel.deleteFromList(listId,movie?.id)
    }
}