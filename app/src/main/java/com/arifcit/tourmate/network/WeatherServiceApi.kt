package com.arifcit.tourmate.network

import com.arifcit.tourmate.model.CurrentWeatherModel
import com.arifcit.tourmate.model.ForecastWeatherModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Url

const val weather_api_key = "818beef7c4478fc7c5ae2539ea15c112"

const val base_url = "https://api.openweathermap.org/data/2.5/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(base_url)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface WeatherServiceApi {
    @GET
    suspend fun getCurrentWeatherData(@Url endUrl : String) : CurrentWeatherModel

    @GET
    suspend fun getForecastWeatherData(@Url endUrl: String) : ForecastWeatherModel
}

object WeatherApi {
    val weatherServiceApi : WeatherServiceApi by lazy {
        retrofit.create(WeatherServiceApi::class.java)
    }
}