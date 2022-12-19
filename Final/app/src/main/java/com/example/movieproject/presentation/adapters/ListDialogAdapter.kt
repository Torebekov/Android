package com.example.movieproject.presentation.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.movieproject.R
import com.example.movieproject.data.models.InfoList
import com.example.movieproject.presentation.view_model.AccountListViewModel
import com.example.movieproject.presentation.view_model.DetailActivityViewModel
import com.example.movieproject.presentation.view_model.ViewModelProviderFactory

class ListDialogAdapter(var movieId: Int?,
        var context: Context?,
        var userList: List<InfoList>? = null,
        var accountListViewModel: AccountListViewModel?
) : RecyclerView.Adapter<ListDialogAdapter.ListDialogAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDialogAdapter.ListDialogAdapterViewHolder {
        val  view = LayoutInflater.from(parent.context).inflate(R.layout.custom_list_dialog, parent, false)
        return ListDialogAdapterViewHolder(view)
    }

    override fun getItemCount(): Int = userList?.size ?: 0

    override fun onBindViewHolder(holder: ListDialogAdapter.ListDialogAdapterViewHolder, position: Int) {
        holder.bind(userList?.get(position))
    }

    inner class ListDialogAdapterViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bind(list: InfoList?) {
            val name  = view.findViewById<TextView>(R.id.dialog_list_name)
            name.text = list?.name

            view.setOnClickListener {
                Toast.makeText(context, "It is on process", Toast.LENGTH_SHORT).show()
                accountListViewModel?.addMovieToList(list?.id,movieId)
            }

        }
    }
}