package com.happycomp.weatherforecast.model.interfaces

import androidx.lifecycle.LiveData
import com.happycomp.weatherforecast.model.pojo.BaseWeather

interface FavoriteActions {
    fun observeAllFavorites(): LiveData<List<BaseWeather>>
    suspend fun addNewFavorite(lat: Double, long: Double, networkHandler: NetworkHandler)
    suspend fun deleteFavorite(baseWeather: BaseWeather)
    suspend fun updateFavorite(baseWeather: BaseWeather)
}