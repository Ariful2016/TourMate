package com.arifcit.tourmate.ui.weather

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arifcit.tourmate.model.CurrentWeatherModel
import com.arifcit.tourmate.model.ForecastWeatherModel
import com.arifcit.tourmate.network.WeatherApi
import com.arifcit.tourmate.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()
    private val _currentWeatherData = MutableLiveData<CurrentWeatherModel>()
    private val _forecastWeatherData = MutableLiveData<ForecastWeatherModel>()

    var  tempStatus = false
    val current : LiveData<CurrentWeatherModel>
        get() = _currentWeatherData

    val forecast : LiveData<ForecastWeatherModel>
        get() = _forecastWeatherData

    fun getWeatherData(location: Location) {
        viewModelScope.launch {
            try {
                _currentWeatherData.value = repository.fetchCurrentWeatherData(location,tempStatus)
                _forecastWeatherData.value = repository.fetchForecastWeatherData(location,tempStatus)
            }catch (e: Exception){
                Log.e("Error", e.localizedMessage!!)
            }

        }
    }
}