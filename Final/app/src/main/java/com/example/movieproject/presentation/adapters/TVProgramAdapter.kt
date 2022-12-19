package com.example.movieproject.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieproject.R
import com.example.movieproject.data.models.TVProgram

class TVProgramAdapter(
        var context: Context,
        var programList: List<TVProgram>? = null
) : RecyclerView.Adapter<TVProgramAdapter.TVProgramViewHolder>() {
    inner class TVProgramViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bind(tvProgram: TVProgram?) {
            val title = view.findViewById<TextView>(R.id.item_tv_title)
            val thumbnail = view.findViewById<ImageView>(R.id.tv_poster)
            title.text = tvProgram?.original_name
            Glide.with(context)
                    .load(tvProgram?.getPosterPath())
                    .into(thumbnail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVProgramViewHolder {
        val  view = LayoutInflater.from(parent.context).inflate(R.layout.tv_program_item, parent, false)
        return TVProgramViewHolder(view)
    }

    override fun getItemCount(): Int = programList?.size ?: 0

    override fun onBindViewHolder(holder: TVProgramViewHolder, position: Int) {
        holder.bind(programList?.get(position))
    }
}