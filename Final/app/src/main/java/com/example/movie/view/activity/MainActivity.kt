package com.example.movie.view.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.movie.R
import com.example.movie.view.fragment.FavouritesFragment
import com.example.movie.view.fragment.MapFragment
import com.example.movie.view.fragment.MovieListFragment
import com.example.movie.view.fragment.UserFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.iid.FirebaseInstanceId


class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    val fm: FragmentManager? = supportFragmentManager
    var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragment = fm?.findFragmentById(R.id.fragment_container)

        firebaseAnalytics = Firebase.analytics
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bot)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    fragment = MovieListFragment()
                    fm?.beginTransaction()
                        ?.replace(R.id.fragment_container, fragment as MovieListFragment)
                        ?.addToBackStack("firstFrag")
                        ?.commit()
                    bottomNavigationView.menu.findItem(R.id.nav_home).isChecked = true
                }
                R.id.nav_fav -> {
                    fragment = FavouritesFragment()
                    fm?.beginTransaction()
                        ?.replace(R.id.fragment_container, fragment as FavouritesFragment)
                        ?.addToBackStack("Third")
                        ?.commit()
                    bottomNavigationView.menu.findItem(R.id.nav_fav).isChecked = true
                    Log.d("lol", fm?.backStackEntryCount.toString())

                }
                R.id.nav_profile -> {
                    fragment = UserFragment()
                    fm?.beginTransaction()
                        ?.replace(R.id.fragment_container, fragment as UserFragment)
                        ?.addToBackStack("Fourth")
                        ?.commit()
                    bottomNavigationView.menu.findItem(R.id.nav_profile).isChecked = true

                }
                R.id.nav_map -> {
                    fragment = MapFragment()
                    fm?.beginTransaction()
                        ?.replace(R.id.fragment_container, fragment as MapFragment)
                        ?.addToBackStack("Second")
                        ?.commit()
                    bottomNavigationView.menu.findItem(R.id.nav_map).isChecked = true
                }
            }
            false
        }
        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId =
                R.id.nav_home
        }


        intent.extras?.let {

            for (key in it.keySet()) {

                val value = intent.extras?.get(key)

                Log.d(TAG, "Key: $key Value: $value")

            }

        }
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                val msg = getString(R.string.msg_token_fmt, token)
                Log.d(TAG, msg)
            })

    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
