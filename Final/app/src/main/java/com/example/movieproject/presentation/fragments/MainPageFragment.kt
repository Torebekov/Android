package com.example.movieproject.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.movieproject.R
import com.example.movieproject.presentation.adapters.MoviesAdapter
import com.example.movieproject.presentation.adapters.SliderMovieAdapter
import com.example.movieproject.presentation.adapters.TVProgramAdapter
import com.example.movieproject.data.api.RetrofitService
import com.example.movieproject.domain.model.Movie
import com.example.movieproject.data.models.MovieResponse
import com.example.movieproject.data.models.TVProgram
import com.example.movieproject.data.models.TVProgramResponse
import com.example.movieproject.presentation.view_model.MainFragmentViewModel
import com.example.movieproject.presentation.view_model.TVProgramViewModel
import com.example.movieproject.presentation.view_model.ViewModelProviderFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainPageFragment : Fragment() {
    private lateinit var titleMovie: TextView
    private lateinit var titleProgram: TextView
    private lateinit var nestedScroll: NestedScrollView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var rootView: View? = null
    private lateinit var viewPager: ViewPager2
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvProgramRecyclerView: RecyclerView
    private var movieAdapter: MoviesAdapter? = null
    private var programAdapter: TVProgramAdapter? = null
    private var slideAdapter: SliderMovieAdapter? = null
    private var movList: List<Movie>? = null
    private var slidemovList: List<Movie>? = null
    private var programList: List<TVProgram>? = null
    private lateinit var indicator: TabLayout
    private lateinit var movieListViewModel: MainFragmentViewModel
    private lateinit var programViewModel: TVProgramViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.activity_main, container, false) as ViewGroup
        val viewModelProviderFactory = ViewModelProviderFactory(context = this.activity as Context)
        movieListViewModel =
                ViewModelProvider(this, viewModelProviderFactory).get(MainFragmentViewModel::class.java)
        programViewModel = ViewModelProvider(this, viewModelProviderFactory).get(TVProgramViewModel::class.java)
        findViews()
        swipeRefreshLayout.isRefreshing = true
        swipeRefreshLayout.setOnRefreshListener {
            if (swipeRefreshLayout.isRefreshing) {
                destroyAll()
            }
        }
        initViews()
        movieListViewModel.liveData.observe(this, androidx.lifecycle.Observer { result->
            slideAdapter?.moviesList = result.subList(0,4)
            slideAdapter?.notifyDataSetChanged()
            movieAdapter?.moviesList = result.subList(4,result!!.size)
            movieAdapter?.notifyDataSetChanged()
            var timer = Timer()
            timer.scheduleAtFixedRate(SliderTimer(),4000,6000)
            TabLayoutMediator(indicator, viewPager){
                    tab, position ->
                viewPager.setCurrentItem(tab.position, true)
            }.attach()
        })
        programViewModel.liveData.observe(this, androidx.lifecycle.Observer { result ->
            programAdapter?.programList = result
            programAdapter?.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        })

        return rootView
    }

    private fun destroyAll() {
        titleProgram.visibility = View.INVISIBLE
        titleMovie.visibility = View.INVISIBLE
        movieAdapter?.moviesList = null
        movieAdapter?.notifyDataSetChanged()
        slideAdapter?.moviesList = null
        slideAdapter?.notifyDataSetChanged()
        programAdapter?.programList = null
        programAdapter?.notifyDataSetChanged()
        initViews()
    }

    private fun findViews() {
        titleMovie = (rootView as ViewGroup).findViewById(R.id.title_recycleView)
        titleProgram = (rootView as ViewGroup ).findViewById(R.id.program_tittle_recyclerView)
        nestedScroll = (rootView as ViewGroup).findViewById(R.id.nestedScroll_main)
        swipeRefreshLayout = (rootView as ViewGroup).findViewById(R.id.swipe_main)
        tvProgramRecyclerView = (rootView as ViewGroup).findViewById(R.id.tvShows_recycler_view)
        recyclerView = (rootView as ViewGroup).findViewById(R.id.moview_recycler_view)
        viewPager = (rootView as ViewGroup).findViewById(R.id.slide_page)
        indicator = (rootView as ViewGroup).findViewById(R.id.indicator)
    }

    private fun initViews() {
        movieAdapter = activity?.application?.let { MoviesAdapter(it, movList) }
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = movieAdapter
        movieAdapter?.notifyDataSetChanged()
        programAdapter = TVProgramAdapter(activity as Context, programList)
        tvProgramRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        tvProgramRecyclerView.adapter = programAdapter
        programAdapter?.notifyDataSetChanged()
        slideAdapter = SliderMovieAdapter(activity as Context, slidemovList, viewPager)
        viewPager.adapter = slideAdapter
        slideAdapter?.notifyDataSetChanged()
        loadAllPosts()
        titleMovie.visibility = View.VISIBLE
        titleProgram.visibility = View.VISIBLE
    }

    private fun loadAllPosts() {
        movieListViewModel.loadMovies(1)
        programViewModel.loadTVShows(1)
    }

    inner class SliderTimer : TimerTask() {
        override fun run() {
            activity?.runOnUiThread(Runnable {
                if (viewPager.currentItem < 4) {
                    viewPager.currentItem = viewPager.currentItem + 1
                } else {
                    viewPager.currentItem = 0
                }
            })
        }

    }
}