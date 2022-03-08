package com.arifcit.tourmate.prefarence

import android.content.SharedPreferences


const val temp_status = "status"

fun setTimeStatus (status : Boolean, editor: SharedPreferences.Editor){
    editor.putBoolean(temp_status, status)
    editor.commit()
}

fun getTempStatus(preferences: SharedPreferences) =
    preferences.getBoolean(temp_status, false)