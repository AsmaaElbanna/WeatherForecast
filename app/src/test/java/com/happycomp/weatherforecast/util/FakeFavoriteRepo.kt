package com.happycomp.weatherforecast.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.happycomp.weatherforecast.model.interfaces.FavoriteActions
import com.happycomp.weatherforecast.model.interfaces.NetworkHandler
import com.happycomp.weatherforecast.model.pojo.BaseWeather
import com.happycomp.weatherforecast.model.pojo.Current
import com.happycomp.weatherforecast.model.pojo.Weather

class FakeFavoriteRepo : FavoriteActions {

    private val weatherData = mutableListOf<BaseWeather>()
    private val observeWeatherData = MutableLiveData<List<BaseWeather>>(weatherData)

    override fun observeAllFavorites(): LiveData<List<BaseWeather>> = observeWeatherData

    override suspend fun addNewFavorite(lat: Double, long: Double, networkHandler: NetworkHandler) {
        try {
            networkHandler.showIndicator()

            if (lat == -1.0 || long == -1.0)
                throw Exception("TimeOut")
            else if (lat == 0.0 && long == 0.0)
                networkHandler.onErrorOccurred()
            else {
                weatherData.add(fakeFavorites.first())
                refreshLiveData()
                networkHandler.onSuccess()
            }
        } catch (e: Exception) {
            networkHandler.onConnectionFailed()
        }
    }

    override suspend fun deleteFavorite(baseWeather: BaseWeather) {
        weatherData.remove(baseWeather)
        refreshLiveData()
    }

    override suspend fun updateFavorite(baseWeather: BaseWeather) {

    }

    private fun refreshLiveData() {
        observeWeatherData.postValue(weatherData)
    }

    private val fakeFavorites = listOf(
        BaseWeather(
            31.1467777, 30.9073034, "Home", 7200,
            Current(
                1625916123,
                1625886001,
                1625936582,
                307.62F,
                308.61F,
                1005,
                37,
                290.82F,
                9.64F,
                0,
                10000,
                6.92F,
                322,
                5.71F,
                listOf(Weather(800, "Clear", "clear sky", "01d"))
            )
        ),
        BaseWeather(
            31.2242, 29.8848, "Alex", 7200,
            Current(
                1625916363,
                1625886236,
                1625936838,
                303.11F,
                306.31F,
                1006,
                62,
                295.04F,
                9.85F,
                40,
                10000,
                6.17F,
                310,
                weather = listOf(Weather(802, "Clouds", "scattered clouds", "03d"))
            )
        )
    )
}