package com.happycomp.weatherforecast.util

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.happycomp.weatherforecast.model.enums.Units
import com.happycomp.weatherforecast.model.pojo.UserUnits

object Constants {

    var currentUnits: MutableLiveData<Units> = MutableLiveData()
    var userUnits: UserUnits = UserUnits("C", "M/S")

    fun getSavedUnit(context: Context): String {
        currentUnits.value = Units.valueOf(context.getSharedPreferences(METRICS, Context.MODE_PRIVATE)
            .getString(VALUE, Units.Metric.name) ?: Units.Metric.name)

        when(currentUnits.value){
            Units.Metric -> userUnits = UserUnits("C", "M/S")
            Units.Standard -> userUnits = UserUnits("F", "M/S")
            Units.Imperial -> userUnits = UserUnits("K", "Mi/H")
        }
        return currentUnits.value!!.name
    }


    fun saveUnit(context: Context, value: String) {
        context.getSharedPreferences(METRICS, Context.MODE_PRIVATE)
            .edit().putString(VALUE, value).apply()

        currentUnits.value = Units.valueOf(value)
        when(currentUnits.value){
            Units.Metric -> userUnits = UserUnits("C", "M/S")
            Units.Standard -> userUnits = UserUnits("F", "M/S")
            Units.Imperial -> userUnits = UserUnits("K", "Mi/H")
        }
    }

    const val EXTRA_EXACT_ALARM_TIME = "EXTRA_EXACT_ALARM_TIME"
    const val WEATHER_DB = "weatherdb"

    private const val METRICS = "METRICS"
    private const val VALUE = "VALUE"
}

