package com.happycomp.weatherforecast.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.happycomp.weatherforecast.model.pojo.BaseWeather

@Database(entities = [BaseWeather::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WeatherDataBase: RoomDatabase() {
    abstract val favoritesDao: FavoritesDao
}