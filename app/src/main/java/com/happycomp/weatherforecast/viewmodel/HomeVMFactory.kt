package com.happycomp.weatherforecast.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.happycomp.weatherforecast.model.interfaces.NetworkHandler
import dagger.assisted.AssistedFactory

@AssistedFactory
interface HomeVMFactory {
    fun create(networkHandler: NetworkHandler): HomeVM

    class Factory(
        private val assistedFactory: HomeVMFactory,
        private val networkHandler: NetworkHandler,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return assistedFactory.create(networkHandler) as T
        }
    }
}