package com.happycomp.weatherforecast.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.happycomp.weatherforecast.model.interfaces.NetworkHandler
import dagger.assisted.AssistedFactory

@AssistedFactory
interface FavoriteVMFactory {
    fun create(networkHandler: NetworkHandler): FavoriteVM

    class Factory(
        private val assistedFactory: FavoriteVMFactory,
        private val networkHandler: NetworkHandler,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return assistedFactory.create(networkHandler) as T
        }
    }
}