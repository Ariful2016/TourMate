package com.arifcit.tourmate.userLocation

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import java.util.jar.Manifest

fun isLocationPermissionGranted(context: Context) : Boolean =
   ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
   PackageManager.PERMISSION_GRANTED

fun requestLocationPermission(activity: Activity) {
    activity.requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),101)
}