package com.happycomp.weatherforecast.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.happycomp.weatherforecast.model.enums.Units
import com.happycomp.weatherforecast.model.interfaces.ApiInterface
import com.happycomp.weatherforecast.model.pojo.BaseWeather
import com.happycomp.weatherforecast.model.room.WeatherDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FavoriteVM(application: Application) : AndroidViewModel(application) {
    private val apiInterface: ApiInterface =
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

    private val favoritesDao = WeatherDataBase.getDatabase(application).favoritesDao
    lateinit var favorites: LiveData<List<BaseWeather>>

    fun addNewFavorite(lat: Double, long: Double) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = apiInterface.getWeatherData(lat, long, "minutely,hourly,daily", Units.metric.name)
            if (response.isSuccessful) {
                if(response.body() != null){
                    val baseWeather: BaseWeather = response.body()!!
                    addFavorite(baseWeather)
                }
            }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.Default) {
            favorites = favoritesDao.getAllFavorites()
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