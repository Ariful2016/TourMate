package com.arifcit.tourmate.userLocation

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel : ViewModel() {
    private val _location = MutableLiveData<Location>()

    val location : LiveData<Location>
        get() = _location

    fun setNewLocation(location: Location){
        _location.value = location
    }

}