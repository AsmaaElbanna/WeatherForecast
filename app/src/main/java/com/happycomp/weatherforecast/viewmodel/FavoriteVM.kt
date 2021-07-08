package com.happycomp.weatherforecast.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.happycomp.weatherforecast.model.pojo.BaseWeather
import com.happycomp.weatherforecast.model.room.WeatherDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteVM(application: Application) : AndroidViewModel(application) {
    private val favoritesDao = WeatherDataBase.getDatabase(application).favoritesDao

    lateinit var favorites: LiveData<List<BaseWeather>>

    init {
        viewModelScope.launch(Dispatchers.Default) {
            favorites = favoritesDao.getAllFavorites()
        }
    }

    fun addFavorite(baseWeather: BaseWeather) {
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