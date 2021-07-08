package com.happycomp.weatherforecast.model.pojo

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorites", primaryKeys = ["lat", "lon"])
data class BaseWeather(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("timezone_offset") val timezone_offset: Int,
    @SerializedName("current") val current: Current
){
    @Ignore
    @SerializedName("hourly") val hourly: List<Hourly>? = null

    @Ignore
    @SerializedName("daily") val daily: List<Daily>? = null
}