package com.happycomp.weatherforecast.model.pojo

import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("dt") val dt: Int,
    @SerializedName("sunrise") val sunrise: Int,
    @SerializedName("sunset") val sunset: Int,
    @SerializedName("temp") val temp: Float,
    @SerializedName("feels_like") val feels_like: Float,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("dew_point") val dew_point: Float,
    @SerializedName("uvi") val uvi: Float,
    @SerializedName("clouds") val clouds: Int,
    @SerializedName("visibility") val visibility: Int,
    @SerializedName("wind_speed") val wind_speed: Float,
    @SerializedName("wind_deg") val wind_deg: Int,
    @SerializedName("wind_gust") val wind_gust: Float = 0.0F,
    @SerializedName("weather") val weather: List<Weather>
)