package com.example.arman

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


class MainActivity : AppCompatActivity(), PostsListAdapter.FragmentButtonListener, PostsListAdapter.FragmentLikeListener, LikedListAdapter.FragmentLikeListener {
    private var lockableViewPager: LockableViewPager? = null
    private var pagerAdapter: PagerAdapter? = null
    var fragment1: Fragment = MainFragment()
    var fragment2: Fragment = LikeFragment()
    var list: MutableList<Fragment> = ArrayList()
    var bottomNavigationView: BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.title = "Новости"
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bn)
        list.add(fragment1)
        list.add(fragment2)
        lockableViewPager = findViewById(R.id.lvp)
        pagerAdapter = PagerAdapter(supportFragmentManager, list)
        lockableViewPager?.setAdapter(pagerAdapter)
//        lockableViewPager?.setSwipable(false)
        bottomNavigationView?.setOnNavigationItemSelectedListener(
                BottomNavigationView.OnNavigationItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.main -> {
                            lockableViewPager?.setCurrentItem(0, false)
                            supportActionBar!!.title = "Новости"
                        }
                        R.id.favs -> {
                            lockableViewPager?.setCurrentItem(1, false)
                            supportActionBar!!.title = "Понравившиеся"
                        }
                    }
                    false
                }
        )
    }

    override fun myClick(posts: Posts?, option: Int) {
        val fragment = supportFragmentManager.findFragmentById(R.id.lvp)
        if (option == 1) posts?.let { (fragment as LikeFragment?)?.savePosts(it) } else (fragment as LikeFragment?)?.removePosts(posts)
    }

    override fun removeItemLike(posts: Posts?) {
        (fragment1 as MainFragment).removeLike(posts)
        (fragment2 as LikeFragment).removeLike(posts)
    }
}





