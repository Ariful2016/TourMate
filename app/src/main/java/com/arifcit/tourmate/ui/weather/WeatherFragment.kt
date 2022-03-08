package com.arifcit.tourmate.ui.weather

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.arifcit.tourmate.databinding.FragmentWeatherBinding
import com.arifcit.tourmate.prefarence.getTempStatus
import com.arifcit.tourmate.prefarence.setTimeStatus
import com.arifcit.tourmate.userLocation.LocationViewModel


class WeatherFragment : Fragment() {

    private lateinit var binding : FragmentWeatherBinding
    private  val weatherViewModel: WeatherViewModel by viewModels()
    private val locationViewModel : LocationViewModel by activityViewModels()
    private lateinit var preference : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater)

        preference = requireActivity().getSharedPreferences("weather_prefs", Context.MODE_PRIVATE)

        binding.tempSwitch.isChecked = getTempStatus(preference)
        weatherViewModel.tempStatus = getTempStatus(preference)

       // weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        locationViewModel.location.observe(viewLifecycleOwner) {
            weatherViewModel.getWeatherData(it)
        }

        weatherViewModel.current.observe(viewLifecycleOwner) {

            binding.current = it

        }

        weatherViewModel.forecast.observe(viewLifecycleOwner) {

            Toast.makeText(activity, "${it.list?.size}", Toast.LENGTH_LONG ).show()
            Log.d("fore", "onCreateView:${it.list?.size} ")
        }

        binding.tempSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            setTimeStatus(isChecked, preference.edit())
            weatherViewModel.tempStatus = isChecked
            weatherViewModel.getWeatherData(locationViewModel.location.value!!)
        }






        return binding.root
    }


}