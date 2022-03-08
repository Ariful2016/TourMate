package com.arifcit.tourmate.bindingAdapters

import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

@BindingAdapter("app:setImageUrl")
fun ImageBindingAdapter(iv : ImageView, imageUrl : String) {
    Picasso.get().load(imageUrl).into(iv)

}

@BindingAdapter("app:setWeatherIcon")
fun setWeatherIcon(iv : ImageView, icon : String?){
    icon?.let {
        val url = "http://openweathermap.org/img/wn/${icon}@2x.png"
       // Picasso.get().load(url).into(iv)
        Glide.with(iv).load(url).into(iv)
    }

}