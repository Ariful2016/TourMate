package com.arifcit.tourmate

import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Layout
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.arifcit.tourmate.databinding.ActivityMainBinding
import com.arifcit.tourmate.ui.login.LoginFragment
import com.arifcit.tourmate.userLocation.LocationViewModel
import com.arifcit.tourmate.userLocation.isLocationPermissionGranted
import com.arifcit.tourmate.userLocation.requestLocationPermission
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val locationViewModel : LocationViewModel by viewModels()
    private lateinit var client : FusedLocationProviderClient
    private lateinit var locationRequest : LocationRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        client = LocationServices.getFusedLocationProviderClient(this)

        val toolbar : Toolbar = findViewById(R.id.toolbar)
            setSupportActionBar(toolbar)




        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // hide toolbar from login and registration fragment
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.loginFragment || destination.id == R.id.registerFragment) {
                toolbar.setVisibility(View.GONE)

            } else {
                toolbar.setVisibility(View.VISIBLE)

            }
        }



        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_tours, R.id.nav_weather, R.id.nav_nearby, R.id.nav_geo
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //val navHostFragment = NavHostFragment.findNavController(this);
        NavigationUI.setupWithNavController(toolbar, navController,appBarConfiguration)


        if (isLocationPermissionGranted(this)){
            detectUseLocation()
        }else{
            requestLocationPermission(this)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101){
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)){
                detectUseLocation()
            }else{
                Toast.makeText(
                    this,
                    "Location is required to detect your current city"
                        ,Toast.LENGTH_LONG
                ).show()


            }
        }
    }

    private fun detectUseLocation() {
        /*fun createLocationRequest() {
            locationRequest = LocationRequest.create()?.apply {
                interval = 10000
                fastestInterval = 5000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
            val client2: SettingsClient = LocationServices.getSettingsClient(this)
            val task: Task<LocationSettingsResponse> = client2.checkLocationSettings(builder.build())
            task.addOnSuccessListener { locationSettingsResponse ->
                client.lastLocation.addOnSuccessListener { location ->
                    location?.let { locat->
                        locationViewModel.setNewLocation(locat)
                    }
                }
            }

            task.addOnFailureListener { exception ->
                if (exception is ResolvableApiException){
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                       *//* exception.startResolutionForResult(this@MainActivity,
                            REQUEST_CHECK_SETTINGS)*//*
                    } catch (sendEx: IntentSender.SendIntentException) {
                        // Ignore the error.
                    }
                }
            }
        }*/


       client.lastLocation.addOnSuccessListener { location ->
            location?.let { locat->
                locationViewModel.setNewLocation(locat)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_logout){
            FirebaseAuth.getInstance().signOut()
           // findNavController().navigate()
            //val intent = Intent(this,LoginFragment::class.java)
            //startActivity(intent)
            Toast.makeText(this,"Logout successfully", Toast.LENGTH_LONG).show()

            finish()
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}