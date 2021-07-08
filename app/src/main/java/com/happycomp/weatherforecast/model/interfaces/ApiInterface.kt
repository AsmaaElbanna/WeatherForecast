package com.happycomp.weatherforecast.model.interfaces

import com.happycomp.weatherforecast.model.enums.Exclude
import com.happycomp.weatherforecast.model.pojo.BaseWeather
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// https://api.openweathermap.org/data/2.5/onecall?lat=33.44&lon=-94.04&appid=5d81fed7ec24e35f8359e5d0d3919b5a
// https://maps.googleapis.com/maps/api/geocode/json?latlng=31.0455976,30.7809564&sensor=false&key=AIzaSyCJwAX8vgHpMN1VweL7A7WUpHg-QonHhq0

// https://api.openweathermap.org/data/2.5/onecall?
// lat=33.44&
// lon=-94.04&
// exclude=hourly,daily&
// appid=5d81fed7ec24e35f8359e5d0d3919b5a
interface ApiInterface {
//    @GET("data/2.5/onecall")
//    fun getWeatherData(
//        @Query("lat") lat: Double,
//        @Query("lon") lon: Double,
//        @Query("exclude") exclude: String = "minutely",
//        @Query("appid") APIKEY: String = "5d81fed7ec24e35f8359e5d0d3919b5a"
//    ): Call<BaseWeather>

    @GET("data/2.5/onecall")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String = "minutely",
        @Query("units") units: String = "standard",
        @Query("appid") APIKEY: String = "5d81fed7ec24e35f8359e5d0d3919b5a"
    ): Response<BaseWeather>
}