package com.happycomp.weatherforecast.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import com.happycomp.weatherforecast.reciever.AlarmReciever
import com.happycomp.weatherforecast.util.Constants
import com.happycomp.weatherforecast.util.RandomInUtil

class AlarmService(private val context: Context) {


    private val alarmManager: AlarmManager? =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

    fun setExactAlarm(timeInMillis: Long) {
        setAlarm(
            timeInMillis,
            getPendingIntent(
                getIntent().apply {
                    action = Constants.ACTION_SET_EXACT_ALARM
                    putExtra(Constants.EXTRA_EXACT_ALARM_TIME, timeInMillis)
                }
            )
        )

    }

    // repeat or not
    fun setRepetitiveAlarm(timeInMillis: Long) {
        setAlarm(
            timeInMillis,
            getPendingIntent(
                getIntent().apply {
                    action = Constants.ACTION_SET_REPETITIVE_ALARM
                    putExtra(Constants.EXTRA_EXACT_ALARM_TIME, timeInMillis)
                }
            )
        )

    }

    private fun setAlarm(timeInMillis: Long, pendingIntent: PendingIntent) {
        alarmManager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
            }
        }
    }

    private fun getIntent(): Intent = Intent(context, AlarmReciever::class.java)

    private fun getPendingIntent(intent: Intent): PendingIntent =
        PendingIntent.getBroadcast(
            context,
            RandomInUtil.getRandomInt().also {
                listOfAlarmsIDs.add(it)
                Toast.makeText(context, "id $it", Toast.LENGTH_SHORT).show()
            },
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    companion object {
        val listOfAlarmsIDs = arrayListOf<Int>()
    }


}