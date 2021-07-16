package com.happycomp.weatherforecast.model.room.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.happycomp.weatherforecast.model.pojo.Current
import com.happycomp.weatherforecast.model.pojo.Weather

class Converters {
    @TypeConverter
    fun currentToString(current: Current): String {
        return Gson().toJson(current)
    }

    @TypeConverter
    fun stringToCurrent(currentString: String): Current {
        return Gson().fromJson(currentString, Current::class.java)
    }

    @TypeConverter
    fun weatherToString(weather: Weather): String {
        return Gson().toJson(weather)
    }

    @TypeConverter
    fun stringToWeather(weatherString: String): Weather {
        return Gson().fromJson(weatherString, Weather::class.java)
    }

}