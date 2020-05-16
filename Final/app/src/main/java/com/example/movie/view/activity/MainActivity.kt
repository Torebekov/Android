package com.example.movie.view.activity

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.content.pm.PackageManager
import android.location.LocationManager
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
import androidx.lifecycle.ViewModelProvider
import com.example.movie.model.cinema.Cinema
import com.example.movie.view_model.CinemaListViewModel
import com.example.movie.view_model.CinemaProviderFactory

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var cinemaListViewModel: CinemaListViewModel
    val fm: FragmentManager? = supportFragmentManager
    var fragment: Fragment? = null
    private var locationPermissionGranted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val cinemaProviderFactory = CinemaProviderFactory(this)
        cinemaListViewModel = ViewModelProvider(this, cinemaProviderFactory)
            .get(CinemaListViewModel::class.java)
        cinemaListViewModel.addCinemaListToDatabase(cinemaListGenerator())

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

    fun cinemaListGenerator(): List<Cinema> {
        val cinema = Cinema(
            1,
            "Bekmambetov Cinema",
            "пл. Республики 13, Алматыпл. Республики 13, Алматы",
            43.23795,
            76.945586
        )
        val cinema1 = Cinema(
            2,
            "CINEMAX Dostyk Multiplex",
            "мкр.Самал 2, 111 д. в ТРЦ \"Dostyk Plaza\"",
            43.232974,
            76.955679
        )
        val cinema2 = Cinema(
            3,
            "Lumiera Cinema",
            "проспект Абылай Хана 62, Алматы",
            43.262106,
            76.941394
        )
        val cinema3 = Cinema(
            4,
            "Kinopark 11 IMAX Esentai",
            "проспект Аль-Фараби, 77/8 Esentai Mall, Алматы",
            43.218559,
            76.927724
        )
        val cinema4 = Cinema(
            5,
            "Cinema Towers 3D",
            "улица Байзакова 280, Алматы",
            43.237484,
            76.91504
        )
        val cinema5 = Cinema(
            6,
            "Chaplin Cinemas(Mega)",
            "город, ул. Макатаева 127/9, Алматы",
            43.264043,
            76.929472
        )
        val cinema6 = Cinema(
            7,
            "Арман 3D",
            "ТРЦ \"MART\"",
            43.336739,
            76.952996
        )
        val cinema7 = Cinema(
            8,
            "Nomad Cinema",
            "город, проспект Рыскулова 103, Алматы",
            43.267059,
            76.87022
        )
        val cinema8 = Cinema(
            9,
            "Kinopark 6 Sputnik",
            "Мамыр, микрорайон 1, 8а ТРЦ \"Sputnik Mall, Алматы",
            43.211989,
            76.842285
        )
        val cinema9 = Cinema(
            10,
            "Kinopark 8 Moskva",
            "8 микрорайон, 37/1 ТРЦ MOSKVA Metropolitan, Алматы",
            43.226885,
            76.864132
        )
        val cinema10 = Cinema(
            11,
            "Kinopark 5 Atakent",
            "ул. Тимирязева, 42 к3 ТРК \"Atakent Mall, Алматы",
            43.225622,
            76.907748
        )
        val cinema11 = Cinema(
            12,
            "Kinopark 16 Forum",
            "пр. Сейфуллина, 615 ТРЦ \"Forum",
            43.234228,
            76.935831
        )
        val list: MutableList<Cinema> = ArrayList()
        list.add(cinema)
        list.add(cinema1)
        list.add(cinema2)
        list.add(cinema3)
        list.add(cinema5)
        list.add(cinema6)
        list.add(cinema7)
        list.add(cinema8)
        list.add(cinema9)
        list.add(cinema10)
        list.add(cinema11)
        list.add(cinema4)
        return list
    }


    companion object {
        private const val TAG = "MainActivity"
        private const val PERMISSIONS_REQUEST_ENABLE_GPS = 9002
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9003
    }
}
