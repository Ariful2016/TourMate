package com.arifcit.tourmate.bindingAdapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*


@BindingAdapter("app:setTimeStamp")
fun setFormattedDate(tv : TextView, timestamp: Timestamp){
    val formattedDate = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault() )
        .format(timestamp.toDate())
    tv.text = "$formattedDate"
}

@BindingAdapter("app:setWeatherTimeStamp")
fun setFormattedWeatherDate(tv : TextView, date: Long){
    val formattedDate = SimpleDateFormat("EEE, dd, yyyy", Locale.getDefault() )
        .format(Date(date *1000))
    tv.text = "$formattedDate"
}