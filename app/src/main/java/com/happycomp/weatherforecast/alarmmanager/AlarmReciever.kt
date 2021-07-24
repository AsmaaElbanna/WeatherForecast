package com.happycomp.weatherforecast.alarmmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.happycomp.weatherforecast.R
import com.happycomp.weatherforecast.model.enums.AlarmType
import com.happycomp.weatherforecast.model.enums.Units
import com.happycomp.weatherforecast.model.pojo.Alarm
import com.happycomp.weatherforecast.model.retrofit.WeatherInterface
import com.happycomp.weatherforecast.model.room.data.AlarmDao
import com.happycomp.weatherforecast.viewmodel.AlarmVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReciever : BroadcastReceiver() {

    @Inject
    lateinit var alarmDao: AlarmDao

    @Inject
    lateinit var weatherInterface: WeatherInterface

    private lateinit var context: Context
    private lateinit var alarmVM: AlarmVM
    private lateinit var alarm: Alarm

    override fun onReceive(context: Context, intent: Intent) {
        this.context = context

        alarmVM = AlarmVM(alarmDao)

        alarm = alarmVM.getAlarmByID(intent.getIntExtra("ID", 0))

        getWeather(LatLng(alarm.location.latitude, alarm.location.longitude))
    }

    fun convertDate(timeInMillis: Long): String =
        DateFormat.format("EE dd/MM/yyyy, hh:mm:ss a", timeInMillis).toString()

    private fun getWeather(location: LatLng = LatLng(30.0595581, 31.223445)) {
        GlobalScope.launch {
            try {
                val response =
                    weatherInterface.getWeatherData(
                        location.latitude,
                        location.longitude,
                        units = Units.Metric.value,
                        exclude = "minutely,hourly"
                    )
                if (response.isSuccessful && response.body() != null) {
                    GlobalScope.launch(Dispatchers.Main) {
                        val weatherData = response.body()
                        alarmVM.deleteByID(alarm.id)

                        val type = AlarmType.valueOf(alarm.type)
                        if (weatherData!!.current.weather.first().description.contains(
                                type.value,
                                true
                            )
                        )

                            displayNotification("The weather today will be ${weatherData.daily!!.first().weather.first().description}")
                    }
                }
            } catch (e: Exception) {
                GlobalScope.launch(Dispatchers.Main) {
                    Log.i("getWeatherTAG", e.toString())
                }
            }

        }
    }

    private fun displayNotification(contentText: String) {
        val notifications = Notifications(context)
        notifications.createNotificationChannelID(
            context.resources.getString(R.string.notification_Channel_ID),
            "Weather forecast", "weatherChannel"
        )
        notifications.displayNotification(contentText)
    }
}