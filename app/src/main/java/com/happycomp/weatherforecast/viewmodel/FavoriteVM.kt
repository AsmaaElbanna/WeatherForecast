package com.happycomp.weatherforecast.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.happycomp.weatherforecast.model.interfaces.FavoriteActions
import com.happycomp.weatherforecast.model.interfaces.NetworkHandler
import com.happycomp.weatherforecast.model.pojo.BaseWeather
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteVM @AssistedInject constructor(
    private val favoriteActions: FavoriteActions,
    @Assisted private val networkHandler: NetworkHandler
) : ViewModel() {

    val favorites: LiveData<List<BaseWeather>> = favoriteActions.observeAllFavorites()
    var isRefreshed = false

    fun addNewFavorite(lat: Double, long: Double) {
        GlobalScope.launch(Dispatchers.IO) {
            favoriteActions.addNewFavorite(lat, long, networkHandler)
        }
    }

    fun deleteFavorite(baseWeather: BaseWeather) {
        GlobalScope.launch {
            favoriteActions.deleteFavorite(baseWeather)
        }
    }

    private fun updateFavorite(baseWeather: BaseWeather) {
        GlobalScope.launch {
            favoriteActions.updateFavorite(baseWeather)
        }
    }

    fun refresh() {
        for (favorite in favorites.value!!)
            updateFavorite(favorite)
        isRefreshed = true
    }
}