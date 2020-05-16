package com.example.movie.view.activity

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.ktx.Firebase
import android.app.AlertDialog
import android.app.AlertDialog.Builder


class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    val fm: FragmentManager? = supportFragmentManager
    var fragment: Fragment? = null
    private var locationPermissionGranted: Boolean = false

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
                    if(checkMapServices()){
                        if(locationPermissionGranted){
                            fragment = MapFragment()
                            fm?.beginTransaction()
                                ?.replace(R.id.fragment_container, fragment as MapFragment)
                                ?.addToBackStack("Second")
                                ?.commit()
                            bottomNavigationView.menu.findItem(R.id.nav_map).isChecked = true
                        }
                        else{
                            getLocationPermission()
                        }
                    }
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

    private fun checkMapServices(): Boolean {

        if (isMapsEnabled()) {
            return true
        }

        return false
    }

    private fun buildAlertMessageNoGps() {
        val builder: AlertDialog.Builder = Builder(this)
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                val enableGpsIntent =
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS)
            })
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    private fun isMapsEnabled(): Boolean {
        val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps()
            return false
        }
        return true
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true

        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted = true
                }
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult: called.")
        when (requestCode) {
            PERMISSIONS_REQUEST_ENABLE_GPS -> {
                if (locationPermissionGranted) {

                } else {
                    getLocationPermission()
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val PERMISSIONS_REQUEST_ENABLE_GPS = 9002
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9003
    }
}
