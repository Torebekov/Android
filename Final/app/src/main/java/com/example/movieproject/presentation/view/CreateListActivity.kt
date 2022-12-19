package com.example.movieproject.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieproject.R
import com.example.movieproject.domain.model.Movie
import com.example.movieproject.domain.model.User
import com.example.movieproject.presentation.adapters.FavouriteAdapter
import com.example.movieproject.presentation.adapters.ListAdapter
import com.example.movieproject.presentation.view_model.ListDetailViewModel
import com.example.movieproject.presentation.view_model.MarkItemViewModel
import com.example.movieproject.presentation.view_model.ViewModelProviderFactory
import java.lang.Exception

class CreateListActivity : AppCompatActivity() {
    private var listId: Int? = null
    private lateinit var deleteImageView: ImageView
    private lateinit var nameList: TextView
    private lateinit var clearTextView: TextView
    private lateinit var countList: TextView
    private lateinit var favouriteCoumtList: TextView
    private lateinit var listDetailViewModel: ListDetailViewModel
    private lateinit var markItemViewModel: MarkItemViewModel
    private lateinit var createTextView: TextView
    private lateinit var user: User
    private lateinit var recyclerView: RecyclerView
    private var movList: List<Movie>? = null
    private var movieAdapter: FavouriteAdapter? = null
    private var listAdapter: ListAdapter? = null
    private var rootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_list)
        val viewModelProviderFactory = ViewModelProviderFactory(this)
        listDetailViewModel = ViewModelProvider(this, viewModelProviderFactory).get(
            ListDetailViewModel::class.java)

        markItemViewModel = ViewModelProvider(this, viewModelProviderFactory).get(
            MarkItemViewModel::class.java)

        initView()
        listDetailViewModel.liveData.observe(this, Observer {
            listAdapter?.moviesList = it.items
            listAdapter?.notifyDataSetChanged()
            nameList.text = it.name
            countList.text = it.item_count.toString()
            favouriteCoumtList.text = it.favorite_count.toString()
        })
        markItemViewModel.liveData.observe(this, Observer {
            if(it == "ListUpgrade"){
                loadDetails()
            }
            if(it == "ListClear"){
                listAdapter?.moviesList=null
                listAdapter?.notifyDataSetChanged()
            }
            if(it=="ListDelete"){
                onBackPressed()
            }

        })
        clearTextView.setOnClickListener {
            markItemViewModel.clearAllPostsFromList(listId)
        }
        deleteImageView.setOnClickListener {
            Toast.makeText(this,"It is on process",Toast.LENGTH_SHORT).show()
            markItemViewModel.deleteList(listId)
        }
    }

    private fun initView(){
        deleteImageView = findViewById(R.id.detail_List_delete)
        clearTextView = findViewById(R.id.detail_List_clear)
        nameList = findViewById(R.id.name_detail_List)
        favouriteCoumtList = findViewById(R.id.item_favourites_detail_List)
        countList = findViewById(R.id.item_detail_List)
        recyclerView = findViewById(R.id.list_detail_recyclerView)
        listAdapter =  ListAdapter(this, movList, markItemViewModel,0)
        var linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.layoutManager = GridLayoutManager(this,2)
        recyclerView.adapter = listAdapter
        listAdapter?.notifyDataSetChanged()
        loadPost()
    }

    private fun loadPost(){
        try{
            listId = intent.getIntExtra("list_id",0)
            listAdapter?.listId = listId
            listAdapter?.notifyDataSetChanged()
            loadDetails()
        }
        catch (e:Exception){
            Toast.makeText(this,"Sorry error",Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadDetails(){
        listDetailViewModel.getDetailList(listId)
    }

}