package com.happycomp.weatherforecast.alarmmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.happycomp.weatherforecast.R
import com.happycomp.weatherforecast.model.pojo.BaseWeather
import com.happycomp.weatherforecast.model.retrofit.WeatherInterface
import com.happycomp.weatherforecast.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import io.karn.notify.Notify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReciever : BroadcastReceiver() {

    @Inject
     lateinit var weatherInterface:WeatherInterface
    private lateinit var context: Context
    override fun onReceive(context: Context, intent: Intent) {
//        val timeInMillis = intent.getLongExtra(Constants.EXTRA_EXACT_ALARM_TIME, 0L)
//        buildNotification(context, title = "set exact time ", convertDate(timeInMillis))

            this.context=context
            getWeather()

        //displayNotification("weather will be good")


//        val notifications = Notifications(context)
//        notifications.createNotificationChannelID(context.resources.getString(R.string.notification_Channel_ID),
//            "Weather forecast", "hhhhhhhhhhhhhhhhhhhhhhhhh")
//        notifications.displayNotification("The weather is good")


    }

    private fun buildNotification(context: Context, title: String, msg: String) {
        Notify
            .with(context)
            .content {
                this.title = title
                this.text = "it was rain at $msg"
            }
            .show()
    }

    fun convertDate(timeInMillis: Long): String =
        DateFormat.format("EE dd/MM/yyyy, hh:mm:ss a", timeInMillis).toString()


    fun getWeather(location: LatLng = LatLng(30.0595581, 31.223445)) {
        GlobalScope.launch {
            try {
                val response =
                    weatherInterface.getWeatherData(
                        location.latitude,
                        location.longitude,
                        units = Constants.currentUnits.value!!.value
                    )
                if (response.isSuccessful && response.body() != null) {
                    GlobalScope.launch(Dispatchers.Main) {
                       val weatherData = response.body()
                        displayNotification("The weather today will be ${weatherData!!.current.weather[0].description}")
                    }
                }
            } catch (e: Exception) {
                GlobalScope.launch(Dispatchers.Main) {
                    Toast.makeText(context, "Exception", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    fun displayNotification(contentText:String){
        val notifications = Notifications(context)
        notifications.createNotificationChannelID(context.resources.getString(R.string.notification_Channel_ID),
            "Weather forecast", "weatherChannel")
        notifications.displayNotification(contentText)

    }
}