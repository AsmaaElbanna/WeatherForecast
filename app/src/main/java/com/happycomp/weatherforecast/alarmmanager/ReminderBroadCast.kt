package com.happycomp.weatherforecast.alarmmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.happycomp.weatherforecast.R

class ReminderBroadCast : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {


//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"notifyApp")
//                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
//                .setContentTitle("get up my lovely user")
//                .setContentText("let's make a new achievement")
//                .setPriority(NotificationCompat.PRIORITY_HIGH);
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//
//        notificationManager.notify(200,builder.build());

        val notifications = Notifications(context)
        notifications.createNotificationChannelID(context!!.resources.getString(R.string.notification_Channel_ID),
                "get up y user ", "hhhhhhhhhhhhhhhhhhhhhhhhh")
        notifications.displayNotification("The weather is good")


    }
}