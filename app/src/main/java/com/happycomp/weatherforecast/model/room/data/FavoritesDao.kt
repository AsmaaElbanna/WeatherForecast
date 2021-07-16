package com.happycomp.weatherforecast.model.room.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.happycomp.weatherforecast.model.pojo.BaseWeather

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(baseWeather: BaseWeather)

    @Delete
    suspend fun deleteFavorite(baseWeather: BaseWeather)

    @Update
    suspend fun updateFavorite(baseWeather: BaseWeather)

    @Transaction
    @Query("SELECT * FROM favorites")
    fun observeAllFavorites(): LiveData<List<BaseWeather>>
}