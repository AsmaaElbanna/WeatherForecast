package com.happycomp.weatherforecast.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.happycomp.weatherforecast.model.pojo.BaseWeather

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(baseWeather: BaseWeather)

    @Transaction
    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): LiveData<List<BaseWeather>>
}