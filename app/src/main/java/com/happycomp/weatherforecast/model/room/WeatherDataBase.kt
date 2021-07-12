package com.happycomp.weatherforecast.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.happycomp.weatherforecast.model.pojo.Alarm
import com.happycomp.weatherforecast.model.pojo.BaseWeather

@Database(entities = [BaseWeather::class, Alarm::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WeatherDataBase: RoomDatabase() {
    abstract val favoritesDao: FavoritesDao
    abstract val alarmDao: AlarmDao
}