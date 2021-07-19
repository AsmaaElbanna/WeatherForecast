package com.happycomp.weatherforecast.model.retrofit

import com.happycomp.weatherforecast.BuildConfig
import com.happycomp.weatherforecast.model.enums.Units
import com.happycomp.weatherforecast.model.pojo.BaseWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherInterface {
    @GET("data/2.5/onecall")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String = "minutely",
        @Query("units") units: String = Units.Standard.value,
        @Query("appid") APIKEY: String = BuildConfig.WEATHER_KEY
    ): Response<BaseWeather>
}