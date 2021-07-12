package com.happycomp.weatherforecast.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.happycomp.weatherforecast.model.enums.Units
import com.happycomp.weatherforecast.model.pojo.BaseWeather
import com.happycomp.weatherforecast.model.retrofit.WeatherInterface
import com.happycomp.weatherforecast.model.room.FavoritesDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteVM @Inject constructor(
    private val weatherInterface: WeatherInterface,
    private val favoritesDao: FavoritesDao
) : ViewModel() {

    val favorites: LiveData<List<BaseWeather>> = favoritesDao.getAllFavorites()

    fun addNewFavorite(lat: Double, long: Double) {
        GlobalScope.launch(Dispatchers.IO) {
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
                }
            }
        }
    }

    private fun addFavorite(baseWeather: BaseWeather) {
        viewModelScope.launch {
            favoritesDao.addFavorite(baseWeather)
        }
    }

    fun deleteFavorite(baseWeather: BaseWeather) {
        viewModelScope.launch {
            favoritesDao.deleteFavorite(baseWeather)
        }
    }
}