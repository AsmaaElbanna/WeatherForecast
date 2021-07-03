package com.happycomp.weatherforecast.model.interfaces

import com.happycomp.weatherforecast.model.enums.Exclude
import com.happycomp.weatherforecast.model.pojo.BaseWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// https://api.openweathermap.org/data/2.5/onecall?lat=33.44&lon=-94.04&exclude=hourly,daily&appid=5d81fed7ec24e35f8359e5d0d3919b5a

// https://api.openweathermap.org/data/2.5/onecall?
// lat=33.44&
// lon=-94.04&
// exclude=hourly,daily&
// appid=5d81fed7ec24e35f8359e5d0d3919b5a
interface ApiInterface {
    @GET("data/2.5/onecall")
    fun getWeatherData(@Query("lat") lat: Double,
                       @Query("lon") lon: Double,
                       @Query("exclude") exclude: String = "${Exclude.Hourly.name}, daily",
                       @Query("appid") APIKEY:String = "5d81fed7ec24e35f8359e5d0d3919b5a"
    ): Call<BaseWeather>
}