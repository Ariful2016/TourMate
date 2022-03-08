package com.arifcit.tourmate.ui.geo

import android.app.PendingIntent
import android.content.Intent
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.arifcit.tourmate.R
import com.arifcit.tourmate.receivers.GeofencingBroadcastReceiver
import com.arifcit.tourmate.userLocation.LocationViewModel
import com.arifcit.tourmate.userLocation.isLocationPermissionGranted
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private lateinit var map : GoogleMap
    private val locationViewModel : LocationViewModel by activityViewModels()
    private lateinit var geofencingClient: GeofencingClient
    private var geofenceList = mutableListOf<Geofence>()
    private val pendingIntent : PendingIntent by lazy {
        val intent = Intent(requireContext(), GeofencingBroadcastReceiver::class.java)
        //intent.setAction("com.arifcit.tourmate.ACTION_GEOFENCE")
        PendingIntent.getBroadcast(requireContext(),101,intent,PendingIntent.FLAG_UPDATE_CURRENT)
    }
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        if (isLocationPermissionGranted(requireActivity())){
            map.isMyLocationEnabled = true
        }
        map.uiSettings.isZoomControlsEnabled = true
         locationViewModel.location.observe(viewLifecycleOwner) {
             val myLocation = LatLng(it.latitude, it.longitude)
             map.addMarker(MarkerOptions().position(myLocation).title("I am here"))
             map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15f))
         }

        map.setOnMapLongClickListener { latLng->
            createGeofence(latLng )
        }

    }

    private fun createGeofence(latLng: LatLng) {

        val geofence = Geofence.Builder()
            .setRequestId("Target")
            .setCircularRegion(latLng.latitude,latLng.longitude, 200f)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
            .setExpirationDuration(6 * 60 * 60 * 1000)
            .build()

        geofenceList.add(geofence)

        val request = GeofencingRequest.Builder()
            .addGeofences(geofenceList)
            .setInitialTrigger(Geofence.GEOFENCE_TRANSITION_ENTER)
            .build()

        geofencingClient.addGeofences(request,pendingIntent)
            .addOnSuccessListener {
                Toast.makeText(requireActivity(),"Place added",Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(requireActivity(),"Failed",Toast.LENGTH_LONG).show()
            }



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
        geofencingClient = LocationServices.getGeofencingClient(requireActivity())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}