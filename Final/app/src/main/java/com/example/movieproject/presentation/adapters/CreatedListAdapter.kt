package com.example.movieproject.presentation.adapters

import android.content.Context
import android.content.Intent
import android.icu.text.IDNA
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieproject.R
import com.example.movieproject.data.models.CreatedLists
import com.example.movieproject.data.models.InfoList
import com.example.movieproject.domain.model.Movie
import com.example.movieproject.presentation.view.CreateListActivity
import com.example.movieproject.presentation.view.DetailActivity
import com.example.movieproject.presentation.view_model.MarkItemViewModel
import kotlinx.android.synthetic.main.main_page.view.*

class CreatedListAdapter(
    var context: Context?,
    var userList: List<InfoList>? = null
): RecyclerView.Adapter<CreatedListAdapter.CreatedListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CreatedListAdapter.CreatedListViewHolder {
        val  view = LayoutInflater.from(parent.context).inflate(R.layout.created_user_list, parent, false)
        return CreatedListViewHolder(view)
    }

    override fun getItemCount(): Int = userList?.size ?: 0

    override fun onBindViewHolder(holder: CreatedListAdapter.CreatedListViewHolder, position: Int) {
        holder.bind(userList?.get(position))
    }

    inner class CreatedListViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bind(list: InfoList?) {
            val name  = view.findViewById<TextView>(R.id.name_list)
            val descrption = view.findViewById<TextView>(R.id.description_list)
            name.text = list?.name
            val s = list?.description + " " + list?.id
            descrption.text = s

            view.setOnClickListener {
                Toast.makeText(context,"Please wait a few second",Toast.LENGTH_SHORT).show()
                val intent = Intent(context, CreateListActivity::class.java)
                intent.putExtra("list_id",list?.id)
                view.context.startActivity(intent)
            }

        }
    }

}