package com.happycomp.weatherforecast.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.happycomp.weatherforecast.model.enums.Units
import com.happycomp.weatherforecast.model.pojo.BaseWeather
import com.happycomp.weatherforecast.model.retrofit.WeatherInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(private val weatherInterface: WeatherInterface) : ViewModel() {
    var weatherData = MutableLiveData<BaseWeather>()

    fun getWeather() {
        GlobalScope.launch(Dispatchers.IO) {
            val response =
                weatherInterface.getWeatherData(31.2242387,29.8848462, units = Units.metric.name)
            if (response.isSuccessful) {
                GlobalScope.launch(Dispatchers.Main) {
                    weatherData.value = response.body()
                }
            }
        }
    }
}