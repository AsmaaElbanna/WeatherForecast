package com.happycomp.weatherforecast.util

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.happycomp.weatherforecast.model.enums.Units
import com.happycomp.weatherforecast.model.enums.WindSpeed

object Constants {

    var currentUnits: MutableLiveData<Units> = MutableLiveData()
    var windSpeed: MutableLiveData<WindSpeed> = MutableLiveData()

    fun getSavedUnit(context: Context): String {
        currentUnits.value = Units.valueOf(context.getSharedPreferences(METRICS, Context.MODE_PRIVATE)
            .getString(VALUE, Units.Metric.name) ?: Units.Metric.name)

        return currentUnits.value!!.name
    }


    fun saveUnit(context: Context, value: String) {
        context.getSharedPreferences(METRICS, Context.MODE_PRIVATE)
            .edit().putString(VALUE, value).apply()
        currentUnits.value = Units.valueOf(value)
    }


    const val ACTION_SET_EXACT_ALARM = "ACTION_SET_EXACT_ALARM"
    const val ACTION_SET_REPETITIVE_ALARM = "ACTION_SET_REPETITIVE_ALARM"
    const val EXTRA_EXACT_ALARM_TIME = "EXTRA_EXACT_ALARM_TIME"
    const val WEATHER_DB = "weatherdb"

    private const val METRICS = "METRICS"
    private const val VALUE = "VALUE"
}

