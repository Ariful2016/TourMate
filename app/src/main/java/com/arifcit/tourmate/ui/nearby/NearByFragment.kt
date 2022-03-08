package com.arifcit.tourmate.ui.nearby

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arifcit.tourmate.R
import com.arifcit.tourmate.databinding.FragmentNearbyBinding
import com.arifcit.tourmate.userLocation.isLocationPermissionGranted
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class NearByFragment : Fragment() {

    private lateinit var map : GoogleMap
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true
        /*locationViewModel.location.observe(viewLifecycleOwner) {
            val myLocation = LatLng(it.latitude, it.longitude)
            map.addMarker(MarkerOptions().position(myLocation).title("I am here"))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15f))
        }*/
        val myLocation = LatLng(23.7434, 90.4546)
        map.addMarker(MarkerOptions().position(myLocation).title("I am here"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15f))

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //geofencingClient = LocationServices.getGeofencingClient(requireActivity())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}