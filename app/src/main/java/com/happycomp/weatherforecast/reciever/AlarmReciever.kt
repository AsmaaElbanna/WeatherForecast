package com.happycomp.weatherforecast.reciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import com.happycomp.weatherforecast.service.AlarmService
import com.happycomp.weatherforecast.util.Constants
import io.karn.notify.Notify
import java.util.*
import java.util.concurrent.TimeUnit


class AlarmReciever :BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent) {
        val timeInMillis = intent.getLongExtra(Constants.EXTRA_EXACT_ALARM_TIME,0L)

        when(intent.action){
            Constants.ACTION_SET_EXACT_ALARM ->{
                buildNotification(context,title = "set exact time ",convertDate(timeInMillis))
            }

            Constants.ACTION_SET_REPETITIVE_ALARM ->{
                val cal =Calendar.getInstance().apply {
                    this.timeInMillis =timeInMillis + TimeUnit.DAYS.toMillis(7)
                }
                AlarmService(context).setRepetitiveAlarm(cal.timeInMillis)
                buildNotification(context,"set Repetitive ",convertDate(timeInMillis))
            }
        }
    }

    private fun buildNotification(context: Context,title:String,msg:String){
     Notify
         .with(context)
         .content {
             this.title = title
             this.text = "it was rain at $msg"
         }
         .show()
    }

    private fun convertDate(timeInMillis:Long) :String =
        DateFormat.format("dd/mm/yyyy , hh:mm:ss",timeInMillis).toString()

}