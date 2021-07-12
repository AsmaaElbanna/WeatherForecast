package com.happycomp.weatherforecast.di

import android.content.Context
import androidx.room.Room
import com.happycomp.weatherforecast.model.retrofit.WeatherInterface
import com.happycomp.weatherforecast.model.room.FavoritesDao
import com.happycomp.weatherforecast.model.room.WeatherDataBase
import com.happycomp.weatherforecast.util.Constants
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
        .databaseBuilder(application, WeatherDataBase::class.java,
            Constants.WEATHER_DB).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideFavoriteDao(weatherDataBase: WeatherDataBase): FavoritesDao = weatherDataBase.favoritesDao

    @Provides
    @Singleton
    fun weatherInterface(): WeatherInterface = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherInterface::class.java)
}