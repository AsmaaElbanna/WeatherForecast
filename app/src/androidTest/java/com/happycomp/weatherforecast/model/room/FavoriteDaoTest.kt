package com.happycomp.weatherforecast.model.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.happycomp.weatherforecast.extra.getOrAwaitValue
import com.happycomp.weatherforecast.model.pojo.BaseWeather
import com.happycomp.weatherforecast.model.pojo.Current
import com.happycomp.weatherforecast.model.pojo.Weather
import com.happycomp.weatherforecast.model.room.data.FavoritesDao
import com.happycomp.weatherforecast.model.room.data.WeatherDataBase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class FavoriteDaoTest {
    private lateinit var dataBase: WeatherDataBase
    private lateinit var dao: FavoritesDao

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

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        dataBase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDataBase::class.java
        ).allowMainThreadQueries().build()

        dao = dataBase.favoritesDao
    }

    @Test
    fun addFavorite() = runBlocking {
        dao.addFavorite(fakeFavorites.first())

        val allFavorites = dao.observeAllFavorites().getOrAwaitValue()

        assert(allFavorites.contains(fakeFavorites.first()))
    }

    @Test
    fun deleteFavorite() = runBlocking {
        dao.addFavorite(fakeFavorites.first())
        dao.addFavorite(fakeFavorites.last())
        dao.deleteFavorite(fakeFavorites.last())

        val allFavorites = dao.observeAllFavorites().getOrAwaitValue()
        assert(!allFavorites.contains(fakeFavorites.last()))
        assert(allFavorites.count() == 1)
    }

    @After
    fun tearDown() {
        dataBase.close()
    }
}