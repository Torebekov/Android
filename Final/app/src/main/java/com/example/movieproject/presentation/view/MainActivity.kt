package com.example.movieproject.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import com.example.movieproject.R
import com.example.movieproject.presentation.adapters.SlidePagerAdapter
import com.example.movieproject.presentation.fragments.*
import com.example.movieproject.presentation.utilities.LockableViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var pager: LockableViewPager
    private lateinit var pagerAdapter: PagerAdapter
    private var mainFragment: Fragment = MainPageFragment()
    private var genresFragment: Fragment = GenresFragment()
    private var fragmentLike: Fragment = LikeMoviesFragment()
    private var fragmentProfile: Fragment = ProfileFragment()
    private var list: MutableList<Fragment> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)
        findView()
        initview()

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    pager.setCurrentItem(0, false)
//                    supportActionBar?.title = "Кино ТВ - Онлайн Фильмы"
                }
                R.id.catygory ->{
                    pager.setCurrentItem(1, false)
                }
                R.id.like_posts -> {
                    pager.setCurrentItem(2, false)
//                    supportActionBar?.title = "Закладки"
                }
                R.id.about -> {
                    pager.setCurrentItem(3, false)
//                    supportActionBar?.title = "Профиль"
                }
            }
            false
        }



    }

    private fun initview() {
        list.add(mainFragment)
        list.add(genresFragment)
        list.add(fragmentLike)
        list.add(fragmentProfile)
        pager.setSwipable(false)
        pagerAdapter = SlidePagerAdapter(supportFragmentManager, list)
        pager.adapter = pagerAdapter
    }

    private fun findView() {
        pager = findViewById(R.id.pager)
        bottomNavigationView = findViewById(R.id.bottom_navigation)
    }


}