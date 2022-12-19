package com.example.movieproject.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieproject.R
import com.example.movieproject.data.models.InfoList
import com.example.movieproject.domain.model.Movie
import com.example.movieproject.presentation.adapters.CreatedListAdapter
import com.example.movieproject.presentation.adapters.FavouriteAdapter
import com.example.movieproject.presentation.view_model.AccountListViewModel
import com.example.movieproject.presentation.view_model.DetailActivityViewModel
import com.example.movieproject.presentation.view_model.MainFragmentViewModel
import com.example.movieproject.presentation.view_model.ViewModelProviderFactory

class GenresFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var createButton: Button
    private lateinit var cardViewCreateList: CardView
    private lateinit var cardViewListInfo: CardView
    private lateinit var createTextView: TextView
    private lateinit var showTextView: TextView
    private lateinit var loginTextView: TextView
    private var rootView: View? = null
    private lateinit var accountListViewModel: AccountListViewModel
    private var userLis: List<InfoList>? = null
    private var createdListAdapter: CreatedListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.genre_fragment, container, false) as ViewGroup

        val viewModelProviderFactory = ViewModelProviderFactory(context!!)
        accountListViewModel = ViewModelProvider(this, viewModelProviderFactory).get(
            AccountListViewModel::class.java)

        initRecycle()
        nameEditText = (rootView as ViewGroup).findViewById(R.id.name_create_list)
        descriptionEditText = (rootView as ViewGroup).findViewById(R.id.description_create_list)
        createButton = (rootView as ViewGroup).findViewById(R.id.btn_create_list)
        createTextView = (rootView as ViewGroup).findViewById(R.id.create_own_list)
        cardViewCreateList = (rootView as ViewGroup).findViewById(R.id.cv_create)
        cardViewListInfo = (rootView as ViewGroup).findViewById(R.id.cv_show_list)
        showTextView = (rootView as ViewGroup).findViewById(R.id.show_own_list)

        createTextView.setOnClickListener {
            if(cardViewCreateList.visibility == View.GONE){
                cardViewCreateList.visibility = View.VISIBLE
                cardViewListInfo.visibility = View.GONE
            }
            else{
                cardViewCreateList.visibility = View.GONE
            }
        }
        showTextView.setOnClickListener {
            if(cardViewListInfo.visibility == View.GONE){
                Toast.makeText(context,"Please wait",Toast.LENGTH_SHORT).show()
                accountListViewModel.getCreatedLists()
                cardViewCreateList.visibility = View.GONE
                cardViewListInfo.visibility = View.VISIBLE
            }
            else{
                cardViewListInfo.visibility = View.GONE
            }
        }
        createButton.setOnClickListener {
            accountListViewModel.createOwnList(nameEditText.text.toString(),descriptionEditText.text.toString())
        }

        accountListViewModel.liveData.observe(this, Observer {
            createdListAdapter?.userList = it
            createdListAdapter?.notifyDataSetChanged()
        })
        return rootView
    }

    fun initRecycle(){
        recyclerView = (rootView as ViewGroup).findViewById(R.id.recyclerview_created_list)
        createdListAdapter = this.activity?.let { CreatedListAdapter(context, userLis) }
        var linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = createdListAdapter
        createdListAdapter?.notifyDataSetChanged()
    }
}