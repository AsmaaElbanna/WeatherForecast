package com.happycomp.weatherforecast.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.happycomp.weatherforecast.model.interfaces.NetworkHandler
import com.happycomp.weatherforecast.util.FakeFavoriteRepo
import com.happycomp.weatherforecast.util.MainCoroutineRule
import com.happycomp.weatherforecast.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoriteVMTest : NetworkHandler {
    private lateinit var favoriteVM: FavoriteVM

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        favoriteVM = FavoriteVM(FakeFavoriteRepo(), this)
    }

    @Test
    fun `add favorite, result Timeout Exception and empty list`() {
        favoriteVM.addNewFavorite(-1.0, -1.0)
        assertThat(favoriteVM.favorites.getOrAwaitValue().count()).isEqualTo(0)
    }

    @Test
    fun `add favorite, result error and empty list`() {
        favoriteVM.addNewFavorite(0.0, 0.0)
        assertThat(favoriteVM.favorites.getOrAwaitValue().count()).isEqualTo(0)
    }

    @Test
    fun `add favorite, result success and one favorite`() {
        favoriteVM.addNewFavorite(31.1467777, 30.9073034)
        assertThat(favoriteVM.favorites.getOrAwaitValue().count()).isEqualTo(1)
    }

    @Test
    fun `delete favorite, result success and empty favorite`() {
        favoriteVM.addNewFavorite(31.1467777, 30.9073034)
        assertThat(favoriteVM.favorites.getOrAwaitValue().count()).isEqualTo(1)
        favoriteVM.deleteFavorite(favoriteVM.favorites.value!!.first())
        assertThat(favoriteVM.favorites.getOrAwaitValue().count()).isEqualTo(0)
    }

    override fun onConnectionFailed() {
        println("Connection Failed")
        super.onConnectionFailed()
        println()
    }

    override fun onErrorOccurred() {
        println("Error Occurred")
        super.onErrorOccurred()
        println()
    }

    override fun onSuccess() {
        println("Success")
        super.onSuccess()
        println()
    }

    override fun showIndicator() {
        println("Show Indicator")
    }

    override fun hideIndicator() {
        println("Hide Indicator")
    }
}