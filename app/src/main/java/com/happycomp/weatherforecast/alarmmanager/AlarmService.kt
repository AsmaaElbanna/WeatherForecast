package com.happycomp.weatherforecast.alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import com.happycomp.weatherforecast.util.Constants
import com.happycomp.weatherforecast.util.RandomInUtil
import dagger.hilt.android.AndroidEntryPoint

class AlarmService(private val context: Context) {

    fun setExactAlarm(timeInMillis: Long, id: Int) {
        val reciever = Intent(context, AlarmReciever::class.java).apply {
            putExtra(Constants.EXTRA_EXACT_ALARM_TIME, timeInMillis)
        }

        val pendingIntent =
            PendingIntent.getBroadcast(context, id, reciever, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)

    }


    private fun getIntent(): Intent = Intent(context, AlarmReciever::class.java)

    private fun getPendingIntent(intent: Intent, id: Int): PendingIntent =
        PendingIntent.getBroadcast(
            context,
            id,
            intent,
            0
        )
}