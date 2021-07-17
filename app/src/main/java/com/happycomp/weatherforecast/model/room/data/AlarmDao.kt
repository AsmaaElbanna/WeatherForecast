package com.happycomp.weatherforecast.model.room.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.happycomp.weatherforecast.model.pojo.Alarm
import java.util.*

@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAlarm(alarm: Alarm)

    @Delete
    suspend fun deleteAlarm(alarm: Alarm)

    @Query("DELETE FROM alarm WHERE timeMS < :currentTime")
    suspend fun deleteOldAlarms(currentTime: Long = Calendar.getInstance().timeInMillis)

    @Transaction
    @Query("SELECT * FROM alarm ORDER BY id")
    fun observeAllAlarms(): LiveData<List<Alarm>>
}