package com.happycomp.weatherforecast.model.room.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.happycomp.weatherforecast.model.pojo.Alarm

@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAlarm(alarm: Alarm)

    @Delete
    suspend fun deleteAlarm(alarm: Alarm)

    @Query("DELETE FROM alarm WHERE id =:ID")
    suspend fun deleteByID(ID: Int)

    @Transaction
    @Query("SELECT * FROM alarm ORDER BY id")
    fun observeAllAlarms(): LiveData<List<Alarm>>

    @Query("SELECT * FROM alarm WHERE id =:ID")
    fun getAlarmByID(ID: Int): Alarm
}