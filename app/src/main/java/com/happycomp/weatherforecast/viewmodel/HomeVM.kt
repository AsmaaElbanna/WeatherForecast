package com.happycomp.weatherforecast.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.happycomp.weatherforecast.model.interfaces.NetworkHandler
import com.happycomp.weatherforecast.model.pojo.BaseWeather
import com.happycomp.weatherforecast.model.retrofit.WeatherInterface
import com.happycomp.weatherforecast.model.extra.Constants
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeVM @AssistedInject constructor(
    private val weatherInterface: WeatherInterface,
    @Assisted private val networkHandler: NetworkHandler
) : ViewModel() {
    var weatherData = MutableLiveData<BaseWeather>()
    var lastKnownLocation = LatLng(26.8448466, 26.3846369)

    fun getWeather(location: LatLng = lastKnownLocation) {
        networkHandler.showIndicator()
        GlobalScope.launch {
            try {
                val response =
                    weatherInterface.getWeatherData(
                        location.latitude,
                        location.longitude,
                        units = Constants.currentUnits.value!!.value
                    )

                if (response.isSuccessful && response.body() != null) {
                    GlobalScope.launch(Dispatchers.Main) {
                        weatherData.value = response.body()
                        networkHandler.onSuccess()
                    }
                } else {
                    networkHandler.onErrorOccurred()
                }
            } catch (e: Exception) {
                networkHandler.onConnectionFailed()
            }

        }
    }

    fun saveAsLastResult(context: Context, baseWeather: BaseWeather) {
        context.getSharedPreferences("LastResult", Context.MODE_PRIVATE)
            .edit()
            .putString("LastLocation", Gson().toJson(baseWeather))
            .apply()
    }

    fun loadLastResult(context: Context): BaseWeather? {
        val lastLocation = context.getSharedPreferences("LastResult", Context.MODE_PRIVATE)
            .getString("LastLocation", null)

        return if (lastLocation != null) Gson().fromJson(
            lastLocation,
            BaseWeather::class.java
        ) else null
    }

}