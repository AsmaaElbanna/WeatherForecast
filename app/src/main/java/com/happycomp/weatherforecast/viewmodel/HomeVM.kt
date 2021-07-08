package com.happycomp.weatherforecast.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.happycomp.weatherforecast.model.enums.Units
import com.happycomp.weatherforecast.model.interfaces.ApiInterface
import com.happycomp.weatherforecast.model.pojo.BaseWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeVM : ViewModel() {
    private val apiInterface: ApiInterface =
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

    var weatherData = MutableLiveData<BaseWeather>()

    fun getWeather() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = apiInterface.getWeatherData(31.0455976,30.7809564, units = Units.metric.name)
            if (response.isSuccessful) {
                GlobalScope.launch(Dispatchers.Main) {
                    weatherData.value = response.body()
                }
            }
        }
    }
}