package com.happycomp.weatherforecast.model.room.repo

import androidx.lifecycle.LiveData
import com.happycomp.weatherforecast.model.enums.Units
import com.happycomp.weatherforecast.model.interfaces.FavoriteActions
import com.happycomp.weatherforecast.model.interfaces.NetworkHandler
import com.happycomp.weatherforecast.model.pojo.BaseWeather
import com.happycomp.weatherforecast.model.retrofit.WeatherInterface
import com.happycomp.weatherforecast.model.room.data.FavoritesDao
import javax.inject.Inject

class FavoriteRepo @Inject constructor(
    private val weatherInterface: WeatherInterface,
    private val favoritesDao: FavoritesDao
) : FavoriteActions {

    override fun observeAllFavorites(): LiveData<List<BaseWeather>> =
        favoritesDao.observeAllFavorites()

    override suspend fun addNewFavorite(lat: Double, long: Double, networkHandler: NetworkHandler) {
        try {
            networkHandler.showIndicator()
            val response = weatherInterface.getWeatherData(
                lat,
                long,
                "minutely,hourly,daily",
                Units.metric.name
            )
            if (response.isSuccessful) {
                if (response.body() != null) {
                    val baseWeather: BaseWeather = response.body()!!
                    addFavorite(baseWeather)
                    networkHandler.onSuccess()
                }
            } else {
                networkHandler.onErrorOccurred()
            }
        } catch (e: Exception) {
            networkHandler.onConnectionFailed()
        }
    }

    private suspend fun addFavorite(baseWeather: BaseWeather) {
        favoritesDao.addFavorite(baseWeather)
    }

    override suspend fun deleteFavorite(baseWeather: BaseWeather) {
        favoritesDao.deleteFavorite(baseWeather)
    }
}