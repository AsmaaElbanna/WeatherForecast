package com.happycomp.weatherforecast.di

import android.content.Context
import androidx.room.Room
import com.happycomp.weatherforecast.model.interfaces.FavoriteActions
import com.happycomp.weatherforecast.model.retrofit.WeatherInterface
import com.happycomp.weatherforecast.model.room.data.AlarmDao
import com.happycomp.weatherforecast.model.room.data.FavoritesDao
import com.happycomp.weatherforecast.model.room.data.WeatherDataBase
import com.happycomp.weatherforecast.model.room.repo.FavoriteRepo
import com.happycomp.weatherforecast.model.extra.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext application: Context): WeatherDataBase = Room
        .databaseBuilder(
            application, WeatherDataBase::class.java,
            Constants.WEATHER_DB
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideFavoriteRepo(
        weatherInterface: WeatherInterface,
        favoritesDao: FavoritesDao
    ): FavoriteActions = FavoriteRepo(weatherInterface, favoritesDao)

    @Provides
    @Singleton
    fun provideFavoriteDao(weatherDataBase: WeatherDataBase): FavoritesDao =
        weatherDataBase.favoritesDao

    @Provides
    @Singleton
    fun provideAlarmDao(weatherDataBase: WeatherDataBase): AlarmDao = weatherDataBase.alarmDao

    @Provides
    @Singleton
    fun weatherInterface(): WeatherInterface = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherInterface::class.java)

}