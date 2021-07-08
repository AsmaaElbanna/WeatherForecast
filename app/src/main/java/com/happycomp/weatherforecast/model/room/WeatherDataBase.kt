package com.happycomp.weatherforecast.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.happycomp.weatherforecast.model.pojo.BaseWeather

@Database(entities = [BaseWeather::class], version = 2)
@TypeConverters(Converters::class)
abstract class WeatherDataBase: RoomDatabase() {
    abstract val favoritesDao: FavoritesDao

    companion object {
        @Volatile
        private var instance: WeatherDataBase? = null

        fun getDatabase(context: Context): WeatherDataBase {
            synchronized(this){
                return instance?: Room.databaseBuilder(context.applicationContext,
                    WeatherDataBase::class.java,
                "weatherDB").fallbackToDestructiveMigration().build().also {
                    instance = it
                }
            }
        }
    }
}