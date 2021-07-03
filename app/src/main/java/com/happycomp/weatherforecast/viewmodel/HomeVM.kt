package com.happycomp.weatherforecast.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.happycomp.weatherforecast.model.interfaces.ApiInterface
import com.happycomp.weatherforecast.model.pojo.BaseWeather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeVM : ViewModel() {
    private val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val apiInterface: ApiInterface = retrofit.create(ApiInterface::class.java)

    var weatherData = MutableLiveData<BaseWeather>()

    fun getWeather(){
        val weatherCall: Call<BaseWeather> = apiInterface.getWeatherData(33.44, -94.04)

        weatherCall.enqueue(object : Callback<BaseWeather>{
            override fun onResponse(call: Call<BaseWeather>, response: Response<BaseWeather>) {
                weatherData.value = response.body()
//                if(response.body() != null)
//                    Log.i("MYDATAFROMAPI", response.body()!!.minutely.joinToString())
            }

            override fun onFailure(call: Call<BaseWeather>, t: Throwable) {

            }
        })
    }
}