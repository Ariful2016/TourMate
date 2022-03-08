package com.arifcit.tourmate.repository

import android.location.Location
import com.arifcit.tourmate.model.CurrentWeatherModel
import com.arifcit.tourmate.model.ForecastWeatherModel
import com.arifcit.tourmate.network.WeatherApi
import com.arifcit.tourmate.network.weather_api_key

class WeatherRepository {

    suspend fun fetchCurrentWeatherData(location: Location, tempStatus : Boolean) : CurrentWeatherModel{

        val temp_unit = if (tempStatus) "imperial" else "metric"
        val endUrl = "weather?lat=${location.latitude}&lon=${location.longitude}&units=$temp_unit&appid=$weather_api_key"
        val currentWeatherData = WeatherApi.weatherServiceApi.getCurrentWeatherData(endUrl)

        return currentWeatherData
    }

    suspend fun fetchForecastWeatherData(location: Location, tempStatus: Boolean) : ForecastWeatherModel{

        val temp_unit = if (tempStatus) "imperial" else "metric"
        val endUrl = "forecast?lat=${location.latitude}&lon=${location.longitude}&units=$temp_unit&appid=$weather_api_key"
        val forecastWeatherData = WeatherApi.weatherServiceApi.getForecastWeatherData(endUrl)

        return forecastWeatherData
    }



}