package com.example.movie.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.movie.R
import com.example.movie.model.cinema.Cinema
import com.example.movie.view_model.CinemaListViewModel
import com.example.movie.view_model.CinemaProviderFactory
import com.example.movie.view_model.ViewModelProviderFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_map.*


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var cinemaList: List<Cinema>
    private lateinit var cinemaListViewModel: CinemaListViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        map_view.onCreate(savedInstanceState)
        map_view.onResume()
        map_view.getMapAsync(this)
        val viewModelProviderFactory = ViewModelProviderFactory(requireContext())
        cinemaListViewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(CinemaListViewModel::class.java)
        cinemaList = cinemaListViewModel.getCinemaList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = LayoutInflater.from(container?.context)
            .inflate(R.layout.fragment_map, container, false)

        return view
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            googleMap = it
        }
        lateinit var lastMarker: LatLng
        // For showing a move to my location button
        googleMap.isMyLocationEnabled = true

        for (cinema in cinemaList) {
            val marker = LatLng(cinema.latitude, cinema.longitude)
            googleMap.addMarker(MarkerOptions().position(marker).title(cinema.title))
            lastMarker = marker
        }
        val cameraPosition =
            CameraPosition.Builder().target(lastMarker).zoom(12f).build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}
