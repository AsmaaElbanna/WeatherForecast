package com.happycomp.weatherforecast.alarmmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.happycomp.weatherforecast.R

class Notifications(val context: Context) {

    private var notificationManager: NotificationManager? = null

    init {
        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    fun createNotificationChannelID(id: String, name: String, channelDescription: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importanceHigh = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importanceHigh).apply {
                description = channelDescription
            }
            notificationManager?.createNotificationChannel(channel)
        }
    }

    fun displayNotification(contentText: String) {
        val notificationID = 33
        val notification = NotificationCompat.Builder(
            context,
            context.resources.getString(R.string.notification_Channel_ID)
        )
            .setContentTitle("Weather")
            .setContentText(contentText)
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.clouds
                )
            )
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        notificationManager?.notify(notificationID, notification.build())
    }
}