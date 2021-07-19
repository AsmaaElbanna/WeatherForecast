package com.happycomp.weatherforecast.model.room.data

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.happycomp.weatherforecast.model.pojo.Current

class Converters {
    @TypeConverter
    fun currentToString(current: Current): String = Gson().toJson(current)

    @TypeConverter
    fun stringToCurrent(currentString: String): Current =
        Gson().fromJson(currentString, Current::class.java)

    @TypeConverter
    fun locationToString(location: LatLng): String = Gson().toJson(location)

    @TypeConverter
    fun stringToLocation(locationString: String): LatLng =
        Gson().fromJson(locationString, LatLng::class.java)
}