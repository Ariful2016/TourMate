package com.arifcit.tourmate.receivers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.arifcit.tourmate.R
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofencingBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val event = GeofencingEvent.fromIntent(intent)
        var transitionMsg = ""
        if (event.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER){

            transitionMsg = "entered"
        } else{
            transitionMsg = "exit"
        }
        var targetList = mutableListOf<String>()
        event.triggeringGeofences.forEach {
            targetList.add(it.requestId)
        }

        val  nameString = targetList.joinToString(separator = ", ")
        sendNotification(context,transitionMsg,nameString)

    }

    private fun sendNotification(context: Context, transitionMsg: String, nameString: String) {
        var builder = NotificationCompat.Builder(context,"my_channel")
            .setSmallIcon(R.drawable.ic_baseline_notifications)
            .setContentTitle("Location Alert")
            .setContentText("You have $transitionMsg $nameString")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Geofence Channel"
            val descriptionText = "Notification for Location Alert"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("my_channel", name, importance).apply {
                description = descriptionText
            }
            manager.createNotificationChannel(channel)


        }
        manager.notify(1, builder.build())
    }
}